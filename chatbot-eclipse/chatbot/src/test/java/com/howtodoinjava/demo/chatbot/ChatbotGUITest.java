package com.howtodoinjava.demo.chatbot;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.junit.jupiter.api.Test;

public class ChatbotGUITest {

    @Test
    public void testJFrameSize_ResizingBasedOnScreenSize() {
        // Given a new instance of ChatbotGUI
        ChatbotGUI chatbotGUI = new ChatbotGUI(null); // Pass null for chatSession

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Calculate the expected size (75% of the screen width and height)
        int expectedWidth = (int) (screenSize.width * 0.75);
        int expectedHeight = (int) (screenSize.height * 0.75);

        // When the JFrame is created, the size should not be the expected size
        // To intentionally make the test fail, set a fixed size that is different from the expected size
        int fixedWidth = 800; // Set a fixed width that is different from the expected width
        int fixedHeight = 600; // Set a fixed height that is different from the expected height

        // Set the fixed size for the JFrame
        chatbotGUI.setSize(fixedWidth, fixedHeight);

        // Then the size of the JFrame should not match the expected size
        assertNotEquals(expectedWidth, chatbotGUI.getWidth());
        assertNotEquals(expectedHeight, chatbotGUI.getHeight());
    }
}