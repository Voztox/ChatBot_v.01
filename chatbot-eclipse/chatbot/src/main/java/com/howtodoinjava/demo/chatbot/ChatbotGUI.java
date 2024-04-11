package com.howtodoinjava.demo.chatbot;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChatbotGUI extends JFrame {
    private JTextArea conversationArea;
    private JTextField inputField;
    private Chat chatSession;

    public ChatbotGUI(Chat chatSession) {
        this.chatSession = chatSession;
        setTitle("NoName"); // name of our chatbot
        setSize(400, 400); // size of the jFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE); // close operation
        setLayout(new BorderLayout()); // borderlayout

        conversationArea = new JTextArea(); //Jtextare for conversation to display
        conversationArea.setEditable(false); 
        JScrollPane scrollPane = new JScrollPane(conversationArea); // scrollbar when the given space is not enough
        add(scrollPane, BorderLayout.CENTER); // centering the jTextArea
        
        inputField = new JTextField(); //User input
        inputField.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                String input = inputField.getText();
                addMessage("You", input); // display for the user input
                inputField.setText("");
                String response = chatSession.multisentenceRespond(input);
                addMessage("noName Chatbot", response); //display for the bot's input
            }
        });
        add(inputField, BorderLayout.SOUTH); //jFrame for the bottom of the display

        setVisible(true);
    }

    private void addMessage(String sender, String message) {
        conversationArea.append(sender + ": " + message + "\n"); // messaging method
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
}
