package com.example.recyclerService.Demonuts;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.recyclerService.ApiService;
import com.example.recyclerService.DBHandler;
import com.example.recyclerService.MainActivity;
import com.example.recyclerService.Marvel.Marvel;
import com.example.recyclerService.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DemoNutsWorkJob extends Worker {

    private DemoNuts demoNuts;

    private DBHandler dbHandler;

    private static final String root_url="https://demonuts.com/Demonuts/JsonTest/Tennis/";

    public DemoNutsWorkJob(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

    String DATABASE_NAME= MainActivity.DATABASE_NAME;

        dbHandler=new DBHandler(getApplicationContext(),DATABASE_NAME);


        Utils.logcatMarvel("AfterDatabaseCall");
        Retrofit retrofit=new Retrofit.Builder().baseUrl(root_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api=retrofit.create(ApiService.class);

        //locating the data endpoint path in webservice
        final Call<DemoNuts> demoNutsCall=api.getDemoNuts();

        //retrieve the data where store in webservice using internet
        demoNutsCall.enqueue(new Callback<DemoNuts>() {
            @Override
            public void onResponse(@NotNull Call<DemoNuts> call, @NotNull Response<DemoNuts> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        List<DataItem> dataItems=response.body().getData();
                        dbHandler.insertDemoNutsData((ArrayList<DataItem>) dataItems,dbHandler.getWritableDatabase("Vishnu"));
                    }
                }
                else
                {
                    Utils.logcatDemoNuts("not response");
                }
            }
            @Override
            public void onFailure(@NotNull Call<DemoNuts> call, @NotNull Throwable t) {

            }
        });
        return Result.success();

    }
}
