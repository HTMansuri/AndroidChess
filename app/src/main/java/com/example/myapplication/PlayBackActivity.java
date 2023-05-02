package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PlayBackActivity extends AppCompatActivity
{
    Board[][] chessboard = new Board[8][8];
    ImageView initialImageView, finalImageView, board, enPassantImageView,castledRookImageView;
    int turn = 0, queenblackPromo = 3, queenwhitePromo = 3, randid = 1, initialrow, initialcol, row, col, enPassantRow, enPassantCol, castledRookInitRow, castledRookInitCol, castledRookFinRow, castledRookFinCol;
    HashMap<String, Integer> idUI = new HashMap<>();
    TextView turnTV, messageTV;
    String color;
    int pos = 0;



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);
        board = findViewById(R.id.board);
        Chess.initChessBoard(chessboard);
        turnTV = findViewById(R.id.turn);
        messageTV = findViewById(R.id.message);
        messageTV.setText("");

        String selectedItem = getIntent().getStringExtra("selectedItem");

        File dataDir = getApplicationContext().getFilesDir();
        String selectedFileName = selectedItem.substring(0,selectedItem.lastIndexOf("-")).trim()+".txt";
        List<String> moves = new ArrayList<String>();
        try {
            File selectedFile = new File(dataDir, selectedFileName);
            Scanner sc = new Scanner(selectedFile);
            while(sc.hasNext()){
                moves.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Button next = findViewById(R.id.nextmove);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turn++;
                messageTV.setText("");
                String move = moves.get(pos);
                if(move.equals("draw") || move.equals("resign") || move.equals("checkmate")){
                    turnTV.setText("Game Over!");
                    messageTV.setText(moves.get(pos+1));
                    turn = -1;
                    next.setClickable(false);
                    next.setAlpha(0.5f);
                    return;
                }
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
                initialrow = Character.getNumericValue(move.charAt(0));
                initialcol = Character.getNumericValue(move.charAt(1));
                row = Character.getNumericValue(move.charAt(3));
                col = Character.getNumericValue(move.charAt(4));
                int sqsize = board.getWidth() / 8;

                String name = chessboard[initialrow][initialcol].getUIName();
                int id = getResources().getIdentifier(name, "id", getPackageName());
                if(id == 0)
                {
                    id = idUI.get(chessboard[initialrow][initialcol].getUIName());
                }
                initialImageView = findViewById(id);

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) initialImageView.getLayoutParams();
                ViewGroup parent = (ViewGroup) initialImageView.getParent();
                ConstraintLayout playLayout = findViewById(R.id.playLayout);
                params.leftMargin = col * sqsize;
                params.topMargin = row * sqsize;
                parent.removeView(initialImageView);
                if(chessboard[row][col] != null)
                {
                    name = chessboard[row][col].getUIName();
                    id = getResources().getIdentifier(name, "id", getPackageName());
                    finalImageView = findViewById(id);
                    parent.removeView(finalImageView);
                }
                chessboard[row][col] = chessboard[initialrow][initialcol];
                chessboard[initialrow][initialcol] = null;
                if(move.contains("promotion")) {
                    if(color.equals("b"))
                    {
                        chessboard[row][col] = new Queen(queenblackPromo++);
                        chessboard[row][col].setColor("b");
                        initialImageView.setImageResource(R.drawable.blackqueen);
                    }
                    else
                    {
                        chessboard[row][col] = new Queen(queenwhitePromo++);
                        chessboard[row][col].setColor("w");
                        initialImageView.setImageResource(R.drawable.whitequeen);
                    }
                    randid++;
                    ImageView iv = findViewById(randid);
                    while(iv != null)
                    {
                        randid++;
                        iv = findViewById(randid);
                    }
                    idUI.put(chessboard[row][col].getUIName(), randid);
                    initialImageView.setId(randid);
                }
                playLayout.addView(initialImageView, params);

                if(move.contains("enPassant")){
                    enPassantRow = Character.getNumericValue(move.charAt(move.indexOf("enPassant ")+10));
                    enPassantCol = Character.getNumericValue(move.charAt(move.indexOf("enPassant ")+12));
                    name = chessboard[enPassantRow][enPassantCol].getUIName();
                    id = getResources().getIdentifier(name, "id", getPackageName());
                    enPassantImageView = findViewById(id);
                    chessboard[enPassantRow][enPassantCol] = null;
                    parent.removeView(enPassantImageView);
                }
                if(move.contains("castledRook")){
                    castledRookInitRow = Character.getNumericValue(move.charAt(move.indexOf("castledRook ")+12));
                    castledRookInitCol = Character.getNumericValue(move.charAt(move.indexOf("castledRook ")+14));
                    castledRookFinRow = Character.getNumericValue(move.charAt(move.indexOf("castledRook ")+16));
                    castledRookFinCol = Character.getNumericValue(move.charAt(move.indexOf("castledRook ")+18));
                    name = chessboard[castledRookInitRow][castledRookInitCol].getUIName();
                    id = getResources().getIdentifier(name, "id", getPackageName());
                    castledRookImageView = findViewById(id);
                    ConstraintLayout.LayoutParams paramsCast = (ConstraintLayout.LayoutParams) castledRookImageView.getLayoutParams();
                    paramsCast.leftMargin = castledRookFinCol * sqsize;
                    paramsCast.topMargin = castledRookFinRow * sqsize;
                    chessboard[castledRookFinRow][castledRookFinCol] = chessboard[castledRookInitRow][castledRookInitCol];
                    chessboard[castledRookInitRow][castledRookInitCol] = null;
                    parent.removeView(castledRookImageView);
                    playLayout.addView(castledRookImageView, paramsCast);
                }
                if(move.contains("checkmate")){
                    turnTV.setText("Game Over!");
                    messageTV.setText(moves.get(pos+1));
                    turn = -1;
                    next.setClickable(false);
                    next.setAlpha(0.5f);
                    return;
                }
                if(move.contains("check")){
                    messageTV.setText("Check");
                }
                pos++;
            }
            });
            }
}