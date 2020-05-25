package com.coolprojects;

import com.coolprojects.updatehandlers.CommandsHandler;
import com.coolprojects.utilities.Utilities;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

public class Main {
    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try{
            botsApi.registerBot(new CommandsHandler(Utilities.BOT_USERNAME));
        }catch(Exception exp){
            exp.printStackTrace();
        }

    }


}
