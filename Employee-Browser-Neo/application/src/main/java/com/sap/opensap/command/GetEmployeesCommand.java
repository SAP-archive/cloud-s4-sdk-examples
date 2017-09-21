package com.sap.opensap.command;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.List;

import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.frameworks.hystrix.CachingCommand;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryBuilder;
import com.sap.opensap.datamodel.successfactors.EmployeeDetails;

public class GetEmployeesCommand extends CachingCommand<List<EmployeeDetails>>
{
    private static final Cache<CacheKey, List<EmployeeDetails>> cache =
            CacheBuilder.newBuilder().concurrencyLevel(10).build();

    public GetEmployeesCommand()
    {
        super(
            HystrixUtil.getDefaultErpCommandSetter(
                GetEmployeesCommand.class,
                HystrixUtil.getDefaultErpCommandProperties()));
    }

    // Caching
    @Override
    protected Cache<CacheKey, List<EmployeeDetails>> getCache()
    {
        return cache;
    }

    @Override
    protected List<EmployeeDetails> runCacheable()
            throws ODataException
    {
        // OData Consumption
        return ODataQueryBuilder
                .withEntity("/odata/v2", "PerPerson")
                .expand("employmentNav/jobInfoNav")
                .select(
                        "personIdExternal",
                        "employmentNav/jobInfoNav/costCenter",
                        "employmentNav/jobInfoNav/department",
                        "employmentNav/jobInfoNav/division",
                        "employmentNav/jobInfoNav/employmentType",
                        "employmentNav/jobInfoNav/jobTitle",
                        "employmentNav/jobInfoNav/payGrade")
                .withoutMetadata()
                .build()
                .execute("SuccessFactorsODataEndpoint")
                .asList(EmployeeDetails.class);
    }
}
