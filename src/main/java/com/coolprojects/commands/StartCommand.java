package com.coolprojects.commands;

import com.coolprojects.handlers.MessageHandler;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

public class StartCommand extends BotCommand {
    public StartCommand() {
        super("start", "initiates bot, asks user for game preferences");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        Long chatId = chat.getId();
        String message = "Which game would you rather play?";

        InlineKeyboardMarkup inLineMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> firstButtonRow = new ArrayList<>();

        InlineKeyboardButton ticTacToeButton = new InlineKeyboardButton();
        InlineKeyboardButton connect4Button = new InlineKeyboardButton();
        ticTacToeButton.setText("Tic Tac Toe").setCallbackData("start_tic_tac_toe");
        connect4Button.setText("Connect 4").setCallbackData("start_connect_4");

        firstButtonRow.add(ticTacToeButton);
        firstButtonRow.add(connect4Button);
        buttonRows.add(firstButtonRow);
        inLineMarkup.setKeyboard(buttonRows);

        new MessageHandler().sendMessageWithMarkup(absSender,chatId,message,true,inLineMarkup);

    }

}
