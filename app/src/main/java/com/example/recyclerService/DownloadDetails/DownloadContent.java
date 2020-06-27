package com.example.recyclerService.DownloadDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadContent {
    @SerializedName("ContentData")
    @Expose
    private DataList[] download;


    public DataList[] getDownload() {
        return download;
    }
}
