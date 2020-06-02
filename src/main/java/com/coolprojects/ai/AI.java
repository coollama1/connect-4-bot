package com.coolprojects.ai;

import com.coolprojects.game.components.Board;

public abstract class AI {
    protected Board gameBoard;

    public AI(Board newBoard){
        gameBoard = newBoard;
    }
    public abstract void makeMove();
}
