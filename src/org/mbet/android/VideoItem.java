package org.mbet.android;

import android.util.Log;

public class VideoItem {
	
	private int id;
	private String title, link, thumbnail;
	
	public VideoItem(){}
	public VideoItem(int myId){
		id = myId;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getLink() {
		return link;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}
	

	public void setTitle(String currentValue) {
		title = currentValue;
	}

	public void setLink(String currentValue) {
		link = currentValue;
	}

	public void setThumbnail(String currentValue) {
		thumbnail = currentValue;
	}

	public void save() {
		DatabaseHelper dbh = DatabaseHelper.getInstance();
		dbh.saveVideo(this);
	}

}
