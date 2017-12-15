package com.mycompany.service;


import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DefaultTenantProvisioningService implements TenantProvisioningService {

    public static final String LIQUIBASE_PATH = "db/changelog/db.changelog-master.yaml";
    @Autowired
    private DataSource dataSource;

    private static final Logger logger = LoggerFactory.getLogger(DefaultTenantProvisioningService.class);

    @Override
    public void subscribeTenant(final String tenantId) {
        String defaultSchemaName;
        try {
            final Connection connection = dataSource.getConnection();
            final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            connection.createStatement().execute("CREATE SCHEMA IF NOT EXISTS \"" + tenantId + "\"");
            connection.commit();

            defaultSchemaName = database.getDefaultSchemaName();
            database.setDefaultSchemaName(tenantId);

            final String filePath= LIQUIBASE_PATH;
            final Liquibase liquibase = new liquibase.Liquibase(filePath,
            new ClassLoaderResourceAccessor(), database);

            liquibase.update(new Contexts(), new LabelExpression());
            database.setDefaultSchemaName(defaultSchemaName);


        } catch (SQLException | LiquibaseException e) {
            final BadRequestException badRequestException = new BadRequestException();
            logger.error("Tenant subscription failed for {}.", tenantId, e);
            throw badRequestException;
        }
    }

    @Override
    public void unsubscribeTenant(final String tenantId) {
        try {
            final Connection connection = dataSource.getConnection();
            connection.createStatement().execute("DROP SCHEMA IF EXISTS \"" + tenantId + "\"" + " CASCADE");
        } catch (SQLException e) {
            final BadRequestException badRequestException = new BadRequestException();
            logger.error("Tenant unsubscription failed for {}.", tenantId, e);
            throw badRequestException;
        }
    }
}
