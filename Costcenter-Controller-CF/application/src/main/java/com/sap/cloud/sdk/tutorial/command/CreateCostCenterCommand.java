package com.sap.cloud.sdk.tutorial.command;

import com.netflix.config.ConfigurationManager;

import java.util.List;

import com.sap.cloud.sdk.s4hana.config.S4HanaConfig;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.services.CostCenterService;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.structures.CostCenterCreateInput;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.structures.ReturnParameter;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CostCenter;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CostCenterManager;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CurrencyKey;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.IndicatorForCostCenterType;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.SetId;
import com.sap.cloud.sdk.s4hana.serialization.ErpBoolean;
import com.sap.cloud.sdk.tutorial.models.CostCenterDetails;

import static com.sap.cloud.sdk.s4hana.config.S4HanaConfig.BAPI_SERIALIZATION_STRATEGY;

public class CreateCostCenterCommand extends ErpCommand<List<ReturnParameter>>
{
    private final CostCenterDetails details;
    private final boolean testRun;

    public CreateCostCenterCommand( final ErpConfigContext configContext, final CostCenterDetails costCenterDetails, final boolean isTestRun)
    {
        super(CreateCostCenterCommand.class, configContext);
        this.details = costCenterDetails;
        this.testRun = isTestRun;
    }

    @Override
    protected List<ReturnParameter> run()
        throws Exception
    {
        final List<ReturnParameter> returnValues =
            CostCenterService
                .createMultiple(
                    // Required parameter: ControllingArea
                    details.getControllingArea(),
                    // Required parameter: CostCenter Input
                    CostCenterCreateInput
                        .builder()
                        .validFrom(CostCenterDetails.asLocalDate(details.getValidFrom()))
                        .validTo(CostCenterDetails.asLocalDate(details.getValidTo()))
                        .costcenter(new CostCenter(details.getId().getValue()))
                        .name("SAP dummy")
                        .currency(CurrencyKey.of("EUR"))
                        .descript(details.getDescription())
                        .costcenterType(IndicatorForCostCenterType.of(details.getCategory()))
                        .personInCharge(CostCenterManager.of(details.getPersonResponsible()))
                        .costctrHierGrp(SetId.of(details.getCostCenterGroup()))
                        .compCode(details.getCompanyCode())
                        .profitCtr(details.getProfitCenter())
                        .build())
                .testRun(new ErpBoolean(testRun))
                .execute(getConfigContext())
                .getMessages();

        return returnValues;
    }
}
