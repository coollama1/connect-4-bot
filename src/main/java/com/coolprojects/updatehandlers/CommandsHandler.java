package com.coolprojects.updatehandlers;

import com.coolprojects.commands.LoveYouCommand;
import com.coolprojects.commands.StartCommand;
import com.coolprojects.utilities.Utilities;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class CommandsHandler extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "COMMANDSHANDLER";

    public CommandsHandler(String botUsername) {
        super(botUsername);
        register(new LoveYouCommand());
        register(new StartCommand());
        registerDefaultAction((absSender, message) -> {
            Long chatId = message.getChatId();
            String messageText = "Sorry, the following command:\n" + message.getText()
                                + "\n has not been recognized";
            Utilities.sendMessage(absSender,chatId,messageText,false);
        });

    }

    @Override
    public void processNonCommandUpdate(Update update) {
        String defaultGameBoardMessage = "Before the game begins, please tell me " +
                                         "what board size you'd like play on.\n\n" +
                                         "You can either choose one of the default " +
                                         "sizes below, or create your own board using " +
                                         "the /createboard command, followed by the " +
                                         "board type and the board dimensions.\n\n";
        if(update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callData = callbackQuery.getData();
            Integer messageId = callbackQuery.getMessage().getMessageId();
            Long chatId = callbackQuery.getMessage().getChatId();

            if(callData.equals("start_tic_tac_toe") || callData.equals("start_connect_4")){
                String messageText = "";
                InlineKeyboardMarkup inLineMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
                List<InlineKeyboardButton> firstRow = new ArrayList<>();
                List<InlineKeyboardButton> secondRow = new ArrayList<>();

                InlineKeyboardButton boardSize3x3Button = new InlineKeyboardButton();
                InlineKeyboardButton boardSize4x4Button = new InlineKeyboardButton();
                InlineKeyboardButton boardSize5x5Button = new InlineKeyboardButton();
                InlineKeyboardButton boardSize6x6Button = new InlineKeyboardButton();
                InlineKeyboardButton boardSize7x7Button = new InlineKeyboardButton();

                if(callData.equals("start_tic_tac_toe")){
                    messageText = defaultGameBoardMessage +
                            "/createboard tictactoe 5x6\n" +
                            "/createboard tictactoe 3x6\n" +
                            "/createboard tictactoe 4x5";

                    boardSize3x3Button.setText("3 x 3").setCallbackData("initiate_3x3_tic_tac_toe_board");
                    boardSize4x4Button.setText("4 x 4").setCallbackData("initiate_4x4_tic_tac_toe_board");
                    boardSize5x5Button.setText("5 x 5").setCallbackData("initiate_5x5_tic_tac_toe_board");
                    boardSize6x6Button.setText("6 x 6").setCallbackData("initiate_6x6_tic_tac_toe_board");

                    firstRow.add(boardSize3x3Button);
                    firstRow.add(boardSize4x4Button);
                    secondRow.add(boardSize5x5Button);
                    secondRow.add(boardSize6x6Button);
                    buttonRows.add(firstRow);
                    buttonRows.add(secondRow);
                    inLineMarkup.setKeyboard(buttonRows);
                }
                else if (callData.equals("start_connect_4")){
                    messageText = defaultGameBoardMessage +
                            "/createboard connect4 6x7\n" +
                            "/createboard connect4 6x6\n" +
                            "/createboard connect4 4x5";

                    boardSize4x4Button.setText("4 x 4").setCallbackData("initiate_4x4_connect_4_board");
                    boardSize5x5Button.setText("5 x 5").setCallbackData("initiate_5x5_connect_4_board");
                    boardSize6x6Button.setText("6 x 6").setCallbackData("initiate_6x6_connect_4_board");
                    boardSize7x7Button.setText("7 x 7").setCallbackData("initiate_7x7_connect_4_board");

                    firstRow.add(boardSize4x4Button);
                    firstRow.add(boardSize5x5Button);
                    secondRow.add(boardSize6x6Button);
                    secondRow.add(boardSize7x7Button);
                    buttonRows.add(firstRow);
                    buttonRows.add(secondRow);
                    inLineMarkup.setKeyboard(buttonRows);

                }
                Utilities.editMessageWithMarkup(this,chatId,messageId,messageText,inLineMarkup);
            }
        }
        else if(update.hasMessage()){
            Message message = update.getMessage();

            if(message.hasText()){
                String messageToBeSent = "Sorry, don't really get what you mean";
                Utilities.sendMessage(this,message.getChatId(),messageToBeSent,false);
            }
        }
    }

    @Override
    public String getBotToken() {
        return Utilities.BOT_TOKEN;
    }
}
