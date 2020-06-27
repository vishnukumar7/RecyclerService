package com.example.recyclerService.Demonuts;

import java.util.List;
import java.io.Serializable;

public class DemoNuts implements Serializable {
	private String status;
	private String message;
	private List<DataItem> data;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"DemoNuts{" + 
			"status = '" + status + '\'' + 
			",message = '" + message + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}