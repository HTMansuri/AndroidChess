package com.example.myapplication;

/**
 * The Pawn class represents a pawn chess piece on a chess board. 
 * It extends the Board class and inherits its methods and properties.
 * 
 * @author Pavitra Patel, Huzaif Mansuri
 */

public class Pawn extends Board
{
	/**
     * The color of the pawn. ("w" for White or "b" for Black).
     */
	private String color;
	
	/**
     * The name of the pawn. Always "p" for Pawn.
     */
	private String name;
	
	/** 
	 * Indicates whether en passant is possible. 
	 */
    public static Boolean enPassant = false;
	
    /** 
     * The position where en passant is possible. 
     */
    public static String enPassantPos;
	
    /**
     * Creates a new Pawn object with default values for color and name.
     */
	public Pawn()
	{
		color="";
		name="p";
	}
	
	/**
	 * Checks if the move is valid for the Pawn
	 * @param board the game board
	 * @param initiali the initial column of the Pawn
	 * @param initialj the initial row of the Pawn
	 * @param finali the final column of the Pawn
	 * @param finalj the final row of the Pawn
	 * @return true if the move is valid, false otherwise
	 */
	public boolean isValid(Board[][] board, int initiali, int initialj, int finali, int finalj)
	{
		if(this.getColor().equals("w")) {
			int iChange = finali-initiali;
			int jChange = finalj-initialj;
			if(iChange==1 || iChange==2) {
				if((iChange==1 && jChange == 0)) {
					if((board[finali][finalj] == null)) {
						{
							return true;
						}
					}
				}
				else if((iChange==2 && jChange == 0) && initiali==1) {
					if((board[initiali+1][initialj] == null) && (board[finali][finalj] == null))
						{
							if((finalj-1>=0 && board[finali][finalj-1] != null && board[finali][finalj-1].getName().equals("p") && board[finali][finalj-1].getColor().equals("b"))||(finalj+1<8 && board[finali][finalj+1] != null && board[finali][finalj+1].getName().equals("p") && board[finali][finalj+1].getColor().equals("b")))
								{
									enPassantPos = String.valueOf(finali)+String.valueOf(finalj);
									enPassant = true;
								}
							return true;
						}
				}
				else if((iChange==1 && jChange == 1) || (iChange==1 && jChange == -1)) {
					if((board[finali][finalj] != null) && (board[finali][finalj].getColor()=="b"))
						{
							return true;
						}
					else if(enPassant && enPassantPos.equals(String.valueOf(finali-1)+String.valueOf(finalj))) {
						board[finali-1][finalj] = null;
						return true;
					}
				}
			}
			return false;
		}
		else {
			int iChange = finali-initiali;
			int jChange = finalj-initialj;
			if(iChange==-1 || iChange==-2) {
				if((iChange==-1 && jChange == 0)) {
					if((board[finali][finalj] == null)) {
						return true;
					}
				}
				else if((iChange==-2 && jChange == 0) && initiali==6) {
					if((board[initiali-1][initialj] == null) && (board[finali][finalj] == null))
					{
						if((finalj-1>=0 && board[finali][finalj-1] != null && board[finali][finalj-1].getName().equals("p") && board[finali][finalj-1].getColor().equals("w"))||(finalj+1<8 && board[finali][finalj+1] != null && board[finali][finalj+1].getName().equals("p") && board[finali][finalj+1].getColor().equals("w")))
						{
							enPassantPos = String.valueOf(finali)+String.valueOf(finalj);
							enPassant = true;
						}
						return true;
					}
				}
				else if((iChange==-1 && jChange == -1) || (iChange==-1 && jChange == 1)) {
					if((board[finali][finalj] != null) && (board[finali][finalj].getColor()=="w"))
						{
							return true;
						}
					else if(enPassant && enPassantPos.equals(String.valueOf(finali+1)+String.valueOf(finalj))) {
						board[finali+1][finalj] = null;
						return true;
					}
				}
			}
			return false;
			}
	}
	
	/**
	 * Creates a new Pawn object with the same color and name as the current Pawn object.
	 * @param obj the Board object to move the Pawn to
	 * @return a new Pawn object with the same color and name as the current Pawn object
	 */
	public Board move(Board obj)
	{
		obj = new Pawn();
		obj.setColor(this.getColor());
		obj.setName(this.getName());
		return obj;
	}
	
	/**
	 * Sets the color of the Pawn object.
	 * @param c a String representing the color of the Pawn object ("w" for white, "b" for black)
	 */
	public void setColor(String c)
	{
		color=c;
	}
	
	/**
	 * Sets the name of the Pawn object.
	 * @param n a String representing the name of the Pawn object ("p")
	 */
	public void setName (String n)
	{
		name=n;
	}
	
	/**
	 * Gets the color of the Pawn object.
	 * @return a String representing the color of the Pawn object ("w" for white, "b" for black)
	 */
	public String getColor()
	{
		return color;
	}
    
	/**
	 * Gets the name of the Pawn object.
	 * @return a String representing the name of the Pawn object ("p")
	 */
	public String getName()
	{
		return name;
	}
}