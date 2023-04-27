package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.*;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PlayActivity extends AppCompatActivity
{
    Board[][] chessboard = new Board[8][8];
    ImageView initialImageView, finalImageView, board;
    int initialrow, initialcol, clickCount = 0;
    TextView turnTV, messageTV;

    String color = "";
    int turn = 0;
    boolean status = true;
    int bchecki = 7;
    int bcheckj = 4;
    int wchecki = 0;
    int wcheckj = 4;
    int queenblackPromo = 3;
    int queenwhitePromo = 3;
    boolean check = false;
    boolean checkMate = false;

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
            if(turn%2 != 0)
            {
                color = "b";
                turnTV.setText("Black's Move");
            }
            else
            {
                color = "w";
                turnTV.setText("White's Move");
            }

            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    messageTV.setText("");
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
                        //debug
                        System.out.println(name);

                        int id = getResources().getIdentifier(name, "id", getPackageName());
                        initialImageView = findViewById(id);

                        GradientDrawable border = new GradientDrawable();
                        border.setShape(GradientDrawable.RECTANGLE);
                        border.setStroke(4, Color.WHITE);
                        border.setColor(Color.TRANSPARENT);
                        initialImageView.setBackground(border);
                    }
                    else if(clickCount == 1 && ((chessboard[row][col] != null && !chessboard[row][col].getColor().equals(color)) || chessboard[row][col] == null))
                    {
                        clickCount = 0;
                        initialImageView.setBackground(null);

                        if(chessboard[initialrow][initialcol].isValid(chessboard, initialrow, initialcol, row, col))
                        {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) initialImageView.getLayoutParams();
                            params.leftMargin = col * sqsize;
                            params.topMargin = row * sqsize;

                            ViewGroup parent = (ViewGroup) initialImageView.getParent();
                            parent.removeView(initialImageView);

                            if(chessboard[row][col] != null)
                            {
                                String name = chessboard[row][col].getUIName();
                                int id = getResources().getIdentifier(name, "id", getPackageName());
                                finalImageView = findViewById(id);
                                parent.removeView(finalImageView);
                            }

                            ConstraintLayout playLayout = findViewById(R.id.playLayout);
                            playLayout.addView(initialImageView, params);
                            chessboard[row][col] = chessboard[initialrow][initialcol].move(chessboard[row][col]);

                            //castling
                            String nm = Chess.castling(chessboard, initialrow, initialcol, row, col);
                            if(nm != null)
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

                            //promotion - default is Queen
                            if((row==7 && chessboard[initialrow][initialcol].getName()=="p") || (row==0 && chessboard[initialrow][initialcol].getName()=="p"))
                            {
                                params = (ConstraintLayout.LayoutParams) initialImageView.getLayoutParams();
                                params.leftMargin = col * sqsize;
                                params.topMargin = row * sqsize;
                                Resources res = v.getContext().getResources();
                                int id = 0;
                                //Performs promotion of pawn
                                if(color.equals("b"))
                                {
                                    chessboard[row][col] = new Queen(queenblackPromo);
                                    initialImageView.setImageResource(R.drawable.blackqueen);
                                    id = res.getIdentifier("blackqueen"+queenblackPromo++, "id", v.getContext().getPackageName());
                                }
                                else
                                {
                                    chessboard[row][col] = new Queen(queenwhitePromo++);
                                    initialImageView.setImageResource(R.drawable.whitequeen);
                                    id = res.getIdentifier("whitequeen"+queenblackPromo++, "id", v.getContext().getPackageName());
                                }
                                chessboard[row][col].setColor(color);
                                chessboard[initialrow][initialcol] = null;
                                initialImageView.setId(id);
                                initialImageView.setLayoutParams(params);
                            }

                            chessboard[initialrow][initialcol] = null;
                            turn++;
                            initialImageView = finalImageView = null;

                            //debug
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