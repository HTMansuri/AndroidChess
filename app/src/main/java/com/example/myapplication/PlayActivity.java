//should castling be allowed after undoing it
//checkmate bug when king can be saved by interference key

package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayActivity extends AppCompatActivity
{
    Board[][] chessboard = new Board[8][8];
    Board prevFinObj, prevInitObj, prevCastledRook, prevEnPassantPawn;
    ImageView initialImageView, finalImageView, board, prevFinObjImg, prevInitObjImg, prevCastledRookImg, prevEnPassantPawnImg;
    int initialrow, initialcol, clickCount = 0, randid = 1, prevRow, prevCol, currRow, currCol, prevEnPassantRow, prevEnPassantCol;
    TextView turnTV, messageTV;
    HashMap<String, Integer> idUI = new HashMap<>();
    String color = "", won = "";
    int turn = 0, enPassantCheck = 0, wchecki = 7, wcheckj = 4, bchecki = 0, bcheckj = 4, queenblackPromo = 3, queenwhitePromo = 3;
    boolean check = false, checkMate = false;
    Board finalCache = null, initialCache = null;

    Button undoButton, resignButton, drawButton;
    List<String> moves = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        undoButton = findViewById(R.id.undo);
        resignButton = findViewById(R.id.resign);
        drawButton = findViewById(R.id.draw);
        undoButton.setAlpha(0.5f);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    messageTV.setText("");
                    int sqsize = board.getWidth() / 8;
                    chessboard[prevRow][prevCol] = prevInitObj;
                    chessboard[currRow][currCol] = prevFinObj;
                    ConstraintLayout playLayout = findViewById(R.id.playLayout);
                    if (prevInitObjImg != null) {
                        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) prevInitObjImg.getLayoutParams();
                        ViewGroup parent = (ViewGroup) prevInitObjImg.getParent();

                        params.leftMargin = prevCol * sqsize;
                        params.topMargin = prevRow * sqsize;
                        parent.removeView(prevInitObjImg);
                        playLayout.addView(prevInitObjImg, params);
                    }
                    if (prevFinObjImg != null) {
                        ConstraintLayout.LayoutParams paramsFin = (ConstraintLayout.LayoutParams) prevFinObjImg.getLayoutParams();
                        ViewGroup parentFin = (ViewGroup) prevFinObjImg.getParent();
                        paramsFin.leftMargin = currCol * sqsize;
                        paramsFin.topMargin = currRow * sqsize;
                        playLayout.addView(prevFinObjImg, paramsFin);
                    }
                    if(prevEnPassantPawn != null){
                        ConstraintLayout.LayoutParams paramsEnpPawn = (ConstraintLayout.LayoutParams) prevEnPassantPawnImg.getLayoutParams();
                        ViewGroup parentEnpPawn = (ViewGroup) prevEnPassantPawnImg.getParent();
                        paramsEnpPawn.leftMargin = prevEnPassantCol * sqsize;
                        paramsEnpPawn.topMargin = prevEnPassantRow * sqsize;
                        playLayout.addView(prevEnPassantPawnImg, paramsEnpPawn);
                        chessboard[prevEnPassantRow][prevEnPassantCol] = prevEnPassantPawn;
                    }
                    if (prevCastledRook != null) {
                        ConstraintLayout.LayoutParams paramsRk = (ConstraintLayout.LayoutParams) prevCastledRookImg.getLayoutParams();
                        ViewGroup parentRk = (ViewGroup) prevCastledRookImg.getParent();
                        String nm = prevCastledRook.getUIName();
                        if (nm.equals("blackrook1")) {
                            chessboard[0][3] = null;
                            chessboard[0][0] = prevCastledRook;
                            paramsRk.leftMargin = 1 * sqsize;
                            paramsRk.topMargin = 0 * sqsize;
                        } else if (nm.equals("blackrook2")) {
                            chessboard[0][5] = null;
                            chessboard[0][7] = prevCastledRook;
                            paramsRk.leftMargin = 7 * sqsize;
                            paramsRk.topMargin = 0 * sqsize;
                        } else if (nm.equals("whiterook1")) {
                            chessboard[7][3] = null;
                            chessboard[7][0] = prevCastledRook;
                            paramsRk.leftMargin = 0 * sqsize;
                            paramsRk.topMargin = 7 * sqsize;
                        } else if (nm.equals("whiterook2")) {
                            chessboard[7][5] = null;
                            chessboard[7][7] = prevCastledRook;
                            paramsRk.leftMargin = 7 * sqsize;
                            paramsRk.topMargin = 7 * sqsize;
                        }
                        parentRk.removeView(prevCastledRookImg);
                        playLayout.addView(prevCastledRookImg, paramsRk);
                    }
                    if(chessboard[prevRow][prevCol]!=null) {
                        turn--;
                        moves.remove(moves.size()-1);
                    }
                    if (turn != -1 && turn % 2 != 0) {
                        color = "b";
                        turnTV.setText("Black's Move");
                    } else if (turn != -1) {
                        color = "w";
                        turnTV.setText("White's Move");
                    }
                    check = false;
                    if (chessboard[prevRow][prevCol]!=null && chessboard[prevRow][prevCol].getColor().equals("w")) {
                        if (chessboard[prevRow][prevCol] instanceof King) {
                            wchecki = prevRow;
                            wcheckj = prevCol;
                            System.out.println("wchecki: " + wchecki + ", wcheckj: " + wcheckj);
                        }
                        check = Chess.check(chessboard, wchecki, wcheckj);
                    } else {
                        if (chessboard[prevRow][prevCol]!=null && chessboard[prevRow][prevCol] instanceof King) {
                            bchecki = prevRow;
                            bcheckj = prevCol;
                            System.out.println("bchecki: " + bchecki + ", bcheckj: " + bcheckj);
                        }
                        check = Chess.check(chessboard, bchecki, bcheckj);
                    }
                    if (check) {
                        messageTV.setText("Check");
                    }
                    undoButton.setClickable(false);
                    undoButton.setAlpha(0.5f);
                    //debug
                    Chess.displayChessBoard(chessboard);
            }
        });

        resignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                if(color.equals("b"))
                    builder.setTitle("White Wins - Black Resigned!");
                else
                    builder.setTitle("Black Wins - White Resigned!");
                builder.setMessage("Would you like to save this game ?");
                //builder.setCancelable(true);

//                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialogInterface) {
//                        // Same as code to "NO"
//                    }
//                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle "Yes" button click
                        AlertDialog.Builder nameBuilder = new AlertDialog.Builder(v.getContext());
                        nameBuilder.setTitle("Save Game");
                        nameBuilder.setMessage("\nEnter game name");

                        final EditText input = new EditText(nameBuilder.getContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        //lp.setMargins(convertDpToPx(100), 0, convertDpToPx(48), 0); // Add left margin of 24dp
                        input.setLayoutParams(lp);

                        // Add padding to the EditText
                        int paddingDp = 24;
                        float density = v.getContext().getResources().getDisplayMetrics().density;
                        int paddingPx = (int) (paddingDp * density);
                        input.setPadding(paddingPx, paddingPx, paddingPx, (int)(paddingPx/1.5));

// Add a background to the EditText
                        //input.setBackgroundResource(R.drawable.edit_text_background);

                        nameBuilder.setView(input);

                        nameBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Handle "Save" button click
                                String gameName = input.getText().toString();
                                try{
                                    File newFile = new File(getApplicationContext().getFilesDir(),gameName+".txt");
                                    newFile.createNewFile();
                                    FileWriter fw = new FileWriter(newFile);
                                    for(String move : moves)
                                        fw.write(move+"\n");
                                    fw.write("resign\n");
                                    fw.write((color.equals("b")?"White":"Black")+" Wins - "+(color.equals("w")?"White":"Black")+" Resinged!");
                                    newFile.setLastModified(System.currentTimeMillis());
                                    fw.close();
                                }
                                catch(IOException e){
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(v.getContext(), "Game Saved!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        nameBuilder.show();

                    }
                });

                builder.setNegativeButton("No, Exit!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle "No" button click
                        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                builder.show();
            }
        });

        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                if(color.equals("b"))
                    builder.setTitle("Game Drawn by Black!");
                else
                    builder.setTitle("Game Drawn by White!");
                builder.setMessage("Would you like to save this game ?");
                //builder.setCancelable(true);

//                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialogInterface) {
//                        // Same as code to "NO"
//                    }
//                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle "Yes" button click
                        AlertDialog.Builder nameBuilder = new AlertDialog.Builder(v.getContext());
                        nameBuilder.setTitle("Save Game");
                        nameBuilder.setMessage("\nEnter game name");

                        final EditText input = new EditText(nameBuilder.getContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        //lp.setMargins(convertDpToPx(100), 0, convertDpToPx(48), 0); // Add left margin of 24dp
                        input.setLayoutParams(lp);

                        // Add padding to the EditText
                        int paddingDp = 24;
                        float density = v.getContext().getResources().getDisplayMetrics().density;
                        int paddingPx = (int) (paddingDp * density);
                        input.setPadding(paddingPx, paddingPx, paddingPx, (int)(paddingPx/1.5));

// Add a background to the EditText
                        //input.setBackgroundResource(R.drawable.edit_text_background);

                        nameBuilder.setView(input);

                        nameBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Handle "Save" button click
                                String gameName = input.getText().toString();
                                try{
                                    File newFile = new File(getApplicationContext().getFilesDir(),gameName+".txt");
                                    newFile.createNewFile();
                                    FileWriter fw = new FileWriter(newFile);
                                    for(String move : moves)
                                        fw.write(move+"\n");
                                    fw.write("draw\n");
                                    fw.write("Game Drawn by "+(color.equals("w")?"White":"Black"));
                                    newFile.setLastModified(System.currentTimeMillis());
                                    fw.close();
                                }
                                catch(IOException e){
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(v.getContext(), "Game Saved!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        nameBuilder.show();

                    }
                });

                builder.setNegativeButton("No, Exit!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle "No" button click
                        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                builder.show();
            }
        });
    }

    private int convertDpToPx(int dp) {
            float density = getResources().getDisplayMetrics().density;
            return Math.round(dp * density);
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
                        if(check)
                        {
                            messageTV.setText("Check");
                        }
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
                        String move = "";
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
                            prevFinObj = chessboard[row][col];
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
                                prevInitObjImg = initialImageView;
                                parent.removeView(initialImageView);
                                prevFinObjImg = null;
                                prevInitObj = chessboard[initialrow][initialcol];
                                prevRow = initialrow;
                                prevCol = initialcol;
                                undoButton.setClickable(true);
                                undoButton.setAlpha(1.0f);
                                prevCastledRook = null;
                                prevCastledRookImg = null;
                                prevEnPassantPawn = null;
                                prevEnPassantPawnImg = null;
                                if(chessboard[row][col] != null)
                                {
                                    prevFinObjImg = finalImageView;
                                    parent.removeView(finalImageView);
                                }
                                playLayout.addView(initialImageView, params);
                                currRow = row;
                                currCol = col;
                                move = String.valueOf(initialrow)+String.valueOf(initialcol)+" "+String.valueOf(row)+String.valueOf(col);
                                if(Pawn.enPassant)
                                {
                                    if(chessboard[initialrow][initialcol].getColor().equals("w"))
                                    {
                                        if(Pawn.enPassantPos.equals(String.valueOf(row+1)+String.valueOf(col)))
                                        {
                                            String name = chessboard[row+1][col].getUIName();
                                            int id = getResources().getIdentifier(name, "id", getPackageName());
                                            finalImageView = findViewById(id);
                                            prevEnPassantPawnImg = finalImageView;
                                            parent.removeView(finalImageView);
                                            prevEnPassantPawn = chessboard[row+1][col];
                                            prevEnPassantRow = row+1;
                                            prevEnPassantCol = col;
                                            chessboard[row+1][col] = null;
                                            move += " enPassant "+String.valueOf(row+1)+" "+String.valueOf(col);
                                        }
                                    }
                                    else
                                    {
                                       if(Pawn.enPassantPos.equals(String.valueOf(row-1)+String.valueOf(col)))
                                       {
                                           String name = chessboard[row-1][col].getUIName();
                                           int id = getResources().getIdentifier(name, "id", getPackageName());
                                           finalImageView = findViewById(id);
                                           prevEnPassantPawnImg = finalImageView;
                                           parent.removeView(finalImageView);
                                           prevEnPassantPawn = chessboard[row-1][col];
                                           prevEnPassantRow = row-1;
                                           prevEnPassantCol = col;
                                           chessboard[row-1][col] = null;
                                           move += " enPassant "+String.valueOf(row-1)+" "+String.valueOf(col);
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
                                    prevCastledRookImg = temp;
                                    params = (ConstraintLayout.LayoutParams) temp.getLayoutParams();

                                    if(nm.equals("blackrook1"))
                                    {
                                        prevCastledRook = chessboard[0][3];
                                        move += " castledRook 0 0 0 3";
                                        params.leftMargin = 3 * sqsize;
                                        params.topMargin = 0 * sqsize;
                                    }
                                    else if(nm.equals("blackrook2"))
                                    {
                                        prevCastledRook = chessboard[0][5];
                                        move += " castledRook 0 7 0 5";
                                        params.leftMargin = 5 * sqsize;
                                        params.topMargin = 0 * sqsize;
                                    }
                                    else if(nm.equals("whiterook1"))
                                    {
                                        prevCastledRook = chessboard[7][3];
                                        move += " castledRook 7 0 7 3";
                                        params.leftMargin = 3 * sqsize;
                                        params.topMargin = 7 * sqsize;
                                    }
                                    else if(nm.equals("whiterook2"))
                                    {
                                        prevCastledRook = chessboard[7][5];
                                        move += " castledRook 7 7 7 5";
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
                                    move += " promotion";
                                }
                                else
                                {
                                    chessboard[row][col] = new Queen(queenwhitePromo++);
                                    initialImageView.setImageResource(R.drawable.whitequeen);
                                    move += " promotion";
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
                                move += " check";
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
                                    move += "checkmate";
                                    turn = -1;
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    if(color.equals("b"))
                                        builder.setTitle("Black Wins!");
                                    else
                                        builder.setTitle("White Wins!");
                                    builder.setMessage("Would you like to save this game ?");
                                    //builder.setCancelable(true);

//                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialogInterface) {
//                        // Same as code to "NO"
//                    }
//                });

                                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Handle "Yes" button click
                                            AlertDialog.Builder nameBuilder = new AlertDialog.Builder(v.getContext());
                                            nameBuilder.setTitle("Save Game");
                                            nameBuilder.setMessage("\nEnter game name");

                                            final EditText input = new EditText(nameBuilder.getContext());
                                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.MATCH_PARENT);
                                            //lp.setMargins(convertDpToPx(100), 0, convertDpToPx(48), 0); // Add left margin of 24dp
                                            input.setLayoutParams(lp);

                                            // Add padding to the EditText
                                            int paddingDp = 24;
                                            float density = v.getContext().getResources().getDisplayMetrics().density;
                                            int paddingPx = (int) (paddingDp * density);
                                            input.setPadding(paddingPx, paddingPx, paddingPx, (int)(paddingPx/1.5));

// Add a background to the EditText
                                            //input.setBackgroundResource(R.drawable.edit_text_background);

                                            nameBuilder.setView(input);

                                            nameBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Handle "Save" button click
                                                    String gameName = input.getText().toString();
                                                    try{
                                                        File newFile = new File(getApplicationContext().getFilesDir(),gameName+".txt");
                                                        newFile.createNewFile();
                                                        FileWriter fw = new FileWriter(newFile);
                                                        for(String move : moves)
                                                            fw.write(move+"\n");
                                                        fw.write("checkmate, "+(color.equals("w")?"White":"Black")+" Wins!");
                                                        newFile.setLastModified(System.currentTimeMillis());
                                                        fw.close();
                                                    }
                                                    catch(IOException e){
                                                        e.printStackTrace();
                                                    }
                                                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    Toast.makeText(v.getContext(), "Game Saved!", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            nameBuilder.show();

                                        }
                                    });

                                    builder.setNegativeButton("No, Exit!", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Handle "No" button click
                                            Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                                    builder.show();
                                    //set not required buttons to disabled
                                    initialImageView = null;
                                    finalImageView = null;
                                    moves.add(move);
                                    return true;
                                }
                            }

                            chessboard[initialrow][initialcol] = null;
                            turn++;
                            moves.add(move);
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