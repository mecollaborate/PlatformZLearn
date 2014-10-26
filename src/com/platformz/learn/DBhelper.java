package com.platformz.learn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

	private final Context mContext;
	
	// TABLE INFORMATTION
		public static final int TABLE_TYPE_DEFAULT = 0;

		public static final String TABLE_NAME_WORDS = "words";
		public static final String TABLE_NAME_VERBS = "verbs";

		public static final String WORD_ID = "_id";//Id 0
		public static final String WORD_NAME = "word";//name of word 1
		public static final String WORD_MEANING = "meaning";//word type 2		
		public static final String WORD_P1 = "prop1";//article 3
		public static final String WORD_P2 = "prop2";//plural 4
		public static final String WORD_P3 = "prop3";//Other 5
		public static final String WORD_P4 = "prop4";//Other 6
		public static final String WORD_TYPE = "type";//word type

		// DATABASE INFORMATION
		static final String DB_NAME = "MEMBER.DB";
		static final int DB_VERSION = 1;
		
	// TABLE CREATION STATEMENT
	private static final String CREATE_TABLE_WORD = "create table "
			+ TABLE_NAME_WORDS + "(" 
			+ WORD_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ WORD_NAME  + " TEXT NOT NULL,"
			+ WORD_MEANING + " TEXT,"
			+ WORD_P1 + " TEXT,"
			+ WORD_P2 + " TEXT,"
			+ WORD_P3 + " TEXT,"			
			+ WORD_P4 + " TEXT,"
			+ WORD_TYPE + " TEXT);";
	//+ WORD_NAME + " TEXT NOT NULL);";

	// TABLE CREATION STATEMENT
	private static final String CREATE_TABLE_VERB = "create table "
			+ TABLE_NAME_VERBS + "(" 
			+ WORD_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ WORD_NAME  + " TEXT NOT NULL,"
			+ WORD_MEANING + " TEXT,"
			+ WORD_P1 + " TEXT,"
			+ WORD_P2 + " TEXT,"
			+ WORD_P3 + " TEXT,"			
			+ WORD_P4 + " TEXT,"
			+ WORD_TYPE + " TEXT);";
	//+ WORD_NAME + " TEXT NOT NULL);";
	
	public DBhelper(Context context) {
		super(context, DB_NAME, null,DB_VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_WORD);
		List<String[]> word_list = ParseFileFromAsset("words.csv", ';');
		insertBulkWords(db, word_list);
		
		db.execSQL(CREATE_TABLE_VERB);
		List<String[]> verb_list = ParseFileFromAsset("verbs.csv", ';');
		insertBulkVerbs(db, verb_list);
	}

	public void readFileInDBWords(SQLiteDatabase db, String fileName)
	{
		List<String[]> word_list = ParseFileFromPhone(fileName, ';');
		insertBulkWords(db, word_list);
	}
	
	public void readFileInDBVerbs(SQLiteDatabase db, String fileName)
	{
		List<String[]> verb_list = ParseFileFromPhone(fileName, ';');
		insertBulkVerbs(db, verb_list);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_WORDS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_VERBS);
		onCreate(db);
	}
	
	public List<String[]> ParseFileFromAsset(String fileName, char fieldSep) {
		String next[] = {};
		List<String[]> list = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new InputStreamReader(mContext
					.getAssets().open(fileName), "utf8"), fieldSep);// Specify asset
																// file name
			// in open();
			for (;;) {
				next = reader.readNext();
				if (next != null) {
					list.add(next);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String[]> ParseFileFromPhone(String fileName, char fieldSep) {
		String next[] = {};
		List<String[]> list = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(fileName), "utf8"), fieldSep);// Specify asset
																// file name
			// in open();
			for (;;) {
				next = reader.readNext();
				if (next != null) {
					list.add(next);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	

	/*
	 * public static final String TABLE_WORDS = "words"; public static final
	 * String WORD_ID = "_id";//Id 0 public static final String WORD_NAME =
	 * "word";//name of word 1 public static final String WORD_TYPE =
	 * "type";//word type 5 public static final String WORD_P1 =
	 * "prop1";//meaning 2 public static final String WORD_P2 =
	 * "prop2";//article 3 public static final String WORD_P3 = "prop3";//plural
	 * 4 public static final String WORD_P4 = "prop4";//Other1 6 public static
	 * final String WORD_P5 = "prop5";//Other1 7
	 */
	public long insertBulkWords(SQLiteDatabase db, List<String[]> lst) {
		try {
			db.beginTransaction();

			final int nrows = lst.size();
			for (int r = 0; r < nrows; r++) {
				String strs[] = lst.get(r);
				int nwords = strs.length;

				ContentValues cv = new ContentValues();
				for (int w = 0; w < nwords; w++) {
					String str = strs[w];
					switch (w) {
					case 0:// name
						cv.put(DBhelper.WORD_NAME, str);
						break;
					case 1:// meaning
						cv.put(DBhelper.WORD_MEANING, str);
						break;
					case 2:// article
						cv.put(DBhelper.WORD_P1, str);
						break;
					case 3:// plural
						cv.put(DBhelper.WORD_P2, str);
						break;
					case 4:// other p3
						cv.put(DBhelper.WORD_P3, str);
						break;
					case 5:// other p4
						cv.put(DBhelper.WORD_P4, str);
						break;
					case 6:// other
						cv.put(DBhelper.WORD_TYPE, str);
						break;
					}
				}
				db.insert(DBhelper.TABLE_NAME_WORDS, null, cv);
			}

			db.setTransactionSuccessful();
			
		} catch (SQLException e) 
		{
		}
		
		finally 
		{
			db.endTransaction();
		}
		return 1;
	}
	
	public long insertBulkVerbs(SQLiteDatabase db, List<String[]> lst) {
		try {
			db.beginTransaction();

			final int nrows = lst.size();
			for (int r = 0; r < nrows; r++) {
				String strs[] = lst.get(r);
				int nwords = strs.length;

				ContentValues cv = new ContentValues();
				for (int w = 0; w < nwords; w++) {
					String str = strs[w];
					switch (w) {
					case 0:// name
						cv.put(DBhelper.WORD_NAME, str);
						break;
					case 1:// meaning
						cv.put(DBhelper.WORD_MEANING, str);
						break;
					case 2:// article
						cv.put(DBhelper.WORD_P1, str);
						break;
					case 3:// plural
						cv.put(DBhelper.WORD_P2, str);
						break;
					case 4:// other p3
						cv.put(DBhelper.WORD_P3, str);
						break;
					case 5:// other p4
						cv.put(DBhelper.WORD_P4, str);
						break;
					case 6:// other
						cv.put(DBhelper.WORD_TYPE, str);
						break;
					}
				}
				db.insert(DBhelper.TABLE_NAME_VERBS, null, cv);
			}

			db.setTransactionSuccessful();
		} catch (SQLException e) {
		} finally {
			db.endTransaction();
		}
		return 1;
	}
}