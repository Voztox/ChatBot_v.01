package com.howtodoinjava.demo.chatbot;

//Import necessary libraries
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

//Main class for the Chatbot GUI
public class ChatbotGUI extends JFrame {
 // RapidAPI Key
 private static final String RAPID_API_KEY = "5cbd79b3f8mshd316ad243985354p122029jsn597e43ad9374";

 // RapidAPI Host
 private static final String RAPID_API_HOST = "visual-crossing-weather.p.rapidapi.com";

 // Components for the GUI
 private JTextArea conversationArea;
 private JTextField inputField;
 private Chat chatSession;
 private int locationIndex = 0;
 private String[] locations = new String[5];

 // Constructor
 public ChatbotGUI(Chat chatSession) {
     this.chatSession = chatSession;
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
     conversationArea.setEditable(false);
     conversationArea.setFont(new Font("Arial", Font.PLAIN, 24));
     JScrollPane scrollPane = new JScrollPane(conversationArea);
     add(scrollPane, BorderLayout.CENTER);

     // Create and configure input field
     inputField = new JTextField();
     inputField.setFont(new Font("Arial", Font.PLAIN, 24));
     inputField.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             String input = inputField.getText();
             addMessage("You", input);
             inputField.setText("");

             if (locationIndex < 5) {
                 locations[locationIndex++] = input;
                 if (locationIndex == 5) {
                     fetchWeatherAndSuggestClothing();
                 } else {
                     askForNextLocation();
                 }
             }
         }
     });
     add(inputField, BorderLayout.SOUTH);

     // Prompt user to enter the first location
     askForNextLocation();

     setVisible(true); // Make the frame visible
 }

 // Prompt user to enter next location
 private void askForNextLocation() {
     conversationArea.append("Please enter destination " + (locationIndex + 1) + ": ");
 }

 // Fetch weather information for each location and suggest clothing
 private void fetchWeatherAndSuggestClothing() {
     for (String location : locations) {
         addMessage("NoName Chatbot", "Fetching weather for " + location);
         try {
             String weatherResponse = getWeatherResponse(location);
             printTemperaturesForNextThreeDays(location, weatherResponse);
             double currentTemperature = getCurrentTemperature(weatherResponse);
             String clothingSuggestion = suggestClothing(currentTemperature);
             addMessage("NoName Chatbot", "Clothing suggestion for " + location + ": " + clothingSuggestion);
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

 // Main method
 public static void main(String[] args) {
     try {
         String resourcesPath = getResourcesPath();
         MagicBooleans.trace_mode = false;
         Bot bot = new Bot("super", resourcesPath);
         Chat chatSession = new Chat(bot);
         new ChatbotGUI(chatSession);
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

 // Get resources path
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
     conversationArea.append(sender + ": " + message + "\n");
     conversationArea.setCaretPosition(conversationArea.getDocument().getLength());
 }

 // Suggest clothing based on temperature
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
