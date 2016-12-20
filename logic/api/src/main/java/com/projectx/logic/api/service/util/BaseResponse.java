package com.projectx.logic.api.service.util;

/**
 * A base class for a response object.
 * 
 * @author Assaf Moldavsky
 */

public class BaseResponse {
    
	public static final int SUCCESS = 0;
    public static final int FAILURE = -1;

    public BaseResponse( int resultCode ) {
        this.resultCode = resultCode;
    }

    public BaseResponse( int resultCode, String message ) {
        this.resultCode = resultCode;
        this.message = message;
    }

    protected int resultCode;

    /**
     * Get the value of resultCode
     *
     * @return the value of resultCode
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * Set the value of resultCode
     *
     * @param resultCode new value of resultCode
     */
    public void setResultCode( int resultCode ) {
        this.resultCode = resultCode;
    }
    protected String message;

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

    public boolean isSuccess() {
        return resultCode == SUCCESS;
    }
    
    /**
     * returns failure if result code is less than 0. This allows for multiple error codes so long
     * as they are all negative
     * 
     * @return
     */
    public boolean isFailure() {
        return resultCode < 0;
    }

}