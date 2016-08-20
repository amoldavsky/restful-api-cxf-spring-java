package com.projectx.logic.api.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

//import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.mchange.v2.c3p0.DriverManagerDataSource;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@PropertySource( value = { 
		"file:${app.config.root}/${spring.profiles.active}/datasources.properties", 
		"file:${app.config.root}/${spring.profiles.active}/c3p0.properties" 
})
@Configuration
public class DataSourceConfig {
	
	@Autowired
    private Environment environment;
	
	@Bean
	public DataSource usersDataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClass(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setProperties( c3p0Properties() );
		dataSource.setJdbcUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUser( environment.getRequiredProperty("jdbc.username") );
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
		
	}
	
    private Properties c3p0Properties() {
        Properties properties = new Properties();
        properties.put("hibernate.c3p0.min_size",
                environment.getRequiredProperty("c3p0.min_size"));
        properties.put("hibernate.c3p0.max_size",
                environment.getRequiredProperty("c3p0.max_size"));
        properties.put("hibernate.c3p0.timeout",
                environment.getRequiredProperty("c3p0.timeout"));
        properties.put("hibernate.c3p0.max_statements",
                environment.getRequiredProperty("c3p0.max_statements"));
        properties.put("hibernate.c3p0.idle_test_period",
                environment.getRequiredProperty("c3p0.idle_test_period"));
        return properties;
    }
	
}
