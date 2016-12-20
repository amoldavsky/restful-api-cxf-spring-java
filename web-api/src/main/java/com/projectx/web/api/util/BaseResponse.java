package com.projectx.web.api.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * A base class for a response object.
 * 
 * @author Assaf Moldavsky
 */

public class BaseResponse<T> implements Serializable {
    
	private static final long serialVersionUID = 2616215761549934975L;
	
	public static final int SUCCESS = 0;
    public static final int FAILURE = -1;
    
    protected int code;
    protected String message;
    protected T data;

    public BaseResponse() {}
    
    @JsonCreator
    public BaseResponse( int code, String message, @JsonProperty("data") T data ) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * Get the value of resultCode
     *
     * @return the value of result code
     */
    public int getCode() {
        return code;
    }

    /**
     * Set the value of resultCode
     *
     * @param code new value of resultCode
     */
    public void setCode( int code ) {
        this.code = code;
    }

    /**
     * Get the value of message
     *
     * @return the value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the value of message
     *
     * @param message new value of message
     */
    public void setMessage( String message ) {
        this.message = message;
    }
    
    public void setData( T data ) {
    	this.data = data;
    }
    
    public T getData() {
    	return data;
    }

    public boolean isSuccess() {
        return code >= 0;
    }
    
    /**
     * returns failure if result code is less than 0. This allows for multiple error codes so long
     * as they are all negative
     * 
     * @return
     */
    public boolean isFailure() {
        return code < 0;
    }

}