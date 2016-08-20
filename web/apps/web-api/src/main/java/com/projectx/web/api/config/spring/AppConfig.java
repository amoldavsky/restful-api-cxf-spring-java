package com.projectx.web.api.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.context.annotation.FilterType;

@Configuration 
@ComponentScan(
		basePackages={
			"com.projetx.web.api",
			"com.projetx.logic.api"
		},
		excludeFilters={@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)})
@EnableWebMvc
@EnableTransactionManagement
public class AppConfig extends WebMvcConfigurerAdapter {
	
	 @Override
	 public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		 configurer.enable();
	 }
}