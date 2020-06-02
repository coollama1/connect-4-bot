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
            int randomRow = (int)(Math.random() * numberOfRows);
            int randomCol = (int)(Math.random() * numberOfCols);
            String encodedRowPosition = Character.toString((char)('a' + randomRow));
            String encodedColPosition = randomCol + "";
            randomPosition = encodedRowPosition + encodedColPosition;
        }while(!gameBoard.placeSecondarySymbol(randomPosition));
    }
}
