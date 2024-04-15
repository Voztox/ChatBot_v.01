package com.howtodoinjava.demo.chatbot;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;


import java.io.File;

public class Chatbot {

    // RapidAPI Key
    private static final String RAPID_API_KEY = "b47b0922e4msh19452d17bed706cp1ad4bdjsn6a8c20371cb6";

    // RapidAPI Host
    private static final String RAPID_API_HOST = "visual-crossing-weather.p.rapidapi.com";

    public static void main(String[] args) {
       {
            Scanner scanner = new Scanner(System.in);
            String[] locations = new String[5];

            // Ask the user to enter 5 locations
            for (int i = 0; i < 5; i++) {
                System.out.print("Enter location " + (i + 1) + ": ");
                locations[i] = scanner.nextLine();
            }

       }}}
