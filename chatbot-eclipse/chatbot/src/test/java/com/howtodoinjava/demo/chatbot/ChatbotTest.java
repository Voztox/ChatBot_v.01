package com.howtodoinjava.demo.chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.mockito.Mock;
import org.mockito.Mockito;


public class ChatbotTest {
	
	@Mock
	//declaring variables 
	private OkHttpClient httpClientMock;
	private Chatbot chatbot;

	@BeforeEach
	void setUp() throws Exception {//this method must be implemented before each test
		//mock instance of HTTP 
		httpClientMock = Mockito.mock(OkHttpClient.class);
		//Initialize instance of location.
		chatbot = new Chatbot(httpClientMock);

	}
	
	@Test
	void getWeatherResponseDublin() throws Exception{
		//1)create a response
		//2)create mock call response.
		//String apiURL = "https://visual-crossing-weather.p.rapidapi.com/forecast?aggregateHours=24&location=";
//	    String  responseCnt = "Dublin,2024-04-15T12:00:00-04:00,2024-04-15,69.9,Fog\n" +
//	            "Dublin,2024-04-16T12:00:00-04:00,2024-04-16,72.5,Partially Clear\n" +
//	            "Dublin,2024-04-17T12:00:00-04:00,2024-04-17,68.3,Light Rain\n";
		
		String responseCnt = "Dublin, 04/17/2024, Dublin, Ireland, Dublin, Rain, Overcast\n" +
				"Dublin, 04/18/2024, Dublin, Ireland, Dublin, Partially cloudy\n" +
				"Dublin, 04/19/2024, Dublin, Ireland, Dublin, Overcast\n" +
				"Dublin, 04/20/2024, Dublin, Ireland, Dublin, Partially cloudy\n" +
				"Dublin, 04/21/2024, Dublin, Ireland, Dublin, Clear\n"+
				"Dublin, 04/22/2024, Dublin, Ireland, Dublin, Partially cloudy\n"+
				"Dublin, 04/23/2024, Dublin, Ireland, Dublin, Partially cloudy\n"+
				"Dublin, 04/24/2024, Dublin, Ireland, Dublin, Partially cloudy\n";
		
	 // Create a mock Call object
	    Call callMock = Mockito.mock(Call.class);
	    Response mockResponse = new Response.Builder()
	            .code(200) // HTTP status code
	            .message("OK") // Status message
	            .protocol(Protocol.HTTP_1_1) // HTTP protocol version
	            .request(new Request.Builder().url("https://visual-crossing-weather.p.rapidapi.com/forecast?aggregateHours=24&location=Dublin").build()) // URL of the request
	            .body(ResponseBody.create(responseCnt, MediaType.parse("text/csv"))) // Response body content
	            .build();
	    
	    Mockito.when(httpClientMock.newCall(Mockito.any(Request.class))).thenReturn(callMock);
	    Mockito.when(callMock.execute()).thenReturn(mockResponse);
	    
	    // Invoke the method under test
	    String weatherResponse = chatbot.getWeatherResponse("Dublin");

	    // Assert the result
	    //assertEquals(responseCnt, weatherResponse);
	    assertTrue(weatherResponse.contentEquals("Dublin")); // Assuming this part should be present in the weather response
	    
	    
	}
	
	@Test 
	void getCurrTempTestOne() {
		//mock expected response:
		String responseCnt = "Dublin, 04/17/2024, Dublin, Ireland, Dublin, Rain, Overcast\n" +
				"Dublin, 04/18/2024, Dublin, Ireland, Dublin, Partially cloudy\n" +
				"Dublin, 04/19/2024, Dublin, Ireland, Dublin, Overcast\n" +
				"Dublin, 04/20/2024, Dublin, Ireland, Dublin, Partially cloudy\n" +
				"Dublin, 04/21/2024, Dublin, Ireland, Dublin, Clear\n"+
				"Dublin, 04/22/2024, Dublin, Ireland, Dublin, Partially cloudy\n"+
				"Dublin, 04/23/2024, Dublin, Ireland, Dublin, Partially cloudy\n"+
				"Dublin, 04/24/2024, Dublin, Ireland, Dublin, Partially cloudy\n";
		
		//calling the chatbot get current temperature method.
		double temp = chatbot.getCurrentTemperature(responseCnt);
		//assert equal the expected values
		Assert.assertEquals(responseCnt, temp);
	}
	
	
	
	
	//Testing if basic  temperatures for 
	//Clothing Suggestion methods works:::

	@Test
	void suggest_HeavyCoat() {
		//for temperature of -4 clothing suggetions:
		double temp = -4.0;
		//calling the Suggest Clothing method in chatbot
				String clothingSuggestion = Chatbot.suggestClothing(temp);
				//assert equal the result:
				Assert.assertEquals("You should wear a heavy coat.", clothingSuggestion);
					
	}
	
	@Test
	void suggest_LightJacketTest() {
		//for temperature 5 and 17degree
		double temp = 4.0;
		//calling the Suggest Clothing method in chatbot
		String clothingSuggestion = Chatbot.suggestClothing(temp);
		//assert equal the result:
		Assert.assertEquals("You should wear a light jacket.", clothingSuggestion);
	}

	@Test
	void suggest_ShirtTest() {
		//for temperature 5 and 17degree
		double temp = 10.0;
		//calling the Suggest Clothing method in chatbot
		String clothingSuggestion = Chatbot.suggestClothing(temp);
		//assert equal the result:
		Assert.assertEquals("You should wear a shirt.", clothingSuggestion);
	}
	
	void suggest_ShortTest() {
		//for temperature 5 and 17degree
		double temp = 20.0;
		//calling the Suggest Clothing method in chatbot
		String clothingSuggestion = Chatbot.suggestClothing(temp);
		//assert equal the result:
		Assert.assertEquals("You should wear shorts.", clothingSuggestion);
	}
	
	
	@Test
	void suggest_Mankini() {
		//for temperature 5 and 17degree
		double temp = 25;

		//implementing a con
		if (temp >= 25) {
			//calling the Suggest Clothing method in chatbot
			String clothingSuggestion = Chatbot.suggestClothing(temp);
			//assert Equal the conditon
			Assert.assertEquals("You should wear a Borat's mankini.", clothingSuggestion);
		}
	}
	
	
	
	//Testing clothing suggestion on more temperature range range of temperatures:::
	
	@Test
	void heavyCoatForTempMinusTen() {
		
	}
	@Test
	void lightJacketForTempThree() {
		
	}
	@Test
	void shirtForTempTen() {
		
	}
	@Test
	void shortForTempTwentyTwo() {
		
	}
	@Test
	void mankiniForTempTwentySix() {
		
	}
	
	
	
	
	
	
	
	
	
}
