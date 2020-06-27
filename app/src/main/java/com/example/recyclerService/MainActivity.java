package com.example.recyclerService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;

import net.sqlcipher.database.SQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME ="WebService.db";
    public static SQLiteDatabase db;
    private static final int BUFFER_SIZE=6*1024;
    //private static final String BACKUP_NAME="Employee.zip";
    private static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager =  findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        viewPager.setOffscreenPageLimit(3);

        final TabLayout tabLayout =  findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());//setting current selected item over viewpager
                switch (tab.getPosition()) {
                    case 0:
                        Log.e("TAG","TAB 1");
                        break;
                    case 1:
                        Log.e("TAG","TAB 2");
                        break;
                    case 2:
                        Log.e("TAG","TAB 3");
                        break;
                    case 3:
                        Log.e("TAG", "TAB 4");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Tab("Employee"),"Employee");
    //    adapter.addFrag(new Tab("Download"),"Download");
        adapter.addFrag(new Tab("Marvel"),"Marvel");
        adapter.addFrag(new Tab("DemoNuts"),"DemoNuts");
        adapter.addFrag(new Tab("Coming soon"),"Coming soon");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        askForPermission();
        super.onResume();
    }

    private void askForPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            permissions();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void permissions() {
        startService(new Intent(MainActivity.this,MyService.class));
    }

    public static void zip(String[] files, String zipFile) throws IOException {

        File file=new File(zipFile);
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        BufferedInputStream origin = null;
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        byte[] data = new byte[BUFFER_SIZE];

        for (String file1 : files) {
            FileInputStream fi = new FileInputStream(file1);
            origin = new BufferedInputStream(fi, BUFFER_SIZE);
            ZipEntry entry = new ZipEntry(file1.substring(file1.lastIndexOf("/") + 1));
            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                out.write(data, 0, count);
            }
            origin.close();
        }
        out.close();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();//fragment arrayList
        private final List<String> mFragmentTitleList = new ArrayList<>();//title arrayList

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        //adding fragments and title method
        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


     /*backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String backupDBPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+ "WebService"+File.separator+"databases" ;
                final File backupDBFolder = new File(backupDBPath);
                final File backupDB = new File(backupDBFolder, DATABASE_NAME);
                String[] s = new String[1];
                s[0] = backupDB.getAbsolutePath();
                try {

                    zip(s, backupDBPath + "/Backup/"+BACKUP_NAME);
                    alertDialog.setMessage("Backup file is Successfully");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                } catch (IOException e) {
                    alertDialog.setMessage("Backup failed");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                    e.printStackTrace();
                }
            }
        });

    }*/

}
