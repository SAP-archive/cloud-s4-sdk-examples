package com.sap.opensap;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.HttpClientConfig;
import com.jayway.restassured.path.json.JsonPath;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;
import java.util.List;

import com.sap.cloud.sdk.testutil.MockUtil;
import com.sap.opensap.service.CompanyCodesService;
import com.sap.opensap.service.EmployeeBrowserService;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class CostCenterEmployeesServiceTest {
    private static final MockUtil mockUtil = new MockUtil();

    @ArquillianResource
    private URL baseUrl;

    @Deployment
    public static WebArchive createDeployment() {
        return TestUtil.createDeployment(CompanyCodesService.class, EmployeeBrowserService.class);
    }

    @BeforeClass
    public static void beforeClass() {
        mockUtil.mockDefaults();
    }

    @Before
    public void before() {
        RestAssured.config().httpClient(HttpClientConfig.httpClientConfig().setParam("CONNECTION_MANAGER_TIMEOUT", 3600*1000));
        RestAssured.baseURI = baseUrl.toExternalForm();

        mockUtil.mockErpDestination(mockUtil.getErpSystem());
        mockUtil.mockDestination("SuccessFactorsODataEndpoint", "sfsf");
    }

    @Test
    public void testServiceEmployeesFromAllCompanyCodes() {
        final List<String> companyCodeList = given().get("/costcenter-employees").body().jsonPath().getList("companyCode");
        assertThat(companyCodeList).isNotEmpty().contains("1110");
    }

    @Test
    public void testServiceCompanyCodes() {
        final List<String> companyCodeList = given().get("/companycodes").body().jsonPath().getList("");
        assertThat(companyCodeList).isNotEmpty();
    }

    @Test
    public void testServiceEmployeesFromDefinedCompanyCode() {
        final String request1110 = "/costcenter-employees?companyCode=1110";
        final JsonPath responsePath = given().get(request1110).body().jsonPath();
        assertThat(responsePath.getList("costCenter")).isNotEmpty();
        assertThat(responsePath.getList("companyCode")).isNotEmpty().containsOnly("1110");
    }
}
