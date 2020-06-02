package com.coolprojects.commands;

import com.coolprojects.ai.AI;
import com.coolprojects.ai.ConnectFourAI;
import com.coolprojects.ai.TicTacToeAI;
import com.coolprojects.game.components.Board;
import com.coolprojects.game.state.GameBoardType;
import com.coolprojects.game.state.GameState;
import com.coolprojects.game.state.GameType;
import com.coolprojects.game.state.PlayerTurn;
import com.coolprojects.utilities.Utilities;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.games.Game;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class MoveCommand extends BotCommand {
    public MoveCommand() {
        super("move", "allows player to make a move by placing a symbol on the board");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(strings.length == 1){
            runCommand(absSender,user,chat,strings[0]);
        }
        else{
            commandFailed(absSender,chat.getId());
        }
    }

    public void runCommand(AbsSender absSender, User user, Chat chat, String positionString){
        int userId = user.getId();
        long chatId = chat.getId();
        if (GameState.isGameInitiated() && GameState.isPlayerTurn(userId)) {
            try {
                Board gameBoard = GameState.getGameBoard();
                if (gameBoard.placePrimarySymbol(positionString)) {
                    String boardString = gameBoard.getFormattedBoardString();
                    Utilities.sendMessage(absSender, chatId, boardString, true);
                    GameState.setPlayerTurn(PlayerTurn.AI_TURN);
                    startAI(absSender,chatId);
                }
                else {
                    invalidLocation(absSender,chatId);
                }
            }
            catch (Exception e) {
                gameNotInitiated(absSender,chatId);
            }
        }
        else{
            gameNotInitiated(absSender,chatId);
        }
    }

    private void startAI(AbsSender absSender, Long chatId){
        Board gameBoard = GameState.getGameBoard();
        GameBoardType gameBoardType = GameState.getGameBoardType();
        AI gameAI;
        if(gameBoardType == GameBoardType.TIC_TAC_TOE){
            gameAI = new TicTacToeAI(gameBoard);
            gameAI.makeMove();
        }
        else if(gameBoardType == GameBoardType.CONNECT_FOUR){
            gameAI = new ConnectFourAI(gameBoard);
            gameAI.makeMove();
        }
        GameState.setPlayerTurn(PlayerTurn.PRIMARY_PLAYER_TURN);
        String aiOutput = "Alright, now it's my turn";
        String gameBoardString = gameBoard.getFormattedBoardString();
        Utilities.sendMessage(absSender,chatId,aiOutput,true);
        Utilities.sendMessage(absSender,chatId,gameBoardString,true);
    }

    private void invalidLocation(AbsSender absSender, Long chatId){
        String errorMessage = "Invalid location. Make sure location you pick is empty and within the range of the board";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }

    private void gameNotInitiated(AbsSender absSender, Long chatId){
        String errorMessage = "Game must be initiated before using the /move command";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }

    private void commandFailed(AbsSender absSender,Long chatId){
        String errorMessage = "Failed to run the command. Make sure it's formatted correctly. For example: /move a, /move a5";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }
}
