package com.controlup.automation.restapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.controlup.automation.listeners.ApiAllureListener;
import org.testng.annotations.Listeners;

import com.controlup.automation.BaseTest;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

@Listeners({ApiAllureListener.class})
public class BaseApiTest extends BaseTest {
	
	public final static class RestWeather {
		public final static String URL = "http://api.weatherapi.com/v1/current.json";
		public final static String SCHEME = "rest/weather-schema.json";
		public final static Map<String, String> HEADERS = new HashMap<String, String>();
		
		static{
			HEADERS.put("key","5416a61c915f41e7b60133951222110");
		}
	}
	
	public class RestFilter implements Filter{
		private String dumpName;
		
		public RestFilter (String dumpName) {
			this.dumpName = dumpName;
		}
		
		@Override
		public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
			
			Response resp = ctx.next(requestSpec, responseSpec);
	        File myJsonFile = new File(Paths.get("test-output", dumpName + ".json").toAbsolutePath().toString());
	        
	        try (FileWriter fw = new FileWriter(myJsonFile);) {
	        	fw.write(resp.asString());
			} catch (IOException e) { e.printStackTrace(); }
	        
			return resp;
		}
	}
	
	private static String readFromResourse(String filePath){
		String content = "";
		InputStream fileName = Thread.currentThread().getClass().getResourceAsStream(filePath);
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileName))){
		    content = reader.lines().collect(Collectors.joining(System.lineSeparator()));			
		} catch (IOException e) {}
		
		return content;
	}
}
