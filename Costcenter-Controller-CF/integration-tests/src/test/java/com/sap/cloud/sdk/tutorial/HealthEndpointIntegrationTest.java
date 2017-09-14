package com.sap.cloud.sdk.tutorial;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URISyntaxException;

import com.sap.cloud.sdk.cloudplatform.logging.JmxLoggerFactory;
import com.sap.cloud.sdk.cloudplatform.servlet.Executable;
import com.sap.cloud.sdk.testutil.MockUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HealthEndpointIntegrationTest
{
    private static final MockUtil mockSdk = new MockUtil();

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @BeforeClass
    public static void beforeClass() throws URISyntaxException {
        mockSdk.mockDefaults();
        mockSdk.mockErpDestination();
    }

    @Test
    public void testHealthEndpoint() throws Exception {
        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                final ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/health"));
                action.andExpect(MockMvcResultMatchers.status().isOk());
            }
        });
    }

    @Test
    public void testStaticFile() throws Exception {
        mockSdk.requestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                final ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/index.html"));
                action.andExpect(MockMvcResultMatchers.status().isOk());
            }
        });
    }
}
