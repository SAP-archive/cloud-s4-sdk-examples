package com.sap.opensap.service;

import com.google.common.collect.Lists;

import java.util.Set;

import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.connectivity.ErpDestination;
import com.sap.cloud.sdk.s4hana.serialization.SapClient;
import com.sap.opensap.command.GetCompanyCodesCommand;
import com.sap.opensap.generated.resource.CompanycodesResource;

public class CompanyCodesService implements CompanycodesResource
{
    @Override
    public GetCompanycodesResponse getCompanycodes() throws Exception {
        // Adjust SAP client to your respective S/4HANA system
        final SapClient sapClient = new SapClient("SAPCLIENT-NUMBER");

        // ERP context for calls to S/4HANA
        ErpConfigContext erpContext = new ErpConfigContext(ErpDestination.getDefaultName(), sapClient);

        // Query S/4HANA for company codes
        Set<String> companyCodes = new GetCompanyCodesCommand(erpContext).execute();
        return GetCompanycodesResponse.withJsonOK(Lists.newArrayList(companyCodes));
    }
}
