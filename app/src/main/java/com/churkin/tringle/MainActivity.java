package com.churkin.tringle;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private ArrayList<FileData> files = new ArrayList<FileData>();
    private File currentDirectory = new File("/");
    private FileAdapter adapter;
    private ArrayList<String> highLvlDirs = new ArrayList<String>();


    //when application started
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //set main layout
        setContentView(R.layout.activity_main);
        //browse to root directory
        String device="dima_p";
        if(Environment.isExternalStorageEmulated()){
            String userStoragePath=Environment.getExternalStorageDirectory().getPath();
            highLvlDirs.add(userStoragePath);
            browseTo(new File(userStoragePath));
        } else {
            if(device==""){
                highLvlDirs.add("/");
                browseTo(new File("/"));
            } else if(device=="dima_p"){
                highLvlDirs.add("/storage/emulated/0");
                browseTo(new File("/storage/emulated/0"));
            } else if(device=="local"){
                highLvlDirs.add("/sdcard");
                browseTo(new File("/sdcard"));
            }
        }
    }

    //browse to parent directory
    private void upOneLevel() {
        String parent = this.currentDirectory.getParent();
        if (parent != null && !this.highLvlDirs.contains(this.currentDirectory.getAbsolutePath())) {
            this.browseTo(this.currentDirectory.getParentFile());
        }
    }

    //browse to file or directory
    private void browseTo(final File aDirectory) {
        //if we want to browse directory
        try {
            if (aDirectory.isDirectory()) {
                //fill list with files from this directory
                this.currentDirectory = aDirectory;
                fill(aDirectory.listFiles());

                //set titleManager text
                TextView titleManager = (TextView) findViewById(R.id.tvTitleManager);
                titleManager.setText(aDirectory.getAbsolutePath());
            }
        } catch (Exception e) {

        }
    }

    //fill list
    private void fill(File[] files) {
        //clear list
        this.files.clear();
        String parent = this.currentDirectory.getParent();
        if (parent != null && !this.highLvlDirs.contains(this.currentDirectory.getAbsolutePath())) {
            FileData fd = new FileData("..", "..");
            fd.isDirectory = true;
            this.files.add(fd);
        }

        //add every file into list
        for (File file : files) {
            FileData fd = new FileData(file.getName(), file.getAbsolutePath());
            if (file.isDirectory()) {
                fd.isDirectory = true;
                fd.size=TringlUtil.folderSize(file);
            } else {
                Date date=(new Date(file.lastModified()));
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(date);
                fd.date=String.format("%1$tB %1$td %1$tY, %1$tH:%1$tM", calendar);
                fd.size=file.length();
            }

            this.files.add(fd);
            //this.directoryEntries.add(file.getAbsolutePath());
        }

        //create array adapter to show everything
        FileAdapter directoryList = new FileAdapter(this, this.files);
        ListView lv = (ListView) findViewById(R.id.lvSelectFiles);
        lv.setAdapter(directoryList);
    }
    //when you clicked onto item

    protected void onListItemClick(FileData fd) {
        //get selected file name
        String selectedFileString = fd.absolutePath;

        //if we select ".." then go upper
        if (selectedFileString.equals("..")) {
            this.upOneLevel();
        } else {
            //browse to clicked file or directory using browseTo()
            File clickedFile = null;
            clickedFile = new File(selectedFileString);
            if (clickedFile != null) {
                this.browseTo(clickedFile);
            }
        }
    }
    @Override
    public void onBackPressed() {
        this.upOneLevel();
    }


}