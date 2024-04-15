package com.howtodoinjava.demo.chatbot;

import org.alicebot.ab.Bot;
import org.alicebot.ab.MagicBooleans;
import java.io.File;

public class AddAiml {
    private static final boolean TRACE_MODE = false;

    public static void main(String[] args) {
        try {
            String resourcesPath = getResourcesPath();
            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot("super", resourcesPath);
            bot.writeAIMLFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}
