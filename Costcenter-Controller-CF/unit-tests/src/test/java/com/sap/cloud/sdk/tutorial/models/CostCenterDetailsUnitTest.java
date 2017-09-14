package com.sap.cloud.sdk.tutorial.models;

import com.google.common.collect.Lists;
import org.joda.time.LocalDate;
import org.junit.Test;

import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CompanyCode;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.ControllingArea;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CostCenter;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.ProfitCenter;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CostCenterDetailsUnitTest {
    private static final CostCenter id = new CostCenter("ID");
    private static final String personResponsible = "USER";
    private static final String companyCode = "1010";
    private static final String category = "E";
    private static final String controllingArea = "A000";
    private static final String name = "01234TEST";
    private static final String description = "This is a test.";
    private static final String group = "0001";
    private static final String status = "ACTIVE";
    private static final String profitCenter = "YB101";
    private static final String validFrom = CostCenterDetails.asDateString("2016-12-31");
    private static final String validTo = CostCenterDetails.asDateString("2016-01-01");

    private static LocalDate dateValidTo;   // will be generated
    private static LocalDate dateValidFrom; // will be generated

    @Test
    public void testGetterAndSetter() {
        final CostCenterDetails details = new CostCenterDetails();
        details.setId(id);
        details.setCompanyCode(new CompanyCode(companyCode));
        details.setControllingArea(new ControllingArea(controllingArea));
        details.setCategory(category);
        details.setId(new CostCenter(name));
        details.setDescription(description);
        details.setCostCenterGroup(group);
        details.setPersonResponsible(personResponsible);
        details.setStatus(status);
        details.setProfitCenter(new ProfitCenter(profitCenter));
        details.setValidFrom(validFrom);
        details.setValidTo(validTo);

        assertEquals(
                Lists.newArrayList(
                        details.getCompanyCode().getValue(),
                        details.getControllingArea().getValue(),
                        details.getCategory(),
                        details.getId().getValue(),
                        details.getDescription(),
                        details.getCostCenterGroup(),
                        details.getPersonResponsible(),
                        details.getStatus(),
                        details.getProfitCenter().getValue(),
                        details.getValidFrom(),
                        details.getValidTo()),
                Lists.newArrayList(
                        companyCode, controllingArea, category, name, description, group,
                        personResponsible, status, profitCenter, validFrom, validTo));
    }
}
