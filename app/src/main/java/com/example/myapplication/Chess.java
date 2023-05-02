/**
 * This class has implementation of few methods that runs a chess game.
 *
 * @author Pavitra Patel, Huzaif Mansuri
 */
package com.example.myapplication;

public class Chess
{
	/**
     * Checks if King at provided position is in Check.
     *
     * @param board the chess board as a 2D array of Board objects
     * @param kingi the initial column index of the king on the board
     * @param kingj the initial row index of the king on the board
     * @return true if the King is at Check at provided position, false otherwise
     */
	public static boolean check(Board[][] board, int kingi, int kingj)
	{
		boolean check;
		for(int i=7; i>=0; i--)
		{
			for(int j=0; j<=7; j++)
			{
				String color="";
				if(board[i][j]!=null)
					color = board[i][j].getColor();
				if(board[i][j] != null && !board[kingi][kingj].getColor().equals(color))
				{
					check = board[i][j].isValid(board, i, j, kingi, kingj);
					if(check)
						return true;
				}
			}
		}
		return false;
	}
//
	/**
	 * This method checks if any of the key of the provided color except King can be validly moved to the provided position
	 *
     * This is a helper function to CheckMate method. It helps in the following ways:
     * It checks if the provided position is safe for the King or not
     * It also checks if the check giving Knight can be killed.
     * It also helps to see if any other key can be placed in between the check.
     *
     * @param board the chess board as a 2D array of Board objects
     * @param finali the final column index intended to reach on the board
     * @param finalj the final row index intended to reach on the board
     * @return true if the any key of provided color except King can be validly moved to provided position, false otherwise
     */
	public static boolean reachHere(Board[][] board, int finali, int finalj, String color)
	{
		boolean reached;
		for(int i=7; i>=0; i--)
		{
			for(int j=0; j<=7; j++)
			{
				if(board[i][j] != null && !board[i][j].getName().equals("K") && board[i][j].getColor().equals(color))
				{
					reached = board[i][j].isValid(board, i, j, finali, finalj);
					if(reached) {
						return true;
					}
				}
			}
		}
		return false;
	}
//
	/**
     * Checks if King at provided position is CheckMated.
     *
     * @param board the chess board as a 2D array of Board objects
     * @param kingi the initial column index of the king on the board
     * @param kingj the initial row index of the king on the board
     * @return true if the King is CheckMated at provided position, false otherwise
     */
	public static boolean checkMate(Board[][] board, int kingi, int kingj) {
		boolean checkMate;
		for(int i=7; i>=0; i--)
		{
			for(int j=0; j<=7; j++)
			{
				String color="";
				if(board[i][j]!=null)
					color = board[i][j].getColor();
				if(board[i][j] != null && !board[kingi][kingj].getColor().equals(color))
				{
					checkMate = board[i][j].isValid(board, i, j, kingi, kingj);
					if(checkMate)
					{
						for(int p=-1; p<=1; p++)
						{
							for(int q=1; q>=-1; q--)
							{
								if(kingi-p<0 || kingi-p>7 || kingj-q<0 || kingj-q>7 || (board[kingi-p][kingj-q]!=null && !board[kingi-p][kingj-q].getColor().equals(color)))
									{
										continue;
									}
								else {
									boolean check = true;
									if(board[kingi-p][kingj-q]==null)
									{
										board[kingi - p][kingj - q] = board[kingi][kingj];
										board[kingi][kingj] = null;
										check = reachHere(board, kingi - p, kingj - q, color);
										board[kingi][kingj] = board[kingi - p][kingj - q];
										board[kingi - p][kingj - q] = null;
									}
									else{
										if(board[kingi][kingj].isValid(board,kingi,kingj,kingi-p,kingj-q))
											check = false;
									}
										if(!check) {
											checkMate = false;
											return checkMate;
										}
								}
							}
						}
						color = board[kingi][kingj].getColor();
						// region of code to check if we can kill check giving knight
						if(board[i][j].getName().equals("N"))
						{
								if(reachHere(board,i,j,color))
								{
									checkMate = false;
								}
								return checkMate;
						}
						// region of code to check if we can defend by killing the check giving piece or by bringing the piece in between.
						int iChange = 0;
						int jChange = 0;
						int x = i, y = j;
						if(x-kingi != 0)
							iChange = (kingi-x)/Math.abs(x-kingi);
						if(y-kingj!=0)
							jChange = (kingj-y)/Math.abs(y-kingj);
						while(!(x==kingi && y==kingj))
						{
							if(reachHere(board,x,y,color))
							{
								checkMate = false;
								return checkMate;
							}
							x += iChange;
							y += jChange;
						}
					}
				}
			}
		}
		return true;
		}

	/**
     * Initialize the default/initial Chess Board.
     *
     * @param board the chess board as a 2D array of Board objects
     */
	public static void initChessBoard(Board[][] board)
	{
		board[0][0] = new Rook(1);
		board[0][7] = new Rook(2);
		board[7][0] = new Rook(1);
		board[7][7] = new Rook(2);

		board[0][1] = new Knight(1);
		board[0][6] = new Knight(2);
		board[7][1] = new Knight(1);
		board[7][6] = new Knight(2);

		board[0][2] = new Bishop(1);
		board[0][5] = new Bishop(2);
		board[7][2] = new Bishop(1);
		board[7][5] = new Bishop(2);

		board[0][3] = new Queen(1);
		board[7][3] = new Queen(1);

		board[0][4] = new King();
		board[7][4] = new King();

		for(int i=0; i<1; i++)
		{
			for(int j=0; j<8; j++)
			{
				board[i][j].setColor("b");
			}
		}

		for(int i=7; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				board[i][j].setColor("w");
			}
		}

		for(int i=1; i<2; i++)
		{
			for(int j=0; j<8; j++)
			{
				board[i][j] = new Pawn(j+1);
				board[i][j].setColor("b");
			}
		}

		for(int i=6; i<7; i++)
		{
			for(int j=0; j<8; j++)
			{
				board[i][j] = new Pawn(j+1);
				board[i][j].setColor("w");
			}
		}
	}

	public static String castling(Board[][] chessboard, int initialrow, int initialcol, int row, int col)
	{
		String nm = null;
		// Checks for valid castling situation
		if(((chessboard[initialrow][initialcol].getColor().equals("w") && (King.wrcast || King.wlcast)) || (chessboard[initialrow][initialcol].getColor().equals("b") && (King.blcast || King.brcast))) && chessboard[initialrow][initialcol].getName().equals("K"))
		{
			//Identify castling side and color, and perform rook move accordingly
			if(King.blcast && row==0 && col==2)
			{
				//rook from 0,0 to 0,3
				chessboard[0][3] = new Rook(chessboard[0][0].pieceCount);
				chessboard[0][3].setColor("b");
				chessboard[0][3].setName("R");
				chessboard[0][0] = null;
				nm = chessboard[0][3].getUIName();
			}
			else if(King.wlcast && row==7 && col==2)
			{
				//rook from 7,0 to 7,3
				chessboard[7][3] = new Rook(chessboard[7][0].pieceCount);
				chessboard[7][3].setColor("w");
				chessboard[7][3].setName("R");
				chessboard[7][0] = null;
				nm = chessboard[7][3].getUIName();
			}
			else if(King.brcast && row==0 && col==6)
			{
				//rook from 0,7 to 0,5
				chessboard[0][5] = new Rook(chessboard[0][7].pieceCount);
				chessboard[0][5].setColor("b");
				chessboard[0][5].setName("R");
				chessboard[0][7] = null;
				nm = chessboard[0][5].getUIName();
			}
			else if(King.wrcast && row==7 && col==6)
			{
				//rook from 7,7 to 7,5
				chessboard[7][5] = new Rook(chessboard[7][7].pieceCount);
				chessboard[7][5].setColor("w");
				chessboard[7][5].setName("R");
				chessboard[7][7] = null;
				nm = chessboard[7][5].getUIName();
			}
		}
		//if the rooks or kings move then castling is disabled
		if(chessboard[row][col] != null)
		{
			if((chessboard[row][col].getColor().equals("w")
					&& chessboard[initialrow][initialcol].isValid(chessboard, initialrow, initialcol, row, col)))
			{
				if(chessboard[row][col].getName().equals("K"))
				{
					King.wlcast = false;
					King.wrcast = false;
				}
				else if(chessboard[row][col].getName().equals("R"))
				{
					if(initialcol==7)
					{
						King.wrcast = false;
					}
					else if(initialcol==0)
					{
						King.wlcast = false;
					}
				}
			}
			else if((chessboard[row][col].getColor().equals("b")
					&& chessboard[initialrow][initialcol].isValid(chessboard, initialrow, initialcol, row, col)))
			{
				if(chessboard[row][col].getName().equals("K"))
				{
					King.blcast = false;
					King.brcast = false;
				}
				else if(chessboard[row][col].getName().equals("R"))
				{
					if(initialcol==7)
					{
						King.brcast = false;
					}
					else if(initialcol==0)
					{
						King.blcast = false;
					}
				}
			}
		}
		return nm;
	}
}

/*

00 01 02 03 04 05 06 07
10 11 12 13 14 15 16 17
20 21 22 23 24 25 26 27
30 31 32 33 34 35 36 37
40 41 42 43 44 45 46 47
50 51 52 53 54 55 56 57
60 61 62 63 64 65 66 67
70 71 72 73 74 75 76 77

 */