package com.sap.cloud.s4hana.tutorial;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataExceptionType;
import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.readcostcenterdata.CostCenter;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultReadCostCenterDataService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.ReadCostCenterDataService;

import lombok.NonNull;

public class GetCostCentersByCompanyCodeCommand extends CachingErpCommand<List<CostCenter>>
{
    @NonNull
    private final String companyCode;

    public GetCostCentersByCompanyCodeCommand(
        @NonNull final ErpConfigContext configContext,
        @NonNull final String companyCode )
    {
        super(GetCostCentersByCompanyCodeCommand.class, configContext);
        this.companyCode = companyCode;
    }

    private static final Cache<CacheKey, List<CostCenter>> cache =
        CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(60, TimeUnit.SECONDS).concurrencyLevel(10).build();

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
        try {
            final List<CostCenter> costCenters =
                new DefaultReadCostCenterDataService()
                    .getAllCostCenter()
                    .select(
                        CostCenter.COST_CENTER_ID,
                        CostCenter.STATUS,
                        CostCenter.COMPANY_CODE,
                        CostCenter.CATEGORY,
                        CostCenter.COST_CENTER_DESCRIPTION)
                    .filter(CostCenter.COMPANY_CODE.eq(companyCode))
                    .execute(getConfigContext());

            return costCenters;
        }
        catch( final Exception e ) {
            throw new ODataException(ODataExceptionType.OTHER, "Failed to get CostCenters from OData command.", e);
        }
    }

    @Override
    protected List<CostCenter> getFallback()
    {
        return Collections.emptyList();
    }
}
