package com.howtodoinjava.demo.chatbot;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.utils.IOUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChatbotStart {
    // API keys
    private static final String RAPID_API_KEY = "5cbd79b3f8mshd316ad243985354p122029jsn597e43ad9374";
    private static final String RAPID_API_HOST = "visual-crossing-weather.p.rapidapi.com";

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String[] locations = new String[5];  // Stores locations

            //  input 5 locations
            for (int i = 0; i < 5; i++) {
                System.out.print("Hi, please enter destination " + (i + 1) + ": ");
                locations[i] = scanner.nextLine();
            }

            // location for weather data
            for (String location : locations) {
                String weatherResponse = getWeatherResponse(location);
                printTemperaturesForNextThreeDays(location, weatherResponse);
                double currentTemperature = getCurrentTemperature(weatherResponse);
                String clothingSuggestion = suggestClothing(currentTemperature);
                System.out.println("Percentage of Precipitation: " + getPrecipitationPercentage(weatherResponse));
                System.out.println("Clothing suggestion for " + location + ": " + clothingSuggestion);
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Prints temperature for the next three days
    private static void printTemperaturesForNextThreeDays(String location, String weatherResponse) {
        String[] splitter = weatherResponse.split("\n");

        System.out.println("Temperatures for " + location + ":");
        for (int i = 1; i <= 3 && i < splitter.length; i++) {
            String[] data = splitter[i].split(",");
            double fahrenheit = Double.parseDouble(data[9]);
            double celsius = (fahrenheit - 32) * 5 / 9;
            System.out.println("Day " + i + ": " + Math.round(celsius) + " degrees Celsius");
        }
        System.out.println();
    }

    //  provide precipitation information for the next three days
    public static String getPrecipitationPercentage(String weatherResponse) {
        String[] lines = weatherResponse.split("\n");
        StringBuilder result = new StringBuilder();

        for (int i = 1; i <= 3 && i < lines.length; i++) {
            String[] data = lines[i].split(",");
            try {
                double precipitationPercentage = Double.parseDouble(data[14]);
                result.append("Day ").append(i).append(": ");
                if (precipitationPercentage >= 90.0) {
                    result.append("Alert - Heavy Rainfall!");
                } else if (precipitationPercentage >= 70.0) {
                    result.append("Heavy Rain Expected");
                } else if (precipitationPercentage >= 50.0) {
                    result.append("Moderate Rain Expected");
                } else if (precipitationPercentage >= 30.0) {
                    result.append("Light Rain Expected");
                } else if (precipitationPercentage >= 10.0) {
                    result.append("Slight Chance of Showers");
                } else {
                    result.append("No Rain Expected");
                }
                result.append("\n");
            } catch (NumberFormatException e) {
                System.err.println("Error parsing precipitation data for day " + i);
                result.append("Day ").append(i).append(": Error parsing data\n");
            }
        }

        return result.toString().trim();
    }

    // Calculates the current temperature 
    private static double getCurrentTemperature(String weatherResponse) {
        String[] splitter = weatherResponse.split("\n");
        String[] data = splitter[1].split(",");
        double fahrenheit = Double.parseDouble(data[9]);
        return Math.round((fahrenheit - 32) * 5 / 9);
    }

    // Fetches weather data from an API 
    private static String getWeatherResponse(String location) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String encodedLocation = URLEncoder.encode(location, "UTF-8");
        Request request = new Request.Builder()
                .url("https://visual-crossing-weather.p.rapidapi.com/forecast?aggregateHours=24&location="
                        + encodedLocation + "&contentType=csv&unitGroup=us&shortColumnNames=0")
                .get()
                .addHeader("X-RapidAPI-Key", RAPID_API_KEY)
                .addHeader("X-RapidAPI-Host", RAPID_API_HOST)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Error " + response.code());
            }
        }
    }

    // Suggests appropriate clothing 
    public static String suggestClothing(double temperature) {
        if (temperature < 5) {
            return "You should wear a light jacket.";
        } else if (temperature < 17) {
            return "You should wear a shirt.";
        } else {
            return "You should wear shorts";
        }
    }
}
