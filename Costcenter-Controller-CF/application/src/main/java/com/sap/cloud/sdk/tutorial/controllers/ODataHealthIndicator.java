package com.sap.cloud.sdk.tutorial.controllers;

import org.slf4j.Logger;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Locale;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.connectivity.ErpDestination;
import com.sap.cloud.sdk.s4hana.serialization.SapClient;
import com.sap.cloud.sdk.tutorial.command.HealthCheckCommand;

@Component
public class ODataHealthIndicator implements HealthIndicator {
    private static final Logger logger = CloudLoggerFactory.getLogger(ODataHealthIndicator.class);

    @Override
    public Health health() {
        final SapClient sapClient = new SapClient("SAPCLIENT-NUMBER"); // adjust SAP client to your respective S/4HANA system
        final String problem = checkForProblem(sapClient);
        if (problem != null) {
            return Health.down().withDetail("Error", problem).build();
        }
        return Health.up().build();
    }

    private String checkForProblem(final SapClient sapClient) {
        try {
            final ErpConfigContext config = new ErpConfigContext(
                    ErpDestination.getDefaultName(),
                    sapClient,
                    Locale.ENGLISH);

            final HealthCheckCommand healthCheckCommand = new HealthCheckCommand(config);
            Assert.isTrue(healthCheckCommand.execute(), "Empty OData result.");
            return null;
        }
        catch( final Exception e ) {
            logger.error("Could not complete health check for OData Service.", e);
            return e.getMessage() + Arrays.toString(e.getStackTrace());
        }
    }
}
