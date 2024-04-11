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
        setTitle("NoName");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        conversationArea = new JTextArea();
        conversationArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(conversationArea);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                addMessage("You", input);
                inputField.setText("");
                String response = chatSession.multisentenceRespond(input);
                addMessage("Bot", response);
            }
        });
        add(inputField, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addMessage(String sender, String message) {
        conversationArea.append(sender + ": " + message + "\n");
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
