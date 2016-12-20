package com.projectx.web.api.test.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


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
