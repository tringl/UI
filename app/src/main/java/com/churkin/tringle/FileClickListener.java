package com.churkin.tringle;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by artur on 30.07.2017.
 */
public class FileClickListener implements View.OnClickListener
{

    FileAdapter fa;
    public FileClickListener(FileAdapter _fa) {
        this.fa = _fa;
    }

    @Override
    public void onClick(View v)
    {
        TextView tv=(TextView)v.findViewById(R.id.tvFileName);
        FileData fd=fa.getFileByName((String)tv.getText());
        if(fd==null){
            return;
        }
        MainActivity mainactivity =(MainActivity) this.fa.ctx;
        RelativeLayout rl=(RelativeLayout) mainactivity.findViewById(R.id.rlFilesCommit);
        rl.setVisibility(View.INVISIBLE);
        mainactivity.onListItemClick(fd);
    }

};