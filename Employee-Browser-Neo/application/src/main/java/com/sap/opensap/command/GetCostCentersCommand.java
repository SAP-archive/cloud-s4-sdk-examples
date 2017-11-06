package com.sap.opensap.command;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.NonNull;

import java.util.List;

import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.ExpressionFluentHelper;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.ReadCostCenterDataNamespace.CostCenter;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.ReadCostCenterDataService;

public class GetCostCentersCommand extends CachingErpCommand<List<CostCenter>> // Circuit Breaker + Bulkhead Pattern
{
    private static final Cache<CacheKey, List<CostCenter>> cache =
            CacheBuilder.newBuilder().concurrencyLevel(10).build();

    private final String companyCode;

    public GetCostCentersCommand(@NonNull final ErpConfigContext configContext, final String companyCode )
    {
        super(GetCostCentersCommand.class, configContext);
        this.companyCode = companyCode;
    }

    @Override
    protected Cache<CacheKey, List<CostCenter>> getCache()
    {
        return cache;
    }

    @Override
    protected CacheKey getCommandCacheKey()
    {
        return super.getCommandCacheKey().append(companyCode);
    }

    @Override
    protected List<CostCenter> runCacheable()
            throws ODataException
    {
        ExpressionFluentHelper<CostCenter> filter = companyCode == null
                ? CostCenter.COMPANY_CODE.ne("")
                : CostCenter.COMPANY_CODE.eq(companyCode);

        return ReadCostCenterDataService
                .getAllCostCenter()
                .select(CostCenter.COST_CENTER_I_D, CostCenter.COMPANY_CODE, CostCenter.COST_CENTER_DESCRIPTION)
                .filter(filter)
                .execute(getConfigContext());
    }
}
