package com.example.recyclerService;

import android.content.ContentValues;
import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.util.Log;

import com.example.recyclerService.Demonuts.DataItem;
import com.example.recyclerService.DownloadDetails.DataList;
import com.example.recyclerService.EmployeeDetails.Employee;
import com.example.recyclerService.Marvel.Marvel;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {

    private static final String MARVEL_TABLE="CREATE TABLE IF NOT EXISTS Marvel(character_name varchar(30) primary key,real_Name varchar(100),team varchar(30),first_appearance varchar(30)," +
            "created_by varchar(30),publisher varchar(30), image_url varchar(30), bio varchar(1000))";

    private static final String DOWNLOAD_TABLE="CREATE TABLE IF NOT EXISTS DownloadContent(content_upload_id int(6) primary key," +
            "content_type varchar(100),content_download_path varchar(100),contentSize varchar(10)," +
            "checksum varchar(100),content_display_name varchar(20))";

    private static final String EMPLOYEE_TABLE="CREATE TABLE IF NOT EXISTS Employee(EID varchar(30) primary key,EName varchar(100),salary varchar(10),age varchar(3))";

    private static final String DEMONUTS_TABLE="CREATE TABLE IF NOT EXISTS Demonuts(id varchar(10) primary key ,name varchar(20), city varchar(20),country varchar(30),image_url varchar(100))";

    private static final String MARVELS_DATA="Marvel";
    private static final String EMPLOYEE_DATA="Employee";
    private static final String DOWNLOAD_DATA="DownloadContent";
    private static final String DEMONUTS_DATA="Demonuts";

    public DBHandler(Context context,String DATABASE_NAME){
        //Create the Database
        super(new DataBaseContext(context), DATABASE_NAME, null, 1);
        Utils.logcatMarvel("Database Created ");
        SQLiteDatabase.loadLibs(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db){}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onCreate(db);
    }

    public void insertEmployeeData(ArrayList<Employee> employeeArrayList, SQLiteDatabase db){
        try{
            //get the current database
            db.execSQL(EMPLOYEE_TABLE);
            for(Employee emp : employeeArrayList){

                ContentValues cv=new ContentValues();
                cv.put("EID",emp.getId());
                cv.put("EName",emp.getName());
                cv.put("salary",emp.getSalary());
                cv.put("age",emp.getAge());
                db.insert(EMPLOYEE_DATA,null,cv);
            }
            db.close();
            Log.i("databaseService","Insert values : "+employeeArrayList.size()+" Employee");

            // store the databases file

        }
        catch(Exception e){
            Log.i("DatabaseService"," database error : "+e.getMessage());
        }
    }

    public void insertDownloadData(ArrayList<DataList> downloadContents, SQLiteDatabase db){
        try{
            //get the current database
            db.execSQL(DOWNLOAD_TABLE);

            for(DataList download : downloadContents){
                ContentValues cv=new ContentValues();
                cv.put("content_upload_id",download.getUpload_id());
                cv.put("content_type",download.getContent_type());
                cv.put("content_download_path",download.getContent_download_path());
                cv.put("contentSize",download.getContentSize());
                cv.put("checksum",download.getChecksum());
                cv.put("content_display_name",download.getContent_display_name());
                db.insert(DOWNLOAD_DATA,null,cv);
            }
            db.close();
            Log.i("databaseService","Insert values : "+downloadContents.size());

            // store the databases file

        }
        catch(Exception e){
            Log.i("DatabaseService"," database error : "+e.getMessage());
        }
    }

    public void insertMarvelData(ArrayList<Marvel> marvels, SQLiteDatabase db){
        try{
            //get the current database
            db.execSQL(MARVEL_TABLE);
            for(Marvel marvel : marvels){
                ContentValues cv=new ContentValues();
                cv.put("character_name",marvel.getName());
                cv.put("real_Name",marvel.getRealName());
                cv.put("team",marvel.getTeam());
                cv.put("first_appearance",marvel.getFirstAppearance());
                cv.put("created_by",marvel.getCreatedBy());
                cv.put("publisher",marvel.getPublisher());
                cv.put("image_url",marvel.getImage());
                cv.put("bio",marvel.getBio());
                db.insert(MARVELS_DATA,null,cv);

            }
            db.close();
            Utils.logcatMarvel("Insert values : "+marvels.size()+" Marvel");

            // store the databases file

        }
        catch(Exception e){
            Utils.logcatMarvel(" database error : "+e.getMessage());
        }

    }

    public void insertDemoNutsData(ArrayList<DataItem> dataItems,SQLiteDatabase db){
        try{
            db.execSQL(DEMONUTS_TABLE);
            for(DataItem dataItem : dataItems){
                ContentValues contentValues=new ContentValues();
                contentValues.put("id",dataItem.getId());
                contentValues.put("name",dataItem.getName());
                contentValues.put("city",dataItem.getCity());
                contentValues.put("country",dataItem.getCountry());
                contentValues.put("image_url",dataItem.getImgURL());
                db.insert(DEMONUTS_DATA,null,contentValues);
            }
            db.close();
            Utils.logcatDemoNuts(" Insert values "+dataItems.size()+" DemoNuts" );
        }
        catch (Exception e){
            Utils.logcatDemoNuts("database error : "+e.getMessage());
        }
    }


}



