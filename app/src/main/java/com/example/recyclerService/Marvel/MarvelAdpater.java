package com.example.recyclerService.Marvel;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerService.MainActivity;
import com.example.recyclerService.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class MarvelAdpater extends androidx.recyclerview.widget.RecyclerView.Adapter<MarvelAdpater.CustomViewHolder>  {
    private final ArrayList<Marvel> marvelContent;
    private final Context context;
    static String FILE_STORAGE_APP="WebService";



    public MarvelAdpater(ArrayList<Marvel> marvelContents, Context context) {
        this.marvelContent = marvelContents;
        this.context=context;


    }

    @Override
    public MarvelAdpater.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_marvel, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MarvelAdpater.CustomViewHolder holder, final int position) {
        Marvel marvel = marvelContent.get(position);
        holder.characterName.setText(marvel.getName());
        holder.realName.setText(marvel.getRealName());
        holder.publisher.setText(marvel.getPublisher());
        holder.createdBy.setText(marvel.getCreatedBy());
        holder.bio.setText(marvel.getBio());
        holder.appearance.setText(marvel.getFirstAppearance());
        holder.team.setText(marvel.getTeam());
        holder.current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Marvel marvel=marvelContent.get(position);
                String text=marvel.getImage();
               // Toast.makeText(context,""+text,Toast.LENGTH_LONG).show();
                downloadImage(text);
            }
        });
    }

    @Override
    public int getItemCount() {
        return marvelContent.size();
    }

    public void downloadImage(String url){
        String fileName=url.substring(url.lastIndexOf('/')+1);
        String localFile=Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+FILE_STORAGE_APP+File.separator+"Images";
        String localFileName= localFile+File.separator+fileName;
        File file=new File(localFile);
        File imageFile=new File(localFileName);
        if(imageFile.exists()){
            view(imageFile,fileName);
        }
        else{
            file.mkdirs();
            /*
            DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url))
                    .setTitle(localFileName)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationInExternalFilesDir(context,localFile,fileName)
                    .setDestinationInExternalPublicDir(localFile,fileName)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true);
            downloadManager.enqueue(request);*/
            Uri uri = Uri.parse(url);
            DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(fileName);
            request.setDescription("Downloading");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            request.setVisibleInDownloadsUi(false);
            //String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            Log.d(TAG, localFile);
        // request.setDestinationUri(Uri.parse(url));
           // request.setDestinationInExternalFilesDir(context,localFile, File.separator+fileName);
            request.setDestinationUri(Uri.fromFile(imageFile));

            downloadManager.enqueue(request);
        }

    }

    public void view(File image,String filename){
        /*Intent intent=new Intent();
        intent.setDataAndType(Uri.fromFile(image),"image/*");
        context.startActivity(intent);*/

        Intent intent=new Intent(context, MarvelImageView.class);
        intent.putExtra("Image",image.toString());
        intent.putExtra("filename",filename);
        context.startActivity(intent);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        final TextView characterName;
        final TextView realName;
        final TextView team;
        final TextView appearance;
        final TextView bio;
        final TextView createdBy;
        final TextView publisher;
        final CardView current;


        CustomViewHolder(View view) {
            super(view);
            characterName =view.findViewById(R.id.characterName);
            realName=view.findViewById(R.id.realName);
            team=view.findViewById(R.id.team);
            appearance=view.findViewById(R.id.firstAppearance);
            bio=view.findViewById(R.id.bio);
            createdBy=view.findViewById(R.id.createdBy);
            publisher=view.findViewById(R.id.publisher);
            current=view.findViewById(R.id.currentData);
       }
    }
}
