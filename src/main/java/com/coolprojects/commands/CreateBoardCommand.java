package com.coolprojects.commands;

import com.coolprojects.gamecomponents.Board;
import com.coolprojects.gamecomponents.ConnectFourBoard;
import com.coolprojects.gamecomponents.TicTacToeBoard;
import com.coolprojects.utilities.Utilities;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

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
                             "# of rows: 3-8\n# of columns: 3-6\n\n" +
                             "**Connect 4 Board**\n" +
                             "# of rows: 4-12\n# of columns: 4-8";
        Utilities.sendMessage(absSender,chatId,errorMessage,true);
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

                if(boardType.equals(ticTacToe)){
                    newGameBoard = new TicTacToeBoard(numberOfRows,numberOfCols);
                }else if(boardType.equals(connectFour)){
                    newGameBoard = new ConnectFourBoard(numberOfRows,numberOfCols);
                }else {
                    commandFailed(absSender,chatId);
                    return;
                }
                String message = "This is what the board should look like\n\n" + newGameBoard.getFormattedBoard();
                Utilities.sendMessage(absSender,chatId,message,true);

            }
            else{
                commandFailed(absSender,chatId);
            }
        }catch(Exception e){
            commandFailed(absSender,chatId);
        }
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] commandParameters) {
        runCommand(absSender,chat.getId(),commandParameters);
    }
}
