package com.coolprojects.ai;

import com.coolprojects.game.components.Board;

public class ConnectFourAI extends AI{
    public ConnectFourAI(Board newBoard) {
        super(newBoard);
    }

    @Override
    public void makeMove() {
        int numberOfCols = gameBoard.getNumberOfCols();
        String randomPosition;
        do{
            int randomCol = (int)(Math.random() * numberOfCols);
            randomPosition = Character.toString((char)('a' + randomCol));
        }while(!gameBoard.placeSecondarySymbol(randomPosition));

    }
}
