package com.example.myapplication;

import androidx.annotation.Nullable;

/**
 * This is an abstract class that represents a chess board.
 * 
 * @author Pavitra Patel, Huzaif Mansuri
 */
public abstract class Board 
{
    public int pieceCount;

    /**
     * This is the default constructor for the Board class.
     */
    public Board()
    {
    	
    }

//    public int getPieceCount()
//    {
//        return pieceCount;
//    }
//    public void setPieceCount(int pC)
//    {
//        pieceCount = pC;
//    }

    public abstract String getUIName();
    
    /**
     * This is an abstract method that should be implemented to make a move on the board.
     *
     * @param obj	the Board object to move
     * @return 		a Board object representing the new state of the board after the move
     */
    public abstract Board move(Board obj);
    
    
    /**
     * This is an abstract method that should be implemented to determine if a move is valid on the board.
     *
     * @param board		 a 2D array representing the current state of the board
     * @param initiali	 the initial column of the piece being moved
     * @param initialj	 the initial row of the piece being moved
     * @param finali	 the final column of the piece being moved
     * @param finalj	 the final row of the piece being moved
     * @return 			 true if the move is valid, false otherwise
     */
    public abstract boolean isValid(Board[][] board, int initiali, int initialj, int finali, int finalj);
    
    
    /**
     * This is an abstract method that should be implemented to set the color of a piece on the board.
     *
     * @param c		a String representing the color of a piece on the board
     */
    public abstract void setColor(String c);
    
    
    /**
     * This is an abstract method that should be implemented to set the name of a piece on the board.
     *
     * @param n		a String representing the name of a piece on the board
     */
    public abstract void setName(String n);
    
    
    /**
     * This is an abstract method that should be implemented to get the color of a piece on the board.
     *
     * @return		a String representing the color of a piece on the board
     */
    public abstract String getColor();

    public static boolean equalsB(Board[][] a, Board[][] b)
    {
        for(int i=0; i<7; i++)
        {
            for(int j=0; j<7; j++)
            {
                if(a[i][j] != b[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This is an abstract method that should be implemented to get the name of a piece on the board.
     *
     * @return		a String representing the name of a piece on the board
     */
    public abstract String getName();
}