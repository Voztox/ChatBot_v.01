package com.howtodoinjava.demo.chatbot;

// Import necessary libraries
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

// Main class for the Chatbot GUI
public class ChatbotGUI extends JFrame {
	// RapidAPI Key for accessing the weather API
	private static final String RAPID_API_KEY = "16648ceb4dmsh7173f54cfdee342p1be6dajsn8282c066674d";
	// RapidAPI Host for accessing the weather API
	private static final String RAPID_API_HOST = "visual-crossing-weather.p.rapidapi.com";

	// Components for the GUI
	private JTextArea conversationArea; // Area to display conversation
	private JTextField inputField; // Field for user input
	private Chat chatSession; // Chat session object for handling interactions
	private int locationIndex = 0; // Index to track the number of locations entered
	private String[] locations = new String[5]; // Array to store user-entered locations

	// Constructor for ChatbotGUI
	public ChatbotGUI(Chat chatSession) {
		this.chatSession = chatSession; // Initialize the chat session
		setTitle("NoName"); // Set window title
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Set default close operation
		setLayout(new BorderLayout()); // Set layout manager

		// Get the screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Set the size to 75% of the screen width and height
		int width = (int) (screenSize.width * 0.75);
		int height = (int) (screenSize.height * 0.75);
		setSize(width, height);

		// Center the frame on the screen
		setLocationRelativeTo(null);

		// Create and configure conversation area
		conversationArea = new JTextArea();
		conversationArea.setEditable(false); // Make the conversation area read-only
		conversationArea.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font
		JScrollPane scrollPane = new JScrollPane(conversationArea); // Add scroll pane to conversation area
		add(scrollPane, BorderLayout.CENTER); // Add conversation area to the center of the layout

		// Create and configure input field
		inputField = new JTextField();
		inputField.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font
		inputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = inputField.getText(); // Get text from input field
				addMessage("You", input); // Add user message to conversation area
				inputField.setText(""); // Clear the input field

				if (locationIndex < 5) {
					locations[locationIndex++] = input; // Store the input location
					if (locationIndex == 5) {
						fetchWeatherAndSuggestClothing(); // Fetch weather and suggest clothing after 5 locations
					} else {
						askForNextLocation(); // Ask for the next location
					}
				}
			}
		});
		add(inputField, BorderLayout.SOUTH); // Add input field to the bottom of the layout

		// Prompt user to enter the first location
		askForNextLocation();

		setVisible(true); // Make the frame visible
	}

	// Prompt user to enter the next location
	private void askForNextLocation() {
		conversationArea.append("Please enter destination " + (locationIndex + 1) + ": ");
	}

	// Fetch weather information for each location and suggest clothing
	private void fetchWeatherAndSuggestClothing() {
		for (String location : locations) {
			addMessage("NoName Chatbot", "Fetching weather for " + location);
			try {
				String weatherResponse = getWeatherResponse(location); // Fetch weather data
				printTemperaturesForNextThreeDays(location, weatherResponse); // Print temperatures
				double currentTemperature = getCurrentTemperature(weatherResponse); // Get current temperature
				String clothingSuggestion = suggestClothing(currentTemperature); // Get clothing suggestion
				String precipitationInfo = getPrecipitationPercentage(weatherResponse); // Get precipitation information
				addMessage("NoName Chatbot", "Clothing suggestion for " + location + ": " + clothingSuggestion);
				addMessage("NoName Chatbot", "Precipitation information for " + location + ": " + precipitationInfo);
			} catch (Exception e) {
				addMessage("NoName Chatbot", "Error fetching weather for " + location);
			}
		}
	}

	// Print temperatures for the next three days for a location
	private void printTemperaturesForNextThreeDays(String location, String weatherResponse) {
		String[] splitter = weatherResponse.split("\n");
		conversationArea.append("Temperatures for " + location + ":\n");
		for (int i = 1; i <= 3 && i < splitter.length; i++) {
			String[] data = splitter[i].split(",");
			double fahrenheit = Double.parseDouble(data[9]);
			double celsius = (fahrenheit - 32) * 5 / 9;
			celsius = Math.round(celsius * 10.0) / 10.0; // Round the temperature to one decimal place
			conversationArea.append("Day " + i + ": " + celsius + " degrees Celsius\n");
		}
		conversationArea.append("\n");
	}

	// Get current temperature from weather response
	private double getCurrentTemperature(String weatherResponse) {
		String[] splitter = weatherResponse.split("\n");
		String[] data = splitter[1].split(",");
		double fahrenheit = Double.parseDouble(data[9]);
		return (fahrenheit - 32) * 5 / 9;
	}

	// Get weather response from API
	private String getWeatherResponse(String location) throws Exception {
		OkHttpClient client = new OkHttpClient();
		String encodedLocation = URLEncoder.encode(location, "UTF-8"); // Encode location for URL
		Request request = new Request.Builder()
				.url("https://visual-crossing-weather.p.rapidapi.com/forecast?location=" + encodedLocation + "&aggregateHours=24&shortColumnNames=0&unitGroup=us&contentType=csv")
				.get()
				.addHeader("X-RapidAPI-Key", RAPID_API_KEY) // Add API key header
				.addHeader("X-RapidAPI-Host", RAPID_API_HOST) // Add API host header
				.build();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				return response.body().string(); // Return the response body as a string
			} else {
				throw new IOException("Error " + response.code()); // Throw exception if response is not successful
			}
		}
	}

	// Main method to start the application
	public static void main(String[] args) {
		try {
			String resourcesPath = getResourcesPath(); // Get resources path
			MagicBooleans.trace_mode = false; // Disable tracing mode
			Bot bot = new Bot("super", resourcesPath); // Create a new bot instance
			Chat chatSession = new Chat(bot); // Create a new chat session
			new ChatbotGUI(chatSession); // Create and display the GUI
		} catch (Exception e) {
			e.printStackTrace(); // Print stack trace in case of an exception
		}
	}

	// Get resources path for AIML files
	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		String resourcesPath = path + File.separator + "src"
				+ File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}

	// Add a message to the conversation area
	private void addMessage(String sender, String message) {
		conversationArea.append(sender + ": " + message + "\n"); // Append message to conversation area
		conversationArea.setCaretPosition(conversationArea.getDocument().getLength()); // Scroll to the bottom
	}

	// Suggest clothing based on temperature
	public static String suggestClothing(double temperature) {
		if (temperature < -10) { // Very cold
			return "You should wear thermal underwear, a heavy coat, a hat, gloves, and a scarf.";
		} else if (temperature < 0) { // Cold
			return "You should wear a heavy coat, a hat, gloves, and a scarf.";
		} else if (temperature < 5) { // Chilly
			return "You should wear a thick jacket and a warm hat.";
		} else if (temperature < 10) { // Cool
			return "You should wear a jacket and possibly a light hat.";
		} else if (temperature < 15) { // Mild
			return "You should wear a light jacket or a sweater.";
		} else if (temperature < 20) { // Pleasant
			return "You can wear a long-sleeved shirt or a light sweater.";
		} else if (temperature < 25) { // Warm
			return "You can wear a short-sleeved shirt and pants or shorts.";
		} else if (temperature < 30) { // Hot
			return "You should wear shorts and a t-shirt.";
		} else { // Very hot
			return "You should wear lightweight, breathable clothing, such as a tank top and shorts.";
		}
	}

	// Get precipitation information
	public static String getPrecipitationPercentage(String weatherResponse) {
		// Split by lines
		String[] splitter = weatherResponse.split("\n");
		String ret = "not set";
		// If API weather response contains multiple lines
		if (splitter.length >= 2) {
			// We would also have to split the second line
			String[] data = splitter[1].split(",");
			// Try-catch block
			try {
				// Ensure that the index exists and is not empty
				if (data.length > 14 && !data[14].isEmpty()) {
					double precipitationPercentage = Double.parseDouble(data[14]);
					System.out.println("Percentage of Precipitation: " + precipitationPercentage);

					// Messages that come with each percentage of precipitation
					if (precipitationPercentage >= 90.0) { // Greater or equal to 90
						ret = "Alert - Heavy Rainfall! \n ";
					} else if (precipitationPercentage >= 70.0) { // 89-70
						ret = "Heavy Rain Expected \n";
					} else if (precipitationPercentage >= 50.0) { // 69-50
						ret = "Moderate Rain Expected \n";
					} else if (precipitationPercentage >= 30.0) { // 49-30
						ret = "Light Rain Expected \n";
					} else if (precipitationPercentage >= 10.0) { // 29-10
						ret = "Slight Chance of Showers \n";
					} else {
						ret = "No Rain Expected \n"; // 9 and less
					}
				} else {
					ret = "Precipitation data not available \n";
				}
			} catch (NumberFormatException e) {
				// Handle parsing errors if any
				e.printStackTrace();
			}
		}
		return ret;
	}
}
