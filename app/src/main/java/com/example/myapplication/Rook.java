package com.example.myapplication;

/**
 * This class represents a rook in the game of chess.
 * 
 * @author Pavitra Patel, Huzaif Mansuri
 */
public class Rook extends Board
{
	/**
	 * The color of the Rook piece. (usually "b" or "w")
	 */
	private String color;
	/**
	 * The name of the Rook piece. (usually "R")
	 */
	private String name;
	
	/**
     * Constructs a new Rook object with default values.
     */
	public Rook(int pC)
	{
		color="";
		name = "R";
		pieceCount=pC;
	}

	public String getUIName()
	{
		if(color.equals("b"))
			return "blackrook" + pieceCount;
		else
			return "whiterook" + pieceCount;
	}
	
	/**
     * Determines if the rook's move from its initial position to its final position
     * is a valid move on the given chess board.
     *
     * @param board     the chess board on which the move is made
     * @param initiali  the initial column position of the rook
     * @param initialj  the initial row position of the rook
     * @param finali    the final column position of the rook
     * @param finalj    the final row position of the rook
     * @return          true if the move is valid, false otherwise
     */
	public boolean isValid(Board[][] board, int initiali, int initialj, int finali, int finalj)
	{
		boolean status = false;
		
		if(initiali == finali)	
		{
			status = true;
			if(initialj < finalj) // example moving from (0,0) to (0,7)
			{
				initialj++; //don't want to check the initial position for any object present or not
				//returns false if there is any other object between initial and final move of the Rook
				while(initialj < finalj)
				{
					if(board[initiali][initialj] != null)
					{
						return false;
					}
					initialj++;
				}
			}
			else if(initialj > finalj) // example moving from (0,7) to (0,0)
			{
				initialj--; //don't want to check the initial position for any object present or not
				//returns false if there is any other object between initial and final move of the Rook
				while(initialj > finalj)
				{
					if(board[initiali][initialj] != null)
					{
						return false;
					}
					initialj--;
				}
			}
		}
		else if(initialj == finalj)
		{
			status = true;
			if(initiali < finali)
			{	
				initiali++;
				while(initiali < finali)
				{
					if(board[initiali][initialj] != null)
					{
						return false;
					}
					initiali++;
				}
			}
			else if(initiali > finali)
			{
				initiali--;
				while(initiali > finali)
				{
					if(board[initiali][initialj] != null)
					{
						return false;
					}
					initiali--;
				}
			}
		}
		
		return status;
	}
	
	/**
     * Moves the rook to a new position on the chess board.
     *
     * @param obj   the new position for the rook
     * @return      a new Rook object with the specified color
     */
	public Board move(Board obj)
	{
		obj = new Rook(pieceCount);
		obj.setColor(this.getColor());
		obj.setName(this.getName());
		return obj;
	}
	
	/**
     * Sets the color of the rook.
     *
     * @param c     the color of the rook
     */
	public void setColor(String c)
	{
		color=c;
	}
	
	/**
     * Sets the name of the rook.
     *
     * @param n     the name of the rook (usually "R")
     */
	public void setName(String n)
	{
		name = n;
	}
	
	/**
     * Gets the color of the rook.
     *
     * @return  the color of the rook
     */
	public String getColor()
	{
		return color;
	}
	
	/**
     * Gets the name of the rook.
     *
     * @return  the name of the rook (usually "R")
     */
	public String getName()
	{
		return name;
	}
}