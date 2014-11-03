package com.platformz.learn;

import java.io.File;

import com.platformz.learn.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.TextView;

public class WordFlashPlay extends Activity implements OnClickListener {

	public static final String EMPTY_STRING = "";
	static Boolean flashName_b = true;
	static Boolean flashMeaning_b = false;
	static Boolean flashP1_b = false;
	static Boolean flashP2_b = false;
	static Boolean flashP3_b = false;
	static Boolean flashPress_b = false;
	static Boolean isWordTab = true;

	File mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

	TextView p1_tx, p2_tx, p3_tx, p4_tx, p5_tx, id_tx, name_tx, type_tx;
	CheckBox  name_cb, p1_cb, p2_cb, p3_cb, p4_cb, p5_cb;
	SQLController dbcon;
	
	TextView word_title_tx;
	TextView word_id_tx;
	TextView word_type_tx;
	TextView word_word_tx;
	TextView word_meaning_tx;
	TextView word_p1_tx;
	TextView word_p2_tx;
	TextView word_p3_tx;
	TextView word_p4_tx;
	
	CheckBox word_word_cb;
	CheckBox word_meaning_cb;
	CheckBox word_p1_cb;
	CheckBox word_p2_cb;
	CheckBox word_p3_cb;
	CheckBox word_p4_cb;
	
	Button word_lt_bt, word_flash_bt, word_rt_bt;

	String Id_str, Name_str, Meaning_str, P1_str, P2_str, P3_str, Type_str ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_flash);

		getControls();
		populateControls();
		setCheckBox();
		
		dbcon = new SQLController(this);
		dbcon.open();
		flashPress_b = false;
	}

	private void populateControls()
	{
		Cursor c = WordsFragment.mCursor;
		if(c == null)
			return;

		Id_str = c.getString(0);
		Name_str = c.getString(1);
		Meaning_str = c.getString(2);

		P1_str = c.getString(3);
		P2_str = c.getString(4);
		P3_str = c.getString(5);
		Type_str = c.getString(7);
		
		Intent i = getIntent();
		String flashName_str = i.getStringExtra("flashName");
		String flashMeaning_str = i.getStringExtra("flashMeaning");
		String flashP1_str = i.getStringExtra("flashP1");
		String flashP2_str = i.getStringExtra("flashP2");
		String flashP3_str = i.getStringExtra("flashP3");
		String flashPress_str = i.getStringExtra("flashPress");

		flashName_b = Boolean.parseBoolean(flashName_str);
		flashMeaning_b = Boolean.parseBoolean(flashMeaning_str);
		flashP1_b = Boolean.parseBoolean(flashP1_str);
		flashP2_b = Boolean.parseBoolean(flashP2_str);
		flashP3_b = Boolean.parseBoolean(flashP3_str);
		flashPress_b = Boolean.parseBoolean(flashPress_str);
		
		word_id_tx.setText(Id_str);
		word_type_tx.setText(Type_str);
		
		if(flashPress_b || flashName_b)
			word_word_tx.setText(Name_str);
		else
			word_word_tx.setText(EMPTY_STRING);

		if(flashPress_b || flashMeaning_b)
			word_meaning_tx.setText(Meaning_str);
		else
			word_meaning_tx.setText(EMPTY_STRING);
		
		if(flashPress_b || flashP1_b)
			word_p1_tx.setText(P1_str);
		else
			word_p1_tx.setText(EMPTY_STRING);
		
		if(flashPress_b || flashP2_b)
			word_p2_tx.setText(P2_str);
		else
			word_p2_tx.setText(EMPTY_STRING);
		
		if(flashPress_b || flashP3_b)
			word_p3_tx.setText(P3_str);
		else
			word_p3_tx.setText(EMPTY_STRING);
	}
	
	private void setCheckBox()
	{
		word_word_cb.setChecked(flashName_b);
		word_meaning_cb.setChecked(flashMeaning_b);;
		word_p1_cb.setChecked(flashP1_b);
		word_p2_cb.setChecked(flashP2_b);
		word_p3_cb.setChecked(flashP3_b);
	}

	public void returnHome() {

		Intent home_intent = new Intent(getApplicationContext(),
				HomeFragment.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(home_intent);
	}

	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.word_word_cb:
			flashName_b = ((CheckBox) v).isChecked();
			RefreshPage(flashPress_b);
			break;
			
		case R.id.word_meaning_cb:
			flashMeaning_b = ((CheckBox) v).isChecked();
			RefreshPage(flashPress_b);
			break;
					
		case R.id.word_p1_cb:
			flashP1_b = ((CheckBox) v).isChecked();
			RefreshPage(flashPress_b);
			
			break;
			
		case R.id.word_p2_cb:
			flashP2_b = ((CheckBox) v).isChecked();
			RefreshPage(flashPress_b);
			break;

		case R.id.word_p3_cb:
			flashP3_b = ((CheckBox) v).isChecked();
			RefreshPage(flashPress_b);
			break;

		case R.id.word_lt_bt:
			TraverseCursor(-1, flashPress_b);
			break;

		case R.id.word_rt_bt:
			
			TraverseCursor(1, flashPress_b);
			break;

		case R.id.word_flash_bt:
			flashPress_b = true;
			TraverseCursor(0, flashPress_b);
			break;
		
		}
		flashPress_b = false;
	}
	
	private void TraverseCursor(int offset, Boolean flashPress)
	{
		WordsFragment.updateCursor(offset);

		Intent intent = getIntent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		intent.putExtra("flashName", flashName_b.toString());
		intent.putExtra("flashMeaning", flashMeaning_b.toString());
		intent.putExtra("flashP1", flashP1_b.toString());
		intent.putExtra("flashP2", flashP2_b.toString());
		intent.putExtra("flashP3", flashP3_b.toString());
		intent.putExtra("flashPress", flashPress.toString());
		
	    finish();
	    startActivity(intent);
	}

	private void RefreshPage(Boolean flashPress)
	{
		Cursor c = WordsFragment.mCursor;
		if(c == null)
			return;

		Intent intent = getIntent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		intent.putExtra("flashName", flashName_b.toString());
		intent.putExtra("flashMeaning", flashMeaning_b.toString());
		intent.putExtra("flashP1", flashP1_b.toString());
		intent.putExtra("flashP2", flashP2_b.toString());
		intent.putExtra("flashP3", flashP3_b.toString());
		intent.putExtra("flashPress", flashPress.toString());
		
	    finish();
	    //startActivity(intent);
	    startActivityForResult(intent, 0);
	    overridePendingTransition(0,0); 
	}
	private void Add()
	{
		String fileName = mPath + "/words.csv";
		dbcon.readFileInDBWords(fileName);
	    returnHome();
	}
	private void getControls()
	{
		word_title_tx = (TextView)findViewById(R.id.word_title_tx);
		word_id_tx = (TextView)findViewById(R.id.word_id_tx);
		word_type_tx = (TextView)findViewById(R.id.word_type_tx);
		
		word_word_tx = (TextView)findViewById(R.id.word_word_tx);
		word_meaning_tx = (TextView)findViewById(R.id.word_meaning_tx);
		word_p1_tx = (TextView)findViewById(R.id.word_p1_tx);
		word_p2_tx = (TextView)findViewById(R.id.word_p2_tx);
		word_p3_tx = (TextView)findViewById(R.id.word_p3_tx);
		
		word_word_cb = (CheckBox)findViewById(R.id.word_word_cb);
		word_meaning_cb = (CheckBox)findViewById(R.id.word_meaning_cb);
		word_p1_cb = (CheckBox)findViewById(R.id.word_p1_cb);
		word_p2_cb = (CheckBox)findViewById(R.id.word_p2_cb);
		word_p3_cb = (CheckBox)findViewById(R.id.word_p3_cb);

		word_word_cb = (CheckBox)findViewById(R.id.word_word_cb);
		word_meaning_cb.setOnClickListener(this);
		word_p1_cb.setOnClickListener(this);
		word_p2_cb.setOnClickListener(this);
		word_p3_cb.setOnClickListener(this);

		word_lt_bt = (Button)findViewById(R.id.word_lt_bt);
		word_flash_bt = (Button)findViewById(R.id.word_flash_bt);
		word_rt_bt = (Button)findViewById(R.id.word_rt_bt);
		
		word_lt_bt.setOnClickListener(this);
		word_flash_bt.setOnClickListener(this);
		word_rt_bt.setOnClickListener(this);
	}
}
