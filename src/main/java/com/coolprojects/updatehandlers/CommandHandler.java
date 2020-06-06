package com.coolprojects.updatehandlers;

import com.coolprojects.commands.*;
import com.coolprojects.game.components.Board;
import com.coolprojects.game.state.GameState;
import com.coolprojects.game.state.GameType;
import com.coolprojects.utilities.Utilities;
import jdk.jshell.execution.Util;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "COMMANDSHANDLER";
    public static final String initiate3x3TicTacToeBoard = "initiate_3x3_tic_tac_toe_board";
    public static final String initiate4x4TicTacToeBoard = "initiate_4x4_tic_tac_toe_board";
    public static final String initiate5x5TicTacToeBoard = "initiate_5x5_tic_tac_toe_board";
    public static final String initiate6x6TicTacToeBoard = "initiate_6x6_tic_tac_toe_board";

    public static final String initiate4x4Connect4Board = "initiate_4x4_connect_4_board";
    public static final String initiate5x5Connect4Board = "initiate_5x5_connect_4_board";
    public static final String initiate6x6Connect4Board = "initiate_6x6_connect_4_board";
    public static final String initiate7x7Connect4Board = "initiate_7x7_connect_4_board";

    public static final String ticTacToeBoardType = "tictactoe";
    public static final String connect4BoardType = "connect4";

    public CommandHandler(String botUsername) {
        super(botUsername);
        register(new LoveYouCommand());
        register(new StartCommand());
        register(new CreateBoardCommand());
        register(new ChangeSymbolCommand());
        register(new StartGameCommand());
        register(new MoveCommand());
        registerDefaultAction((absSender, message) -> {
            Long chatId = message.getChatId();
            String messageText = "Sorry, I didn't recognize the command";
            Utilities.sendMessage(absSender,chatId,messageText,false);
        });

    }

    @Override
    public void processNonCommandUpdate(Update update) {
        String defaultGameBoardMessage = "Before the game begins, please tell me " +
                                         "what board size you'd like play on.\n\n" +
                                         "You can either choose one of the default " +
                                         "sizes below, or create your own board using " +
                                         "the /createboard command, followed by the " +
                                         "board type and the board dimensions.\n\n" +
                                         "For example:\n";
        if(update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message messageObject = callbackQuery.getMessage();
            Integer messageId = messageObject.getMessageId();
            Long chatId = callbackQuery.getMessage().getChatId();

            String callData = callbackQuery.getData();
            String [] callDataTokens = callData.split("_");



            if(callDataTokens.length >= 3){
                String firstToken = callDataTokens[0];
                String secondToken = callDataTokens[1];
                String thirdToken = callDataTokens[2];
                String lastToken = callDataTokens[callDataTokens.length - 1];

                if(callData.equals("start_tic_tac_toe") || callData.equals("start_connect_4")){
                    String messageText = "";
                    InlineKeyboardMarkup inLineMarkup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
                    List<InlineKeyboardButton> firstRow = new ArrayList<>();
                    List<InlineKeyboardButton> secondRow = new ArrayList<>();

                    InlineKeyboardButton boardSize3x3Button = new InlineKeyboardButton();
                    InlineKeyboardButton boardSize4x4Button = new InlineKeyboardButton();
                    InlineKeyboardButton boardSize5x5Button = new InlineKeyboardButton();
                    InlineKeyboardButton boardSize6x6Button = new InlineKeyboardButton();
                    InlineKeyboardButton boardSize7x7Button = new InlineKeyboardButton();

                    if(callData.equals("start_tic_tac_toe")){
                        messageText = defaultGameBoardMessage +
                                "/createboard tictactoe 5x6\n" +
                                "/createboard tictactoe 3x6\n" +
                                "/createboard tictactoe 4x5";

                        boardSize3x3Button.setText("3 x 3").setCallbackData(initiate3x3TicTacToeBoard);
                        boardSize4x4Button.setText("4 x 4").setCallbackData(initiate4x4TicTacToeBoard);
                        boardSize5x5Button.setText("5 x 5").setCallbackData(initiate5x5TicTacToeBoard);
                        boardSize6x6Button.setText("6 x 6").setCallbackData(initiate6x6TicTacToeBoard);

                        firstRow.add(boardSize3x3Button);
                        firstRow.add(boardSize4x4Button);
                        secondRow.add(boardSize5x5Button);
                        secondRow.add(boardSize6x6Button);
                        buttonRows.add(firstRow);
                        buttonRows.add(secondRow);
                        inLineMarkup.setKeyboard(buttonRows);
                    }
                    else if (callData.equals("start_connect_4")){
                        messageText = defaultGameBoardMessage +
                                "/createboard connect4 6x7\n" +
                                "/createboard connect4 6x6\n" +
                                "/createboard connect4 4x5";

                        boardSize4x4Button.setText("4 x 4").setCallbackData(initiate4x4Connect4Board);
                        boardSize5x5Button.setText("5 x 5").setCallbackData(initiate5x5Connect4Board);
                        boardSize6x6Button.setText("6 x 6").setCallbackData(initiate6x6Connect4Board);
                        boardSize7x7Button.setText("7 x 7").setCallbackData(initiate7x7Connect4Board);

                        firstRow.add(boardSize4x4Button);
                        firstRow.add(boardSize5x5Button);
                        secondRow.add(boardSize6x6Button);
                        secondRow.add(boardSize7x7Button);
                        buttonRows.add(firstRow);
                        buttonRows.add(secondRow);
                        inLineMarkup.setKeyboard(buttonRows);
                    }

                    Utilities.editMessageWithMarkup(this,chatId,messageId,messageText,inLineMarkup);
                }
                else if(firstToken.equals("initiate") &&  lastToken.equals("board")){
                    String boardType = ticTacToeBoardType;
                    String boardDimensions = secondToken;
                    if(thirdToken.equals("connect")){
                        boardType = connect4BoardType;
                    }
                    String [] commandParameters = {boardType,boardDimensions};
                    CreateBoardCommand.runCommand(this,chatId,commandParameters);
                }

            }
            if(callData.equals(CallbackValues.SET_SINGLE_PLAYER)){
                GameState.setGameType(GameType.SINGLE_PLAYER);
                GameState.setPrimaryPlayerId(callbackQuery.getFrom().getId());
                GameState.setWaitingForMatchingSymbols(true);
                String matchingSymbolMessage = "How many symbols in a row does a player need to win?";
                Utilities.sendMessage(this,chatId,matchingSymbolMessage, true);
            }
            else if(callData.equals(CallbackValues.SET_MULTIPLAYER)){
                GameState.setGameType(GameType.MULTI_PLAYER);
                GameState.setPrimaryPlayerId(callbackQuery.getFrom().getId());
                String joinMessage = "If someone else wants to join, just use the /join command";
                Utilities.sendMessage(this,chatId,joinMessage,false);
            }
        }
        else if(update.hasMessage()){
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            Integer userId = message.getFrom().getId();
            String invalidBoardLocation = "Sorry, that's not a valid location";
            if(message.hasText()) {
                if(GameState.isWaitingForMatchingSymbols()){
                    Board gameBoard = GameState.getGameBoard();
                    int numberOfRows = gameBoard.getNumberOfRows();
                    int numberOfCols = gameBoard.getNumberOfCols();
                    String messageText = message.getText();
                    if(StringUtils.isNumeric(messageText)){
                        int matchingSymbols = Integer.parseInt(messageText);
                        if(matchingSymbols <= Math.min(numberOfRows,numberOfCols)
                                && matchingSymbols > 2){
                            GameState.setNumberOfMatchingSymbolsToWin(matchingSymbols);
                            sendGameInitiationMessage(chatId);
                        }
                        else{
                            numberOutOfBounds(chatId);
                        }
                    }
                    else{
                        notNumeric(chatId);
                    }
                }
                else if (GameState.isGameInitiated() && GameState.isPlayerTurn(userId)) {
                    MoveCommand moveCommand = new MoveCommand();
                    User messageUser = message.getFrom();
                    Chat messageChat = message.getChat();
                    String messageText = message.getText();
                    moveCommand.runCommand(this,messageUser,messageChat,messageText);
                }
                else if (!GameState.isPlayerTurn(userId)){
                    notPlayerTurn(chatId);
                }else {
                    gameNotCreated(chatId);
                }
            }
        }
    }

    private void sendGameInitiationMessage(Long chatId){
        String gameInitiationMessage = "When you're ready, use the /startgame command " +
                                        "to initiate the game. \n\nIf you want to set up a " +
                                        "different game, just use the /start or /createboard commands";
        Utilities.sendMessage(this,chatId,gameInitiationMessage,false);
    }

    private void numberOutOfBounds(Long chatId){
        Board gameBoard = GameState.getGameBoard();
        int numberOfRows = gameBoard.getNumberOfRows();
        int numberOfCols = gameBoard.getNumberOfCols();
        int maxMatchingSymbols = Math.min(numberOfRows,numberOfCols);
        String errorMessage = "Number must be between 3-" + maxMatchingSymbols;
        Utilities.sendMessage(this,chatId,errorMessage,false);
    }

    private void notNumeric(Long chatId){
        String errorMessage = "Make sure you enter a valid number, not a symbol or letter";
        Utilities.sendMessage(this,chatId,errorMessage,false);
    }

    private void notPlayerTurn(Long chatId){
        String errorMessage = "It's not your turn";
        Utilities.sendMessage(this,chatId,errorMessage,false);
    }

    private void gameNotCreated(Long chatId){
        String defaultMessage = "The game hasn't been set up yet. Use the /start or /createboard commands to start a new game";
        Utilities.sendMessage(this,chatId,defaultMessage,false);
    }

    @Override
    public String getBotToken() {
        return Utilities.BOT_TOKEN;
    }
}
