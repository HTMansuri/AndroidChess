package com.example.myapplication;

import android.graphics.*;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PlayActivity extends AppCompatActivity
{
    Board[][] chessboard = new Board[8][8];
    ImageView initialImageView, finalImageView, board;
    int initialrow, initialcol, clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        board = findViewById(R.id.board);
        board.setOnTouchListener(onTouchListener);

        Chess.initChessBoard(chessboard);
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            int sqsize = board.getWidth()/8;
            int row = (int) event.getY()/sqsize;
            int col = (int) event.getX()/sqsize;

            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    if(clickCount == 0 && (chessboard[row][col] instanceof Bishop || chessboard[row][col] instanceof King || chessboard[row][col] instanceof Knight || chessboard[row][col] instanceof Pawn || chessboard[row][col] instanceof Queen || chessboard[row][col] instanceof Rook))
                    {
                        clickCount++;

                        initialrow = row;
                        initialcol = col;

                        String name = chessboard[initialrow][initialcol].getUIName();
                        int id = getResources().getIdentifier(name, "id", getPackageName());
                        initialImageView = findViewById(id);

                        GradientDrawable border = new GradientDrawable();
                        border.setShape(GradientDrawable.RECTANGLE);
                        border.setStroke(4, Color.WHITE);
                        border.setColor(Color.TRANSPARENT);
                        initialImageView.setBackground(border);
                    }
                    else if(clickCount == 1)
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
                        }
                    }
                    else
                        clickCount = 0;
                    return true;
                }
                default:
                    return false;
            }
        }
    };
}