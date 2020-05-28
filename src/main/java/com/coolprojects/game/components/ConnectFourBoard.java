package com.coolprojects.game.components;

public class ConnectFourBoard extends Board {

    public ConnectFourBoard(int numberOfRows, int numberOfCols, String primarySymbol, String secondarySymbol) {
        if(numberOfRows > 12){
            this.numberOfRows = 12;
        }
        else if (numberOfRows < 4){
            this.numberOfRows = 4;
        }
        else{
            this.numberOfRows = numberOfRows;
        }

        if(numberOfCols > 7){
            this.numberOfCols = 7;
        }
        else if (numberOfCols < 4){
            this.numberOfCols = 4;
        }
        else{
            this.numberOfCols = numberOfCols;
        }

        setPrimarySymbol(primarySymbol);
        setSecondarySymbol(secondarySymbol);

        board = new int [numberOfRows][numberOfCols];
    }

    public ConnectFourBoard(int numberOfRows,int numberOfCols) {
        this(numberOfRows,numberOfCols,"X","O");
    }

    public ConnectFourBoard(){
        this(5,5,"X","O");
    }

    private int getValidRowForSymbol(int colPosition){
        int rowPosition = -1;
        for(int c = board.length - 1; c >= 0; c--){
            if(board[c][colPosition] == EMPTY_SYMBOL_VALUE){
                rowPosition = c;
                return rowPosition;
            }
        }
        return rowPosition;
    }

    private boolean placeSymbol(String encodedPosition, int symbolValue){
        try{
            boolean symbolSuccessfullyPlaced = false;
            if(encodedPosition.length() == 1){
                int colLocation = encodedPosition.toLowerCase().charAt(0) - 'a';
                int rowLocation = getValidRowForSymbol(colLocation);
                if(validLocationForSymbol(rowLocation,colLocation)){
                    placeSymbol(rowLocation,colLocation,symbolValue);
                    symbolSuccessfullyPlaced = true;
                }
            }
            return symbolSuccessfullyPlaced;
        }catch(Exception e){
            return false;
        }

    }

    @Override
    public boolean placePrimarySymbol(String encodedPosition){
        return placeSymbol(encodedPosition,PRIMARY_SYMBOL_VALUE);
    }

    @Override
    public boolean placeSecondarySymbol(String encodedPosition){
        return placeSymbol(encodedPosition,SECONDARY_SYMBOL_VALUE);
    }

    @Override
    public String getInstructions(){
        String instructionsMessage = "You can make a move by either typing in the " +
                                    "letter of the column, (e.g. \"a\") or by " +
                                    "using the command (e.g. \"/move a\")";
        return instructionsMessage;
    }

    private String getIndicesString(){
        StringBuilder indices = new StringBuilder("");
        for(int c = 0; c < numberOfCols; c++){
            int charOfA = 'a';
            char currentIndex = (char)(charOfA + c);
            indices.append("  " + currentIndex + " ");
        }
        indices.append("\n");
        return indices.toString();
    }

    @Override
    protected String getBoardString(boolean includeIndices){
        String repeatingString = "----".repeat(numberOfCols);
        String boardFloor = "-" + repeatingString;
        StringBuilder boardString = new StringBuilder();
        String indices = "";

        if(includeIndices){
            indices = getIndicesString();
        }
        boardString.append(indices);

        for(int c = 0; c < numberOfRows; c++){
            boardString.append("|");
            for(int d = 0; d < numberOfCols; d++){
                String currentSymbol = "   ";
                if(board[c][d] == 1){
                    currentSymbol = getFormattedPrimarySymbol();
                }
                else if(board[c][d] == -1){
                    currentSymbol = getFormattedSecondarySymbol();
                }
                boardString.append(currentSymbol + "|");
            }
            boardString.append("\n");
        }
        boardString.append(boardFloor);
        return boardString.toString();
    }


}
