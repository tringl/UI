package com.churkin.tringle;

/**
 * Created by artur on 30.07.2017.
 */
public class FileData {
    public String name;
    public String absolutePath;
    public Boolean selected=false;
    public Boolean isDirectory=false;
    public String date="";
    public long size=0;

    FileData(String _name,String _absolutePath){
        name=_name;
        absolutePath=_absolutePath;
    }
}
