package com.churkin.tringle;

import android.view.View;
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
        if(fd==null){
            return true;
        }
        if(fd.name==".."){
            MainActivity ma=(MainActivity) this.fa.ctx;
            ma.onListItemClick(fd);
        }
        if(fd.selected){
            fd.selected=false;
            v.setBackgroundResource(R.color.white);
        } else {
            fd.selected=true;
            v.setBackgroundResource(R.color.fileSelectedColor);
        }
        return true;
    }

};