package com.sap.opensap.datamodel.s4;

import lombok.Data;

import com.sap.cloud.sdk.result.ElementName;
import com.sap.cloud.sdk.s4hana.serialization.CompanyCode;
import com.sap.cloud.sdk.s4hana.serialization.CostCenter;

@Data
public class CostCenterDetails
{
    @ElementName( "CostCenterID" )
    private CostCenter id;

    @ElementName( "CompanyCode" )
    private CompanyCode companyCode;

    @ElementName( "CostCenterDescription" )
    private String description;
}
