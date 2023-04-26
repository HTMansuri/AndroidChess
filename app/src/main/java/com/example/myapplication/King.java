package com.example.myapplication;

/**
 * The King class represents a king chess piece on a chess board. 
 * It extends the Board class and inherits its methods and properties.
 * 
 * @author Pavitra Patel, Huzaif Mansuri
 */
public class King extends Board
{
	/**
     * The color of the king. ("w" for White or "b" for Black).
     */
	private String color;
	
	/**
     * The name of the king. Always "K" for king.
     */
	private String name;
	
	/**
     * Indicates if the white left castling is still possible.
     */
	public static boolean wlcast=true;
	
	/**
     * Indicates if the white right castling is still possible.
     */
	public static boolean wrcast=true;
	
	/**
     * Indicates if the black left castling is still possible.
     */
	public static boolean blcast=true;
	
	/**
     * Indicates if the black right castling is still possible.
     */
	public static boolean brcast=true;
	
	/**
     * Creates a new King object with default values for color and name.
     */
	public King()
	{
		color="";
		name="K";
	}

	public String getUIName()
	{
		if(color.equals("b"))
			return "blackking";
		else
			return "whiteking";
	}
	
	/**
     * Checks if a move from the initial position to the final position of the king is valid.
     * 
     * @param board the chess board as a 2D array of Board objects
     * @param initiali the initial column index of the king on the board
     * @param initialj the initial row index of the king on the board
     * @param finali the final column index of the king on the board
     * @param finalj the final row index of the king on the board
     * @return true if the move is valid, false otherwise
     */
	public boolean isValid(Board[][] board, int initiali, int initialj, int finali, int finalj)
	{
		//checks if there are any pieces between Rook and King before castling
		if(((this.getColor().equals("w") && (wrcast || wlcast)) || (this.getColor().equals("b") && (blcast || brcast))) && Math.abs(initiali-finali) == 0 && Math.abs(initialj-finalj) == 2)
		{
			boolean status = false;
			if(finalj==2 && ((wlcast && finali==0) || (blcast && finali==7)))
			{
				status = true;
				int i=0;
				if(finali==7)
					i=7;
				for(int j=1; j<4; j++)
				{
					if(board[i][j] != null)
						return false;
				}
			}
			else if(finalj==6 && ((wrcast && finali==0) || (brcast && finali==7)))
			{
				status = true;
				int i=0;
				if(finali==7)
					i=7;
				for(int j=5; j<7; j++)
				{
					if(board[i][j] != null)
						return false;
				}
			}
			return status;
		}
				
		int iChange = finali-initiali;
		int jChange = finalj-initialj;
		if(iChange*jChange==0 && Math.abs(iChange+jChange)==1) {
				return true;
		}
		else if(Math.abs(iChange)+Math.abs(jChange)==2 && iChange*jChange!=0){
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a new King object with the same color and name as the current King object.
	 * @param obj the Board object to move the King to
	 * @return a new King object with the same color and name as the current King object
	 */
	public Board move(Board obj)
	{
		obj = new King();
		obj.setColor(this.getColor());
		obj.setName(this.getName());
		return obj;
	}

	/**
	 * Sets the color of the King object.
	 * @param c a String representing the color of the King object ("w" for white, "b" for black)
	 */
	public void setColor(String c)
	{
		color=c;
	}

	/**
	 * Sets the name of the King object.
	 * @param n a String representing the name of the King object ("K")
	 */
	public void setName(String n)
	{
		name=n;
	}

	/**
	 * Gets the color of the King object.
	 * @return a String representing the color of the King object ("w" for white, "b" for black)
	 */
	public String getColor()
	{
		return color;
	}

	/**
	 * Gets the name of the King object.
	 * @return a String representing the name of the King object ("K")
	 */
	public String getName()
	{
		return name;
	}
}