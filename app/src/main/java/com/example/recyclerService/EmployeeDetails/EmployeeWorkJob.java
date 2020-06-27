package com.example.recyclerService.EmployeeDetails;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.recyclerService.ApiService;
import com.example.recyclerService.DBHandler;
import com.example.recyclerService.MainActivity;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeWorkJob extends Worker {
    private static final String root_url ="http://dummy.restapiexample.com/";

    private static ArrayList<Employee> employeeList;

    private DBHandler dbHandler;

    public EmployeeWorkJob(@NonNull Context context, @NonNull WorkerParameters workerParams) {
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
        Retrofit retrofit=new Retrofit.Builder().baseUrl(root_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api=retrofit.create(ApiService.class);

        //locating the data endpoint path in webservice
        Call<ArrayList<Employee>> call=api.getMyJSONEmployee();

        //retrieve the data where store in webservice using internet
        call.enqueue(new Callback<ArrayList<Employee>>() {
            @Override
            public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {

                if(response.isSuccessful()){
                    employeeList=response.body();
                    dbHandler.insertEmployeeData(employeeList,dbHandler.getWritableDatabase("Vishnu"));

                }
                else
                {
                    log("not response");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Employee>> call, Throwable t) {

            }
        });

        return Result.success();
    }

    private void log(String str) {
        Log.i("DatabaseServiceCall",str +"Employee");
    }
}
