package com.sap.opensap.service;

import com.google.common.collect.Multimap;

import java.util.List;
import java.util.concurrent.Future;

import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.connectivity.ErpDestination;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.ReadCostCenterDataNamespace.CostCenter;
import com.sap.cloud.sdk.s4hana.serialization.SapClient;
import com.sap.opensap.command.GetCostCentersCommand;
import com.sap.opensap.command.GetEmployeesCommand;
import com.sap.opensap.datamodel.successfactors.EmployeeDetails;
import com.sap.opensap.generated.model.VmCostCenter;
import com.sap.opensap.generated.model.VmEmployee;
import com.sap.opensap.generated.resource.CostcenterEmployeesResource;

public class EmployeeBrowserService implements CostcenterEmployeesResource
{
    @Override
    public GetCostcenterEmployeesResponse getCostcenterEmployees(final String companyCode) throws Exception {
        // Adjust SAP client to your respective S/4HANA system
        final SapClient sapClient = new SapClient("SAPCLIENT-NUMBER");

        // ERP context for calls to S/4HANA
        ErpConfigContext erpContext = new ErpConfigContext(ErpDestination.getDefaultName(), sapClient);

        // Parallelize access to S/4HANA and SuccessFactors
        Future<List<CostCenter>> costCenters = new GetCostCentersCommand(erpContext, companyCode).queue();
        Future<List<EmployeeDetails>> employees = new GetEmployeesCommand().queue();

        // Join data and build response view model instance
        Multimap<String, VmEmployee> employeesInCostCenter = ViewModelUtil.toVmEmployeesMap(employees.get());
        List<VmCostCenter> result = ViewModelUtil.toVmCostCenters(costCenters.get(), employeesInCostCenter);
        return GetCostcenterEmployeesResponse.withJsonOK(result);
    }
}
