package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class PlayBackHomeActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playbackhome);

        File dataDir = getApplicationContext().getFilesDir();
        File[] files = dataDir.listFiles();
        ArrayList<String> fileNames = new ArrayList<String>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    fileName = fileName.substring(0,fileName.length()-4);
                    fileNames.add(fileName);
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileNames);
        ListView savedGames = findViewById(R.id.savedList);
        savedGames.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        System.out.println(adapter.getCount());
    }
}