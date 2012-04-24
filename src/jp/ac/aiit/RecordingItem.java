package jp.ac.aiit;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RecordingItem {
	private String category;
	private String task;
	private Long startTime;
	private Long endTime;
	
	private DBOpenHelper m_helper;
	private SQLiteDatabase m_db;
	private static final String TBL_NAME = "Test";
	//startTime REAL, endTime REAL, category TEXT, task TEXT

	public RecordingItem() {
	}
	
	public RecordingItem(Context context) {
		m_helper = DBOpenHelper.getInstance(context);
		if( m_helper != null )
			m_db = m_helper.getWritableDatabase();
		else
			m_db = null;
	}
	
	public void close() {
		m_db.close();
	}
	
	public void save() {
		ContentValues val = new ContentValues();
		val.put( "startTime", (double)startTime );
		val.put( "endTime", (double)endTime );
		val.put( "category", category );
		val.put( "task", task );
		m_db.insert( TBL_NAME, null, val );
	}

	public List<RecordingItem> loadAll()
	{
		if( m_db == null )
        	return null;
		
		Cursor c = m_db.query(TBL_NAME,
						new String[] {"category", "task", "startTime", "endTIme"},
						null, null, null, null, null );
        int numRows = 0;
		numRows = c.getCount();

		List<RecordingItem> entries = new ArrayList<RecordingItem>();
        c.moveToFirst();
		for(int i = 0; i < numRows; i++ )
		{
			RecordingItem entry = new RecordingItem();
			entry.setCategory(c.getString(0));
			entry.setTask(c.getString(1));
			entry.setStartTime((long)(Double.parseDouble(c.getString(2))));
			entry.setEndTime((long)(Double.parseDouble(c.getString(3))));
			entries.add(entry);
			c.moveToNext();
 		}
		c.close();

		return entries;
	}

	/*------------------------------------------------------------------*/
	
	/*
	 * getters & setters
	 */
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
}

