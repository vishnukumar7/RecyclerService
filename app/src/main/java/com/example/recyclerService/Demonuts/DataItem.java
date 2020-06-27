package com.example.recyclerService.Demonuts;

import java.io.Serializable;

public class DataItem implements Serializable {
	private String id;
	private String name;
	private String country;
	private String city;
	private String imgURL;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setImgURL(String imgURL){
		this.imgURL = imgURL;
	}

	public String getImgURL(){
		return imgURL;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"id = '" + id + '\'' + 
			",name = '" + name + '\'' + 
			",country = '" + country + '\'' + 
			",city = '" + city + '\'' + 
			",imgURL = '" + imgURL + '\'' + 
			"}";
		}
}