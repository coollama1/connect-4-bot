package com.coolprojects.game.state;

import com.coolprojects.game.components.Board;

public class GameState {
    private static GameType gameType;
    private static boolean boardChosen;
    private static boolean gameInitiated;
    private static GameBoardType gameBoardType;
    private static Board gameBoard;
    private static int primaryPlayerId;
    private static int secondaryPlayerId;
    private static int numberOfMatchingSymbolsToWin;
    private static PlayerTurn currentPlayerTurn;
    private static boolean waitingForMatchingSymbols;

    public static boolean isWaitingForMatchingSymbols() {
        return waitingForMatchingSymbols;
    }

    public static void setWaitingForMatchingSymbols(boolean waitingForMatchingSymbols) {
        GameState.waitingForMatchingSymbols = waitingForMatchingSymbols;
    }

    public static boolean isPlayerTurn(int playerId){
        if(playerId == primaryPlayerId && currentPlayerTurn == PlayerTurn.PRIMARY_PLAYER_TURN){
            return true;
        }
        else if(playerId == secondaryPlayerId && currentPlayerTurn == PlayerTurn.SECONDARY_PLAYER_TURN){
            return true;
        }
        return false;
    }

    public static GameType getGameType() {
        return gameType;
    }

    public static void setGameType(GameType gameType) {
        GameState.gameType = gameType;
    }

    public static boolean isBoardChosen() {
        return boardChosen;
    }

    public static void setBoardChosen(boolean boardChosen) {
        GameState.boardChosen = boardChosen;
    }

    public static boolean isGameInitiated() {
        return gameInitiated;
    }

    public static void setGameInitiated(boolean gameInitiated) {
        GameState.gameInitiated = gameInitiated;
    }

    public static GameBoardType getGameBoardType() {
        return gameBoardType;
    }

    public static void setGameBoardType(GameBoardType gameBoardType) {
        GameState.gameBoardType = gameBoardType;
    }

    public static Board getGameBoard() {
        return gameBoard;
    }

    public static void setGameBoard(Board gameBoard) {
        GameState.gameBoard = gameBoard;
    }

    public static int getPrimaryPlayerId() {
        return primaryPlayerId;
    }

    public static void setPlayerTurn(PlayerTurn playerTurn){
        currentPlayerTurn = playerTurn;
    }

    public static boolean setPlayerTurn(int userId){
        if(userId == primaryPlayerId){
            currentPlayerTurn = PlayerTurn.PRIMARY_PLAYER_TURN;
            return true;
        }
        else if(userId == secondaryPlayerId){
            currentPlayerTurn = PlayerTurn.SECONDARY_PLAYER_TURN;
            return true;
        }
        return false;
    }

    public static void setPrimaryPlayerId(int primaryPlayerId) {
        GameState.primaryPlayerId = primaryPlayerId;
    }

    public static int getSecondaryPlayerId() {
        return secondaryPlayerId;
    }

    public static void setSecondaryPlayerId(int secondaryPlayerId) {
        GameState.secondaryPlayerId = secondaryPlayerId;
    }

    public static int getNumberOfMatchingSymbolsToWin() {
        return numberOfMatchingSymbolsToWin;
    }

    public static void setNumberOfMatchingSymbolsToWin(int numberOfMatchingSymbolsToWin) {
        GameState.numberOfMatchingSymbolsToWin = numberOfMatchingSymbolsToWin;
    }

    public static PlayerTurn getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public static void setCurrentPlayerTurn(PlayerTurn currentPlayerTurn) {
        GameState.currentPlayerTurn = currentPlayerTurn;
    }

    public static void endGame(){
        setGameInitiated(false);
        setBoardChosen(false);
    }

    public static Winner getWinner(){
        Board currentBoard = getGameBoard();
        int matchResult = currentBoard.checkForMatches(numberOfMatchingSymbolsToWin);
        if(matchResult == Board.PRIMARY_SYMBOL_VALUE){
            return Winner.FIRST_PLAYER_WON;
        }
        else if(getGameType() == GameType.SINGLE_PLAYER &&
                matchResult == Board.SECONDARY_SYMBOL_VALUE){
                return Winner.AI_WON;
        }
        else if(getGameType() == GameType.MULTI_PLAYER &&
                matchResult == Board.SECONDARY_SYMBOL_VALUE){

        }

        return Winner.NO_PLAYER_WON;
    }

}
