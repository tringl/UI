package com.churkin.tringle;

import android.util.Log;

import java.io.File;

/**
 * Created by artur on 02.08.2017.
 */
public class TringlUtil {

    /**
     * Folder size in bytes
     * @param folder
     * @return
     */
    public static long folderSize(File folder){
        long size=0;
        if(folder.isFile()){
            return folder.length();
        }
        for(File f:folder.listFiles()){
            if(f.isFile()){
                size+=f.length();
            } else {
                size+=TringlUtil.folderSize(f);
            }
        }
        return size;
    }

    public static String convertByteSizeIntoReadible(long size){
        String sizeStr="";
        if(size<1024){
            sizeStr=String.valueOf(size)+" bytes";
        } else if(size<1024*1024){
            sizeStr=String.valueOf(TringlUtil.floorWithAfterPoint((size/1024f),2))+" Kb";
        } else if(size<1024*1024*1024){
            sizeStr=String.valueOf(TringlUtil.floorWithAfterPoint((size/(1024f*1024f)),2))+" Mb";
        } else {
            sizeStr=String.valueOf(TringlUtil.floorWithAfterPoint((size/(1024f*1024f*1024f)),2))+" Gb";
        }
        return sizeStr;
    }

    public static double floorWithAfterPoint(float num,int afterPoint){
        if(afterPoint==0){
            return Math.floor(num);
        }
        double p=Math.pow(10,(double)afterPoint);
        return Math.floor(num*p)/p;
    }
}
