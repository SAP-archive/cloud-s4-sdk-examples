package com.sap.cloud.sdk.tutorial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sap.cloud.sdk.cloudplatform.servlet.Executable;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CompanyCode;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.ControllingArea;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CostCenter;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.ProfitCenter;
import com.sap.cloud.sdk.s4hana.serialization.SapClient;
import com.sap.cloud.sdk.testutil.MockUtil;
import com.sap.cloud.sdk.tutorial.controllers.CostCenterController;
import com.sap.cloud.sdk.tutorial.models.CostCenterDetails;

@RunWith(SpringRunner.class)
@WebMvcTest(CostCenterController.class)
public class CostCenterServiceIntegrationTest
{
    private static final String EXISTING_COSTCENTER_ID = "0010101101";

    private static final String DEMO_COSTCENTER_ID = generateId(); // 10 chars
    private static final String DEMO_CONTROLLING_AREA = "A000";
    private static final String DEMO_VALID_FROM = CostCenterDetails.asDateString("2016-04-01");
    private static final String DEMO_VALID_TO = CostCenterDetails.asDateString("2016-05-01");

    private static final MockUtil mockSdk = new MockUtil();

    // Update these parameters for your S/4HANA demo data
    public static final String PROFIT_CENTER = "YB101";
    public static final String COST_CENTER_GROUP = "0001";
    public static final String COST_CENTER_CATEGORY = "E";
    public static final String COMPANY_CODE = "1010";

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void beforeClass() {
        mockSdk.mockDefaults();
        mockSdk.mockErpDestination();
    }

    private static String generateId()
    {
        return "T"
                + StringUtils.leftPad(
                Long.toString(new DateTime().minus(Period.years(45)).getMillis(), 36).toUpperCase(),
                9,
                '0');
    }

    private String getNewCostCenterAsJson(final String costCenterId) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(new CostCenterDetails()
                .setId(new CostCenter(costCenterId))
                .setPersonResponsible("USER")
                .setControllingArea(new ControllingArea(DEMO_CONTROLLING_AREA))
                .setValidFrom(DEMO_VALID_FROM)
                .setValidTo(DEMO_VALID_TO)
                .setCostCenterGroup(COST_CENTER_GROUP)
                .setCategory(COST_CENTER_CATEGORY)
                .setCompanyCode(new CompanyCode(COMPANY_CODE))
                .setProfitCenter(new ProfitCenter(PROFIT_CENTER))
                .setDescription("hello meeting"));
    }


    @Test
    public void testHttpGet() throws Exception {
        final SapClient sapClient = mockSdk.getErpSystem().getSapClient();

        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                final ResultActions action = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/rest/client/"+sapClient+"/costcenters"));
                action.andExpect(MockMvcResultMatchers.status().isOk());
            }
        });
    }

    @Test
    public void testHttpAddFailure() throws Exception {
        final SapClient sapClient = mockSdk.getErpSystem().getSapClient();

        final String newCostCenterJson = getNewCostCenterAsJson(EXISTING_COSTCENTER_ID);
        // {"sapClient":"715","controllingArea":"A000","validFrom":...}

        // cost center already exists in database
        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                mockMvc
                    .perform(MockMvcRequestBuilders
                        .request(HttpMethod.POST, "/api/v1/rest/client/"+sapClient+"/costcenters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(newCostCenterJson))
                    .andExpect(MockMvcResultMatchers
                        .status()
                        .is5xxServerError());
            }
        });
    }



    @Test
    public void testHttpAddSuccess() throws Exception {
        final SapClient sapClient = mockSdk.getErpSystem().getSapClient();

        final String newCostCenterJson = getNewCostCenterAsJson(DEMO_COSTCENTER_ID);

        final RequestBuilder newCostCenterRequest = MockMvcRequestBuilders
                .request(HttpMethod.POST, "/api/v1/rest/client/"+sapClient+"/costcenters")
                .param("testRun", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(newCostCenterJson);

        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                mockMvc.perform(newCostCenterRequest).andExpect(MockMvcResultMatchers.status().isOk());
            }
        });
    }

}
