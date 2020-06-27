package com.example.recyclerService;

import com.example.recyclerService.Demonuts.DemoNuts;
import com.example.recyclerService.DownloadDetails.DownloadContent;
import com.example.recyclerService.EmployeeDetails.Employee;
import com.example.recyclerService.Marvel.Marvel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("api/v1/employees")
    Call<ArrayList<Employee>> getMyJSONEmployee();

    @GET("MCaaS-seed/contentClientApi/getDownloadContents/42002210")
    Call<DownloadContent> getMyJSONDownload();

    @GET("demos/marvel/")
    Call<ArrayList<Marvel>> getMyJSONMarvel();

    @GET("json_parsing.php")
    Call<DemoNuts> getDemoNuts();

}
