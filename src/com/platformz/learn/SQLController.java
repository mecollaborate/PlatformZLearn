package com.platformz.learn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLController {

	private DBhelper dbhelper;
	private Context ourcontext;
	private SQLiteDatabase db;

	public SQLController(Context c) {
		ourcontext = c;
	}

	public SQLController open() throws SQLException {
		dbhelper = new DBhelper(ourcontext);
		db = dbhelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbhelper.close();
	}
	
	public long insertDataWord(String name) {
		ContentValues cv = new ContentValues();
		cv.put(DBhelper.WORD_NAME, name);
		long l = db.insert(DBhelper.TABLE_NAME_WORDS, null, cv);
		return l;
	}
	
	public long insertDataVerb(String name) {
		ContentValues cv = new ContentValues();
		cv.put(DBhelper.WORD_NAME, name);
		long l = db.insert(DBhelper.TABLE_NAME_VERBS, null, cv);
		return l;
	}

	public Cursor readDataWords() {
		String[] allColumns = new String[] { 
				DBhelper.WORD_ID,
				DBhelper.WORD_NAME,
				DBhelper.WORD_MEANING,
				DBhelper.WORD_P1,
				DBhelper.WORD_P2,
				DBhelper.WORD_P3,
				DBhelper.WORD_P4,
				DBhelper.WORD_TYPE
				};
		Cursor c = db.query(DBhelper.TABLE_NAME_WORDS, allColumns, null,
				null, null, null, null);
		if(c!= null)
			c.moveToFirst();
		return c;
	}
	
	public Cursor readDataVerbs() {
		String[] allColumns = new String[] { 
				DBhelper.WORD_ID,
				DBhelper.WORD_NAME,
				DBhelper.WORD_MEANING,
				DBhelper.WORD_P1,
				DBhelper.WORD_P2,
				DBhelper.WORD_P3,
				DBhelper.WORD_P4,
				DBhelper.WORD_TYPE				};
		
		Cursor c = db.query(DBhelper.TABLE_NAME_VERBS, allColumns, null,
				null, null, null, null);
		if(c!= null)
			c.moveToFirst();
		return c;
	}
	public Cursor readDataByWord(long wordId) {
		String[] allColumns = new String[] { 
				DBhelper.WORD_ID,
				DBhelper.WORD_NAME,
				DBhelper.WORD_MEANING,
				DBhelper.WORD_P1,
				DBhelper.WORD_P2,
				DBhelper.WORD_P3,
				DBhelper.WORD_P4,
				DBhelper.WORD_TYPE
				};
		Cursor row = db.query(true, DBhelper.TABLE_NAME_WORDS, allColumns, 
				DBhelper.WORD_ID + " = " + wordId,
				null, null, null, null, null);
		if(row!= null)
			row.moveToFirst();		
		return row;
	}

	public Cursor readDataByVerb(long wordId) {
		String[] allColumns = new String[] { 
				DBhelper.WORD_ID,
				DBhelper.WORD_NAME,
				DBhelper.WORD_MEANING,
				DBhelper.WORD_P1,
				DBhelper.WORD_P2,
				DBhelper.WORD_P3,
				DBhelper.WORD_P4,
				DBhelper.WORD_TYPE
				};
		Cursor row = db.query(true, DBhelper.TABLE_NAME_VERBS, allColumns, 
				DBhelper.WORD_ID + " = " + wordId,
				null, null, null, null, null);
		if(row!= null)
			row.moveToFirst();				
		return row;
	}
	
	public int updateDataWord(long wordID, String wordName, String meaning, String p1, String p2, String p3, String p4, String type) {
		ContentValues cvUpdate = new ContentValues();
		
		cvUpdate.put(DBhelper.WORD_NAME, wordName);
		int i = db.update(DBhelper.TABLE_NAME_WORDS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_MEANING, meaning);
		i = db.update(DBhelper.TABLE_NAME_WORDS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_P1, p1);
		i = db.update(DBhelper.TABLE_NAME_WORDS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_P2, p2);
		i = db.update(DBhelper.TABLE_NAME_WORDS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_P3, p3);
		i = db.update(DBhelper.TABLE_NAME_WORDS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_P4, p4);
		i = db.update(DBhelper.TABLE_NAME_WORDS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_TYPE, type);
		i = db.update(DBhelper.TABLE_NAME_WORDS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);
		
		return i;
	}

	public int updateDataVerb(long wordID, String wordName, String meaning, String p1, String p2, String p3, String p4, String type) {
		ContentValues cvUpdate = new ContentValues();
		
		cvUpdate.put(DBhelper.WORD_NAME, wordName);
		int i = db.update(DBhelper.TABLE_NAME_VERBS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_MEANING, meaning);
		i = db.update(DBhelper.TABLE_NAME_VERBS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_P1, p1);
		i = db.update(DBhelper.TABLE_NAME_VERBS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_P2, p2);
		i = db.update(DBhelper.TABLE_NAME_VERBS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_P3, p3);
		i = db.update(DBhelper.TABLE_NAME_VERBS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_P4, p4);
		i = db.update(DBhelper.TABLE_NAME_VERBS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);

		cvUpdate.put(DBhelper.WORD_TYPE, type);
		i = db.update(DBhelper.TABLE_NAME_VERBS, cvUpdate,
				DBhelper.WORD_ID + " = " + wordID, null);
		
		return i;
	}

	public void deleteDataWord(long wordID) {
		db.delete(DBhelper.TABLE_NAME_WORDS, DBhelper.WORD_ID + "="
				+ wordID, null);
	}

	public void deleteDataVerb(long wordID) {
		db.delete(DBhelper.TABLE_NAME_VERBS, DBhelper.WORD_ID + "="
				+ wordID, null);
	}
	
	public void readFileInDBWords(String fileName)
	{
		dbhelper.readFileInDBWords(db, fileName);
	}
	
	public void readFileInDBVerbs(String fileName)
	{
		dbhelper.readFileInDBVerbs(db, fileName);
	}
}// outer class end
