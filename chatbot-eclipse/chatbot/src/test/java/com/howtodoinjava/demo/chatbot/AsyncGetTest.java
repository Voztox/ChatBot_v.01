package com.howtodoinjava.demo.chatbot;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AsyncGetTest {


    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        // API request with key and location set to dublin
        Request request = (new Request.Builder()).url("https://visual-crossing-weather.p.rapidapi.com/forecast?aggregateHours=24&location=Dublin%2C%20IE&contentType=csv&unitGroup=us&shortColumnNames=0").get().addHeader("X-RapidAPI-Key", "5cbd79b3f8mshd316ad243985354p122029jsn597e43ad9374").addHeader("X-RapidAPI-Host", "visual-crossing-weather.p.rapidapi.com").build();
        client.newCall(request).enqueue(new Callback() {// prints request in console log
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();// prints fail cause
            }

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
    }
}
