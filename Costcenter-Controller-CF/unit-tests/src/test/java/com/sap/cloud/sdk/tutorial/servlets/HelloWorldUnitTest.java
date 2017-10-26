package com.sap.cloud.sdk.tutorial.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HelloWorldUnitTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Test
    public void testHello() throws Exception {
        // mock writer of response object
        final StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        // execute HelloWorld servlet with mocked request and response parameters
        new HelloWorldServlet().doGet(request, response);

        // verify response writer has been called at least once
        verify(response, atLeastOnce()).getWriter();

        // assert results
        assertEquals(writer.toString(), "Hello World!");
    }
}
