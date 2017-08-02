package com.churkin.tringle;
import java.io.File;
import java.util.ArrayList;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FileAdapter extends BaseAdapter {
    public Context ctx;
    LayoutInflater lInflater;
    ArrayList<FileData> objects;

    FileAdapter(Context context, ArrayList<FileData> filedatas) {
        ctx = context;
        objects = filedatas;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getPx(int size){
        MainActivity mainActivity=(MainActivity) this.ctx;
        float density=mainActivity.getResources().getDisplayMetrics().density;
        return (int)(size*density);
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.row, parent, false);
        }

        FileData p = getFile(position);
        TextView tv=((TextView) view.findViewById(R.id.tvFileName));
        RelativeLayout ll=((RelativeLayout)view.findViewById(R.id.holder));
        if(p.selected){
            ll.setBackgroundResource(R.color.fileSelectedColor);
        } else {
            ll.setBackgroundResource(R.color.white);
        }
        // для названия файла
        tv.setText(p.name);
        ll.setOnClickListener(new FileClickListener(this));
        ll.setOnLongClickListener(new FileLongClickListener(this));
        //картика файла\папки
        ImageView imageView=(ImageView)view.findViewById(R.id.imageFile);
        int paddingTop=this.getPx(13);
        if(p.isDirectory){
            imageView.setImageResource(R.drawable.directory);
        } else{
            imageView.setImageResource(R.drawable.file);
            paddingTop=0;
        }
        imageView.setPadding(this.getPx(2),paddingTop,0,0);
        imageView.getLayoutParams().width=this.getPx(30);
        //дата файла
        TextView tvInfo = (TextView) view.findViewById(R.id.tvFileInfo);
        if(!p.isDirectory) {
            tvInfo.setText(TringlUtil.convertByteSizeIntoReadible(p.size)+" / "+p.date);
            tv.setPadding(this.getPx(60),this.getPx(10),0,0);
            tvInfo.setPadding(this.getPx(60),0,0,0);
        } else {
            tv.setPadding(this.getPx(60),this.getPx(20),0,0);
            tvInfo.setText("");
        }
        return view;
    }

    // товар по позиции
    FileData getFile(int position) {
        return ((FileData) getItem(position));
    }

    FileData getFileByName(String name){
        for(FileData fd:this.objects){
            if(fd.name==name){
                return fd;
            }
        }
        return null;
    }

    // содержимое корзины
    ArrayList<FileData> getSelectedFiles() {
        ArrayList<FileData> box = new ArrayList<FileData>();
        for (FileData p : objects) {
            // если в корзине
            if (p.selected)
                box.add(p);
        }
        return box;
    }

}
/**
 * Created by artur on 30.07.2017.
 */
