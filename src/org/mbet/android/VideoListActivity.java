package org.mbet.android;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.InputStream;

public class VideoListActivity extends ListActivity {
	
	protected static final int SYNC_DID_COMPLETE = 0666;
	protected Cursor mCursor;

	Handler mHandler = new Handler(){
		
		public void handleMessage(Message msg){
			switch(msg.what){
			case SYNC_DID_COMPLETE:
				mCursor = DatabaseHelper.getInstance().getReadableDatabase().query("videos", new String[] { "_id as _id", "title as title", "link as link" }, null, null, null, null, null );//rawQuery("SELECT _id, title as title , url as url FROM videos", null);
		        startManagingCursor(mCursor);
		        System.out.println("Got " + mCursor.getCount() + " rows.");
				ListAdapter adapter = new SimpleCursorAdapter(
		                 VideoListActivity.this, // Context.
		                 android.R.layout.two_line_list_item,  // Specify the row template to use (here, two columns bound to the two retrieved cursor
		                 mCursor,                              // Pass in the cursor to bind to.
		                 new String[] {"title", "link"},        // Array of cursor columns to bind to.
		                 new int[] {android.R.id.text1, android.R.id.text2});  // Parallel array of which template objects to bind to those columns.

		         // Bind to our new adapter.
		         setListAdapter(adapter);
		         mDialog.dismiss();
		         break;
			}
			
		}
		
	};
	
	private ProgressDialog mDialog;
	private ListView mListView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        
        /** Show the loading dialog **/
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Updating");
        mDialog.setCancelable(false);
        mDialog.setMax(100);
        mDialog.setMessage("Downloading video information");
        mDialog.show();
        
        /** Initialize our database **/
        DatabaseHelper.init(this, "mbet");
        
        /** download and parse xml file **/
        AsyncTask<String, Integer, Boolean> mTask = new AsyncTask<String, Integer, Boolean>() {
			@Override
			protected Boolean doInBackground(String... urls) {
				XMLHelper.parseXML(urls[0]);
				return true;
			}
			
			@Override
			protected void onProgressUpdate(Integer... progressions){
				
			}
			
			protected void onPostExecute(Boolean result){
				mHandler.sendEmptyMessage(SYNC_DID_COMPLETE);
			}
        };
        try {
        	mTask.execute("http://mbet.sethirl.com/xml");
        } catch (Exception e) {
        	
        }
        
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
     super.onListItemClick(l, v, position, id);
     
     // Get the item that was clicked
     //String s = (String)this.getListAdapter().getItem(position);
     	Intent tostart = new Intent(Intent.ACTION_VIEW);
     	int oldPosition = mCursor.getPosition();
     	mCursor.moveToPosition(position);
     	tostart.setDataAndType(Uri.parse(mCursor.getString(mCursor.getColumnIndex("link"))), "video/*");
     	startActivity(tostart);
     	mCursor.moveToPosition(oldPosition);
		return;
    }
}