package com.sap.cloud.s4hana.tutorial;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.sap.cloud.s4hana.tutorial.GetCostCentersCommand;
import com.sap.cloud.sdk.cloudplatform.servlet.Executable;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.connectivity.ErpDestination;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.readcostcenterdata.CostCenter;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultReadCostCenterDataService;
import com.sap.cloud.sdk.s4hana.serialization.SapClient;
import com.sap.cloud.sdk.testutil.MockUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCostCentersCommandTest
{

    private static final MockUtil mockUtil = new MockUtil();

    @BeforeClass
    public static void beforeClass()
    {
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    private List<CostCenter> getCostCenters( final String destination, final SapClient sapClient )
    {
        final ErpConfigContext configContext = new ErpConfigContext(destination, sapClient, Locale.ENGLISH);

        return new GetCostCentersCommand(new DefaultReadCostCenterDataService(), configContext).execute();
    }

    @Test
    public void testWithSuccess()
        throws Exception
    {
        mockUtil.requestContextExecutor().execute(new Executable()
        {
            @Override
            public void execute()
                throws Exception
            {
                assertThat(getCostCenters(ErpDestination.getDefaultName(), mockUtil.getErpSystem().getSapClient()))
                    .isNotEmpty();
            }
        });
    }

    @Test
    public void testWithFallback()
        throws Exception
    {
        mockUtil.requestContextExecutor().execute(new Executable()
        {
            @Override
            public void execute()
                throws Exception
            {
                assertThat(getCostCenters("NoErpSystem", mockUtil.getErpSystem().getSapClient()))
                    .isEqualTo(Collections.emptyList());
            }
        });
    }

}
