package com.projectx.web.api;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Ignore;
import org.springframework.beans.BeansException;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@Ignore // so that we do not run this with the other JUnits
public class BaseTest extends AbstractJUnit4SpringContextTests {

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		// TODO Auto-generated method stub
		
	}

}
