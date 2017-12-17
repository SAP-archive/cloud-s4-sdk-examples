package com.mycompany;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mycompany.models.CostCenterForecast;
import com.mycompany.models.CostCenterRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sap.cloud.sdk.cloudplatform.servlet.Executable;
import com.sap.cloud.sdk.testutil.MockUtil;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CostCenterServiceIntegrationTest
{
    private static final String COSTCENTER_ID_1 = "name1";
    private static final String COSTCENTER_ID_2 = "name2";
    private static final String TENANT_ID_1 = "tenant1";
    private static final String TENANT_ID_2 = "tenant2";

    private static final MockUtil mockSdk = new MockUtil();
    public static final double FORECAST = 50.0;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CostCenterRepository costCenterRepository;


    @BeforeClass
    public static void beforeClass() {
        mockSdk.mockDefaults();
    }

    @Before
    public void before() {
        mockSdk.mockCurrentTenant(TENANT_ID_1);
    }

    @Test
    public void testHttpGet() throws Exception {
        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                ResultActions action = mockMvc.perform(MockMvcRequestBuilders
                        .put("/callback/tenant/" + TENANT_ID_1));
                action.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

                action = mockMvc.perform(MockMvcRequestBuilders
                        .get("/cost-center"));
                action.andExpect(MockMvcResultMatchers.status().isOk());

                action = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/callback/tenant/" + TENANT_ID_1));
                action.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
            }
        });
    }

    @Test
    public void testHttpPost() throws Exception {
        final String newCostCenterJson = buildCostCenterJson(COSTCENTER_ID_1);
        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                ResultActions action = mockMvc.perform(MockMvcRequestBuilders
                        .put("/callback/tenant/" + TENANT_ID_1));
                action.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

                action = mockMvc
                        .perform(MockMvcRequestBuilders
                                .request(HttpMethod.POST, "/cost-center")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(newCostCenterJson));
                action.andExpect(MockMvcResultMatchers.status().isOk());

                action = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/callback/tenant/" + TENANT_ID_1));
                action.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
            }
        });
    }

    @Test
    public void testWithTwoTenants() throws Exception {

        // onboard and create data
        mockSdk.mockCurrentTenant(TENANT_ID_1);
        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                onboardTenant(TENANT_ID_1);
                createDataInTenant(COSTCENTER_ID_1);
            }
        });

        mockSdk.mockCurrentTenant(TENANT_ID_2);
        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                onboardTenant(TENANT_ID_2);
                createDataInTenant(COSTCENTER_ID_2);
            }
        });

        // read and validate data
        mockSdk.mockCurrentTenant(TENANT_ID_1);
        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                readAndValidateDataInTenant(COSTCENTER_ID_1);
            }
        });

        mockSdk.mockCurrentTenant(TENANT_ID_2);
        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                readAndValidateDataInTenant(COSTCENTER_ID_2);
            }
        });

        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                offboardTenant(TENANT_ID_1);
                offboardTenant(TENANT_ID_2);
            }
        });
    }

    private void offboardTenant(final String tenant) throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders
                .delete("/callback/tenant/" + tenant));
        action.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    private void readAndValidateDataInTenant(final String costCenter) throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders
                .get("/cost-center"));

        action.andExpect(MockMvcResultMatchers.status().isOk());
        final String result = action.andReturn().getResponse().getContentAsString();
        final String expected = new Gson().toJson(Lists.newArrayList(new CostCenterForecast(costCenter, FORECAST)));

        Assert.assertEquals(expected, result);
    }

    private void createDataInTenant(String costCenter) throws Exception {
        final String newCostCenterJson = buildCostCenterJson(costCenter);
        ResultActions action = mockMvc
                .perform(MockMvcRequestBuilders
                        .request(HttpMethod.POST, "/cost-center")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(newCostCenterJson));
        action.andExpect(MockMvcResultMatchers.status().isOk());
    }

    private void onboardTenant(String tenant) throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders
                .put("/callback/tenant/" + tenant));
        action.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    private String buildCostCenterJson(String costCenterName) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(new CostCenterForecast()
                .setName(costCenterName).setForecast(FORECAST));
    }
}
