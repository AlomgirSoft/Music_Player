package com.example.music_player_main;

import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] items;

    ListView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.songlist);


        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        dicplaysong();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    //=========================================================

    public ArrayList<File> filesong(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File singlefiles : files) {
            if (singlefiles.isDirectory() && singlefiles.isHidden()) {
                arrayList.addAll(filesong(singlefiles));

            } else {
                if (singlefiles.getName().endsWith(".mp3") || singlefiles.getName().endsWith(".wav")) {
                    arrayList.add(singlefiles);
                }
            }
        }
        return arrayList;
    }
    //=========================================================


    void dicplaysong() {

        final ArrayList<File> mysong = filesong(Environment.getExternalStorageDirectory());
        items = new String[mysong.size()];

        for (int i = 0; i < mysong.size(); i++) {
            items[i] = mysong.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");

        }
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, items);
        recyclerView.setAdapter(myadapter);


    }


}