package jp.ac.aiit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class AndroidDemoActivity extends Activity {
	// ui controls
	private Spinner spinner;
	private EditText editText;
	private ListView listView;
	private Button button;
		
	// data
	RecordingItem recoringItem;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // create data objcet
        recoringItem = new RecordingItem(this);
        
        // set ui controls to fields
        spinner = (Spinner)findViewById(R.id.spinner);
        editText = (EditText)findViewById(R.id.editText);
        listView = (ListView)findViewById(R.id.listView);
        button = (Button)findViewById(R.id.button);
        
        // setup ui controls
        // set text
        setFixedTextToSpinner(spinner);
        
        // set call back listeners
        button.setOnClickListener(mStartListener);
    }
    
    private void setFixedTextToSpinner(Spinner spinner) {
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	adapter.add("01 Requirments Specification");
    	adapter.add("02 Architecture Design");
    	adapter.add("03 Detail Design");
    	adapter.add("04 Coding");
    	adapter.add("05 Testing");
    	spinner.setAdapter(adapter);
    }
    
    /**
     * A call-back for when the user presses the back button.
     */
    OnClickListener mStartListener = new OnClickListener() {
        public void onClick(View v) {
        	recoringItem.setStartTime(System.currentTimeMillis());
        	recoringItem.setCategory((String) spinner.getSelectedItem());
        	recoringItem.setTask(editText.getText().toString());
            //Toast.makeText(AndroidDemoActivity.this, item, Toast.LENGTH_LONG).show();
        	button.setText("stop");
        	button.setOnClickListener(mStopListener);
        }
    };
    
    /**
     * A call-back for when the user presses the back button.
     */
    OnClickListener mStopListener = new OnClickListener() {
        public void onClick(View v) {
        	recoringItem.setEndTime(System.currentTimeMillis());
        	recoringItem.save();
        	showElapseTime();
        	button.setText("start");
        	button.setOnClickListener(mStartListener);
        }
    };
    
    private void showElapseTime() {
    	//List<String> records = new ArrayList<String>();
    	StringBuilder sb = new StringBuilder();
    	
    	Long elapseTime = (recoringItem.getEndTime() - recoringItem.getStartTime()) / (long)60000;
    	//String elapseTimeStr = Long.toString(elapseTime).substring(0, 2) + ":" + Long.toString(elapseTime).substring(3, 2) + "min";
    	/*
    	List<RecordingItem> entries = recoringItem.loadAll();
    	for (Iterator<RecordingItem> it = entries.iterator(); it.hasNext();) {
    		StringBuilder sb = new StringBuilder();
    		sb.append(it.next().getCategory() + "\t");
    		sb.append(it.next().getTask() + "\t");
    		records.add(sb.toString());
    	}
    	*/
    	
    	
    	sb.append(recoringItem.getCategory() + "\t");
    	sb.append(recoringItem.getTask() + "\t");
    	sb.append(Long.toString(elapseTime) + " min");
    	
    	String[] records = new String[] {
    			sb.toString()
    	};
    	
    	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.rowitem, records);
    	listView.setAdapter(arrayAdapter);
    	
    	//System.out.println(elapseTime);
    }
}