package com.sap.cloud.s4hana.tutorial;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;

import com.sap.cloud.sdk.cloudplatform.connectivity.ProxyConfiguration;
import com.sap.cloud.sdk.s4hana.connectivity.ErpEdition;
import com.sap.cloud.sdk.s4hana.connectivity.ErpRelease;
import com.sap.cloud.sdk.s4hana.serialization.SapClient;
import com.sap.cloud.sdk.testutil.ErpSystem;
import com.sap.cloud.sdk.testutil.MockUtil;
import com.sap.cloud.sdk.testutil.TestSystem;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@RunWith( Arquillian.class )
public class BusinessPartnerServiceTest
{
    private static final MockUtil mockUtil = new MockUtil();
    private static final Logger logger = LoggerFactory.getLogger(BusinessPartnerServiceTest.class);

    @ArquillianResource
    private URL baseUrl;

    @Deployment
    public static WebArchive createDeployment()
    {
        return TestUtil.createDeployment(BusinessPartnerServlet.class);
    }

    @BeforeClass
    public static void beforeClass() throws URISyntaxException
    {
        mockUtil.mockDefaults();
        mockUtil.withProxy(new ProxyConfiguration(new URI("http://proxy.wdf.sap.corp:8080")));
        mockUtil.mockErpDestination();
    }

    @Before
    public void before()
    {
        RestAssured.baseURI = baseUrl.toExternalForm();
    }

    @Test
    public void testService()
    {
        // HTTP GET response OK, JSON header and valid content
        when().
        	get("/businesspartners").
        then().
        	statusCode(200).
            contentType(ContentType.JSON).
            body("$", hasSize(greaterThan(0))).
            body("[0].businessPartner", not(isEmptyOrNullString()));
    }
}