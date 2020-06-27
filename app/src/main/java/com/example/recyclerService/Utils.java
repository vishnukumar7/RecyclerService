package com.example.recyclerService;

import android.os.Environment;
import android.util.Log;

import java.io.File;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class Utils {

    public static void logcatMarvel(String str){
       // Log.i("////marvel",str);
    }

    public static void sout(String str) {
        //Log.i("////sout ",str);
    }

    public static File getContentPath(){
        try{
            File contentPath=Environment.getExternalStorageDirectory();
            contentPath=new File(""+contentPath.getAbsolutePath()+File.separator+DataBaseContext.FILE_STORAGE_APP+File.separator+"Content");
            if(!contentPath.isDirectory()){
                if(contentPath.mkdirs()){
                    Utils.sout(":// externalStorage created");
                    return contentPath;
                }
                else{
                    Utils.sout(":// externalStorage not created");
                    return null;
                }
            }
            else{
                Utils.sout(":// already created");
                return contentPath;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    public static void logcatDemoNuts(String str) {
        Log.d("demonuts", str);
    }
}