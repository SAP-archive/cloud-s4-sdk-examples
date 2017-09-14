package com.sap.cloud.sdk.tutorial.models;

import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.result.ElementName;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CompanyCode;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.ControllingArea;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.CostCenter;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.types.ProfitCenter;

@Data
@Accessors(chain = true)
public class CostCenterDetails
{
    private static final Logger logger = CloudLoggerFactory.getLogger(CostCenterDetails.class);
    private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");


    /*
     * for listing
     */
    @ElementName("CostCenterID")
    private CostCenter id;

    @ElementName("CostCenterDescription")
    private String description;

    @ElementName("Status")
    private String status;

    /*
     * for deleting
     */
    @ElementName("ValidityStartDate")
    private String validFrom;

    @ElementName("ValidityEndDate")
    private String validTo;

    /*
     * for creating
     */
    @ElementName("CompanyCode")
    private CompanyCode companyCode;

    @ElementName("Category")
    private String category;

    @ElementName("ControllingArea")
    private ControllingArea controllingArea;

    private String personResponsible;

    private String costCenterGroup;

    private ProfitCenter profitCenter;

    public static String asDateString(final String input) {
        return "/Date(" + dtf.parseDateTime(input).getMillis() + ")/";
    }

    public static LocalDate asLocalDate(final String edmDate) {
        return new LocalDate(Long.valueOf(edmDate.substring(6, edmDate.length() - 2)));
    }
}
