package com.howtodoinjava.demo.chatbot;

import static org.junit.Assert.*;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.junit.Before;
import org.junit.Test;

public class LocationsTest {
	//declaring variables
	Locations location; 

	@Before
	public void setUp() throws Exception {
		//this method must be implemented before each test.
		//Initialize instance of location.
		this.location = new Locations(null);
		
		OkHttpClient client = new OkHttpClient();
	        // API request with key and location set to user input.
	    Request request = (new Request.Builder())
	    		.url("https://visual-crossing-weather.p.rapidapi.com/forecast?aggregateHours=24&location=Dublin%2C%20IE&contentType=csv&unitGroup=us&shortColumnNames=0")
	    		.get().addHeader("X-RapidAPI-Key", "b47b0922e4msh19452d17bed706cp1ad4bdjsn6a8c20371cb6")
	    		.addHeader("X-RapidAPI-Host", "visual-crossing-weather.p.rapidapi.com")
	    		.build();
	    
	    
	    client.newCall(request).enqueue(new Callback() {// prints request in console log
            
	    	//exceptions
	    	public void onFailure(Call call, IOException e) {
                e.printStackTrace();// prints fail cause
            }

	    	//catch responses and close connection.
            public void onResponse(Call call, Response response) throws IOException {
                
            	if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response); //prints error when response is received
                } else {
                	//stores response as a new variable 
                    String responseData = response.body().string();
                    //and prints it
                    System.out.println(responseData);
                    //close connection
                    response.close();
                }
            }
        });
	    
	    
	}//setUp

	@Test
	public void test() {
		
		fail("Not yet implemented");
	}

}//locationTest
