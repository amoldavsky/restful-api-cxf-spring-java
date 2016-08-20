package com.projectx.sdk.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> extends BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 7945649792014593723L;

    @JsonCreator
    public ApiResponse(
            @JsonProperty("code") int resultCode,
            @JsonProperty("message") String message,
            @JsonProperty("data") T data ) {

        super( resultCode, message, data );

    }

    public ApiResponse(
            int resultCode,
            String message ) {

        super( resultCode, message );
    }
}