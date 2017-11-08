package com.sap.cloud.s4hana.tutorial;

import java.util.List;

import org.joda.time.LocalDate;

import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.services.CostCenterService;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.services.DefaultCostCenterService;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.structures.CostCenterCreateInput;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.structures.ReturnParameter;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CompanyCode;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.ControllingArea;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CostCenter;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CostCenterManager;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CurrencyKey;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.IndicatorForCostCenterType;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.ProfitCenter;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.SetId;

import lombok.NonNull;

public class CreateCostCenterCommand extends ErpCommand<List<ReturnParameter>>
{
    private final CostCenterService service;

    private final String id;
    private final String description;

    public CreateCostCenterCommand(
        @NonNull final CostCenterService service,
        @NonNull final ErpConfigContext configContext,
        @NonNull final String id,
        @NonNull final String description )
    {
        super(CreateCostCenterCommand.class, configContext);
        this.service = service;
        this.id = id;
        this.description = description;
    }

    @Override
    protected List<ReturnParameter> run()
        throws Exception
    {
        final ControllingArea controllingArea = ControllingArea.of("A000"); // ERP type "CACCD"

        final CostCenterCreateInput costCenterInput =
            CostCenterCreateInput
                .builder()
                .costcenter(CostCenter.of(id))
                .name(id)
                .descript(description)
                .validFrom(new LocalDate())
                .validTo(new LocalDate().plusYears(1))
                .currency(CurrencyKey.of("EUR")) // ERP type "WAERS"
                .costcenterType(IndicatorForCostCenterType.of("E")) // ERP type "KOSAR"
                .personInCharge(CostCenterManager.of("USER")) // ERP type "VERAK"
                .costctrHierGrp(SetId.of("0001")) // ERP type "SETNR"
                .compCode(CompanyCode.of("1010")) // ERP type "BUKRS"
                .profitCtr(ProfitCenter.of("YB101")) // ERP type "PRCTR"
                .build();

        return service.createMultiple(controllingArea, costCenterInput).execute(getConfigContext()).getMessages();
    }
}
