package com.sap.opensap.command;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import lombok.NonNull;

import java.util.Set;

import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryBuilder;
import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;

public class GetCompanyCodesCommand extends CachingErpCommand<Set<String>> // Circuit Breaker + Bulkhead Pattern
{
    private static final Cache<CacheKey, Set<String>> cache =
            CacheBuilder.newBuilder().concurrencyLevel(10).build();

    public GetCompanyCodesCommand(@NonNull final ErpConfigContext configContext)
    {
        super(GetCompanyCodesCommand.class, configContext);
    }

    @Override
    protected Cache<CacheKey, Set<String>> getCache()
    {
        return cache;
    }

    @Override
    protected Set<String> runCacheable()
            throws ODataException
    {
        return ODataQueryBuilder
                .withEntity("/sap/opu/odata/sap/FCO_PI_COST_CENTER", "CostCenterCollection")
                .select("CompanyCode")
                .build()
                .execute(getErpEndpoint())
                .collect("CompanyCode")
                .asStringSet();
    }

    @Override
    protected Set<String> getFallback() {
        return Sets.newHashSet();
    }
}
