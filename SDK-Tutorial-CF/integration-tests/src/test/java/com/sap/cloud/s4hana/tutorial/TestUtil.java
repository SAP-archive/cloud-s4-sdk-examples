package com.sap.cloud.s4hana.tutorial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.internal.mapper.ObjectMapperType;

import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationsRequestContextListener;
import com.sap.cloud.sdk.cloudplatform.servlet.RequestContextServletFilter;
import com.sap.cloud.sdk.cloudplatform.tenant.TenantRequestContextListener;
import com.sap.cloud.sdk.cloudplatform.exception.ShouldNotHappenException;

public class TestUtil
{
    public static WebArchive createDeployment( final Class<?>... classesUnderTest )
    {
        return ShrinkWrap
            .create(WebArchive.class)
            .addClasses(classesUnderTest)
            .addClass(RequestContextServletFilter.class)
            .addClass(TenantRequestContextListener.class)
            .addClass(DestinationsRequestContextListener.class)
            .addAsManifestResource("arquillian.xml")
            .addAsWebInfResource(new ByteArrayAsset("<beans/>".getBytes()), ArchivePaths.create("beans.xml"));
    }

    public static ObjectMapperType objectMapperType()
    {
        return ObjectMapperType.JACKSON_2;
    }

    public static String toJson( final Object obj )
    {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch( final JsonProcessingException e ) {
            throw new ShouldNotHappenException(e);
        }
    }
}
