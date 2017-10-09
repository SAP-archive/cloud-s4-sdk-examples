package com.sap.cloud.s4hana.tutorial;

import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

public class GetBusinessPartnersCommand extends CachingErpCommand<List<BusinessPartner>>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(GetBusinessPartnersCommand.class);
    
    private Cache<CacheKey, List<BusinessPartner>> cache = CacheBuilder.newBuilder().build();

    protected GetBusinessPartnersCommand( final ErpConfigContext configContext )
    {
        super(
            HystrixUtil
                .getDefaultErpCommandSetter(
                    GetBusinessPartnersCommand.class,
                    HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(5000))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(20)),
            configContext);
    }

    @Override
    protected Cache<CacheKey, List<BusinessPartner>> getCache()
    {
        return cache;
    }

    @Override
    protected List<BusinessPartner> runCacheable()
        throws Exception
    {
        return new DefaultBusinessPartnerService().getAllBusinessPartner()
            .select(BusinessPartner.BUSINESS_PARTNER,
                BusinessPartner.BUSINESS_PARTNER_NAME)
            .filter(BusinessPartner.BUSINESS_PARTNER_CATEGORY.eq("2"))
            .execute(getConfigContext());
    }

    @Override
    protected List<BusinessPartner> getFallback() {
        return Collections.emptyList();
    }
}