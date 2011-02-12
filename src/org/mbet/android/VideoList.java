package org.mbet.android;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;

public class VideoList extends ListActivity {
	
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
        
        /** This is where we would download **/
        
        
        
        
    }
}