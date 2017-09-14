package com.sap.cloud.sdk.tutorial.command;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryBuilder;
import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.tutorial.models.CostCenterDetails;

public class HealthCheckCommand extends CachingErpCommand<Boolean>
{
    private static final Cache<CacheKey, Boolean> cache =
            CacheBuilder.newBuilder()
                    .concurrencyLevel(10)
                    .expireAfterAccess(1, TimeUnit.MINUTES)
                    .build();

    public HealthCheckCommand( final ErpConfigContext configContext ) {
        super(HealthCheckCommand.class, configContext);
    }

    @Override
    protected Cache<CacheKey, Boolean> getCache() {
        return cache;
    }

    @Override
    protected Boolean runCacheable() throws Exception {
        return !ODataQueryBuilder
                .withEntity("/sap/opu/odata/sap/FCO_PI_COST_CENTER", "CostCenterCollection")
                .select("CostCenterID")
                .top(1)
                .build()
                .execute(getConfigContext())
                .asList(CostCenterDetails.class)
                .isEmpty();
    }

    @Override
    protected Boolean getFallback() {
        return false;
    }
}
