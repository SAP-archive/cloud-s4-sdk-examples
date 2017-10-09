package com.sap.cloud.s4hana.tutorial;

import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.BusinessPartnerNamespace.BusinessPartner;

public class GetBusinessPartnersCommand extends ErpCommand<List<BusinessPartner>>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(GetBusinessPartnersCommand.class);

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
    protected List<BusinessPartner> run()
        throws Exception
    {
        // TODO: implement
        return null;
    }

    @Override
    protected List<BusinessPartner> getFallback() {
        return Collections.emptyList();
    }
}