package com.sap.cloud.s4hana.tutorial;

import com.google.gson.Gson;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.services.CostCenterService;
import com.sap.cloud.sdk.s4hana.datamodel.bapi.structures.ReturnParameter;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.readcostcenterdata.CostCenter;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.ReadCostCenterDataService;

@WebServlet( "/costcenters" )
public class CostCenterServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(CostCenterServlet.class);

    @Inject
    private ReadCostCenterDataService readService;

    @Inject
    private CostCenterService createService;

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws ServletException,
            IOException
    {
        final ErpConfigContext configContext = new ErpConfigContext();

        final List<CostCenter> result = new GetCachedCostCentersCommand(readService, configContext).execute();

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(result));
    }

    @Override
    protected void doPost( final HttpServletRequest request, final HttpServletResponse response )
        throws ServletException,
            IOException
    {
        final ErpConfigContext configContext = new ErpConfigContext();

        final List<ReturnParameter> result =
            new CreateCostCenterCommand(
                createService,
                configContext,
                request.getParameter("id"),
                request.getParameter("description")).execute();

        // reset cached get results
        new GetCachedCostCentersCommand(readService, configContext).getCache().invalidateAll();

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(result));
    }
}
