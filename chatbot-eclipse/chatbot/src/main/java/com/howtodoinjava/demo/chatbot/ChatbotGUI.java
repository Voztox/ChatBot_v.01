package com.howtodoinjava.demo.chatbot;

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
import java.net.URLEncoder;
import java.io.IOException;

public class ChatbotGUI extends JFrame {
    // RapidAPI Key
    private static final String RAPID_API_KEY = "b47b0922e4msh19452d17bed706cp1ad4bdjsn6a8c20371cb6";

    // RapidAPI Host
    private static final String RAPID_API_HOST = "visual-crossing-weather.p.rapidapi.com";

    private JTextArea conversationArea;
    private JTextField inputField;
    private Chat chatSession;
    private int locationIndex = 0;
    private String[] locations = new String[5];

    public ChatbotGUI(Chat chatSession) {
        this.chatSession = chatSession;
        setTitle("NoName");
        setSize(850, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        conversationArea = new JTextArea();
        conversationArea.setEditable(false);
        conversationArea.setFont(new Font("Arial", Font.PLAIN, 24));
        JScrollPane scrollPane = new JScrollPane(conversationArea);
        add(scrollPane, BorderLayout.CENTER);

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

        askForNextLocation();

        setVisible(true);
    }

    private void askForNextLocation() {
        conversationArea.append("Please enter destination " + (locationIndex + 1) + ": ");
    }

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

    private void printTemperaturesForNextThreeDays(String location, String weatherResponse) {
        String[] splitter = weatherResponse.split("\n");
        conversationArea.append("Temperatures for " + location + ":\n");
        for (int i = 1; i <= 3 && i < splitter.length; i++) {
            String[] data = splitter[i].split(",");
            double fahrenheit = Double.parseDouble(data[9]);
            double celsius = (fahrenheit - 32) * 5 / 9;
            conversationArea.append("Day " + i + ": " + celsius + " degrees Celsius\n");
        }
        conversationArea.append("\n");
    }

    private double getCurrentTemperature(String weatherResponse) {
        String[] splitter = weatherResponse.split("\n");
        String[] data = splitter[1].split(",");
        double fahrenheit = Double.parseDouble(data[9]);
        return (fahrenheit - 32) * 5 / 9;
    }

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

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        String resourcesPath = path + File.separator + "src"
                + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }

    private void addMessage(String sender, String message) {
        conversationArea.append(sender + ": " + message + "\n");
    }

    public static String suggestClothing(double temperature) {
        if (temperature < 5) {
            return "You should wear a light jacket.";
        } else if (temperature < 17) {
            return "You should wear a shirt.";
        } else {
            return "You should wear Borat's mankini";
        }
    }
}
