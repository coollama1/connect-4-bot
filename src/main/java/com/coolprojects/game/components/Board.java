package com.coolprojects.game.components;

import java.util.List;
import java.util.ArrayList;

public abstract class Board {
    public static int PRIMARY_SYMBOL_VALUE = 1;
    public static int SECONDARY_SYMBOL_VALUE = -1;
    public static int EMPTY_SYMBOL_VALUE = 0;
    public static int NO_MATCHES = 0;
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

    public boolean isBoardFilled(){
        for(int [] row : board){
            for(int cell : row){
                if(cell == EMPTY_SYMBOL_VALUE){
                    return false;
                }
            }
        }
        return true;
    }

    public int checkForMatches(int numberOfMatches){
        List<List<Integer>> listOfRows = getAllRows();
        List<List<Integer>> listOfColumns = getAllColumns();
        List<List<Integer>> listOfDiagonals = getAllDiagonals();
        int rowMatchResult = checkAllListsForMatches(listOfRows,numberOfMatches);
        int columnMatchResult = checkAllListsForMatches(listOfColumns,numberOfMatches);
        int diagonalMatchResult = checkAllListsForMatches(listOfDiagonals,numberOfMatches);

        if(rowMatchResult != NO_MATCHES){
            return rowMatchResult;
        }
        else if(columnMatchResult != NO_MATCHES){
            return columnMatchResult;
        }
        else if(diagonalMatchResult != NO_MATCHES){
            return diagonalMatchResult;
        }
        return NO_MATCHES;
    }

    protected int checkAllListsForMatches(List<List<Integer>> listOfLists, int numberOfMatches){
        for(List<Integer> currentListOfSymbols : listOfLists){
            int matchResult = checkListForMatches(currentListOfSymbols,numberOfMatches);
            if(matchResult != NO_MATCHES){
                return matchResult;
            }
        }
        return NO_MATCHES;
    }

    protected int checkListForMatches(List<Integer> listOfSymbols, int numberOfMatches){
        for(int c = 0; c < listOfSymbols.size(); c++){
            int initialSymbolValue = listOfSymbols.get(c);
            if(initialSymbolValue != EMPTY_SYMBOL_VALUE){
                int count = 1;
                for(int d = c + 1; d < listOfSymbols.size(); d++){
                    int currentSymbol = listOfSymbols.get(d);
                    if(currentSymbol == initialSymbolValue){
                        c++;
                        count++;
                    }
                    else if(currentSymbol == EMPTY_SYMBOL_VALUE){
                        c++;
                        break;
                    }
                    else{
                        break;
                    }
                    if(count >= numberOfMatches){
                        return initialSymbolValue;
                    }
                }
            }
        }
        return NO_MATCHES;
    }

    private List<Integer> intArrayToList(int[] array){
        List<Integer> arrayList = new ArrayList<>();
        for(int currentNumber : array){
            arrayList.add(currentNumber);
        }
        return arrayList;
    }

    protected List<List<Integer>> getAllRows(){
        List<List<Integer>> listOfRows = new ArrayList<>();
        for(int c = 0; c < getNumberOfRows(); c++){
            int [] currentRowArray = board[c];
            List<Integer> currentRowList = intArrayToList(currentRowArray);
            listOfRows.add(currentRowList);
        }
        return listOfRows;
    }

    protected List<List<Integer>> getAllColumns(){
        List<List<Integer>> listOfColumns = new ArrayList<>();
        for(int c = 0; c < getNumberOfCols(); c++){
            List<Integer> currentColumn = columnToList(c);
            listOfColumns.add(currentColumn);
        }
        return listOfColumns;
    }

    protected List<List<Integer>> getAllDiagonals(){
        List<List<Integer>> listOfDiagonals = new ArrayList<>();
        for(int c = 0; c < getNumberOfCols(); c++){
            List<Integer> currentDiagonal = diagonalToList(0,c);
            listOfDiagonals.add(currentDiagonal);
        }
        for(int c = 0; c < getNumberOfRows(); c++){
            List<Integer> currentDiagonal = diagonalToList(c,0);
            List<Integer> currentReverseDiagonal = diagonalToListReverseRows(c,0);
            listOfDiagonals.add(currentDiagonal);
            listOfDiagonals.add(currentReverseDiagonal);

        }
        return listOfDiagonals;
    }


    protected List<Integer> diagonalToList(int startRow,int startColumn){
        List<Integer> diagonal = new ArrayList<>();
        for(int c = startRow, d = startColumn; c < getNumberOfRows() &&
                d < getNumberOfCols(); c++,d++){
                diagonal.add(board[c][d]);
        }
        return diagonal;
    }

    protected List<Integer> diagonalToListReverseRows(int startRow, int startColumn){
        List<Integer> diagonal = new ArrayList<>();
        for(int c = startRow, d = startColumn; c >= 0 && d < getNumberOfCols(); c--,d++){
            diagonal.add(board[c][d]);
        }
        return diagonal;
    }

    protected List<Integer> columnToList(int targetColumn){
        List<Integer> column = new ArrayList<>();
        for(int c = 0; c < getNumberOfRows(); c++){
            column.add(board[c][targetColumn]);
        }
        return column;
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
