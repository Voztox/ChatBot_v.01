package com.howtodoinjava.demo.chatbot;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

import java.io.File;
/*
 * This Chatbot.java class initializes the chatbot. 
 * sets it's trace mode and create chat.
 * there's also chatbotGUI
 * 
 */

public class Chatbot {
	//declare a final boolean variable and set it to false.
    private static final boolean TRACE_MODE = false;

    public static void main(String[] args) {
        try {
            String resourcesPath = getResourcesPath(); //calling getResourcePath in an assigned variable. 
            MagicBooleans.trace_mode = TRACE_MODE; //set chatbot trace.mode.
            Bot bot = new Bot("super", resourcesPath); //new instance of bot.
            Chat chatSession = new Chat(bot); //new instance of chat, passing in bot object.
            bot.brain.nodeStats(); //print bot.

            new ChatbotGUI(chatSession); // Launch GUI

        } catch (Exception e) { //catches exception.
            e.printStackTrace();
        }
    }

    private static String getResourcesPath() {
    	//this method calculates the path to the resource directory.
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}
