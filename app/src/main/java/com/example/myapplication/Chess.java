/**
 * This program is a simple implementation of a command-line 2-player chess game.
 *
 * @author Pavitra Patel, Huzaif Mansuri
 */
package com.example.myapplication;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Chess
{
//	public static void main(String[] args) throws FileNotFoundException
//	{
//		String color = "";
//		int turn = 0;
//		boolean status = true;
//		int bchecki = 7;
//		int bcheckj = 4;
//		int wchecki = 0;
//		int wcheckj = 4;
//		boolean check = false;
//		boolean checkPrint1 = false;
//		boolean checkMate = false;

//		Board[][] board = new Board[8][8];
//		initChessBoard(board);
//
//		do
//		{
//			//Display current chess board
//			if(status)
//			{
//				checkPrint1 = false;
//				displayChessBoard(board);
//			}
//
//			if(status)
//				System.out.println("\n");
//
//			//Determines turn of the next player
//			if(turn%2 != 0)
//			{
//				color = "b";
//				if(!checkPrint1 && check && input.length()<=5)
//					System.out.println("Check");
//				System.out.print("Black's move: ");
//				checkPrint1 = true;
//			}
//			else
//			{
//				color = "w";
//				if(!checkPrint1 && check && input.length()<=5)
//					System.out.println("Check");
//				System.out.print("White's move: ");
//				checkPrint1 = true;
//			}
//
//			//Takes input from the command-line
//			input = kb.nextLine().trim();
//
//			/**
//			 * When a player inputs "resign", the other player wins and the game ends.
//			 */
//			if(input.equals("resign"))
//			{
//				if(turn%2 != 0)
//				{
//					System.out.println("White wins");
//				}
//				else
//				{
//					System.out.println("Black wins");
//				}
//				break;
//			}
//
//			/**
//			 * Check if the move is being made inside the board.
//			 * If Not, Error message displayed
//			 */
//			if(input.equals("") || input.charAt(2)!=' ' || input.charAt(0)<'a' || input.charAt(0)>'h' || input.charAt(3)<'a' || input.charAt(3)>'h' || input.charAt(1)<'1' || input.charAt(1)>'8' || input.charAt(4)<'1' || input.charAt(4)>'8')
//			{
//				status=false;
//				System.out.println("Illegal move, try again");
//				continue;
//			}
//
//
//			int initiali = Character.getNumericValue(input.charAt(1))-1;
//			int finali = Character.getNumericValue(input.charAt(4))-1;
//
//			int initialj = input.charAt(0) - 97;
//			int finalj = input.charAt(3) - 97;
//			String promotionKey = null;
//
//			/**
//			 * Identify Promotion Case, and perform promotion.
//			 * If the player mentioned the promotion key, promotion step is performed accordingly
//			 * Else Queen is promoted by default
//			 */
//			if((finali==7 && board[initiali][initialj].getName()=="p") || (finali==0 && board[initiali][initialj].getName()=="p"))
//			{
//				if(input.length()>=7)
//					promotionKey = String.valueOf(input.charAt(6));
//				else
//					promotionKey = "Q";
//			}
//			/**
//			 * Check for valid input!
//			 */
//			else if(input.length()>5 && !input.substring(6).equals("draw?"))
//			{
//				status=false;
//				System.out.println("Illegal move, try again");
//				continue;
//			}
//
//			Board finalCache = null;
//			Board initialCache = null;
//
//			/**
//			 * Check for various cases when the move is not valid.
//			 * Set status=false if invalid move
//			 */
//
//			if(board[initiali][initialj] == null)
//			{
//				status = false;
//			}
//			else if(!(board[initiali][initialj].getColor().equals(color)))
//			{
//				status = false;
//			}
//			else
//			{
//				if(board[finali][finalj] != null)
//				{
//					if(board[initiali][initialj].getColor().equals(board[finali][finalj].getColor()))
//					{
//						status = false;
//					}
//					else
//					{
//						if(Pawn.enPassant)
//						{
//							status = board[initiali][initialj].isValid(board, initiali, initialj, finali, finalj);
//							if(status)
//							{
//								//Set enPassant to false, as soon as the opportunity round for enPassant gets completed
//								Pawn.enPassant = false;
//								Pawn.enPassantPos=null;
//							}
//						}
//						else
//						{
//							/**
//							 * Calls isValid method of a particular key object class to check validity of the move being performed.
//							 */
//							status = board[initiali][initialj].isValid(board, initiali, initialj, finali, finalj);
//						}
//					}
//				}
//				else
//				{
//					if(Pawn.enPassant)
//					{
//						status = board[initiali][initialj].isValid(board, initiali, initialj, finali, finalj);
//						if(status)
//						{
//							//Set enPassant to false, as soon as the opportunity round for enPassant gets completed
//							Pawn.enPassant = false;
//							Pawn.enPassantPos=null;
//						}
//					}
//					else
//						/**
//						 * Calls isValid method of a particular key object class to check validity of the move being performed.
//						 */
//						status = board[initiali][initialj].isValid(board, initiali, initialj, finali, finalj);
//				}
//			}
//
//			if(status)
//			{
//				/**
//				 * Checks for valid castling situation.
//				 */
//				if(((board[initiali][initialj].getColor().equals("w") && (King.wrcast || King.wlcast)) || (board[initiali][initialj].getColor().equals("b") && (King.blcast || King.brcast))) && board[initiali][initialj].getName().equals("K"))
//				{
//					/**
//					 * Identify castling side and color, and perform rook move accordingly.
//					 */
//
//					if(King.wlcast && finali==0 && finalj==2)
//					{
//						//rook from 0,0 to 0,3
//						board[0][0] = null;
//						board[0][3] = new Rook();
//						board[0][3].setColor("w");
//						board[0][3].setName("R");
//					}
//					else if(King.blcast && finali==7 && finalj==2)
//					{
//						//rook from 7,0 to 7,3
//						board[7][0] = null;
//						board[7][3] = new Rook();
//						board[7][3].setColor("b");
//						board[7][3].setName("R");
//					}
//					else if(King.wrcast && finali==0 && finalj==6)
//					{
//						//rook from 0,7 to 0,5
//						board[0][7] = null;
//						board[0][5] = new Rook();
//						board[0][5].setColor("w");
//						board[0][5].setName("R");
//					}
//					else if(King.brcast && finali==7 && finalj==6)
//					{
//						//rook from 7,7 to 7,5
//						board[7][7] = null;
//						board[7][5] = new Rook();
//						board[7][5].setColor("b");
//						board[7][5].setName("R");
//					}
//				}
//				if((finali==7 && board[initiali][initialj].getName()=="p") || (finali==0 && board[initiali][initialj].getName()=="p"))
//				{
//					if(promotionKey==null)
//						promotionKey="Q";
//					String keyColor = board[initiali][initialj].getColor();
//					/**
//					 * Performs promotion of pawn.
//					 */
//					switch(promotionKey)
//					{
//						case "Q":
//							board[finali][finalj] = new Queen();
//							board[finali][finalj].setColor(keyColor);
//							board[initiali][initialj] = null;
//							break;
//
//						case "R":
//							board[finali][finalj] = new Rook();
//							board[finali][finalj].setColor(keyColor);
//							board[initiali][initialj] = null;
//							break;
//
//						case "N":
//							board[finali][finalj] = new Knight();
//							board[finali][finalj].setColor(keyColor);
//							board[initiali][initialj] = null;
//							break;
//
//						case "B":
//							board[finali][finalj] = new Bishop();
//							board[finali][finalj].setColor(keyColor);
//							board[initiali][initialj] = null;
//							break;
//
//						case "K":
//							promotionKey=null;
//							status = false;
//							break;
//
//						case "p":
//							promotionKey=null;
//							status = false;
//							break;
//
//						default:
//							promotionKey=null;
//							status = false;
//					}
//				}
//
//				if(status && promotionKey==null)
//				{
//					finalCache = board[finali][finalj];
//					board[finali][finalj] = board[initiali][initialj].move(board[finali][finalj]);
//					initialCache = board[initiali][initialj];
//					board[initiali][initialj] = null;
//
//					//keeps track of i and j for check identification
//					if(board[finali][finalj].getName().equals("K"))
//					{
//						if(board[finali][finalj].getColor().equals("w"))
//						{
//							wchecki = finali;
//							wcheckj = finalj;
//						}
//						else
//						{
//							bchecki = finali;
//							bcheckj = finalj;
//						}
//					}
//				}
//			}
//
//			/**
//			 * if the rooks or kings move then castling is disabled.
//			 */
//			if(board[finali][finalj] != null)
//			{
//				if((board[finali][finalj].getColor().equals("w") && status==true))
//				{
//					if(board[finali][finalj].getName().equals("K"))
//					{
//						King.wlcast = false;
//						King.wrcast = false;
//					}
//					else if(board[finali][finalj].getName().equals("R"))
//					{
//						if(initialj==7)
//						{
//							King.wrcast = false;
//						}
//						else if(initialj==0)
//						{
//							King.wlcast = false;
//						}
//					}
//				}
//				else if((board[finali][finalj].getColor().equals("b") && status==true))
//				{
//					if(board[finali][finalj].getName().equals("K"))
//					{
//						King.blcast = false;
//						King.brcast = false;
//					}
//					else if(board[finali][finalj].getName().equals("R"))
//					{
//						if(initialj==7)
//						{
//							King.brcast = false;
//						}
//						else if(initialj==0)
//						{
//							King.blcast = false;
//						}
//					}
//				}
//			}
//
//			/**
//			 * Here we check for the situation where the move of a player can lead its own king to be in Check.
//			 */
//			String c = "";
//			if(board[finali][finalj]!=null)
//				c = board[finali][finalj].getColor();
//			if(c.equals("w"))
//			{
//				check = check(board, wchecki, wcheckj);
//			}
//			else
//			{
//				check = check(board, bchecki, bcheckj);
//			}
//
//			if(check && status)
//			{
//				status=false;
//				board[finali][finalj] = finalCache;
//				board[initiali][initialj] = initialCache;
//				if(board[initiali][initialj].getName().equals("K"))
//				{
//					if(board[initiali][initialj].getColor().equals("w"))
//					{
//						wchecki = initiali;
//						wcheckj = initialj;
//					}
//					else
//					{
//						bchecki = initiali;
//						bcheckj = initialj;
//					}
//				}
//			}
//
//
//			if(!status)
//			{
//				System.out.println("Illegal move, try again");
//				continue;
//			}
//
//			/**
//			 * Here we check for Check or CheckMate to the opponent player.
//			 */
//			if(c.equals("b"))
//			{
//				check = check(board, wchecki, wcheckj);
//			}
//			else
//			{
//				check = check(board, bchecki, bcheckj);
//			}
//			if(check)
//			{
//				if(c.equals("b"))
//					checkMate = checkMate(board, wchecki, wcheckj);
//				else
//					checkMate = checkMate(board, bchecki, bcheckj);
//
//				if(checkMate && input.length()<=5)
//				{
//					System.out.println();
//					displayChessBoard(board);
//					System.out.println("\n\nCheckMate");
//					if(c.equals("b"))
//						System.out.print("Black wins");
//					else
//						System.out.print("White wins");
//					break;
//				}
//			}
//
//			/**
//			 * If a player asks for draw, "draw" is printed and game ends.
//			 */
//			if(status && input.length()>5 && input.substring(6).equals("draw?"))
//			{
//				System.out.print("draw");
//				break;
//			}
//
//			if(status)
//				System.out.println();
//			turn++;
//		}
//		while(!checkMate);
//
//		//close
//		kb.close();
//	}
//
//
//	//methods
//
//	/**
//     * Checks if King at provided position is in Check.
//     *
//     * @param board the chess board as a 2D array of Board objects
//     * @param kingi the initial column index of the king on the board
//     * @param kingj the initial row index of the king on the board
//     * @return true if the King is at Check at provided position, false otherwise
//     */
//	public static boolean check(Board[][] board, int kingi, int kingj)
//	{
//		boolean check;
//		for(int i=7; i>=0; i--)
//		{
//			for(int j=0; j<=7; j++)
//			{
//				String color="";
//				if(board[i][j]!=null)
//					color = board[i][j].getColor();
//				if(board[i][j] != null && !board[kingi][kingj].getColor().equals(color))
//				{
//					check = board[i][j].isValid(board, i, j, kingi, kingj);
//					if(check)
//						return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * This method checks if any of the key of the provided color except King can be validly moved to the provided position
//	 *
//     * This is a helper function to CheckMate method. It helps in the following ways:
//     * It checks if the provided position is safe for the King or not
//     * It also checks if the check giving Knight can be killed.
//     * It also helps to see if any other key can be placed in between the check.
//     *
//     * @param board the chess board as a 2D array of Board objects
//     * @param finali the final column index intended to reach on the board
//     * @param finalj the final row index intended to reach on the board
//     * @return true if the any key of provided color except King can be validly moved to provided position, false otherwise
//     */
//	public static boolean reachHere(Board[][] board, int finali, int finalj, String color)
//	{
//		boolean reached;
//		for(int i=7; i>=0; i--)
//		{
//			for(int j=0; j<=7; j++)
//			{
//				if(board[i][j] != null && !board[i][j].getName().equals("K") && board[i][j].getColor().equals(color))
//				{
//					reached = board[i][j].isValid(board, i, j, finali, finalj);
//					if(reached) {
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
//
//	/**
//     * Checks if King at provided position is CheckMated.
//     *
//     * @param board the chess board as a 2D array of Board objects
//     * @param kingi the initial column index of the king on the board
//     * @param kingj the initial row index of the king on the board
//     * @return true if the King is CheckMated at provided position, false otherwise
//     */
//	public static boolean checkMate(Board[][] board, int kingi, int kingj) {
//		boolean checkMate;
//		for(int i=7; i>=0; i--)
//		{
//			for(int j=0; j<=7; j++)
//			{
//				String color="";
//				if(board[i][j]!=null)
//					color = board[i][j].getColor();
//				if(board[i][j] != null && !board[kingi][kingj].getColor().equals(color))
//				{
//					checkMate = board[i][j].isValid(board, i, j, kingi, kingj);
//					if(checkMate)
//					{
//						for(int p=-1; p<=1; p++)
//						{
//							for(int q=1; q>=-1; q--)
//							{
//								if(kingi-p<0 || kingi-p>7 || kingj-q<0 || kingj-q>7 || (board[kingi-p][kingj-q]!=null && !board[kingi-p][kingj-q].getColor().equals(color)))
//									{
//										continue;
//									}
//								else {
//									if(board[kingi-p][kingj-q]==null)
//										board[kingi-p][kingj-q] = board[kingi][kingj];
//									boolean check = reachHere(board,kingi-p,kingj-q,color);
//									if(board[kingi-p][kingj-q]==board[kingi][kingj])
//										board[kingi-p][kingj-q]=null;
//									if(!check) {
//										checkMate = false;
//										return checkMate;
//									}
//								}
//							}
//						}
//						color = board[kingi][kingj].getColor();
//						// region of code to check if we can kill check giving knight
//						if(board[i][j].getName().equals("N"))
//						{
//								if(reachHere(board,i,j,color))
//								{
//									checkMate = false;
//								}
//								return checkMate;
//						}
//						// region of code to check if we can defend by killing the check giving piece or by bringing the piece in between.
//						int iChange = 0;
//						int jChange = 0;
//						int x = i, y=j;
//						if(x-kingi != 0)
//							iChange = (kingi-x)/Math.abs(x-kingi);
//						if(y-kingj!=0)
//							jChange = (kingj-y)/Math.abs(y-kingj);
//						while(x!=kingi && y!=kingj)
//						{
//							if(reachHere(board,x,y,color))
//							{
//								checkMate = false;
//								return checkMate;
//							}
//							x += iChange;
//							y += jChange;
//						}
//					}
//				}
//			}
//		}
//		return true;
//		}

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


//	/**
//     * Display current chess board.
//     *
//     * @param board the chess board as a 2D array of Board objects
//     */
	public static void displayChessBoard(Board[][] board)
	{
		int vertIndex = 9;
		char horiIndex = 'a';
		for(int i=0; i<=7; i++)
		{
			for(int j=0; j<=7; j++)
			{
				if(board[i][j] != null)
				{
					StringBuilder name = new StringBuilder("");
					name.append(board[i][j].getColor());
					name.append(board[i][j].getName());
					System.out.print(name + " ");
				}
				else if((i%2 == 0 && j%2 != 0) || (i%2 != 0 && j%2 == 0))
				{
					System.out.print("## ");
				}
				else
				{
					System.out.print("   ");
				}
			}
			vertIndex--;
			System.out.println(vertIndex);
		}
		while(horiIndex != 'i')
		{
			if(horiIndex == 'a')
			{
				System.out.print(" " + horiIndex);
			}
			else
			{
				System.out.print("  " + horiIndex);
			}
			horiIndex++;
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