package com.mycompany.service;


import com.mycompany.util.TenantUtil;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

@Service
public class DefaultTenantProvisioningService implements TenantProvisioningService {

    public static final String LIQUIBASE_PATH = "db/changelog/db.changelog-master.yaml";
    @Autowired
    private DataSource dataSource;

    private static final Pattern TENANT_PATTERN = Pattern.compile("[-\\w]+");

    private static final Logger logger = LoggerFactory.getLogger(DefaultTenantProvisioningService.class);

    @Override
    public void subscribeTenant(final String tenantId) {
        String defaultSchemaName;
        try {
            Validate.isTrue(isValidTenantId(tenantId), String.format("Invalid tenant id: \"%s\"", tenantId));
            final String schemaName = TenantUtil.createSchemaName(tenantId);

            final Connection connection = dataSource.getConnection();
            final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            try (Statement statement = connection.createStatement()) {
                statement.execute(String.format("CREATE SCHEMA IF NOT EXISTS \"%s\"", schemaName));
                connection.commit();

                defaultSchemaName = database.getDefaultSchemaName();
                database.setDefaultSchemaName(schemaName);

                final String filePath = LIQUIBASE_PATH;
                final Liquibase liquibase = new liquibase.Liquibase(filePath,
                        new ClassLoaderResourceAccessor(), database);

                liquibase.update(new Contexts(), new LabelExpression());
                database.setDefaultSchemaName(defaultSchemaName);
            }

        } catch (SQLException | LiquibaseException | IllegalArgumentException e) {
            final BadRequestException badRequestException = new BadRequestException();
            logger.error("Tenant subscription failed for {}.", tenantId, e);
            throw badRequestException;
        }
    }

    @Override
    public void unsubscribeTenant(final String tenantId) {
        try {
            Validate.isTrue(isValidTenantId(tenantId), String.format("Invalid tenant id: \"%s\"", tenantId));
            final String schemaName = TenantUtil.createSchemaName(tenantId);
            final Connection connection = dataSource.getConnection();
            try (Statement statement = connection.createStatement()) {
                statement.execute(String.format("DROP SCHEMA IF EXISTS \"%s\" CASCADE", schemaName));
            }
        } catch (SQLException | IllegalArgumentException e) {
            final BadRequestException badRequestException = new BadRequestException();
            logger.error("Tenant unsubscription failed for {}.", tenantId, e);
            throw badRequestException;
        }
    }

    private boolean isValidTenantId(final String tenantId) {
        return tenantId != null && TENANT_PATTERN.matcher(tenantId).matches();
    }
}
