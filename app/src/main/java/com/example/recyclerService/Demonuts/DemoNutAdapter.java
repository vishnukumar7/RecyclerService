package com.example.recyclerService.Demonuts;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerService.Marvel.Marvel;
import com.example.recyclerService.Marvel.MarvelAdpater;
import com.example.recyclerService.Marvel.MarvelImageView;
import com.example.recyclerService.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DemoNutAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<DemoNutAdapter.CustomViewHolder> {

    private final ArrayList<DataItem> dataItems;
    private final Context context;
    static String FILE_STORAGE_APP="WebService";



    public DemoNutAdapter(ArrayList<DataItem> dataItems, Context context) {

        this.dataItems = dataItems;
        this.context=context;


    }

    @Override
    public DemoNutAdapter.CustomViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_demonuts, parent, false);
        return new DemoNutAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull DemoNutAdapter.CustomViewHolder holder, final int position) {
        DataItem dataItem = dataItems.get(position);
        holder.id.setText(dataItem.getId());
        holder.name.setText(dataItem.getName());
        holder.city.setText(dataItem.getCity());
        holder.country.setText(dataItem.getCountry());
        holder.current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataItem dataItem=dataItems.get(position);
                String text=dataItem.getImgURL();
              //  Toast.makeText(context,""+text,Toast.LENGTH_LONG).show();
                downloadImage(text);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public void downloadImage(String url){
        String fileName=url.substring(url.lastIndexOf('/')+1);
        String localFile= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+FILE_STORAGE_APP+File.separator+"Images";
        String localFileName= localFile+File.separator+fileName;
        File file=new File(localFile);
        File imageFile=new File(localFileName);
        if(imageFile.exists()){
            view(imageFile,fileName);
        }
        else{
            file.mkdirs();
            Uri uri = Uri.parse(url);
            DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(fileName);
            request.setDescription("Downloading");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            request.setVisibleInDownloadsUi(false);

            Log.d(TAG, localFile);

            request.setDestinationUri(Uri.fromFile(imageFile));

            downloadManager.enqueue(request);
        }

    }

    private void view(File image, String filename){
        Intent intent=new Intent(context, DemoNutsImageView.class);
        intent.putExtra("Image",image.toString());
        intent.putExtra("filename",filename);
        context.startActivity(intent);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        final TextView id;
        final TextView name;
        final TextView city;
        final TextView country;
        final CardView current;


        CustomViewHolder(View view) {
            super(view);
            id =view.findViewById(R.id.demoId);
            name=view.findViewById(R.id.demoName);
            city=view.findViewById(R.id.city);
            country=view.findViewById(R.id.country);
            current=view.findViewById(R.id.currentData);

        }
    }
}
