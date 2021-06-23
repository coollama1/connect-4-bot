package com.coolprojects.commands;

import com.coolprojects.game.components.Board;
import com.coolprojects.game.state.GameBoardType;
import com.coolprojects.game.state.GameState;
import com.coolprojects.handlers.MessageHandler;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
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
                            new MessageHandler().sendMessage(absSender, chat.getId(),gameBoard.getFormattedBoardString(),true);
                        }
                        else{
                            sendCommandFailedMessage(absSender,chat.getId());
                        }
                    }
                    else if (currentUserId == secondaryPlayerId) {
                        if(gameBoard.setSecondarySymbol(newSymbol)) {
                            new MessageHandler().sendMessage(absSender, chat.getId(),gameBoard.getFormattedBoardString(),true);
                        }
                        else {
                            sendCommandFailedMessage(absSender,chat.getId());
                        }
                    }
                    else{
                        sendPlayerNotParticipatingMessage(absSender,chat.getId());
                    }
                }
                else{
                    sendInvalidGameBoardMessage(absSender,chat.getId());
                }
            }
            else{
                sendGameNotInitiatedMessage(absSender,chat.getId());
            }

        }catch(Exception e){
            sendCommandFailedMessage(absSender,chat.getId());
        }
    }

    private void sendPlayerNotParticipatingMessage(AbsSender absSender, Long chatId){
        String errorMessage = "Sorry, you're not in the game right now";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,false);
    }

    private void sendInvalidGameBoardMessage(AbsSender absSender, Long chatId){
        String errorMessage = "Symbols can't be changed with the current board type";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,false);
    }

    private void sendGameNotInitiatedMessage(AbsSender absSender, Long chatId){
        String errorMessage = "Game hasn't been created yet. Use the /start or /createboard commands to start a new game";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,false);
    }

    private void sendCommandFailedMessage(AbsSender absSender, Long chatId){
        String errorMessage = "Command is either formatted incorrectly or the symbol is not valid";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,false);
    }
}
