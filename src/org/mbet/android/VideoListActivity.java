package org.mbet.android;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;
import java.io.InputStream;

public class VideoListActivity extends ListActivity {
	
	Handler mHandler = new Handler(){
		
		public void handleMessage(Message msg){
			
			super.handleMessage(msg);
			
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
        
        /** Get the ListView **/
        mListView = getListView();
        
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
				mDialog.dismiss();
			}
        };
        try {
        	mTask.execute("http://mbet.sethirl.com/xml");
        } catch (Exception e) {
        	
        }
        
    }
}