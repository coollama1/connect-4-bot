package com.coolprojects.gamecomponents;

public class TicTacToeBoard extends Board{

    public TicTacToeBoard(int numberOfRows,int numberOfCols, String primarySymbol,String secondarySymbol){
        if(numberOfRows > 10){
            this.numberOfRows = 10;
        }
        else if (numberOfRows < 3){
            this.numberOfRows = 3;
        }
        else{
            this.numberOfRows = numberOfRows;
        }

        if(numberOfCols > 6){
            this.numberOfCols = 6;
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

    private String getRowFloor(){
        String beginningOfFloor = "---";
        String repeatingString = "+---";
        String restOfFloor = repeatingString.repeat(numberOfCols - 1);
        String rowFloor = '\n' + beginningOfFloor + restOfFloor + '\n';
        return rowFloor;
    }

    public String getBoard(){
        StringBuilder boardString = new StringBuilder();
        String rowFloor = getRowFloor();
        for(int c = 0; c < numberOfRows; c++){
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
                boardString.append(rowFloor);
            }
            else{
                boardString.append('\n');
            }
        }
        return boardString.toString();
    }
}
