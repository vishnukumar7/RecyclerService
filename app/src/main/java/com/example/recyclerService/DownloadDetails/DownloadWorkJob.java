package com.example.recyclerService.DownloadDetails;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.recyclerService.ApiService;
import com.example.recyclerService.BasicAuth;
import com.example.recyclerService.DBHandler;
import com.example.recyclerService.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownloadWorkJob extends Worker  {
    private static final String root_url ="http://1.23.25.220:8080/";

    private DBHandler dbHandler;
    public DownloadWorkJob(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        log("BeforeDatabaseCall");

        String DATABASE_NAME= MainActivity.DATABASE_NAME;
        //database Created and create Table
        dbHandler=new DBHandler(getApplicationContext(),DATABASE_NAME);


        log("AfterDatabaseCall");

        OkHttpClient client=new OkHttpClient.Builder().addInterceptor(new BasicAuth("nova","nova")).build();

        Retrofit retrofit=new Retrofit.Builder().baseUrl(root_url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api=retrofit.create(ApiService.class);

        //locating the data endpoint path in webservice
        Call<DownloadContent> call=api.getMyJSONDownload();

        //retrieve the data where store in webservice using internet
        call.enqueue(new Callback<DownloadContent>() {
            @Override
            public void onResponse(Call<DownloadContent> call, Response<DownloadContent> response) {

                if(response.isSuccessful()){
                    log("response");
                    DownloadContent downloadContent=response.body();
                    ArrayList<DataList> dataLists=new ArrayList<>(Arrays.asList(downloadContent.getDownload()));
                    dbHandler.insertDownloadData(dataLists,dbHandler.getWritableDatabase("Vishnu"));
                }
                else
                {
                    log(""+response);
                    log(""+response.message());
                    log(""+response.body());
                    log(""+response.code());
                    log(""+response.headers());
                    log("not response");
                }
            }
            @Override
            public void onFailure(Call<DownloadContent> call, Throwable t) {
                log("on failure "+call);
            }
        });

        return Result.success();
    }

    private void log(String str) {
        Log.i("DatabaseServiceCall",str+" Download");
    }
}
