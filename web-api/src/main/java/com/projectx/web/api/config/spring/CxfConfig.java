package com.projectx.web.api.config.spring;


import java.util.List;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.projectx.web.api.service.JaxrsService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;

@Configuration
@EnableTransactionManagement
public class CxfConfig {

	
	public static final String CXF_SERVICES_BASE_PACKAGE = "com.projetx.web.api.service";
	
	@Autowired
	private SpringBus springBus;
	@Autowired
	private ConfigUtils configUtil;
	
	@ApplicationPath(value = "/")
    public class JaxRsApiApplication extends Application { }

    @Bean
    public JaxRsApiApplication jaxRsApiApplication() {
        return new JaxRsApiApplication();
    }
    
    @Bean
    JacksonJsonProvider jsonProvider(){
        return (new JacksonJsonProvider());
    }
    
    @Bean
    AnnotationConfigApplicationContext annotationConfigApplicationContext(){
        return (new AnnotationConfigApplicationContext());
    }

    @Bean(destroyMethod = "shutdown")
    public SpringBus cxf() {
    	System.out.println( "CxfConfig: cxf bean" );
        return new SpringBus();
    }
    
    @Bean
    @DependsOn("cxf")
    public Server jaxRsServer() {
        JAXRSServerFactoryBean serverFactory = RuntimeDelegate.getInstance().createEndpoint(jaxRsApiApplication(), JAXRSServerFactoryBean.class);
        
        //factory.setServiceBean(new DenialCategoryRest());
        
		// get all the class annotated with @JaxrsService
        List<Object> beans = configUtil.findBeans( JaxrsService.class );

		if (beans.size() > 0) {
			
			// add all the CXF service classes into the CXF stack
			serverFactory.setServiceBeans( beans );
			serverFactory.setAddress("/"+ serverFactory.getAddress());
			serverFactory.setBus(springBus);
			serverFactory.setStart(true);
			
			// set JSON as the response serializer
			JacksonJsonProvider provider = new JacksonJsonProvider();
			serverFactory.setProvider(provider);
	        
		}
        
		return serverFactory.create();
    }
    
}
