package com.sap.cloud.s4hana.tutorial;

import org.slf4j.Logger;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.odatav2.connectivity.ErrorResultHandler;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;

public class ODataV2SimpleErrorHandler implements ErrorResultHandler<ODataException>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(ODataV2SimpleErrorHandler.class);

    public ODataException createError( String content, Object origin, int httpStatusCode )
    {
        String msg =
            String.format(
                "OData V2 Simple Error Handler received backend OData V2 service response with status %s, full response was %s",
                httpStatusCode,
                content);
        logger.error(msg);

        ODataException e = new ODataException();
        e.setMessage(msg);
        e.setCode(String.valueOf(httpStatusCode));

        return e;
    }

}
