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

import java.util.HashMap;
import java.util.Map;

public class PlayActivity extends AppCompatActivity
{
    Board[][] chessboard = new Board[8][8];
    ImageView initialImageView, finalImageView, board;
    int initialrow, initialcol, clickCount = 0, randid = 1;
    TextView turnTV, messageTV;
    HashMap<String, Integer> idUI = new HashMap<>();
    String color = "";
    int turn = 0, wchecki = 7, wcheckj = 4, bchecki = 0, bcheckj = 4, queenblackPromo = 3, queenwhitePromo = 3;
    boolean check = false, checkMate = false, status = true;

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
                        int id = getResources().getIdentifier(name, "id", getPackageName());

                        //debug
                        System.out.println();
                        System.out.println(initialrow);
                        System.out.println(initialcol);
                        System.out.println(chessboard[row][col].getUIName());
                        for (Map.Entry<String, Integer> entry : idUI.entrySet()) {
                            System.out.println(entry.getKey() + " -> " + entry.getValue());
                        }

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
                                if(id == 0)
                                {
                                    id = idUI.get(chessboard[row][col].getUIName());
                                }
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
                                if(color.equals("b"))
                                {
                                    //debug
                                    System.out.println("blackpromo");
                                    chessboard[row][col] = new Queen(queenblackPromo++);
                                    initialImageView.setImageResource(R.drawable.blackqueen);
                                }
                                else
                                {
                                    //debug
                                    System.out.println("whitepromo");
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

                            chessboard[initialrow][initialcol] = null;
                            turn++;
                            initialImageView = finalImageView = null;

                            //debug
                            System.out.println();
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