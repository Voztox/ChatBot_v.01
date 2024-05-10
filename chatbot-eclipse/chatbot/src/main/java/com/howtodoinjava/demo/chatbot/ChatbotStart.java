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
    private static final String RAPID_API_KEY = "5cbd79b3f8mshd316ad243985354p122029jsn597e43ad9374";
    private static final String RAPID_API_HOST = "visual-crossing-weather.p.rapidapi.com";

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String[] locations = new String[5];

            for (int i = 0; i < 5; i++) {
                System.out.print("Hi, please enter destination " + (i + 1) + ": ");
                locations[i] = scanner.nextLine();
            }

            for (String location : locations) {
  

            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}


