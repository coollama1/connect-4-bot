package com.coolprojects.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageHandler {

    private String errorMessage;

    public String getErrorMessage(){
        return errorMessage;
    }

    private void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public synchronized void editMessageWithMarkup(AbsSender absSender, Long chatId, Integer messageId, String messageText, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId)
                .setMessageId(messageId)
                .setText(messageText);

        if(inlineKeyboardMarkup != null){
            editMessage.setReplyMarkup(inlineKeyboardMarkup);
        }

        try {
            absSender.execute(editMessage);
        } catch (TelegramApiException e) {
            String errorMessage = "Something went wrong when trying to edit the message. Check to make sure all the parameters are set properly.\n" + e.getMessage();
            setErrorMessage(errorMessage);
            System.out.println(errorMessage);
        }

    }

    public synchronized void editMessage(AbsSender absSender, Long chatId, Integer messageId, String messageText){
        editMessageWithMarkup(absSender,chatId,messageId,messageText,null);
    }

    public synchronized void sendMessage(AbsSender absSender, Long chatId, String message, boolean enableMarkdown){
        sendMessageWithMarkup(absSender,chatId,message,enableMarkdown,null);
    }

    public synchronized void sendMessageWithMarkup(AbsSender absSender, Long chatId, String message, boolean enableMarkdown, InlineKeyboardMarkup inlineKeyboardMarkup){
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
            String errorMessage = "Something went wrong when trying to send the message. Check to make sure all the parameters are set properly.\n" + exp.getMessage();
            setErrorMessage(errorMessage);
            System.out.println(errorMessage);
        }
    }
}
