package com.projectx.logic.api.service.util;

import com.projectx.logic.api.service.validation.ApiError;

/**
 * An extension of the BaseResponse to also take a value Object <T> with
 * the message and the resposne code.
 * 
 * This should be the default return for all services.
 * 
 * @author Assaf Moldavsky
 *
 * @param <T>
 */

public class ValueResponse<T> extends BaseResponse {

    public ValueResponse( int resultCode ) {
        super( resultCode );
    }
    
    public ValueResponse( ApiError apiError ) {
    	super( apiError.getCode(), apiError.getMessage() );
    }
    
    public ValueResponse( int resultCode, String message ) {
        super(resultCode, message);
    }

    public ValueResponse( int resultCode, String message, T value ) {
        super(resultCode, message);
        this.value = value;
    }

    protected T value;

    /**
     * Get the value Object
     *
     * @return the value Object
     */
    public T getValue() {
        return value;
    }

    /**
     * Set the value Object
     *
     * @param value  a value Object
     */
    public void setValue( T value ) {
        this.value = value;
    }

}