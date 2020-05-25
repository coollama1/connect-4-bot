package com.coolprojects.commands;

import com.coolprojects.utilities.Utilities;
import jdk.jshell.execution.Util;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;


public class LoveYouCommand extends BotCommand {

    private static final String LOGTAG = "IOLVEYOUCOMMAND";
    private static final String [] responses = {"Eh... Sorry, don't feel the same way",
                                                "That's nice",
                                                "Lmao, wtf. Where did that come from?",
                                                "I love you too babe \uD83D\uDC96"};

    public LoveYouCommand() {
        super("iloveyou", "Send a lovely response to user");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String response = responses[(int)(Math.random()*responses.length)];
        Long chatId = chat.getId();
        Utilities.sendMessage(absSender,chatId,response,false);
    }
}