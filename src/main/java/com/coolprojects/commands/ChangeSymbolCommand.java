package com.coolprojects.commands;

import com.coolprojects.game.components.Board;
import com.coolprojects.game.state.GameBoardType;
import com.coolprojects.game.state.GameState;
import com.coolprojects.game.state.GameType;
import com.coolprojects.utilities.Utilities;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.games.Game;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ChangeSymbolCommand extends BotCommand {
    public ChangeSymbolCommand() {
        super("changesymbol", "changes the symbol being used by the player");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try{
            if(GameState.isGameInitiated()){
                if(GameState.getGameBoardType() == GameBoardType.CONNECT_FOUR
                    || GameState.getGameBoardType() == GameBoardType.TIC_TAC_TOE){

                    int primaryPlayerId = GameState.getPrimaryPlayerId();
                    int secondaryPlayerId = GameState.getSecondaryPlayerId();
                    int currentUserId = user.getId();
                    Board gameBoard = GameState.getGameBoard();
                    String newSymbol = strings[0];
                    if(currentUserId == primaryPlayerId) {
                        if(gameBoard.setPrimarySymbol(newSymbol)){
                            Utilities.sendMessage(absSender, chat.getId(),gameBoard.getFormattedBoardString(),true);
                        }
                        else{
                            commandFailed(absSender,chat.getId());
                        }
                    }
                    else if (currentUserId == secondaryPlayerId) {
                        if(gameBoard.setSecondarySymbol(newSymbol)) {
                            Utilities.sendMessage(absSender, chat.getId(),gameBoard.getFormattedBoardString(),true);
                        }
                        else {
                            commandFailed(absSender,chat.getId());
                        }
                    }
                    else{
                        playerNotParticipating(absSender,chat.getId());
                    }
                }
                else{
                    invalidGameBoard(absSender,chat.getId());
                }
            }
            else{
                gameNotInitiated(absSender,chat.getId());
            }

        }catch(Exception e){
            commandFailed(absSender,chat.getId());
        }
    }

    private void playerNotParticipating(AbsSender absSender, Long chatId){
        String errorMessage = "Sorry, you're not in the game right now";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }

    private void invalidGameBoard(AbsSender absSender, Long chatId){
        String errorMessage = "Symbols can't be changed with the current board type";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }

    private void gameNotInitiated(AbsSender absSender, Long chatId){
        String errorMessage = "Game hasn't been created yet. Use the /start or /createboard commands to start a new game";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }

    private void commandFailed(AbsSender absSender, Long chatId){
        String errorMessage = "Command is either formatted incorrectly or the symbol is not valid";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }
}
