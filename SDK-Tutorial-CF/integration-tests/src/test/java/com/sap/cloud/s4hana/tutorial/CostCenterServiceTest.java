package com.sap.cloud.s4hana.tutorial;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import java.net.URL;
import java.net.URISyntaxException;

import com.sap.cloud.s4hana.tutorial.CostCenterServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.services.DefaultCostCenterService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultReadCostCenterDataService;
import com.sap.cloud.sdk.testutil.MockUtil;

import static com.jayway.restassured.RestAssured.given;

@RunWith( Arquillian.class )
public class CostCenterServiceTest
{
    private static final MockUtil mockUtil = new MockUtil();
    private static final Logger logger = CloudLoggerFactory.getLogger(CostCenterServiceTest.class);

    @ArquillianResource
    private URL baseUrl;

    @Deployment
    public static WebArchive createDeployment()
    {
        return TestUtil.createDeployment(
            CostCenterServlet.class,
            DefaultReadCostCenterDataService.class,
            DefaultCostCenterService.class);
    }

    @BeforeClass
    public static void beforeClass()
        throws URISyntaxException
    {
        mockUtil.mockDefaults();
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
        // JSON schema validation from resource definition
        final JsonSchemaValidator jsonValidator =
            JsonSchemaValidator.matchesJsonSchemaInClasspath("costcenters-schema.json");

        // HTTP GET response OK, JSON header and valid schema
        given().get("/costcenters").then().assertThat().statusCode(200).contentType(ContentType.JSON).body(
            jsonValidator);
    }
}
