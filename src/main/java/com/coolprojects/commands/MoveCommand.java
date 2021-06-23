package com.coolprojects.commands;

import com.coolprojects.ai.AI;
import com.coolprojects.ai.ConnectFourAI;
import com.coolprojects.ai.TicTacToeAI;
import com.coolprojects.game.components.Board;
import com.coolprojects.game.state.*;
import com.coolprojects.handlers.MessageHandler;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class MoveCommand extends BotCommand {
    public MoveCommand() {
        super("mv", "allows player to make a move by placing a symbol on the board");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(strings.length == 1){
            runCommand(absSender,user,chat,strings[0]);
        }
        else{
            sendCommandFailedMessage(absSender,chat.getId());
        }
    }

    public void runCommand(AbsSender absSender, User user, Chat chat, String positionString){
        int userId = user.getId();
        long chatId = chat.getId();
        if (GameState.isGameInitiated()) {
            if(GameState.isPlayerTurn(userId)){
                try {
                    Board gameBoard = GameState.getGameBoard();
                    if (GameState.getPrimaryPlayerId() == userId && gameBoard.placePrimarySymbol(positionString)) {
                        String boardString = gameBoard.getFormattedBoardString();
                        new MessageHandler().sendMessage(absSender, chatId, boardString, true);
                        if(checkForWins(absSender,chatId) == Winner.NO_PLAYER_WON && GameState.getGameType() == GameType.SINGLE_PLAYER){
                            GameState.setPlayerTurn(PlayerTurn.AI_TURN);
                            startAI(absSender,chatId);
                            checkForWins(absSender,chatId);
                        }else if(GameState.getGameType() == GameType.MULTI_PLAYER){
                            GameState.setPlayerTurn(PlayerTurn.SECONDARY_PLAYER_TURN);
                        }
                    }
                    else if(GameState.getSecondaryPlayerId() == userId && gameBoard.placeSecondarySymbol(positionString)){
                        String boardString = gameBoard.getFormattedBoardString();
                        new MessageHandler().sendMessage(absSender, chatId, boardString, true);
                        if(checkForWins(absSender,chatId) == Winner.NO_PLAYER_WON){
                            GameState.setPlayerTurn(PlayerTurn.PRIMARY_PLAYER_TURN);
                        }
                    }
                    else {
                        sendInvalidLocationMessage(absSender,chatId);
                    }
                }
                catch (Exception e) {
                    sendGameNotInitiatedMessage(absSender,chatId);
                }
            }
            else{
                sendNotPlayerTurnMessage(absSender,chatId);
            }
        }
        else{
            sendGameNotInitiatedMessage(absSender,chatId);
        }
    }

    private Winner checkForWins(AbsSender absSender, Long chatId ){
        Winner currentWinner = GameState.getWinner();
        if(currentWinner == Winner.FIRST_PLAYER_WON){
            GameState.endGame();
            sendFirstPlayerWonMessage(absSender,chatId);
        }
        else if(currentWinner == Winner.SECOND_PLAYER_WON){
            GameState.endGame();
            sendSecondPlayerWonMessage(absSender,chatId);
        }
        else if(currentWinner == Winner.AI_WON){
            GameState.endGame();
            sendAiWonMessage(absSender,chatId);
        }
        else if(currentWinner == Winner.TIE){
            GameState.endGame();
            sendGameIsTiedMessage(absSender,chatId);
        }
        return currentWinner;
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
        new MessageHandler().sendMessage(absSender,chatId,aiOutput,true);
        new MessageHandler().sendMessage(absSender,chatId,gameBoardString,true);
    }

    private void sendFirstPlayerWonMessage(AbsSender absSender, Long chatId){
        String playerWonMessage = "Seems like you won. Congrats :)" ;
        if(GameState.getGameType() == GameType.MULTI_PLAYER){
            String playerName = GameState.getFirstPlayerName();
            playerWonMessage = "Congrats, " + playerName + ". Seems like you won :)";
        }
        new MessageHandler().sendMessage(absSender,chatId,playerWonMessage,false);
    }

    private void sendSecondPlayerWonMessage(AbsSender absSender, Long chatId){
        String playerName = GameState.getSecondPlayerName();
        String playerWonMessage = "Congrats, " + playerName + ". Seems like you won :)";
        new MessageHandler().sendMessage(absSender,chatId,playerWonMessage,false);
    }

    private void sendAiWonMessage(AbsSender absSender, Long chatId){
        String playerLostMessage = "Looks like I won this game. Better luck next time :)";
        new MessageHandler().sendMessage(absSender,chatId,playerLostMessage,false);
    }

    private void sendGameIsTiedMessage(AbsSender absSender, Long chatId){
        String errorMessage = "Game board is filled. Seems like it's a tie";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,false);
    }

    private void sendInvalidLocationMessage(AbsSender absSender, Long chatId){
        String errorMessage = "Invalid location. Make sure location you pick is empty and within the range of the board";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,false);
    }

    private void sendGameNotInitiatedMessage(AbsSender absSender, Long chatId){
        String errorMessage = "Game must be initiated before using the /move command";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,false);
    }

    private void sendCommandFailedMessage(AbsSender absSender, Long chatId){
        String errorMessage = "Failed to run the command. Make sure it's formatted correctly. For example: /mv a, /mv a5";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,false);
    }

    private void sendNotPlayerTurnMessage(AbsSender absSender, Long chatId){
        String errorMessage = "It's not your turn";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,false);
    }
}
