package com.coolprojects.ai;

import com.coolprojects.game.components.Board;

public class TicTacToeAI extends AI {
    public TicTacToeAI(Board newBoard) {
        super(newBoard);
    }

    @Override
    public void makeMove() {
        int numberOfRows = gameBoard.getNumberOfRows();
        int numberOfCols = gameBoard.getNumberOfCols();
        String randomPosition;
        do{
            int randomRow = (int)(Math.random() * numberOfCols) + 1;
            int randomCol = (int)(Math.random() * numberOfRows);
            String encodedRowPosition = randomRow + "";
            String encodedColPosition  = Character.toString((char)('a' + randomCol));

            randomPosition = encodedColPosition + encodedRowPosition;
        }while(!gameBoard.placeSecondarySymbol(randomPosition));
    }
}
