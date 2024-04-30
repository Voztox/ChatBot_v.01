//Importing required Packages
package com.howtodoinjava.demo.chatbot;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//End of imports 

public class Chatbot {
	// RapidAPI Key
	private static final String RAPID_API_KEY = "5cbd79b3f8mshd316ad243985354p122029jsn597e43ad9374";

	// RapidAPI Host
	private static final String RAPID_API_HOST = "visual-crossing-weather.p.rapidapi.com";

	public Chatbot(OkHttpClient httpClient) {
		try {
			Scanner scanner = new Scanner(System.in);
			String[] locations = new String[5];


			// Ask the user to enter 5 locations
			for (int i = 0; i < 5; i++) {
				System.out.print("Hi, please enter destination " + (i + 1) + ": ");
				locations[i] = scanner.nextLine();
			}

			//<<<<<<< HEAD
			// Loop for each location
			for (String location : locations) {
				// Call API
				String weatherResponse = getWeatherResponse(location);

				// Print temperatures for the next three days
				printTemperaturesForNextThreeDays(location, weatherResponse);

				// Get current temperature
				double currentTemperature = getCurrentTemperature(weatherResponse);

				// Suggest clothing based on current temperature
				String clothingSuggestion = suggestClothing(currentTemperature);
				System.out.println("Clothing suggestion for " + location + ": " + clothingSuggestion);
			}

			scanner.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	//<<<<<<< HEAD
	private static void printTemperaturesForNextThreeDays(String location, String weatherResponse) {
		// Split by line
		String[] splitter = weatherResponse.split("\n");

		System.out.println("Temperatures for " + location + ":");
		for (int i = 1; i <= 3 && i < splitter.length; i++) {
			String[] data = splitter[i].split(",");

			// 9th element is temperature
			double fahrenheit = Double.parseDouble(data[9]);
			double celsius = (fahrenheit - 32) * 5 / 9;

			// Print the temperature for 3 days
			System.out.println("Day " + i + ": " + celsius + " degrees Celsius");
		}
		System.out.println();
	}

	public static double getCurrentTemperature(String weatherResponse) {
		// Split by line
		String[] splitter = weatherResponse.split("\n");

		// First line contains current weather data
		String[] data = splitter[1].split(",");

		// 9th element is temperature
		double fahrenheit = Double.parseDouble(data[9]);
		return (fahrenheit - 32) * 5 / 9;
	}

	public String getWeatherResponse(String location) throws IOException {

		OkHttpClient client = new OkHttpClient();

		// Encode the location
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
				throw new IOException("Error " + response);
			}
		}
	}
	//clothing suggesstion based on temperature, if u want to add more based on other conditions use different index F.E double fahrenheit = Double.parseDouble(data[11]);
	// Update suggestClothing() method in your Chatbot class

	// Update suggestClothing() method in your Chatbot class

	public static String suggestClothing(double temperature) {
	    if (temperature < -5) { // Update condition for very cold temperatures
	        return "You should wear a heavy coat."; // Suggest wearing a heavy coat for very cold temperatures
	    } else if (temperature < 5) {
	        return "You should wear a light jacket.";
	    } else if (temperature < 17) {
	        return "You should wear a shirt.";
	    } else if (temperature < 25) {
	        return "You should wear shorts.";
	    } else {
	        return "You should wear Borat's mankini";
	    }
	}


}
