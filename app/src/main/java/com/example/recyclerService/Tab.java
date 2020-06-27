package com.example.recyclerService;

import net.sqlcipher.Cursor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerService.Demonuts.DataItem;
import com.example.recyclerService.Demonuts.DemoNutAdapter;
import com.example.recyclerService.DownloadDetails.DataList;
import com.example.recyclerService.DownloadDetails.DownloadAdapter;
import com.example.recyclerService.EmployeeDetails.Employee;
import com.example.recyclerService.EmployeeDetails.EmployeeAdapter;
import com.example.recyclerService.Marvel.Marvel;
import com.example.recyclerService.Marvel.MarvelAdpater;

import java.util.ArrayList;

import static com.example.recyclerService.MainActivity.DATABASE_NAME;
import static com.example.recyclerService.MainActivity.db;


public class Tab extends Fragment {
    private ArrayList<Employee> employeeArrayList;
    private ArrayList<DataList> dataListArrayList;
    private ArrayList<Marvel> marvelArrayList;
    private ArrayList<DataItem> demoNuts;
    private RecyclerView recyclerView;
    private String title;
    private View view;

    Tab(){
        super();
    }

   public Tab(String title){
        this.title=title;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.activity_recycler_view,container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (title){
            case "Employee" :
                employeeArrayList =getEmployeeArrayList();
                setEmployeRecyclerView(employeeArrayList);
                break;
            case "Download" :
                dataListArrayList =getDataListArrayList();
                setDownloadRecyclerView(dataListArrayList);
                break;
            case "Marvel" :
                marvelArrayList =getMarvelArrayList();
                setMarvelRecyclerView(marvelArrayList);
                break;
            case "DemoNuts" :
                demoNuts=getDemoNutsArrayList();
                setDemoNutsRecyclerView(demoNuts);
                break;
            default:
                view=LayoutInflater.from(requireContext()).inflate(R.layout.content_not_available,null);
        }
    }



    private void setEmployeRecyclerView(ArrayList<Employee> employee){
        EmployeeAdapter employeeAdpater =new EmployeeAdapter(getActivity(),employee);
        recyclerView=view.findViewById(R.id.my_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(employeeAdpater);
        employeeAdpater.notifyDataSetChanged();
    }

    private void setDownloadRecyclerView(ArrayList<DataList> dataLists){
        DownloadAdapter downloadAdpater =new DownloadAdapter(dataLists);
        recyclerView=view.findViewById(R.id.my_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(downloadAdpater);
        downloadAdpater.notifyDataSetChanged();

    }

    private void setMarvelRecyclerView(ArrayList<Marvel> marvels){
        MarvelAdpater marvelAdpater =new MarvelAdpater(marvels,getActivity());
        recyclerView=view.findViewById(R.id.my_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(marvelAdpater);
        marvelAdpater.notifyDataSetChanged();
    }

    private void setDemoNutsRecyclerView(ArrayList<DataItem> dataItems){
        DemoNutAdapter demoAdapter =new DemoNutAdapter(dataItems,getActivity());
        recyclerView=view.findViewById(R.id.my_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(demoAdapter);
        demoAdapter.notifyDataSetChanged();
    }




    private ArrayList<Employee> getEmployeeArrayList(){
        employeeArrayList=new ArrayList<>();
        try{
            db=new DBHandler(getActivity(),DATABASE_NAME).getWritableDatabase("Vishnu");
            net.sqlcipher.Cursor cursor=db.rawQuery("SELECT * FROM Employee",null);

            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Employee emp=new Employee();
                emp.setId(cursor.getString(0));
                emp.setName(cursor.getString(1));
                emp.setSalary(cursor.getString(2));
                emp.setAge(cursor.getString(3));
                employeeArrayList.add(emp);
                // store the local storage
                cursor.moveToNext();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return employeeArrayList;
    }

    private ArrayList<DataList> getDataListArrayList(){
        dataListArrayList=new ArrayList<>();
        try{
            db= new DBHandler(getActivity(),DATABASE_NAME).getWritableDatabase("Vishnu");
            net.sqlcipher.Cursor cursor=db.rawQuery("SELECT * FROM DownloadContent",null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                DataList download=new DataList();
                download.setUpload_id(cursor.getString(0));
                download.setContent_type(cursor.getString(1));
                download.setContent_download_path(cursor.getString(2));
                download.setContentSize(cursor.getString(3));
                download.setChecksum(cursor.getString(4));
                download.setContent_display_name(cursor.getString(5));

                dataListArrayList.add(download);
                cursor.moveToNext();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return dataListArrayList;
    }

    private ArrayList<Marvel> getMarvelArrayList(){
        marvelArrayList=new ArrayList<>();
        try{
            db= new DBHandler(getActivity(),DATABASE_NAME).getWritableDatabase("Vishnu");
            Cursor cursor=db.rawQuery("SELECT * FROM Marvel",null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Marvel marvel=new Marvel();

                marvel.setName(cursor.getString(0));
                marvel.setRealName(cursor.getString(1));
                marvel.setTeam(cursor.getString(2));
                marvel.setFirstAppearance(cursor.getString(3));
                marvel.setCreatedBy(cursor.getString(4));
                marvel.setPublisher(cursor.getString(5));
                marvel.setImage(cursor.getString(6));
                marvel.setBio(cursor.getString(7));
                marvelArrayList.add(marvel);
                cursor.moveToNext();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return marvelArrayList;
    }

    private ArrayList<DataItem> getDemoNutsArrayList(){
        demoNuts=new ArrayList<>();
        try{
            db= new DBHandler(getActivity(),DATABASE_NAME).getWritableDatabase("Vishnu");
            Cursor cursor=db.rawQuery("SELECT * FROM Demonuts",null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                DataItem demoNut=new DataItem();

                demoNut.setId(cursor.getString(0));
                demoNut.setName(cursor.getString(1));
                demoNut.setCity(cursor.getString(2));
                demoNut.setCountry(cursor.getString(3));
                demoNut.setImgURL(cursor.getString(4));
                demoNuts.add(demoNut);
                cursor.moveToNext();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return demoNuts;
    }




}
