package com.coolprojects.commands;

import com.coolprojects.game.state.GameState;
import com.coolprojects.game.state.GameType;
import com.coolprojects.utilities.Utilities;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class JoinCommand extends BotCommand {
    public JoinCommand() {
        super("join", "allows other players to join a multiplayer game");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(GameState.isBoardChosen() && GameState.getGameType() == GameType.MULTI_PLAYER
            && !GameState.isGameInitiated()){
            int userId = user.getId();
            if(!GameState.isPlayerInGame(userId)){
                String userFirstName = user.getFirstName();
                GameState.setSecondaryPlayerId(userId);
                GameState.setSecondPlayerName(userFirstName);
                GameState.setWaitingForMatchingSymbols(true);
                askForSymbols(absSender,chat.getId());
            }
            else{
                playerAlreadyInGame(absSender,chat.getId());
            }
        }
        else{
            gameNotSetUp(absSender,chat.getId());
        }
    }

    private void playerAlreadyInGame(AbsSender absSender, Long chatId){
        String errorMessage = "You're already in the game, you can't rejoin";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }

    private void askForSymbols(AbsSender absSender, Long chatId){
        String matchingSymbolMessage = "How many symbols in a row does a player need to win?";
        Utilities.sendMessage(absSender,chatId,matchingSymbolMessage, false);
    }

    private void gameNotSetUp(AbsSender absSender, Long chatId){
        String errorMessage = "Make sure you set up a new multiplayer game before using the /join command";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }
}
