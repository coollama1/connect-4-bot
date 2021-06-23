package com.coolprojects.commands;

import com.coolprojects.game.state.GameState;
import com.coolprojects.handlers.MessageHandler;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartGameCommand extends BotCommand {
    public StartGameCommand() {
        super("startgame", "initiates game once board has been set up");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(GameState.isMatchingSymbolsSet()){
            GameState.setGameInitiated(true);
            if(GameState.setPlayerTurn(user.getId())){
                String boardString = GameState.getGameBoard().getFormattedBoardString();
                String username = user.getFirstName();
                String initiationMessage = "The game has begun, and " + username +
                        " goes first\n\n" + boardString;
                new MessageHandler().sendMessage(absSender,chat.getId(),initiationMessage,true);
            }
            else{
                sendNonParticipatingUserMessage(absSender,chat.getId());
            }
        }
        else{
            sendGameNotInitiatedMessage(absSender,chat.getId());
        }
    }

    private void sendNonParticipatingUserMessage(AbsSender absSender, Long chatId){
        String errorMessage = "Sorry, you're not part of this game";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,false);
    }

    private void sendGameNotInitiatedMessage(AbsSender absSender, Long chatId){
        String errorMessage = "Game must be set up first before it can be initiated";
        new MessageHandler().sendMessage(absSender,chatId,errorMessage,false);
    }
}
