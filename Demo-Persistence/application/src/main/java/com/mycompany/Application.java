package com.mycompany;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ServletComponentScan( basePackages = { "com.sap.cloud.sdk" } )
public class Application extends JpaBaseConfiguration
{
    @Autowired
    AutowireCapableBeanFactory beanFactory;

    protected Application(DataSource dataSource, JpaProperties properties,
                          ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider,
                          ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        super(dataSource, properties, jtaTransactionManagerProvider, transactionManagerCustomizers);
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        final HashMap<String, Object> props = Maps.newHashMap(ImmutableMap.of());

        return props;
    }

    public static void main( final String[] args )
    {
        SpringApplication.run(Application.class, args);
    }
}
