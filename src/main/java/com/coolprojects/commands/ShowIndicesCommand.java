package com.coolprojects.commands;

import com.coolprojects.game.state.GameState;
import com.coolprojects.utilities.Utilities;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ShowIndicesCommand extends BotCommand {
    public ShowIndicesCommand() {
        super("indices", "shows indices of board");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(GameState.isBoardChosen()){
            String boardString = GameState.getGameBoard().getIndexedBoardString();
            Utilities.sendMessage(absSender,chat.getId(),boardString,true);
        }
        else{
            boardDoesNotExist(absSender,chat.getId());
        }
    }

    private void boardDoesNotExist(AbsSender absSender, Long chatId){
        String errorMessage = "Board has not been created yet";
        Utilities.sendMessage(absSender,chatId,errorMessage,false);
    }
}
