package com.example.myapplication;

import android.graphics.*;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.*;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.HashMap;

public class PlayActivity extends AppCompatActivity
{
    Board[][] chessboard = new Board[8][8];
    ImageView initialImageView, finalImageView, board;
    int initialrow, initialcol, clickCount = 0, randid = 1;
    TextView turnTV, messageTV;
    HashMap<String, Integer> idUI = new HashMap<>();
    String color = "", won = "";
    int turn = 0, enPassantCheck = 0, wchecki = 7, wcheckj = 4, bchecki = 0, bcheckj = 4, queenblackPromo = 3, queenwhitePromo = 3;
    boolean check = false, checkMate = false;
    Board finalCache = null, initialCache = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        turnTV = findViewById(R.id.turn);
        messageTV = findViewById(R.id.message);
        messageTV.setText("");
        board = findViewById(R.id.board);
        board.setOnTouchListener(onTouchListener);

        Chess.initChessBoard(chessboard);
        //debug
        Chess.displayChessBoard(chessboard);
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            int sqsize = board.getWidth()/8;
            int row = (int) event.getY()/sqsize;
            int col = (int) event.getX()/sqsize;

            //Determine turn
            if(turn != -1 && turn%2 != 0)
            {
                color = "b";
                turnTV.setText("Black's Move");
            }
            else if(turn != -1)
            {
                color = "w";
                turnTV.setText("White's Move");
            }

            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    messageTV.setText("");
                    if(turn == -1)
                    {
                        turnTV.setText(won);
                        messageTV.setText("Checkmate");
                        // after buttons are done - check if i need to disable them here
                        return true;
                    }
                    if(initialImageView != null && chessboard[row][col] != null && chessboard[row][col].getColor().equals(color))
                    {
                        initialImageView.setBackground(null);
                        initialImageView = null;
                        clickCount = 0;
                    }

                    if(clickCount == 0 && initialImageView == null
                        && (chessboard[row][col] != null && chessboard[row][col].getColor().equals(color))
                        && (chessboard[row][col] instanceof Bishop || chessboard[row][col] instanceof King
                        || chessboard[row][col] instanceof Knight || chessboard[row][col] instanceof Pawn
                        || chessboard[row][col] instanceof Queen || chessboard[row][col] instanceof Rook))
                    {
                        clickCount++;
                        initialrow = row;
                        initialcol = col;

                        String name = chessboard[initialrow][initialcol].getUIName();
                        int id = getResources().getIdentifier(name, "id", getPackageName());
                        if(id == 0)
                        {
                            id = idUI.get(chessboard[initialrow][initialcol].getUIName());
                        }
                        initialImageView = findViewById(id);

                        GradientDrawable border = new GradientDrawable();
                        border.setShape(GradientDrawable.RECTANGLE);
                        border.setStroke(4, Color.WHITE);
                        border.setColor(Color.TRANSPARENT);
                        initialImageView.setBackground(border);
                    }
                    else if(clickCount == 1 && ((chessboard[row][col] != null && !chessboard[row][col].getColor().equals(color)) || chessboard[row][col] == null))
                    {
                        check = false;
                        clickCount = 0;
                        initialImageView.setBackground(null);

                        if(chessboard[initialrow][initialcol].isValid(chessboard, initialrow, initialcol, row, col))
                        {
                            initialCache = chessboard[initialrow][initialcol];
                            finalCache = chessboard[row][col];
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) initialImageView.getLayoutParams();
                            ViewGroup parent = (ViewGroup) initialImageView.getParent();
                            ConstraintLayout playLayout = findViewById(R.id.playLayout);

                            if(chessboard[row][col] != null)
                            {
                                String name = chessboard[row][col].getUIName();
                                int id = getResources().getIdentifier(name, "id", getPackageName());
                                if(id == 0)
                                {
                                    id = idUI.get(chessboard[row][col].getUIName());
                                }
                                finalImageView = findViewById(id);
                            }
                            chessboard[row][col] = chessboard[initialrow][initialcol].move(chessboard[row][col]);

                            //keeps track of i and j for check identification
                            if(chessboard[row][col].getName().equals("K"))
                            {
                                if(chessboard[row][col].getColor().equals("w"))
                                {
                                    wchecki = row;
                                    wcheckj = col;
                                }
                                else
                                {
                                    bchecki = row;
                                    bcheckj = col;
                                }
                            }

                            //Here we check for the situation where the move of a player can lead its own king to be in Check.
                            String c = "";
                            if(chessboard[row][col]!=null)
				                c = chessboard[row][col].getColor();
                			if(c.equals("w"))
                            {
                                check = Chess.check(chessboard, wchecki, wcheckj);
                            }
                            else if(c.equals("b"))
                            {
                                check = Chess.check(chessboard, bchecki, bcheckj);
                            }
                            if(check)
                            {
                                chessboard[row][col] = finalCache;
                                chessboard[initialrow][initialcol] = initialCache;
                                if(chessboard[initialrow][initialcol].getName().equals("K"))
                                {
                                    if(chessboard[initialrow][initialcol].getColor().equals("w"))
                                    {
                                        wchecki = initialrow;
                                        wcheckj = initialcol;
                                    }
                                    else
                                    {
                                        bchecki = initialrow;
                                        bcheckj = initialcol;
                                    }
                                }
                                messageTV.setText("Illegal move, try again!!!");
                                finalImageView = null;
                                initialImageView = null;
                                return true;
                            }
                            else
                            {
                                params.leftMargin = col * sqsize;
                                params.topMargin = row * sqsize;
                                parent.removeView(initialImageView);
                                if(chessboard[row][col] != null)
                                {
                                    parent.removeView(finalImageView);
                                }
                                playLayout.addView(initialImageView, params);
                                if(Pawn.enPassant)
                                {
                                    if(chessboard[initialrow][initialcol].getColor().equals("w"))
                                    {
                                        if(Pawn.enPassantPos.equals(String.valueOf(row+1)+String.valueOf(col)))
                                        {
                                            String name = chessboard[row+1][col].getUIName();
                                            int id = getResources().getIdentifier(name, "id", getPackageName());
                                            finalImageView = findViewById(id);
                                            parent.removeView(finalImageView);
                                            chessboard[row+1][col] = null;
                                        }
                                    }
                                    else
                                    {
                                       if(Pawn.enPassantPos.equals(String.valueOf(row-1)+String.valueOf(col)))
                                       {
                                           String name = chessboard[row-1][col].getUIName();
                                           int id = getResources().getIdentifier(name, "id", getPackageName());
                                           finalImageView = findViewById(id);
                                           parent.removeView(finalImageView);
                                           chessboard[row-1][col] = null;
                                       }
                                    }
                                    //Set enPassant to false, as soon as the opportunity round for enPassant gets completed
                                    if(enPassantCheck==1)
                                    {
                                        enPassantCheck = 0;
                                        Pawn.enPassant = false;
                                        Pawn.enPassantPos = null;
                                    }
                                    else
                                        enPassantCheck = 1;
                                }
                            }

                            //castling
                            if(!check)
                            {
                                String nm = Chess.castling(chessboard, initialrow, initialcol, row, col);
                                if (nm != null)
                                {
                                    int id = getResources().getIdentifier(nm, "id", getPackageName());
                                    ImageView temp = findViewById(id);
                                    params = (ConstraintLayout.LayoutParams) temp.getLayoutParams();

                                    if(nm.equals("blackrook1"))
                                    {
                                        params.leftMargin = 3 * sqsize;
                                        params.topMargin = 0 * sqsize;
                                    }
                                    else if(nm.equals("blackrook2"))
                                    {
                                        params.leftMargin = 5 * sqsize;
                                        params.topMargin = 0 * sqsize;
                                    }
                                    else if(nm.equals("whiterook1"))
                                    {
                                        params.leftMargin = 3 * sqsize;
                                        params.topMargin = 7 * sqsize;
                                    }
                                    else if(nm.equals("whiterook2"))
                                    {
                                        params.leftMargin = 5 * sqsize;
                                        params.topMargin = 7 * sqsize;
                                    }
                                    parent.removeView(temp);
                                    playLayout.addView(temp, params);
                                }
                            }
                            //promotion - default is Queen
                            if((row==7 && chessboard[initialrow][initialcol].getName()=="p") || (row==0 && chessboard[initialrow][initialcol].getName()=="p"))
                            {
                                if(color.equals("b"))
                                {
                                    chessboard[row][col] = new Queen(queenblackPromo++);
                                    initialImageView.setImageResource(R.drawable.blackqueen);
                                }
                                else
                                {
                                    chessboard[row][col] = new Queen(queenwhitePromo++);
                                    initialImageView.setImageResource(R.drawable.whitequeen);
                                }
                                randid++;
                                ImageView iv = findViewById(randid);
                                while(iv != null)
                                {
                                    randid++;
                                    iv = findViewById(randid);
                                }
                                chessboard[row][col].setColor(color);
                                chessboard[initialrow][initialcol] = null;
                                System.out.println(chessboard[row][col].getUIName() + " " + randid);
                                idUI.put(chessboard[row][col].getUIName(), randid);
                                initialImageView.setId(randid);
                            }

                            //Here we check for Check or CheckMate to the opponent player.
                            if(c.equals("b"))
                            {
                                check = Chess.check(chessboard, wchecki, wcheckj);
                            }
                            else if(c.equals("w"))
                            {
                                check = Chess.check(chessboard, bchecki, bcheckj);
                            }
                            if(check)
                            {
                                messageTV.setText("Check");
                                if(c.equals("b"))
                                    checkMate = Chess.checkMate(chessboard, wchecki, wcheckj);
                                else if(c.equals("w"))
                                    checkMate = Chess.checkMate(chessboard, bchecki, bcheckj);

                                if(checkMate)
                                {
                                    if(c.equals("b"))
                                        won = "Black wins";
                                    else if(c.equals("w"))
                                        won = "White wins";
                                    turnTV.setText(won);
                                    messageTV.setText("Checkmate");
                                    turn = -1;
                                    //set not required buttons to disabled
                                    initialImageView = null;
                                    finalImageView = null;
                                    return true;
                                }
                            }

                            chessboard[initialrow][initialcol] = null;
                            turn++;
                            initialImageView = null;
                            finalImageView = null;

                            //debug
                            System.out.println("\n");
                            Chess.displayChessBoard(chessboard);
                        }
                        else
                        {
                            messageTV.setText("Illegal move, try again!!!");
                            initialImageView = null;
                        }
                    }
                    else
                    {
                        clickCount = 0;
                        if(initialImageView != null)
                            initialImageView.setBackground(null);
                        initialImageView = null;
                    }
                    return true;
                }
                default:
                {
                    return false;
                }
            }
        }
    };
}