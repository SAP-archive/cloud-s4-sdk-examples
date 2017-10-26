package com.sap.cloud.sdk.tutorial.command;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.List;

import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.connectivity.exception.QueryExecutionException;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.ReadCostCenterDataNamespace.CostCenter;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.ReadCostCenterDataService;


public class GetCostCenterCommand extends CachingErpCommand<List<CostCenter>> {
    public GetCostCenterCommand(final ErpConfigContext configContext) {
        super(GetCostCenterCommand.class, configContext);
    }

    private static final Cache<CacheKey, List<CostCenter>> cache =
            CacheBuilder.newBuilder().concurrencyLevel(10).build();

    @Override
    protected Cache<CacheKey, List<CostCenter>> getCache() {
        return cache;
    }

    @Override
    protected CacheKey getCommandCacheKey() {
        return super.getCommandCacheKey().append(getConfigContext());
    }

    @Override
    protected List<CostCenter> runCacheable() throws QueryExecutionException {
        try {
            return ReadCostCenterDataService.getAllCostCenter().select(
                    CostCenter.COST_CENTER_I_D,
                    CostCenter.COST_CENTER_DESCRIPTION,
                    CostCenter.STATUS,
                    CostCenter.COMPANY_CODE,
                    CostCenter.CATEGORY,
                    CostCenter.VALIDITY_START_DATE,
                    CostCenter.VALIDITY_END_DATE
            ).execute(getConfigContext());
        } catch (final Exception e) {
            throw new QueryExecutionException("Failed to get CostCenters from OData by using cached command.", e);
        }
    }

    public void resetCache() {
        cache.invalidate(getCommandCacheKey());
    }
}
