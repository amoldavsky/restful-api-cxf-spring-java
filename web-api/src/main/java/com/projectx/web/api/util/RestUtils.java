package com.projectx.web.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.projectx.logic.api.service.util.ValueResponse;
import com.projectx.sdk.api.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class RestUtils {

	/**
     * Helper to generate an HTTP error exception.
     * Throwing this will cause CXF to return something other than
     * code 200.
     *
     * @param httpStatusCode
     * @param apiResponse
     * @return                  instance of WebApplicationException
     */
	// TODO: add unit tests
    public static WebApplicationException generateError( int httpStatusCode, ApiResponse apiResponse ) {

        apiResponse.setResultCode( ApiResponse.FAILURE );
        Response response = Response.status( httpStatusCode ).entity( apiResponse ).build();
        return new WebApplicationException( response );

    }
    
    // TODO: add unit tests
    public static <T> ApiResponse createApiResponse( ValueResponse<T> resp ) {
        return new ApiResponse( resp.getResultCode(), resp.getMessage(), resp.getValue() );
    }
    
    // TODO: add unit tests
    public static Integer parseInt( String str, Integer defaultValue ) {

        if( StringUtils.isEmpty( str ) ) return defaultValue;

        Integer value = null;
        try {
            value = Integer.valueOf( str );
        } catch( Throwable t ) {

        }

        return value == null ? defaultValue : value;
    }

    // TODO: add unit tests
	public static <T> Integer validateIntParam( String str, String errorMessage ) {

		if( StringUtils.isEmpty( str ) || "null".equals( str ) ) {
			throw generateError(
					HttpServletResponse.SC_BAD_REQUEST,
					new ApiResponse<T>(
							ApiResponse.FAILURE,
							errorMessage,
							null
					)
			);
		}

		Integer value = null;
		try {
			value = Integer.valueOf( str );
		} catch( Throwable t ) {

			throw generateError(
					HttpServletResponse.SC_BAD_REQUEST,
					new ApiResponse<T>(
							ApiResponse.FAILURE,
							errorMessage,
							null
					)
			);

		}

		return value;
	}
	
    public static WebClient initCxf() {
    	return initCxf( new ObjectMapper(), "" );
    }
    
	public static WebClient initCxf( ObjectMapper objectMapper, String baseUtl ) {
		
		final List<Object> providers = new ArrayList<Object>();
		JacksonJaxbJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider();
		jacksonJsonProvider.setMapper( objectMapper );
		
		providers.add( jacksonJsonProvider );
		WebClient webClient = WebClient.create( String.valueOf( baseUtl ), providers );
		webClient
			.accept( MediaType.APPLICATION_JSON )
			.type( MediaType.APPLICATION_JSON );
		
		return webClient;
		
	}
}
