package com.company.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.company.dao")
public class JPAConfig {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = JPAConfig.class
                .getClassLoader()
                .getResourceAsStream("hibernate.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        var factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("jpa-settings");
        factoryBean.setPackagesToScan("com.company.entity");
        return factoryBean;
    }

    @Bean
    public TransactionManager transactionManager() {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean().getObject());
        transactionManager.setJpaProperties(PROPERTIES);
        return transactionManager;
    }
}
