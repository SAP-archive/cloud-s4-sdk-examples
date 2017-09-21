package com.sap.opensap.command;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.NonNull;

import java.util.List;

import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataProperty;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryBuilder;
import com.sap.cloud.sdk.odatav2.connectivity.ODataType;
import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.opensap.datamodel.s4.CostCenterDetails;

public class GetCostCentersCommand extends CachingErpCommand<List<CostCenterDetails>> // Circuit Breaker + Bulkhead Pattern
{
    private static final Cache<CacheKey, List<CostCenterDetails>> cache =
            CacheBuilder.newBuilder().concurrencyLevel(10).build();

    private final String companyCode;

    public GetCostCentersCommand(@NonNull final ErpConfigContext configContext, final String companyCode )
    {
        super(GetCostCentersCommand.class, configContext);
        this.companyCode = companyCode;
    }

    @Override
    protected Cache<CacheKey, List<CostCenterDetails>> getCache()
    {
        return cache;
    }

    @Override
    protected CacheKey getCommandCacheKey()
    {
        return super.getCommandCacheKey().append(companyCode);
    }

    @Override
    protected List<CostCenterDetails> runCacheable()
            throws ODataException
    {
        ODataQueryBuilder builder =
            ODataQueryBuilder.withEntity("/sap/opu/odata/sap/FCO_PI_COST_CENTER", "CostCenterCollection")
                    .select("CostCenterID", "CompanyCode", "CostCenterDescription");

        if( companyCode != null ) {
            builder.filter(ODataProperty.field("CompanyCode").eq(ODataType.of(companyCode)));
        }

        List<CostCenterDetails> costCenters = builder.build().execute(getErpEndpoint()).asList(CostCenterDetails.class);
        return costCenters;
    }
}
