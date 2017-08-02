package com.churkin.tringle;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by artur on 30.07.2017.
 */
public class FileLongClickListener implements View.OnLongClickListener
{

    FileAdapter fa;
    public FileLongClickListener(FileAdapter _fa) {
        this.fa = _fa;
    }

    @Override
    public boolean onLongClick(View v)
    {
        TextView tv=(TextView)v.findViewById(R.id.tvFileName);
        FileData fd=fa.getFileByName((String)tv.getText());
        MainActivity mainactivity =(MainActivity) this.fa.ctx;
        RelativeLayout rl=(RelativeLayout) mainactivity.findViewById(R.id.rlFilesCommit);

        if(fd==null){
            return true;
        }
        if(fd.name==".."){
            rl.setVisibility(View.INVISIBLE);
            mainactivity.onListItemClick(fd);
        }
        if(fd.selected){
            fd.selected=false;
            v.setBackgroundResource(R.color.white);
        } else {
            fd.selected=true;
            v.setBackgroundResource(R.color.fileSelectedColor);
        }
        if(fa.getSelectedFiles().size()>0){
            rl.setVisibility(View.VISIBLE);
            mainactivity.calculateSelectedFilesSize();
        } else {
            rl.setVisibility(View.INVISIBLE);
        }
        return true;
    }

};