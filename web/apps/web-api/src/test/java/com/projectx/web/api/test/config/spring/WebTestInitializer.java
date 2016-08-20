package com.projectx.web.api.test.config.spring;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.projectx.web.api.config.spring.WebInitializer;

public class WebTestInitializer extends AbstractAnnotationConfigDispatcherServletInitializer { //extends WebInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{ AppTestConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
