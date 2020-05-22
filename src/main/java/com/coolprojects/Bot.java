package com.coolprojects;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public class Bot extends TelegramLongPollingBot {
    /*
     * Method for receiving messages
     * the parameter (update) contains a message from the user
     * */
    private final String botUsername = "TicTacHoeBot";
    private final String botToken = "938642235:AAHXq4gve8fojc2hr6551upLoLuMKei-pWU";

    public void onUpdateReceived(Update update){
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(),message);
    }

    public synchronized void sendMsg(String chatId, String s){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try{

        }catch(Exception exp){

        }

    }

    public String getBotUsername(){
        return botUsername;
    }

    public String getBotToken(){
        return botToken;
    }


}
