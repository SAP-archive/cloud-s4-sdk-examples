package com.mycompany;

import com.google.gson.Gson;
import com.mycompany.vdm.namespaces.socialnetworkaccount.SocialNetworkAccount;
import com.mycompany.vdm.services.DefaultSocialNetworkAccountService;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;

@WebServlet("/accounts")
public class AccountServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(AccountServlet.class);

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
            throws ServletException, IOException {
        try {
            final List<SocialNetworkAccount> socialNetworkAccounts = new DefaultSocialNetworkAccountService()
                    .getAllSocialNetworkAccount().select(SocialNetworkAccount.PROVIDER, SocialNetworkAccount.ACCOUNT)
                    .execute();

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(socialNetworkAccounts));
        }
        catch( Exception e ) {
            logger.error("Error during retrieval of business partners", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
        }
    }
}