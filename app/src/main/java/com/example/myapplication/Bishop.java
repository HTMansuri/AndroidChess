package com.example.myapplication;

/**
 * This class represents a bishop in the game of chess.
 * 
 * @author Pavitra Patel, Huzaif Mansuri
 */
public class Bishop extends Board
{
	/**
	 * The color of the Bishop piece. (usually "b" or "w")
	 */
	private String color;
	/**
	 * The name of the Bishop piece. (usually "B")
	 */
	private String name;
	
	/**
     * Constructs a new Bishop object with default values.
     */
	public Bishop(int pC)
	{
		color="";
		name="B";
		pieceCount=pC;
	}

	public String getUIName()
	{
		if(color.equals("b"))
			return "blackbishop" + pieceCount;
		else
			return "whitebishop" + pieceCount;
	}
	
	/**
     * Determines if the bishop's move from its initial position to its final position
     * is a valid move on the given chess board.
     *
     * @param board     the chess board on which the move is made
     * @param initiali  the initial column position of the bishop
     * @param initialj  the initial row position of the bishop
     * @param finali    the final column position of the bishop
     * @param finalj    the final row position of the bishop
     * @return          true if the move is valid, false otherwise
     */
	public boolean isValid(Board[][] board, int initiali, int initialj, int finali, int finalj)
	{
		boolean status = false;
		
		if(Math.abs(initiali-finali) == Math.abs(initialj-finalj))
		{
			status = true;
			if(initiali > finali && initialj > finalj)
			{
				initiali--;
				initialj--;
				while(initiali > finali && initialj > finalj)
				{
					if(board[initiali][initialj] != null)
					{
						return false;
					}
					initiali--;
					initialj--;
				}
			}
			else if(initiali < finali && initialj < finalj)
			{
				initiali++;
				initialj++;
				while(initiali < finali && initialj < finalj)
				{
					if(board[initiali][initialj] != null)
					{
						return false;
					}
					initiali++;
					initialj++;
				}
			}
			else if(initiali < finali && initialj > finalj)
			{
				initiali++;
				initialj--;
				while(initiali < finali && initialj > finalj)
				{
					if(board[initiali][initialj] != null)
					{
						return false;
					}
					initiali++;
					initialj--;
				}
			}
			else if(initiali > finali && initialj < finalj)
			{
				initiali--;
				initialj++;
				while(initiali > finali && initialj < finalj)
				{
					if(board[initiali][initialj] != null)
					{
						return false;
					}
					initiali--;
					initialj++;
				}
			}
			else
			{
				status = false;
			}
		}
		
		return status;
	}
	
	/**
     * Moves the bishop to a new position on the chess board.
     *
     * @param obj   the new position for the bishop
     * @return      a new Bishop object with the specified color
     */
	public Board move(Board obj)
	{
		obj = new Bishop(pieceCount);
		obj.setColor(this.getColor());
		obj.setName(this.getName());
		return obj;
	}
	
	/**
     * Sets the color of the bishop.
     *
     * @param c     the color of the bishop
     */
	public void setColor(String c)
	{
		color=c;
	}
	
	/**
     * Sets the name of the bishop.
     *
     * @param n     the name of the bishop (usually "B")
     */
	public void setName(String n)
	{
		name=n;
	}
	
	/**
     * Gets the color of the bishop.
     *
     * @return  the color of the bishop
     */
	public String getColor()
	{
		return color;
	}
	
	/**
     * Gets the name of the bishop.
     *
     * @return  the name of the bishop (usually "B")
     */
	public String getName()
	{
		return name;
	}
}