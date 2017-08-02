package com.churkin.tringle;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;


/**
 * Created by artur on 02.08.2017.
 */
public class FilesCommitClickListener implements View.OnClickListener
{

    MainActivity ma;
    public FilesCommitClickListener(MainActivity _ma) {
        this.ma = _ma;
    }

    @Override
    public void onClick(View v)
    {
        ArrayList<String> fnames=new ArrayList<String>();
        for(FileData fd:this.ma.adapter.getSelectedFiles()){
            fnames.add(fd.absolutePath);
        }
        //Log.d("MY",fnames.toString());
    }

};