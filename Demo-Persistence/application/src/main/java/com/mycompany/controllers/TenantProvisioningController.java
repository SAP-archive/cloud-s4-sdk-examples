package com.mycompany.controllers;

import com.mycompany.service.TenantProvisioningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RestController
@RequestScope
@RequestMapping(path = "/callback/tenant")
public class TenantProvisioningController {
    private static final Logger logger = LoggerFactory.getLogger(CostCenterController.class);

    @Autowired
    TenantProvisioningService tenantProvisioningService;

    @PutMapping("/{tenantId}")
    public void subscribeTenant(@PathVariable(value = "tenantId") String tenantId){
        logger.info("Tenant callback service was called with method PUT for tenant {}.", tenantId);
        tenantProvisioningService.subscribeTenant(tenantId);
    }

    @DeleteMapping("/{tenantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsubscribeTenant(@PathVariable(value = "tenantId")  String tenantId) {
        logger.info("Tenant callback service was called with method DELETE for tenant {}.", tenantId);
        tenantProvisioningService.unsubscribeTenant(tenantId);
    }
}
