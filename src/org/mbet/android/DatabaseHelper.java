package org.mbet.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static boolean isInited = false;
	private static DatabaseHelper mInstance;
	
	public static void init(Context context, String name){
		mInstance = new DatabaseHelper(context, name);
	}
	
	public static boolean isInited(){
		return isInited;
	}
	
	public static DatabaseHelper getInstance(){
		return mInstance;
	}

	public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
		super(context, name, factory, version);
	}
	
	public DatabaseHelper(Context context, String name) {
		super(context, name, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE videos(_id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(255), link VARCHAR(255), thumbnail VARCHAR(255) )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int fromVersion, int toVersion) {
		

	}

	public void saveVideo(VideoItem videoItem) {
		ContentValues cv = new ContentValues();
		cv.put("title", videoItem.getTitle());
		cv.put("link", videoItem.getLink());
		cv.put("thumbnail", videoItem.getThumbnail());
		try {
			getWritableDatabase().insertOrThrow("videos", null, cv);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
