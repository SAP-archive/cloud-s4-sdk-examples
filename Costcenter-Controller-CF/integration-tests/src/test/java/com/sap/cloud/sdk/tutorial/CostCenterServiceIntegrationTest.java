package com.sap.cloud.sdk.tutorial;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
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

import java.util.Map;

import com.sap.cloud.sdk.cloudplatform.servlet.Executable;
import com.sap.cloud.sdk.s4hana.serialization.SapClient;
import com.sap.cloud.sdk.testutil.MockUtil;
import com.sap.cloud.sdk.tutorial.controllers.CostCenterController;

@RunWith(SpringRunner.class)
@WebMvcTest(CostCenterController.class)
public class CostCenterServiceIntegrationTest
{
    private static final String EXISTING_COSTCENTER_ID = "0010101101";

    private static final String DEMO_COSTCENTER_ID = generateId(); // 10 chars
    private static final String DEMO_CONTROLLING_AREA = "A000";
    private static final LocalDate DEMO_VALID_FROM = new LocalDate(2016,4,1);
    private static final LocalDate DEMO_VALID_TO = new LocalDate(2016,5,1);

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

    private static String generateId() {
        return "T"
                + StringUtils.leftPad(
                Long.toString(new DateTime().minus(Period.years(45)).getMillis(), 36).toUpperCase(),
                9,
                '0');
    }

    private String getNewCostCenterAsJson(final String costCenterId) {
        final Map<String, Object> values = Maps.newHashMap();
        values.put("costcenter", costCenterId);
        values.put("name", costCenterId);
        values.put("personInCharge", "USER");
        values.put("validFrom", DEMO_VALID_FROM.toDateTimeAtStartOfDay().getMillis());
        values.put("validTo", DEMO_VALID_TO.toDateTimeAtStartOfDay().getMillis());
        values.put("costcenterType", COST_CENTER_CATEGORY);
        values.put("costctrHierGrp", COST_CENTER_GROUP);
        values.put("compCode", COMPANY_CODE);
        values.put("profitCtr", PROFIT_CENTER);
        values.put("description", "demo");
        return new Gson().toJson(values);
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
        // {"costcenter":"012346578","description":"demo","validFrom":...}

        // cost center already exists in database
        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                mockMvc
                    .perform(MockMvcRequestBuilders
                        .request(HttpMethod.POST, "/api/v1/rest/client/"+sapClient+"/controllingarea/"+DEMO_CONTROLLING_AREA+"/costcenters")
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
                .request(HttpMethod.POST, "/api/v1/rest/client/"+sapClient+"/controllingarea/"+DEMO_CONTROLLING_AREA+"/costcenters")
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
