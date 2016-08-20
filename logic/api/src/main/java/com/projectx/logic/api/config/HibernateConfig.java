package com.projectx.logic.api.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@PropertySource( value = { 
		"file:${app.config.root}/${spring.profiles.active}/hibernate.properties" 
})
@Configuration
public class HibernateConfig {
	
	@Autowired
    private Environment environment;
	
	@Autowired 
	private DataSource usersDataSource;
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource( this.usersDataSource );
		sessionFactory.setPackagesToScan(new String[] { 
				"com.projectx.logic.api" 
		});
		sessionFactory.setHibernateProperties( hibernateProperties() );
		return sessionFactory;
		
	}
	
	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		
		// enable multi-line processing for sql files
		properties.put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
		properties.put("hibernate.hbm2ddl.import_files", "${app.config.root}/${spring.profiles.active}/import.sql"); 
		
		properties.put("hibernate.generate_statistics", environment.getRequiredProperty("hibernate.generate_statistics"));
		properties.put("hibernate.jdbc.batch_size", environment.getRequiredProperty("hibernate.jdbc.batch_size"));
		
		return properties;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}
}
