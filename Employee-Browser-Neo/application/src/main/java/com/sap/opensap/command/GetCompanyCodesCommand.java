package com.sap.opensap.command;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.ReadCostCenterDataNamespace.CostCenter;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.ReadCostCenterDataService;

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
        Set<String> companyCodes = Sets.newTreeSet();
        List<CostCenter> result = ReadCostCenterDataService
                .getAllCostCenter()
                .select(CostCenter.COMPANY_CODE)
                .execute(getConfigContext());

        for (CostCenter costCenter : result) {
            companyCodes.add(costCenter.getCompanyCode());
        }
        return companyCodes;
    }

    @Override
    protected Set<String> getFallback() {
        return Sets.newHashSet();
    }
}
