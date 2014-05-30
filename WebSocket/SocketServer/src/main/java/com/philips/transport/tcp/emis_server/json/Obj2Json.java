package com.philips.transport.tcp.emis_server.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.codehaus.jackson.JsonGenerationException;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Obj2Json {
	
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    public static String obj2Json(Object obj) throws JsonGenerationException, JsonMappingException, IOException{
    	
    	return objectMapper.writeValueAsString(obj);
    }

}
