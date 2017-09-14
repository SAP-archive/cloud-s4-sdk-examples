package com.sap.cloud.sdk.tutorial.servlets;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;

@WebServlet( "/hello" )
public class HelloWorldServlet extends HttpServlet
{
    private static final long serialVersionUID = 3203196792634761929L;
    private static final Logger logger = CloudLoggerFactory.getLogger(HelloWorldServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response )
        throws ServletException,
            IOException
    {
        logger.info("I am running!");
        response.getWriter().write("Hello World!");
    }
}
