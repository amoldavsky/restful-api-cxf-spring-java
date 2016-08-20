package com.projectx.sdk.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A base class for a response object.
 *
 * @author Assaf Moldavsky
 */

public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 2616215761549934975L;

    public static final int SUCCESS = 0;
    public static final int FAILURE = -1;

    protected int resultCode;
    protected String message;
    protected T data;

    public BaseResponse() {}

    @JsonCreator
    public BaseResponse( int resultCode, String message, @JsonProperty("data") T data ) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public BaseResponse( int resultCode, String message ) {
        this( resultCode, message, null);
    }

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