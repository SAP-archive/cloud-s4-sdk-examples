package com.sap.opensap;

import lombok.Getter;
import lombok.ToString;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import javax.ejb.Local;

import com.sap.cloud.sdk.result.ElementName;

import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.BusinessArea;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CompanyCode;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.ControllingArea;
import com.sap.cloud.sdk.s4hana.serialization.Currency;

@Getter
@ToString
public class CostCenter {
    @ElementName("CO_AREA")
    private ControllingArea controllingArea;

    @ElementName("COSTCENTER")
    private String id;

    @ElementName("NAME")
    private String name;

    @ElementName("DESCRIPT")
    private String description;

    @ElementName("VALID_FROM")
    private String validFrom;

    @ElementName("VALID_TO")
    private String validTo;

    @ElementName("PERSON_IN_CHARGE")
    private String personInCharge;

    @ElementName("COSTCENTER_TYPE")
    private String type;

    @ElementName("COSTCTR_HIER_GRP")
    private String hierarchyGroup;

    @ElementName("COMP_CODE")
    private CompanyCode companyCode;

    @ElementName("BUS_AREA")
    private BusinessArea businessArea;

    @ElementName("CURRENCY")
    private Currency currency;
}
