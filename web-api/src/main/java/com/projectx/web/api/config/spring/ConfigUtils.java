package com.projectx.web.api.config.spring;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

/**
 * Configuration helper class
 * 
 * @author Assaf Moldavsky
 *
 */
@Service( "com.projectx.web.api.config.ConfigUtils" )
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ConfigUtils {

	private static final Log LOG = LogFactory.getLog(ConfigUtils.class);

	/**
	 * Impl package
	 */
	private static final String IMPL_PACKAGE = ".impl";

	/**
	 * Impl package suffix
	 */
	private static final String IMPL_SUFFIX = "Impl";

	/**
	 * Base CXF services package
	 */
	private static final String BASE_PACKAGE = "com.projetx.web.api.service";

	@Autowired
	private ApplicationContext applicationContext;


	public List<Object> findBeans( Class annotation ) {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(annotation));
		
		List<Object> beanList = new ArrayList<Object>();
		
		for (BeanDefinition bd : scanner.findCandidateComponents(BASE_PACKAGE)) {
			beanList.add(applicationContext.getBean(interfaceName(bd.getBeanClassName())));
		}
		
		return beanList;
	}

	public List<Class> findClass( Class annotation ) {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(annotation));
		
		List<Class> classList = new ArrayList<Class>();
		
		for (BeanDefinition bd : scanner.findCandidateComponents(BASE_PACKAGE)) {

			try {
				classList.add(Class.forName(bd.getBeanClassName()));
			} catch (ClassNotFoundException e) {

				LOG.error("problème de déploiement du service associé au bean : " + bd.getBeanClassName());
			}
		}
		
		return classList;
	}

	public String interfaceName(String classeName) {
		return classeName.replaceAll("(?i)(" + IMPL_PACKAGE + ")(.+?)(" + IMPL_SUFFIX + ")", "$2");
	}

	public String moduleName(Class classe) {

		String classeName = classe.getCanonicalName();

		int indexStart = classeName.indexOf('.', BASE_PACKAGE.length() + 1);
		int indexEnd = classeName.indexOf('.', classeName.lastIndexOf('.') - ".dl.dto".length());

		return classeName.substring(indexStart + 1, indexEnd).replace("", "/");
	}
}