package com.coolprojects;

public class BotInfo {
    private String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private String BOT_USERNAME = System.getenv("BOT_USERNAME");


    public String getBotToken() {
        return BOT_TOKEN;
    }

    public String getBotUsername() {
        return BOT_USERNAME;
    }
}
