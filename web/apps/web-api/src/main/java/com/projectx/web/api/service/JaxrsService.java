package com.projectx.web.api.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is a custom annotation to tag all
 * the Jax-rs endpoints. 
 * 
 * Instead of us adding each
 * service into the CXF stack we will get all classes with this
 * annotation and add them to the list. 
 * 
 * @author Assaf Moldavsky
 */
@Target(value = { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JaxrsService {

}
