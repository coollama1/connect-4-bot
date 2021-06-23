package com.coolprojects;

import com.coolprojects.handlers.CommandHandler;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static final String ERROR_MESSAGE = "An error occurred when trying to run the bot. \nMake sure to properly " +
                                               "set the environment variables for the BOT_USERNAME and the BOT_TOKEN.\n" +
                                               "Make sure to also have a stable internet connection";
    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        BotInfo botInfo = new BotInfo();
        try{
            botsApi.registerBot(new CommandHandler(botInfo.getBotUsername()));
        }catch(TelegramApiException exp){
            System.out.println(ERROR_MESSAGE);
        }
    }
}
