package com.example.recyclerService;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import com.example.recyclerService.Demonuts.DemoNutsWorkJob;
import com.example.recyclerService.DownloadDetails.DownloadWorkJob;
import com.example.recyclerService.EmployeeDetails.EmployeeWorkJob;
import com.example.recyclerService.Marvel.MarvelWorkJob;

import java.util.Arrays;

public class MyService extends Service {

    private static void log(){
        Log.i("databaseServiceCall ", "on bink call");
    }
    private Handler callHandler;

    @Override
    public IBinder onBind(Intent intent){
        log();
            return null;
    }

    @Override
    public void onCreate(){
        callHandler=new Handler();
        //locating the data in webservice using retrofit library
       backgroundProcess();
    }

    private void backgroundProcess(){
        callHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                OneTimeWorkRequest employeeRequest=new OneTimeWorkRequest.Builder(EmployeeWorkJob.class).build();
              //  OneTimeWorkRequest downloadRequest=new OneTimeWorkRequest.Builder(DownloadWorkJob.class).build();
                OneTimeWorkRequest marvelRequest=new OneTimeWorkRequest.Builder(MarvelWorkJob.class).build();
                OneTimeWorkRequest demonutsRequest=new OneTimeWorkRequest.Builder(DemoNutsWorkJob.class).build();
                WorkManager workManager=WorkManager.getInstance();

                @SuppressLint("EnqueueWork")
                WorkContinuation continuation=WorkContinuation.combine(Arrays.asList(workManager.beginWith(employeeRequest)
                       // , workManager.beginWith(downloadRequest)
                        ,workManager.beginWith(marvelRequest),
                        workManager.beginWith(demonutsRequest)));

                continuation.enqueue();
                callHandler.postDelayed(this,15*60*1000);
            }
        },0);
    }

}
