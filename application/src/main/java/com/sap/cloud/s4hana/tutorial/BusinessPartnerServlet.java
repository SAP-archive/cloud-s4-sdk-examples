package com.sap.cloud.s4hana.tutorial;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.BusinessPartnerNamespace.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;

@WebServlet("/businesspartners")
public class BusinessPartnerServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(BusinessPartnerServlet.class);

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws ServletException, IOException
    {
        final ErpConfigContext configContext = new ErpConfigContext();
        try {
            List<BusinessPartner> businessPartners = BusinessPartnerService.getAllBusinessPartner()
                .select(BusinessPartner.BUSINESS_PARTNER,
                    BusinessPartner.BUSINESS_PARTNER_NAME)
                .filter(BusinessPartner.BUSINESS_PARTNER_CATEGORY.eq("2"))
                .execute(configContext);

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(businessPartners));
        }
        catch( ODataException e ) {
            logger.error("Error during retrieval of business partners", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
        }
    }
}