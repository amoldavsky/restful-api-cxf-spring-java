package com.projectx.web.api.config.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{ AppConfig.class };
	}

	//for spring dispatcherServlet config
	@Override
	protected Class<?>[] getServletConfigClasses() {
		//return new Class<?>[]{WebConfig.class};
		return null;
	}

	//for spring dispatcherServlet
	@Override
	protected String[] getServletMappings() {
		//return new String[]{"/web/*"};
		return null;
	}
	
	private void registerCXFServlet(ServletContext servletContext) {
		ServletRegistration.Dynamic cxfServlet = servletContext.addServlet(
				"cxf", new CXFServlet());
		cxfServlet.addMapping("/*");
		cxfServlet.setLoadOnStartup(1);
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException{
		super.onStartup(servletContext);
		//servletContext.addListener(new ContextLoaderListener(createWebAppContext()));
		registerCXFServlet(servletContext);
	}
	
	private WebApplicationContext createWebAppContext() {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(CxfConfig.class);
        return appContext;
    }
	
}