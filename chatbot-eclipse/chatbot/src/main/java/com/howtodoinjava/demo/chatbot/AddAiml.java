package com.howtodoinjava.demo.chatbot;

import org.alicebot.ab.Bot;
import org.alicebot.ab.MagicBooleans;
import java.io.File;

/*
 * AIML file (Artificial Intelligence Markup Language) for our chatbot.
 * Get bot to generate AMIL file based on configeration
 */

public class AddAiml {
    private static final boolean TRACE_MODE = false;

    public static void main(String[] args) {
        try {
        	//calling getResourcePath in an assigned variable.
            String resourcesPath = getResourcesPath();
            //set chatbot trace.mode.
            MagicBooleans.trace_mode = TRACE_MODE;
           //new instance of bot.
            Bot bot = new Bot("super", resourcesPath);
            //calls wrieAIMLFILES from bot, to generate the AIMl file based n configuration. 
            bot.writeAIMLFiles(); 
            
        } 
        catch (Exception e) { //catches and exceptions. 
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
