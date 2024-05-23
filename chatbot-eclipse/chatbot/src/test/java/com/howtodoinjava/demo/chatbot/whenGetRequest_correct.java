package com.howtodoinjava.demo.chatbot;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

public class whenGetRequest_correct {

    @Test
    public void whenGetRequest_thenCorrect() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
        		.url("https://visual-crossing-weather.p.rapidapi.com/forecast?aggregateHours=24&location=Dublin%2C%20IE&contentType=csv&unitGroup=us&shortColumnNames=0")
                .get()
        		.addHeader("X-RapidAPI-Key", "16648ceb4dmsh7173f54cfdee342p1be6dajsn8282c066674d")
        		.addHeader("X-RapidAPI-Host", "visual-crossing-weather.p.rapidapi.com")
        		.build();
        try {
            Response response = client.newCall(request).execute();
            //when result equals 200 returns correct
            Assert.assertEquals(200, response.code());
        } catch (IOException e) {
            e.printStackTrace();// prints cause of failure
            Assert.fail("An exception occurred during the HTTP request: " + e.getMessage());
        }
    }
}
