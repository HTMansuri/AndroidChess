//do i even need 2d array of imageview?
package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.view.*;
import android.util.*;
import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity
{
    ImageView blackrook1, blackknight1, blackbishop1, blackking1, blackqueen1, blackrook2, blackknight2, blackbishop2;
    ImageView blackpawn1, blackpawn2, blackpawn3, blackpawn4, blackpawn5, blackpawn6, blackpawn7, blackpawn8;
    ImageView whiterook1, whiteknight1, whitebishop1, whiteking1, whitequeen1, whiterook2, whiteknight2, whitebishop2;
    ImageView whitepawn1, whitepawn2, whitepawn3, whitepawn4, whitepawn5, whitepawn6, whitepawn7, whitepawn8;
    ImageView[][] pieceImages;
    ImageView board;
    ImageView initialImageView, finalImageView;
    int initialImage, finalImage, clickCount = 0;
    View temp;
    ViewGroup.LayoutParams initialLayoutParams, finalLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        board = findViewById(R.id.board);
        pieceImages = new ImageView[8][8];
        
        blackrook1 = findViewById(R.id.blackrook1); blackknight1 = findViewById(R.id.blackknight1);
        blackbishop1 = findViewById(R.id.blackbishop1); blackking1 = findViewById(R.id.blackking1);
        blackqueen1 = findViewById(R.id.blackqueen1); blackrook2 = findViewById(R.id.blackrook2);
        blackknight2 = findViewById(R.id.blackknight2); blackbishop2 = findViewById(R.id.blackbishop2);

        blackpawn1 = findViewById(R.id.blackpawn1); blackpawn2 = findViewById(R.id.blackpawn2);
        blackpawn3 = findViewById(R.id.blackpawn3); blackpawn4 = findViewById(R.id.blackpawn4);
        blackpawn5 = findViewById(R.id.blackpawn5); blackpawn6 = findViewById(R.id.blackpawn6);
        blackpawn7 = findViewById(R.id.blackpawn7); blackpawn8 = findViewById(R.id.blackpawn8);

        whiterook1 = findViewById(R.id.whiterook1); whiteknight1 = findViewById(R.id.whiteknight1);
        whitebishop1 = findViewById(R.id.whitebishop1); whiteking1 = findViewById(R.id.whiteking1);
        whitequeen1 = findViewById(R.id.whitequeen1); whiterook2 = findViewById(R.id.whiterook2);
        whiteknight2 = findViewById(R.id.whiteknight2); whitebishop2 = findViewById(R.id.whitebishop2);

        whitepawn1 = findViewById(R.id.whitepawn1); whitepawn2 = findViewById(R.id.whitepawn2);
        whitepawn3 = findViewById(R.id.whitepawn3); whitepawn4 = findViewById(R.id.whitepawn4);
        whitepawn5 = findViewById(R.id.whitepawn5); whitepawn6 = findViewById(R.id.whitepawn6);
        whitepawn7 = findViewById(R.id.whitepawn7); whitepawn8 = findViewById(R.id.whitepawn8);

        blackrook1.setTag(R.drawable.blackrook); blackknight1.setTag(R.drawable.blackknight);
        blackbishop1.setTag(R.drawable.blackbishop); blackking1.setTag(R.drawable.blackking);
        blackqueen1.setTag(R.drawable.blackqueen); blackrook2.setTag(R.drawable.blackrook);
        blackknight2.setTag(R.drawable.blackknight); blackbishop2.setTag(R.drawable.blackbishop);
        blackpawn1.setTag(R.drawable.blackpawn); blackpawn2.setTag(R.drawable.blackpawn);
        blackpawn3.setTag(R.drawable.blackpawn); blackpawn4.setTag(R.drawable.blackpawn);
        blackpawn5.setTag(R.drawable.blackpawn); blackpawn6.setTag(R.drawable.blackpawn);
        blackpawn7.setTag(R.drawable.blackpawn); blackpawn8.setTag(R.drawable.blackpawn);

        whiterook1.setTag(R.drawable.whiterook); whiteknight1.setTag(R.drawable.whiteknight);
        whitebishop1.setTag(R.drawable.whitebishop); whiteking1.setTag(R.drawable.whiteking);
        whitequeen1.setTag(R.drawable.whitequeen); whiterook2.setTag(R.drawable.whiterook);
        whiteknight2.setTag(R.drawable.whiteknight); whitebishop2.setTag(R.drawable.whitebishop);
        whitepawn1.setTag(R.drawable.whitepawn); whitepawn2.setTag(R.drawable.whitepawn);
        whitepawn3.setTag(R.drawable.whitepawn); whitepawn4.setTag(R.drawable.whitepawn);
        whitepawn5.setTag(R.drawable.whitepawn); whitepawn6.setTag(R.drawable.whitepawn);
        whitepawn7.setTag(R.drawable.whitepawn); whitepawn8.setTag(R.drawable.whitepawn);
        
        pieceImages[7][0] = blackrook1; pieceImages[7][7] = blackrook2;
        pieceImages[7][1] = blackknight1; pieceImages[7][6] = blackknight2;
        pieceImages[7][2] = blackbishop1; pieceImages[7][5] = blackbishop2;
        pieceImages[7][3] = blackqueen1; pieceImages[7][4] = blackking1;

        pieceImages[6][0] = blackpawn1; pieceImages[6][1] = blackpawn2;
        pieceImages[6][2] = blackpawn3; pieceImages[6][3] = blackpawn4;
        pieceImages[6][4] = blackpawn5; pieceImages[6][5] = blackpawn6;
        pieceImages[6][6] = blackpawn7; pieceImages[6][7] = blackpawn8;

        pieceImages[1][0] = whitepawn1; pieceImages[1][1] = whitepawn2;
        pieceImages[1][2] = whitepawn3; pieceImages[1][3] = whitepawn4;
        pieceImages[1][4] = whitepawn5; pieceImages[1][5] = whitepawn6;
        pieceImages[1][6] = whitepawn7; pieceImages[1][7] = whitepawn8;
        
        pieceImages[0][0] = whiterook1; pieceImages[0][7] = whiterook2;
        pieceImages[0][1] = whiteknight1; pieceImages[0][6] = whiteknight2;
        pieceImages[0][2] = whitebishop1; pieceImages[0][5] = whitebishop2;
        pieceImages[0][3] = whitequeen1; pieceImages[0][4] = whiteking1;

        blackrook1.setOnClickListener(onClickListener); blackrook2.setOnClickListener(onClickListener);
        blackknight1.setOnClickListener(onClickListener); blackknight2.setOnClickListener(onClickListener);
        blackbishop1.setOnClickListener(onClickListener); blackbishop2.setOnClickListener(onClickListener);
        blackqueen1.setOnClickListener(onClickListener); blackking1.setOnClickListener(onClickListener);

        blackpawn1.setOnClickListener(onClickListener); blackpawn2.setOnClickListener(onClickListener);
        blackpawn3.setOnClickListener(onClickListener); blackpawn4.setOnClickListener(onClickListener);
        blackpawn5.setOnClickListener(onClickListener); blackpawn6.setOnClickListener(onClickListener);
        blackpawn7.setOnClickListener(onClickListener); blackpawn8.setOnClickListener(onClickListener);

        whiterook1.setOnClickListener(onClickListener); whiterook2.setOnClickListener(onClickListener);
        whiteknight1.setOnClickListener(onClickListener); whiteknight2.setOnClickListener(onClickListener);
        whitebishop1.setOnClickListener(onClickListener); whitebishop2.setOnClickListener(onClickListener);
        whitequeen1.setOnClickListener(onClickListener); whiteking1.setOnClickListener(onClickListener);

        whitepawn1.setOnClickListener(onClickListener); whitepawn2.setOnClickListener(onClickListener);
        whitepawn3.setOnClickListener(onClickListener); whitepawn4.setOnClickListener(onClickListener);
        whitepawn5.setOnClickListener(onClickListener); whitepawn6.setOnClickListener(onClickListener);
        whitepawn7.setOnClickListener(onClickListener); whitepawn8.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(clickCount == 0)
            {
                clickCount++;
                temp = v;
                initialImageView = (ImageView) v;
                initialImage = (int) initialImageView.getTag();
                initialLayoutParams = initialImageView.getLayoutParams();
                v.setBackgroundResource(android.R.drawable.dark_header);
            }
            else
            {
                clickCount = 0;
                temp.setBackgroundResource(0);
                finalImageView = (ImageView) v;
                finalImage = (int) finalImageView.getTag();
                initialImageView.setImageResource(finalImage);
                finalImageView.setImageResource(initialImage);
                initialImageView.setTag(finalImage);
                finalImageView.setTag(initialImage);
                finalLayoutParams = finalImageView.getLayoutParams();
                initialImageView.setLayoutParams(initialLayoutParams);
                finalImageView.setLayoutParams(finalLayoutParams);
            }
        }
    };
}