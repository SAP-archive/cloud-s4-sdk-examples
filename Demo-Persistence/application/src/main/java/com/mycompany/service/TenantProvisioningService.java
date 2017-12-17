package com.mycompany.service;

public interface TenantProvisioningService {
    void subscribeTenant(String tenantId);
    void unsubscribeTenant(String tenantId);
}
