package com.sap.opensap;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationsRequestContextListener;
import com.sap.cloud.sdk.cloudplatform.servlet.RequestContextServletFilter;
import com.sap.cloud.sdk.cloudplatform.tenant.TenantRequestContextListener;
import com.sap.cloud.sdk.frameworks.cxf.jaxrs.JaxRsExceptionMapper;

public class TestUtil
{
    public static WebArchive createDeployment(final Class<?>... classesUnderTest )
    {
        return ShrinkWrap
            .create(WebArchive.class)
            .addClasses(classesUnderTest)
            .addClass(JacksonJsonProvider.class)
            .addClass(JaxRsExceptionMapper.class)
            .addClass(RequestContextServletFilter.class)
            .addClass(TenantRequestContextListener.class)
            .addClass(DestinationsRequestContextListener.class)
            .addAsManifestResource("arquillian.xml");
    }
}
