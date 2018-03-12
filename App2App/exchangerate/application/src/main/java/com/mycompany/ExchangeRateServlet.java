package com.mycompany;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.cloudplatform.security.user.User;
import com.sap.cloud.sdk.cloudplatform.security.user.UserAccessor;
import com.sap.cloud.sdk.cloudplatform.tenant.Tenant;
import com.sap.cloud.sdk.cloudplatform.tenant.TenantAccessor;

@WebServlet("/exchange-rate")
public class ExchangeRateServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(ExchangeRateServlet.class);

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws ServletException, IOException
    {
        final Tenant currentTenant = TenantAccessor.getCurrentTenant();
        final User currentUser = UserAccessor.getCurrentUser();

        System.out.println("Exchange rate - called application: \n");
        System.out.printf("Current tenant: %s \n", currentTenant);
        System.out.printf("Current user: %s \n", currentUser);

        List<Rate> rates = mockRates();

        response.getWriter().write(new Gson().toJson(rates));
    }

    private List<Rate> mockRates() {
        final List<Rate> rates = Lists.newArrayList();
        rates.add(new Rate("EUR", "USD", 1.23));
        rates.add(new Rate("USD", "EUR", 0.81));
        rates.add(new Rate("EUR", "RUB", 69.73));
        rates.add(new Rate("EUR", "GBP", 0.89));

        return rates;
    }
}
