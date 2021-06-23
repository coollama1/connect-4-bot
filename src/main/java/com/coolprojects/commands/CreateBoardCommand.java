package com.coolprojects.commands;

import com.coolprojects.game.components.Board;
import com.coolprojects.game.components.ConnectFourBoard;
import com.coolprojects.game.components.TicTacToeBoard;
import com.coolprojects.game.state.GameBoardType;
import com.coolprojects.game.state.GameState;
import com.coolprojects.handlers.CallbackValues;
import com.coolprojects.handlers.MessageHandler;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

public class CreateBoardCommand extends BotCommand {
    private static final String ticTacToe = "tictactoe";
    private static final String connectFour = "connect4";

    public CreateBoardCommand() {
        super("createboard", "generates either a tic-tac-toe or connect 4 " +
                                                      "board according to the dimensions provided");
    }

    private static void commandFailed(AbsSender absSender, Long chatId){
        String errorMessage = "The command failed. Please make sure it's formatted properly.\n\n" +
                             "For example:\n/createboard tictactoe 5x5\n" +
                             "/createboard connect4 6x6\n\n" +
                             "**Tic Tac Toe Board**\n" +
                             "# of rows: 3-8\n# of columns: 3-7\n\n" +
                             "**Connect 4 Board**\n" +
                             "# of rows: 4-12\n# of columns: 4-7";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,true);
    }

    public static void runCommand(AbsSender absSender, Long chatId,String [] commandParameters){
        try{
            String boardType = commandParameters[0].toLowerCase();
            String [] boardDimensions = commandParameters[1].toLowerCase().split("[x×]");

            if(commandParameters.length == 2 && boardDimensions.length == 2){
                String rowString = boardDimensions[0];
                String colString = boardDimensions[1];
                int numberOfRows = Integer.parseInt(rowString);
                int numberOfCols = Integer.parseInt(colString);
                Board newGameBoard;
                GameBoardType gameBoardType;

                if(boardType.equals(ticTacToe)){
                    newGameBoard = new TicTacToeBoard(numberOfRows,numberOfCols);
                    gameBoardType = GameBoardType.TIC_TAC_TOE;
                }else if(boardType.equals(connectFour)){
                    newGameBoard = new ConnectFourBoard(numberOfRows,numberOfCols);
                    gameBoardType = GameBoardType.CONNECT_FOUR;
                }else {
                    commandFailed(absSender,chatId);
                    return;
                }
                sendCreateBoardMessage(absSender, chatId, newGameBoard);

                GameState.setBoardChosen(true);
                GameState.setGameInitiated(false);
                GameState.setGameBoard(newGameBoard);
                GameState.setGameBoardType(gameBoardType);
            }
            else{
                commandFailed(absSender,chatId);
            }
        }catch(Exception e){
            commandFailed(absSender,chatId);
        }
    }

    private static void sendCreateBoardMessage(AbsSender absSender, Long chatId, Board newGameBoard) {
        String message = "Here are the indices of the board\n\n" +
                        newGameBoard.getIndexedBoardString() + "\n" +
                        newGameBoard.getInstructions() + "\n\n" +
                        "Before you begin, tell me which " +
                        "game mode you want";
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        InlineKeyboardButton singlePlayerButton = new InlineKeyboardButton();
        InlineKeyboardButton multiplayerButton = new InlineKeyboardButton();

        singlePlayerButton.setText("Single Player").setCallbackData(CallbackValues.SET_SINGLE_PLAYER);
        multiplayerButton.setText("Multiplayer").setCallbackData(CallbackValues.SET_MULTIPLAYER);
        firstRow.add(singlePlayerButton);
        firstRow.add(multiplayerButton);
        buttonRows.add(firstRow);
        inlineKeyboardMarkup.setKeyboard(buttonRows);
        new MessageHandler().sendMessageWithMarkup(absSender,chatId,message,true,inlineKeyboardMarkup);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] commandParameters) {
        runCommand(absSender,chat.getId(),commandParameters);
    }
}
