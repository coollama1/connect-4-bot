package com.coolprojects.utilities;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public static String BOT_TOKEN = System.getenv("BOT_TOKEN");
    public static String BOT_USERNAME = System.getenv("BOT_USERNAME");


    public static synchronized  void sendMessageWithMarkup(AbsSender absSender, Long chatId, String message, boolean enableMarkdown, InlineKeyboardMarkup inlineKeyboardMarkup){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(enableMarkdown);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        if(inlineKeyboardMarkup != null){
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        }

        try{
            absSender.execute(sendMessage);
        }catch(TelegramApiException exp){
            exp.printStackTrace();
        }
    }

    public static synchronized void sendMessage(AbsSender absSender, Long chatId, String message, boolean enableMarkdown){
        sendMessageWithMarkup(absSender,chatId,message,enableMarkdown,null);
    }

    public static synchronized void editMessageWithMarkup(AbsSender absSender, Long chatId, Integer messageId, String messageText, InlineKeyboardMarkup inlineKeyboardMarkup){
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId)
                .setMessageId(messageId)
                .setText(messageText);

        if(inlineKeyboardMarkup != null){
            editMessage.setReplyMarkup(inlineKeyboardMarkup);
        }

        try{
            absSender.execute(editMessage);
        }catch(TelegramApiException e){
            e.printStackTrace();
        }
    }

    public static synchronized void editMessage(AbsSender absSender, Long chatId, Integer messageId, String messageText){
        editMessageWithMarkup(absSender,chatId,messageId,messageText,null);
    }


}
