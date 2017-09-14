package com.sap.cloud.sdk.tutorial.command;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;

import java.util.List;

import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQuery;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryBuilder;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryResult;
import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.connectivity.exception.QueryExecutionException;
import com.sap.cloud.sdk.tutorial.models.CostCenterDetails;


public class GetCostCenterCommand extends CachingErpCommand<List<CostCenterDetails>> {
    private static final Logger logger = CloudLoggerFactory.getLogger(GetCostCenterCommand.class);

    public GetCostCenterCommand(final ErpConfigContext configContext) {
        super(GetCostCenterCommand.class, configContext);
    }

    private static final Cache<CacheKey, List<CostCenterDetails>> cache =
            CacheBuilder.newBuilder().concurrencyLevel(10).build();

    @Override
    protected Cache<CacheKey, List<CostCenterDetails>> getCache() {
        return cache;
    }

    @Override
    protected CacheKey getCommandCacheKey() {
        return super.getCommandCacheKey().append(getConfigContext());
    }

    @Override
    protected List<CostCenterDetails> runCacheable() throws QueryExecutionException {
        try {
            final ODataQuery query = ODataQueryBuilder
                .withEntity("/sap/opu/odata/sap/FCO_PI_COST_CENTER", "CostCenterCollection")
                .select("CostCenterID",
                        "CostCenterDescription",
                        "Status",
                        "CompanyCode",
                        "Category",
                        "ValidityStartDate",
                        "ValidityEndDate")
                .build();

            final ODataQueryResult result = query.execute(getErpEndpoint());
            return result.asList(CostCenterDetails.class);
        } catch (final Exception e) {
            throw new QueryExecutionException("Failed to get CostCenters from OData by using cached command.", e);
        }
    }

    public void resetCache() {
        cache.invalidate(getCommandCacheKey());
    }
}
