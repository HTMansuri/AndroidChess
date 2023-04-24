package com.example.myapplication;

/**
 * This class represents a queen in the game of chess.
 * 
 * @author Pavitra Patel, Huzaif Mansuri
 */
public class Queen extends Board
{
	/**
	 * The color of the Queen piece. (usually "b" or "w")
	 */
	private String color;
	/**
	 * The name of the Queen piece. (usually "Q")
	 */
	private String name;
	
	/**
     * Constructs a new Queen object with default values.
     */
	public Queen()
	{
		color="";
		name="Q";
	}
	
	/**
     * Determines if the queen's move from its initial position to its final position
     * is a valid move on the given chess board.
     *
     * @param board     the chess board on which the move is made
     * @param initiali  the initial column position of the queen
     * @param initialj  the initial row position of the queen
     * @param finali    the final column position of the queen
     * @param finalj    the final row position of the queen
     * @return          true if the move is valid, false otherwise
     */
	public boolean isValid(Board[][] board, int initiali, int initialj, int finali, int finalj)
	{
		boolean status = false;
		
		Rook r = new Rook();
		Bishop b = new Bishop();
		
		status = r.isValid(board, initiali, initialj, finali, finalj);
		if(!status)
			status = b.isValid(board, initiali, initialj, finali, finalj);
		
		return status;
	}
	
	/**
     * Moves the queen to a new position on the chess board.
     *
     * @param obj   the new position for the queen
     * @return      a new Queen object with the specified color
     */
	public Board move(Board obj)
	{
		obj = new Queen();
		obj.setColor(this.getColor());
		obj.setName(this.getName());
		return obj;
	}
	
	/**
     * Sets the color of the queen.
     *
     * @param c     the color of the queen
     */
	public void setColor(String c)
	{
		color=c;
	}
	
	/**
     * Sets the name of the queen.
     *
     * @param n     the name of the queen (usually "Q")
     */
	public void setName(String n)
	{
		name=n;
	}
	
	/**
     * Gets the color of the queen.
     *
     * @return  the color of the queen
     */
	public String getColor()
	{
		return color;
	}
	
	/**
     * Gets the name of the queen.
     *
     * @return  the name of the queen (usually "Q")
     */
	public String getName()
	{
		return name;
	}
}