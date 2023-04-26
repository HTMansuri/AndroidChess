package com.example.myapplication;

/**
 * The Knight class represents a chess piece that moves in an L-shape and can jump over other pieces.
 * It extends the Board class.
 * 
 * @author Pavitra Patel, Huzaif Mansuri
 */
public class Knight extends Board
{
	/**
     * The color of the knight. ("w" for White or "b" for Black).
     */
	private String color;
    
	/**
     * The name of the knight. Always "N" for Knight.
     */
	private String name;
    
	/**
     * Creates a new Knight object with default values for color and name.
     */
	public Knight(int pC)
    {
        color = "";
        name = "N";
        pieceCount=pC;
    }

    public String getUIName()
    {
        if(color.equals("b"))
            return "blackknight" + pieceCount;
        else
            return "whiteknight" + pieceCount;
    }
    
    /**
     * Checks whether the move from initial position to final position is valid for the Knight.
     * A valid move for the Knight is a move that moves two squares horizontally or vertically and one square horizontally or vertically.
     * @param board A 2D array representing the chess board.
     * @param initiali The initial column index of the Knight.
     * @param initialj The initial row index of the Knight.
     * @param finali The final column index of the Knight.
     * @param finalj The final row index of the Knight.
     * @return true if the move is valid, false otherwise.
     */
    public boolean isValid(Board[][] board, int initiali, int initialj, int finali, int finalj)
    {
        int iChange=finali-initiali;
        int jChange=finalj-initialj;
        if((Math.abs(iChange)==1 && Math.abs(jChange)==2) || (Math.abs(jChange)==1 && Math.abs(iChange)==2))
            return true;
        return false;
    }
    
    /**
	 * Creates a new Knight object with the same color and name as the current Knight object.
	 * @param obj the Board object to move the Knight to
	 * @return a new Knight object with the same color and name as the current Knight object
	 */
	public Board move(Board obj)
    {
        obj = new Knight(pieceCount);
        obj.setColor(this.getColor());
        obj.setName(this.getName());
        return obj;
    }
    
	/**
	 * Sets the color of the Knight object.
	 * @param c a String representing the color of the Knight object ("w" for white, "b" for black)
	 */
	public void setColor(String c)
    {
        color=c;
    }
    
	/**
	 * Sets the name of the Knight object.
	 * @param n a String representing the name of the Knight object ("N")
	 */
	public void setName (String n)
    {
        name=n;
    }
    
	/**
	 * Gets the color of the Knight object.
	 * @return a String representing the color of the Knight object ("w" for white, "b" for black)
	 */
	public String getColor()
    {
        return color;
    }
    
	/**
	 * Gets the name of the Knight object.
	 * @return a String representing the name of the Knight object ("N")
	 */
	public String getName()
    {
        return name;
    }
}
