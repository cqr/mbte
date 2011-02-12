package org.mbet.android;

import java.util.LinkedList;

public class VideoChannel {

	final LinkedList<VideoItem> mVideoItems;
	
	public VideoChannel() {
		mVideoItems = new LinkedList<VideoItem>();
	}
	
	public void save() {
		VideoItem videoItem;
		DatabaseHelper.getInstance().getWritableDatabase().execSQL("DELETE from videos");
		for (int i = 0; i < mVideoItems.size(); i++){
			videoItem = mVideoItems.get(i);
			videoItem.save();
		}
	}

	public VideoItem addItem() {
		VideoItem videoItem = new VideoItem();
		mVideoItems.add(videoItem);
		return videoItem;
	}

}
