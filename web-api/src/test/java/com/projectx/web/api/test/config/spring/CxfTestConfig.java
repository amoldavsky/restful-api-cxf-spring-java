package com.projectx.web.api.test.config.spring;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
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

@Configuration
@EnableTransactionManagement
public class CxfTestConfig {

	public static final String CXF_SERVICES_BASE_PACKAGE = "com.projectx.web.api.service";
	
	private Bus springBus;
	
	@Autowired
	private ConfigTestUtils configUtil;
	
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
    public Bus cxf() {
    	
    	SpringBusFactory factory = new SpringBusFactory();
    	springBus = factory.createBus();
        BusFactory.setDefaultBus( springBus );
        
        return springBus;
    	
    	//System.out.println( "CxfTestConfig: cxf bean" );
        //return new SpringBus();
    }
    
    @Bean
    @DependsOn("cxf")
    public Server jaxRsServer() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		// sf.setResourceClasses( MyJaxrsResource.class );

		List<Object> providers = new ArrayList<Object>();
		// add custom providers if any
		sf.setProviders(providers);

		// factory.setServiceBean(new DenialCategoryRest());

		// get all the class annotated with @JaxrsService
		List<Object> beans = configUtil.findBeans(JaxrsService.class);
		// List<Class> beansClasses = configUtil.findClasses( JaxrsService.class
		// );

		if (beans.size() > 0) {

			// add all the CXF service classes into the CXF stack
			//sf.setResourceClasses(UserRestImpl.class);
			sf.setServiceBeans(beans);
			sf.setAddress("http://localhost:8080/api");
			sf.setBus(springBus);
			sf.setStart(true);

			// set JSON as the response serializer
			JacksonJsonProvider provider = new JacksonJsonProvider();
			sf.setProvider(provider);

		}

		return sf.create();
    }
	
}
