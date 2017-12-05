package com.sap.opensap;

import org.joda.time.LocalDate;

import javax.annotation.Nullable;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.sap.cloud.sdk.s4hana.connectivity.ErpEndpoint;
import com.sap.cloud.sdk.s4hana.connectivity.exception.QueryExecutionException;
import com.sap.cloud.sdk.s4hana.connectivity.rfc.BapiQuery;
import com.sap.cloud.sdk.s4hana.connectivity.rfc.BapiQueryResult;
import com.sap.cloud.sdk.s4hana.connectivity.rfc.Table;

@WebServlet("/cost-center-creation")
public class CostCenterCreationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final PrintWriter writer = response.getWriter();

        RequestParameters reqParameters;
        try {
            reqParameters = fetchParametersFromRequest(request);
        } catch (IllegalArgumentException e) {
            writer.write(e.getMessage());
            return;
        }

        final ErpEndpoint erpEndpoint = new ErpEndpoint();

        try {
            final List<CostCenter> costCenterList = retrieveCostCenterList(reqParameters, erpEndpoint);

            if (costCenterList.isEmpty()) {
                writer.write("No cost centers found in given controlling area.");
                return;
            }

            writer.write("Number of found cost centers in controlling area " + reqParameters.getControllingArea() + ": " + costCenterList.size() + "\n");

            CostCenter detailedCostCenter = retrieveCostCenterDetails(reqParameters, erpEndpoint, costCenterList);

            writer.write("Reference Cost Center: " + detailedCostCenter.toString() + "\n");

            final BapiQueryResult resultCreate = createCostCenterCopy(reqParameters, erpEndpoint, detailedCostCenter);

            if (resultCreate.wasSuccessful()) {
                writer.write("Cost Centers created successfully.");
            }

        } catch (Exception e) {
            writer.write(e.getMessage());
            e.printStackTrace(writer);
        }
    }

    private BapiQueryResult createCostCenterCopy(final RequestParameters reqParameters, final ErpEndpoint erpEndpoint, final CostCenter detailedCostCenter) throws QueryExecutionException {
        final BapiQuery queryCreate = new BapiQuery("BAPI_COSTCENTER_CREATEMULTIPLE", true)
                .withExporting("CONTROLLINGAREA", "BAPI0012_GEN-CO_AREA", reqParameters.getControllingArea())
                .withTableAsReturn("BAPIRET2");

        Table<BapiQuery> costCenterTables = queryCreate.withTable("COSTCENTERLIST", "BAPI0012_CCINPUTLIST");
        for (int i = reqParameters.getNamingSuffixStartCounter(); i < (reqParameters.getNamingSuffixStartCounter() + reqParameters.getNumberOfCreations()); i++) {
            costCenterTables.row()
                    .field("COSTCENTER", "KOSTL", reqParameters.getNamingPrefix() + i)
                    .field("NAME", "KTEXT", detailedCostCenter.getName())
                    .field("DESCRIPT", "KLTXT", detailedCostCenter.getDescription() + " " + i)
                    .field("VALID_FROM", "DATBI", convertJodaDateToErpDate(LocalDate.now()))
                    .field("VALID_TO", "DATAB", convertJodaDateToErpDate(LocalDate.now().plusYears(1)))
                    .field("PERSON_IN_CHARGE", "VERAK", detailedCostCenter.getPersonInCharge())
                    .field("COSTCENTER_TYPE", "KOSAR", detailedCostCenter.getType())
                    .field("COSTCTR_HIER_GRP", "KHINR", detailedCostCenter.getHierarchyGroup())
                    .field("COMP_CODE", "BUKRS", detailedCostCenter.getCompanyCode())
                    .field("BUS_AREA", "GSBER", detailedCostCenter.getBusinessArea())
                    .field("CURRENCY", "WAERS", detailedCostCenter.getCurrency())
                    .end();
        }

        return new BapiQueryCommitExecutor(queryCreate).execute(erpEndpoint);
    }

    private CostCenter retrieveCostCenterDetails(final RequestParameters reqParameters, final ErpEndpoint erpEndpoint, final List<CostCenter> costCenterList) throws QueryExecutionException {
        CostCenter chosenCostCenter = costCenterList.get(reqParameters.getCostCenterIndex());

        final BapiQueryResult resultCostCenterDetail = new BapiQuery("BAPI_COSTCENTER_GETDETAIL1")
                .withExporting("CONTROLLINGAREA", "BAPI0012_1-CO_AREA", chosenCostCenter.getControllingArea())
                .withExporting("COSTCENTER", "BAPI0012_1-COSTCENTER", chosenCostCenter.getId())
                .withImportingFields("COSTCENTERDETAIL", "BAPI0012_CCOUTPUTLIST").end()
                .withTableAsReturn("BAPIRET2")
                .execute(erpEndpoint);

        return resultCostCenterDetail.get("COSTCENTERDETAIL").getAsObject().as(CostCenter.class);
    }

    private List<CostCenter> retrieveCostCenterList(final RequestParameters reqParameters, final ErpEndpoint erpEndpoint) throws QueryExecutionException {
        final BapiQueryResult resultGetCostCenters = new BapiQuery("BAPI_COSTCENTER_GETLIST1")
                .withExporting("CONTROLLINGAREA", "BAPI0012_GEN-CO_AREA", reqParameters.getControllingArea())
                .withTable("COSTCENTERLIST", "BAPI0012_CCLIST").end()
                .withTableAsReturn("BAPIRET2")
                .execute(erpEndpoint);

        return resultGetCostCenters.get("COSTCENTERLIST").getAsCollection().asList(CostCenter.class);
    }

    private RequestParameters fetchParametersFromRequest(HttpServletRequest request) throws IllegalArgumentException {
        final RequestParameters requestParameters = new RequestParameters();

        requestParameters.setControllingArea(request.getParameter("controllingArea"));
        requestParameters.setNamingPrefix(request.getParameter("namingPrefix"));
        try {
            requestParameters.setCostCenterIndex(retrieveIntParameterFromRequest(request, "costCenterIndex"));
            requestParameters.setNamingSuffixStartCounter(retrieveIntParameterFromRequest(request, "namingSuffixStartCounter"));
            requestParameters.setNumberOfCreations(retrieveIntParameterFromRequest(request, "numberOfCreations"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        if (requestParameters.getControllingArea().isEmpty() ||
                requestParameters.getCostCenterIndex() < 0 ||
                requestParameters.getNamingPrefix().isEmpty() ||
                requestParameters.getNamingSuffixStartCounter() < 1 ||
                requestParameters.getNumberOfCreations() < 1) {
            throw new IllegalArgumentException("Not all parameters specified correctly.");
        }

        return requestParameters;
    }

    private int retrieveIntParameterFromRequest(HttpServletRequest request, String parameterName) throws NumberFormatException {
        return Integer.parseInt(request.getParameter(parameterName));
    }

    private String convertJodaDateToErpDate(LocalDate date) {
        return date.toString("yyyyMMdd");
    }
}
