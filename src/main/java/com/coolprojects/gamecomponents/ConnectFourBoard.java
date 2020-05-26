package com.coolprojects.gamecomponents;

import org.mapdb.Atomic;

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

        if(numberOfCols > 8){
            this.numberOfCols = 8;
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

    public String getBoard(){
        String repeatingString = "----".repeat(numberOfCols);
        String boardFloor = "-" + repeatingString;
        StringBuilder boardString = new StringBuilder();

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
