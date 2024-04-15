package com.howtodoinjava.demo.chatbot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ChatbotClothingTest {

    @Test
    public void testSuggestClothingCold() {
        double temperature = 40;
        String expected = "You should wear Borat's mankini";
        String result = Chatbot.suggestClothing(temperature);
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + result);
        assertEquals(expected, result);
    }
}
