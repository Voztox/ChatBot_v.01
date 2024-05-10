package com.howtodoinjava.demo.chatbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

public class Chatbot {
    private static final boolean TRACE_MODE = false;
    static String botName = "super";
    static Bot bot;
    static Chat chatSession;

    public static void main(String[] args) {
        try {
            initializeBot();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            boolean continueChat = true;

            while (continueChat) {
                System.out.print("Human : ");
                String textLine;
                try {
                    textLine = reader.readLine();
                    if (textLine == null) {
                        // Input stream closed
                        System.err.println("Input stream closed. Exiting.");
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error reading input. Please try again.");
                    continue; // Continue the loop to prompt the user for input again
                }

                if (textLine.trim().isEmpty()) {
                    // Skip empty input lines
                    continue;
                }

                continueChat = processInput(textLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String suggestClothing(double temperature) {
        if (temperature < 5) {
            return "You should wear a light jacket.";
        } else if (temperature < 17) {
            return "You should wear a shirt.";
        } else {
            return "You should wear shorts";
        }
    }

    private static void initializeBot() {
        try {
            String resourcesPath = getResourcesPath();
            MagicBooleans.trace_mode = TRACE_MODE;
            bot = new Bot("super", resourcesPath);
            chatSession = new Chat(bot);
            bot.brain.nodeStats();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean processInput(String textLine) {
        if (textLine.equalsIgnoreCase("WHAT IS THE WEATHER")) {
            System.out.println("Fetching weather information...");
            // After providing weather data, reinitialize the bot
            initializeBot();
        } else if (textLine.equalsIgnoreCase("weather")) {
            // Launch ChatbotStart if input is "weather"
            ChatbotStart chatbotStart = new ChatbotStart();
            chatbotStart.main(new String[]{});
            // After launching ChatbotStart, reinitialize the bot
            initializeBot();
            return false; // Exit the loop after launching ChatbotStart
        } else if (textLine.equalsIgnoreCase("q")) {
            System.exit(0);
        } else if (textLine.equalsIgnoreCase("wq")) {
            bot.writeQuit();
            System.exit(0);
        } else {
            String request = textLine;
            if (MagicBooleans.trace_mode)
                System.out.println(
                        "STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
                                + ":TOPIC=" + chatSession.predicates.get("topic"));
            String response = chatSession.multisentenceRespond(request);
            while (response.contains("&lt;"))
                response = response.replace("&lt;", "<");
            while (response.contains("&gt;"))
                response = response.replace("&gt;", ">");
            System.out.println("Robot : " + response);
        }
        return true; // Continue the loop
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}

