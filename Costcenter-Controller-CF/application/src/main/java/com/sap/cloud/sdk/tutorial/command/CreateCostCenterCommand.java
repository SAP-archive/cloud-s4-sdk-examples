package com.sap.cloud.sdk.tutorial.command;

import com.netflix.config.ConfigurationManager;

import java.util.List;

import com.sap.cloud.sdk.s4hana.config.S4HanaConfig;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.services.CostCenterService;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.structures.CostCenterCreateInput;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.structures.ReturnParameter;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.ControllingArea;
import com.sap.cloud.sdk.s4hana.serialization.ErpBoolean;

import static com.sap.cloud.sdk.s4hana.config.S4HanaConfig.BAPI_SERIALIZATION_STRATEGY;

public class CreateCostCenterCommand extends ErpCommand<List<ReturnParameter>>
{
    private final ControllingArea controllingArea;
    private final CostCenterCreateInput costCenterInput;
    private final boolean isTestRun;

    public CreateCostCenterCommand(
            final ErpConfigContext configContext,
            final ControllingArea controllingArea,
            final CostCenterCreateInput costCenterInput,
            final boolean isTestRun)
    {
        super(CreateCostCenterCommand.class, configContext);
        this.controllingArea = controllingArea;
        this.costCenterInput = costCenterInput;
        this.isTestRun = isTestRun;
    }

    @Override
    protected List<ReturnParameter> run()
        throws Exception
    {
        return CostCenterService
                .createMultiple(controllingArea, costCenterInput)
                .testRun(new ErpBoolean(isTestRun))
                .execute(getConfigContext())
                .getMessages();
    }
}
