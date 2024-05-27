package com.example.multiple_datasource.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource({"classpath:application.yml"})
@EnableJpaRepositories(
        basePackages = "com.example.multiple_datasource.repositories.location",
        entityManagerFactoryRef = "locationEntityManager",
        transactionManagerRef = "locationTransactionManager"
)
public class LocationPersistenceConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean locationEntityManager() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(locationDataSource());
        entityManagerFactoryBean.setPackagesToScan("com.example.multiple_datasource.model.location");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("location.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", environment.getProperty("location.hibernate.dialect"));
        entityManagerFactoryBean.setJpaPropertyMap(properties);

        return entityManagerFactoryBean;
    }

    @Bean
    public DataSource locationDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.location.driverClassName"));
        dataSource.setUrl(environment.getProperty("location.jdbc.url"));
        dataSource.setUsername(environment.getProperty("location.jdbc.username"));
        dataSource.setPassword(environment.getProperty("location.jdbc.password"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager locationTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(locationEntityManager().getObject());
        return transactionManager;
    }
}
