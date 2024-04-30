package com.howtodoinjava.demo.chatbot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ChatbotClothingTest {

    @Test
    public void testSuggestClothingCold() {
        double temperature = 40;
        String expected = "You should wear Borat's mankini";
       // String result = Chatbot.suggestClothing(temperature);
//        System.out.println("Expected: " + expected);
//        System.out.println("Actual: " + result);
//        assertEquals(expected, result);
    }
    @Test
    public void testSuggestClothingWarm() {
        double temperature = 15; // Assume a temperature within the "warm" range
        String expected = "You should wear shorts."; // Expected clothing suggestion for warm temperature
        String result = Chatbot.suggestClothing(temperature);
        assertEquals(expected, result);
    }
}
