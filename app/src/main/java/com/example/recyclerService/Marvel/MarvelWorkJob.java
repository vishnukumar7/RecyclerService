package com.example.recyclerService.Marvel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.recyclerService.ApiService;
import com.example.recyclerService.DBHandler;
import com.example.recyclerService.MainActivity;
import com.example.recyclerService.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarvelWorkJob extends Worker {
    private static final String root_url="https://simplifiedcoding.net/";

    private static ArrayList<Marvel> marvels;

    private DBHandler dbHandler;

    public MarvelWorkJob(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Utils.logcatMarvel("BeforeDatabaseCall");

        String DATABASE_NAME= MainActivity.DATABASE_NAME;
        //database Created and create Table
        dbHandler=new DBHandler(getApplicationContext(),DATABASE_NAME);


        Utils.logcatMarvel("AfterDatabaseCall");
        Retrofit retrofit=new Retrofit.Builder().baseUrl(root_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api=retrofit.create(ApiService.class);

        //locating the data endpoint path in webservice
        Call<ArrayList<Marvel>> call=api.getMyJSONMarvel();

        //retrieve the data where store in webservice using internet
        call.enqueue(new Callback<ArrayList<Marvel>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<Marvel>> call, @NotNull Response<ArrayList<Marvel>> response) {

                if(response.isSuccessful()){
                    marvels=response.body();
                    dbHandler.insertMarvelData(marvels,dbHandler.getWritableDatabase("Vishnu"));

                }
                else
                {
                    Utils.logcatMarvel("not response");
                }
            }
            @Override
            public void onFailure(@NotNull Call<ArrayList<Marvel>> call, @NotNull Throwable t) {

            }
        });

        return Result.success();
    }
}
