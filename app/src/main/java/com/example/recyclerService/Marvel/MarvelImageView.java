package com.example.recyclerService.Marvel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.recyclerService.R;

import java.io.File;

public class MarvelImageView extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        textView=findViewById(R.id.imageText);
        imageView=findViewById(R.id.imageView);
        String filename=getIntent().getStringExtra("filename");
        String url=getIntent().getStringExtra("Image");
        Glide.with(this)
        .load(url)
        .into(imageView);

        textView.setText(filename);

    }
}
