package com.coolprojects.commands;

import com.coolprojects.game.components.Board;
import com.coolprojects.game.state.GameState;
import com.coolprojects.utilities.Utilities;
import com.google.inject.internal.cglib.core.$AbstractClassGenerator;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class MatchesCommand extends BotCommand {
    public MatchesCommand() {
        super("matches","sets number of matching symbols needed for a player to win a game");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(strings.length == 1){
            if(GameState.isWaitingForMatchingSymbols()){
                Board gameBoard = GameState.getGameBoard();
                int numberOfRows = gameBoard.getNumberOfRows();
                int numberOfCols = gameBoard.getNumberOfCols();
                String numberOfMatches = strings[0];
                if(StringUtils.isNumeric(numberOfMatches)){
                    int matchingSymbols = Integer.parseInt(numberOfMatches);
                    if(matchingSymbols <= Math.min(numberOfRows,numberOfCols)
                            && matchingSymbols > 2){
                        GameState.setNumberOfMatchingSymbolsToWin(matchingSymbols);
                        GameState.setWaitingForMatchingSymbols(false);
                        GameState.setMatchingSymbolsSet(true);
                        sendGameInitiationMessage(absSender,chat.getId());
                    }
                    else{
                        numberOutOfBounds(absSender,chat.getId());
                    }
                }
                else{
                    notNumeric(absSender,chat.getId());
                }
            }
            else{
                gameNotCreated(absSender,chat.getId());
            }
        }
        else{
            commandFailed(absSender,chat.getId());
        }

    }

    private void commandFailed(AbsSender absSender, Long chatId){
        String errorMessage = "Command failed. If you want to use the command, it should look something like this: /matches 3";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }

    private void sendGameInitiationMessage(AbsSender absSender, Long chatId){
        String gameInitiationMessage = "When you're ready, use the /startgame command " +
                "to initiate the game. \n\nIf you want to set up a " +
                "different game, just use the /start or /createboard commands";
        Utilities.sendMessage(absSender,chatId,gameInitiationMessage,false);
    }

    private void numberOutOfBounds(AbsSender absSender, Long chatId){
        Board gameBoard = GameState.getGameBoard();
        int numberOfRows = gameBoard.getNumberOfRows();
        int numberOfCols = gameBoard.getNumberOfCols();
        int maxMatchingSymbols = Math.min(numberOfRows,numberOfCols);
        String errorMessage = "Number must be between 3-" + maxMatchingSymbols;
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }

    private void notNumeric(AbsSender absSender,Long chatId){
        String errorMessage = "Make sure you enter a valid number, not a symbol or letter";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }

    private void gameNotCreated(AbsSender absSender, Long chatId){
        String defaultMessage = "The game hasn't been set up yet. Use the /start or /createboard commands to start a new game";
        Utilities.sendMessage(absSender,chatId,defaultMessage,false);
    }
}
