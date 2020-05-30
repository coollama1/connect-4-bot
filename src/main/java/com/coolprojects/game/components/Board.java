package com.coolprojects.game.components;

public abstract class Board {
    protected static int PRIMARY_SYMBOL_VALUE = 1;
    protected static int SECONDARY_SYMBOL_VALUE = -1;
    protected static int EMPTY_SYMBOL_VALUE = 0;
    protected int numberOfRows;
    protected int numberOfCols;
    protected String primarySymbol;
    protected String secondarySymbol;
    protected int [][] board;

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfCols() {
        return numberOfCols;
    }

    public boolean validLocationForSymbol(int rowLocation, int colLocation){
        boolean valid = rowLocation >= 0 && rowLocation < numberOfRows &&
                        colLocation >= 0 && colLocation < numberOfCols &&
                        board[rowLocation][colLocation] == 0;
        return valid;
    }

    public boolean setPrimarySymbol(String primarySymbol){
        if(primarySymbol != null && !primarySymbol.isEmpty() &&
                primarySymbol.length() <= 2 &&
                (secondarySymbol == null || !primarySymbol.equals(secondarySymbol))){
            this.primarySymbol = primarySymbol;
            return true;
        }
        return false;
    }

    public boolean setSecondarySymbol(String secondarySymbol){
        if(secondarySymbol != null && !secondarySymbol.isEmpty() &&
                (secondarySymbol.length() <= 2) &&
                (primarySymbol == null || !primarySymbol.equals(secondarySymbol))){
            this.secondarySymbol = secondarySymbol;
            return true;
        }
        return false;
    }

    protected void placeSymbol(int rowPosition, int colPosition, int symbolValue){
        if(rowPosition >= 0 && rowPosition < board.length && colPosition >= 0 && colPosition < board[0].length){
            board[rowPosition][colPosition] = symbolValue;
        }
    }

    protected String getFormattedSymbol(String symbol){
        if(symbol.length() > 1){
            return symbol + " ";
        }
        else{
            return " " + symbol + " ";
        }
    }
    protected String getFormattedPrimarySymbol(){
        return getFormattedSymbol(primarySymbol);
    }

    protected String getFormattedSecondarySymbol(){
        return getFormattedSymbol(secondarySymbol);
    }

    public String getFormattedBoardString(){
        return "```\n" + getBoardString(false) + "\n```";
    }

    public String getIndexedBoardString(){
        return "```\n" + getBoardString(true) + "\n```";
    }

    public abstract String getInstructions();

    public abstract boolean placePrimarySymbol(String encodedPosition);

    public abstract boolean placeSecondarySymbol(String encodedPosition);

    protected abstract String getBoardString(boolean includeIndex);


}
