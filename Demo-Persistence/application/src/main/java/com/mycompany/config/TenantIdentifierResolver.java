package com.mycompany.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.cloudplatform.tenant.TenantAccessor;
import com.sap.cloud.sdk.cloudplatform.tenant.exception.TenantNotFoundException;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    private static final Logger logger = CloudLoggerFactory.getLogger(TenantIdentifierResolver.class);

    @Value("${multitenant.defaultTenant}")
    String defaultTenant;

    @Override
    public String resolveCurrentTenantIdentifier() {
        try {
            return TenantAccessor.getCurrentTenant().getTenantId();
        } catch (TenantNotFoundException e) {
            logger.warn("Tenant not found", e);
            return defaultTenant;
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
