package com.example.recyclerService.Marvel;

import com.google.gson.annotations.SerializedName;

public class Marvel {

    @SerializedName("name")
    private String name;

    @SerializedName("realName")
    private String realName;

    @SerializedName("team")
    private String team;

    @SerializedName("firstappearance")
    private String firstAppearance;

    @SerializedName("createdby")
    private String createdBy;

    @SerializedName("publisher")
    private String publisher;

    @SerializedName("imageurl")
    private String image;

    @SerializedName("bio")
    private String bio;

    public void setName(String name){
        this.name=name;
    }

    public void setRealName(String realname){
        this.realName =realname;
    }

    public void setTeam(String team){
        this.team=team;
    }

    public void setFirstAppearance(String firstAppearance){
        this.firstAppearance=firstAppearance;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy=createdBy;
    }

    public void setPublisher(String publisher){
        this.publisher=publisher;
    }

    public void setImage(String image){
        this.image=image;
    }

    public void setBio(String bio){
        this.bio=bio;
    }

    public String getName(){
        return this.name;
    }

    public String getRealName(){
        return this.realName;
    }

    public String getTeam(){
        return this.team;
    }

    public String getFirstAppearance(){
        return this.firstAppearance;
    }

    public String getCreatedBy(){
        return this.createdBy;
    }

    public String getPublisher(){
        return this.publisher;
    }

    public String getImage(){
        return this.image;
    }

    public String getBio(){
        return this.bio;
    }

}
