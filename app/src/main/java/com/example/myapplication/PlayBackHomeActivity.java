package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class PlayBackHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playbackhome);

        File dataDir = getApplicationContext().getFilesDir();
        File[] files = dataDir.listFiles();
        ArrayList<String> fileNames = new ArrayList<String>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    fileName = fileName.substring(0, fileName.length() - 4);
                    Date lastModifiedDate = new Date(file.lastModified());
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    String formattedDate = dateFormat.format(lastModifiedDate);
                    fileNames.add(fileName + " - " + formattedDate);
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileNames);
        ListView savedGames = findViewById(R.id.savedList);
        savedGames.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        System.out.println(adapter.getCount());

        Button sortByName = findViewById(R.id.sortByName);
        Button sortByDate = findViewById(R.id.sortByDate);

        sortByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(fileNames, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        sortByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(fileNames, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        // Get the date string portion from each file name
                        String dateString1 = s1.substring(s1.indexOf("-") + 2).trim();
                        String dateString2 = s2.substring(s2.indexOf("-") + 2).trim();

                        // Convert the date strings to Date objects
                        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                        Date date1 = null, date2 = null;
                        try {
                            date1 = dateFormat.parse(dateString1);
                            date2 = dateFormat.parse(dateString2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Compare the two dates
                        return date1.compareTo(date2);
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });
    }
}