package com.coolprojects.gamecomponents;

public abstract class Board {
    private static int PRIMARY_SYMBOL_VALUE = 1;
    private static int SECONDARY_SYMBOL_VALUE = -1;
    protected int numberOfRows;
    protected int numberOfCols;
    protected String primarySymbol;
    protected String secondarySymbol;
    protected int [][] board;

    public boolean validLocationForSymbol(int rowLocation, int colLocation){
        boolean valid = rowLocation > 0 && rowLocation < numberOfRows &&
                        colLocation > 0 && colLocation < numberOfCols &&
                        board[rowLocation][colLocation] == 0;
        return valid;
    }

    public void setPrimarySymbol(String primarySymbol){
        if(!primarySymbol.isEmpty() && (primarySymbol.length() <= 2 &&
                (secondarySymbol == null || !primarySymbol.equals(secondarySymbol)))){
            this.primarySymbol = primarySymbol;
        }
        else if(this.primarySymbol == null){
            this.primarySymbol = "X";
        }
    }

    public void setSecondarySymbol(String secondarySymbol){
        if(!secondarySymbol.isEmpty() && (secondarySymbol.length() <= 2) &&
                (primarySymbol == null || !primarySymbol.equals(secondarySymbol))){
            this.secondarySymbol = secondarySymbol;
        }
        else if (this.secondarySymbol == null){
            this.secondarySymbol = "O";
        }
    }

    private void placeSymbol(int rowPosition, int colPosition, int symbolValue){
        if(rowPosition >= 0 && rowPosition < board.length && colPosition >= 0 && colPosition < board[0].length){
            board[rowPosition][colPosition] = symbolValue;
        }
    }

    private void placeSymbol(String encodedPosition, int symbolValue){
        char rowChar = encodedPosition.charAt(0);
        char colChar = encodedPosition.charAt(1);
        int rowPosition = rowChar - 'A';
        int colPosition = Character.getNumericValue(colChar);
        placeSymbol(rowPosition,colPosition,symbolValue);
    }

    public void placePrimarySymbol(int rowPosition, int colPosition){
        placeSymbol(rowPosition,colPosition,PRIMARY_SYMBOL_VALUE);
    }

    public void placePrimarySymbol(String encodedPosition){
        placeSymbol(encodedPosition,PRIMARY_SYMBOL_VALUE);
    }

    public void placeSecondarySymbol(int rowPosition, int colPosition){
        placeSymbol(rowPosition,colPosition,SECONDARY_SYMBOL_VALUE);
    }

    public void placeSecondarySymbol(String encodedPosition){
        placeSymbol(encodedPosition,SECONDARY_SYMBOL_VALUE);
    }

    protected String getFormattedSymbol(String symbol){
        if(symbol.length() > 1){
            return symbol;
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

    public String getFormattedBoard(){
        return "```\n" + getBoard() + "\n```";
    }

    public abstract String getBoard();
}
