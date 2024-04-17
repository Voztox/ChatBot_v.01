package com.howtodoinjava.demo.chatbot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.mockito.Mockito;

class ChatbotTest {
	
	//declaring variables
		private Chatbot chatbot; 
		private OkHttpClient httpClientMock;

	@BeforeEach
	void setUp() throws Exception {//this method must be implemented before each test
		//mock instance of HTTP 
		httpClientMock = Mockito.mock(OkHttpClient.class);
		//Initialize instance of location.
		chatbot = new Chatbot(httpClientMock);
	}

	//getWeatherResponse method:::::
	@Test
	void getWeatherDublin() throws Exception{
		//this code is more the same as the response request code in main.
		//creating a mocked Response message
		String mockedResponse =  "Mocked Weather Data";
		//create a mock call object::
		Call callMock = Mockito.mock(Call.class);
		//creating a mock behavior of HTTP request, using mockito.
//		Mockito.when(httpClientMock.newCall(Mockito.any(Request.class)).thenReturn()){;
		
//		}
	}

}
