package com.projectx.web.api.test.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.projectx.web.api.config.spring.AppConfig;
import com.projectx.web.api.config.spring.CxfConfig;
import com.projectx.web.api.config.spring.WebInitializer;


@Configuration
@ComponentScan(
		basePackages = {
				"com.projectx.logic.api",
				"com.projectx.web.api.test",
				"com.projectx.web.api.service"
		},
		excludeFilters = {
		})
@EnableTransactionManagement
public class AppTestConfig {

}
