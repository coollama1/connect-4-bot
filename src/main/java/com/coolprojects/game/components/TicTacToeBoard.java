package com.coolprojects.game.components;

public class TicTacToeBoard extends Board{


    public TicTacToeBoard(int numberOfRows,int numberOfCols, String primarySymbol,String secondarySymbol){
        if(numberOfRows > 8){
            this.numberOfRows = 8;
        }
        else if (numberOfRows < 3){
            this.numberOfRows = 3;
        }
        else{
            this.numberOfRows = numberOfRows;
        }

        if(numberOfCols > 7){
            this.numberOfCols = 7;
        }
        else if (numberOfCols < 3){
            this.numberOfCols = 3;
        }
        else{
            this.numberOfCols = numberOfCols;
        }

        setPrimarySymbol(primarySymbol);
        setSecondarySymbol(secondarySymbol);

        board = new int [numberOfRows][numberOfCols];
    }

    public TicTacToeBoard(int numberOfRows,int numberOfCols){
        this(numberOfRows,numberOfCols,"X","O");
    }

    public TicTacToeBoard(){
        this(3,3,"X","O");
    }

    private boolean placeSymbol(String encodedPosition, int symbolValue){
        try{
            boolean symbolSuccessfullyPlaced = false;
            if(!encodedPosition.isEmpty() && encodedPosition.length() == 2){
                int rowLocation = encodedPosition.toLowerCase().charAt(0) - 'a';
                int colLocation = Integer.parseInt(encodedPosition.charAt(1) + "") - 1;
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
                                    "row-column index (e.g. \"a1\") or by using " +
                                    "the command (e.g.\"/move a1\")";
        return instructionsMessage;
    }

    private String getRowFloor(){
        String beginningOfFloor = "---";
        String repeatingString = "+---";
        String restOfFloor = repeatingString.repeat(numberOfCols - 1);
        String rowFloor = beginningOfFloor + restOfFloor;
        return rowFloor;
    }

    private String getTopIndices(){
        StringBuilder indices = new StringBuilder("");
        for(int c = 0; c < numberOfCols; c++){
            indices.append(" " + (c + 1) + "  ");
        }
        return indices.toString();
    }

    @Override
    protected String getBoardString(boolean includeIndices){
        StringBuilder boardString = new StringBuilder();
        String rowFloor = getRowFloor();
        String shiftSpacesToRight = "";
        if(includeIndices){
            String indices = "";
            indices = getTopIndices();
            shiftSpacesToRight = "  ";
            boardString.append(shiftSpacesToRight + indices);
            boardString.append('\n');
        }

        for(int c = 0; c < numberOfRows; c++){
            if(includeIndices){
                int charOfA = 'a';
                char currentIndex = (char)(charOfA + c);
                boardString.append(currentIndex + " ");
            }
            for(int d = 0; d < numberOfCols; d++){
                String currentSymbol = "   ";
                if(board[c][d] == 1){
                    currentSymbol = getFormattedPrimarySymbol();
                }
                else if(board[c][d] == -1){
                    currentSymbol = getFormattedSecondarySymbol();
                }
                boardString.append(currentSymbol);
                if(d < numberOfCols - 1){
                    boardString.append("|");
                }
            }
            if(c < numberOfRows - 1){
                boardString.append('\n' + shiftSpacesToRight + rowFloor + '\n');
            }
            else{
                boardString.append('\n');
            }
        }
        return boardString.toString();
    }

}
