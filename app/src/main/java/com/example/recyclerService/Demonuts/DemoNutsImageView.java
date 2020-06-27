package com.example.recyclerService.Demonuts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recyclerService.R;

public class DemoNutsImageView extends AppCompatActivity {

    TextView textView;
    ImageView demoNutsImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        textView=findViewById(R.id.imageText);
        demoNutsImage=findViewById(R.id.imageView);
        String filename=getIntent().getStringExtra("filename");
        String url=getIntent().getStringExtra("Image");
        Glide.with(this)
                .load(url)
                .into(demoNutsImage);

        textView.setText(filename);

    }

}
