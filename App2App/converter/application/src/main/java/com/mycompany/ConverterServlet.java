package com.mycompany;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.security.user.User;
import com.sap.cloud.sdk.cloudplatform.security.user.UserAccessor;
import com.sap.cloud.sdk.cloudplatform.tenant.Tenant;
import com.sap.cloud.sdk.cloudplatform.tenant.TenantAccessor;

@WebServlet("/converter")
public class ConverterServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static Logger logger;

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws ServletException, IOException
    {
        final Tenant currentTenant = TenantAccessor.getCurrentTenant();
        final User currentUser = UserAccessor.getCurrentUser();

        System.out.println("Converter - caller application: \n");
        System.out.printf("Current tenant: %s \n", currentTenant);
        System.out.printf("Current user: %s \n", currentUser);

        // params: sum, from, to
        final Double sum = request.getParameter("sum") == null ? 1.0 : Double.parseDouble(request.getParameter("sum"));
        final String from = request.getParameter("from") == null ? "EUR" : request.getParameter("from");
        final String to = request.getParameter("to") == null ? "USD" : request.getParameter("to");

        // call exchange rate api
        final HttpClient httpClient = HttpClientAccessor.getHttpClient(DestinationAccessor.getDestination("app"));
        final HttpResponse exchangeRateResponse = httpClient.execute(new HttpGet("/exchange-rate"));

        // parse response: get rate
        final HttpEntity entity = exchangeRateResponse.getEntity();
        if (entity != null) {
            final String ratesJson = EntityUtils.toString(entity);
            final Rate[] rates = new Gson().fromJson(ratesJson, Rate[].class);

            Double rate = 0.0;
            for (int i = 0; i < rates.length; i++) {
                if ((rates[i].getCurrencyFrom().equals(from)) && (rates[i].getCurrencyTo().equals(to))) {
                    rate = rates[i].getRate();
                }
            }

            if (rate == 0) {
                response.getWriter().printf("Exchange rate for the currencies %s to %s is not maintained.", from, to);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                final Double convertedSum = sum * rate;
                response.getWriter().write(convertedSum.toString());
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            response.getWriter().write("Exchange rates are not available.");
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }
}
