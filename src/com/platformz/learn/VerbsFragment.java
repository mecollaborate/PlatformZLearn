package com.platformz.learn;

import com.platformz.learn.R;
import com.platformz.learn.SQLController;
import com.platformz.learn.VerbFlashPlay;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class VerbsFragment extends ListFragment {
	SQLController dbcon;

	private Context ourcontext;
	public static int mCursorPosition = 0;
	public static Cursor mCursor = null;

	public static void updateCursor(int offset) {
		if (mCursor == null)
			return;

		mCursorPosition += offset;
		if (mCursor.moveToPosition((int) (mCursorPosition + offset)))
			mCursorPosition += offset;
	}	
	public VerbsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
        
        ourcontext = getActivity().getApplicationContext();
        
		dbcon = new SQLController(ourcontext);
		dbcon.open();

		if(mCursor != null)
		{
			mCursor.close();
		}
		mCursor = dbcon.readDataVerbs();
		
		String[] fromStr = new String[] { "word", "meaning", "prop1", "prop2","_id"};
		int[] toInt = new int[] { R.id.verb_word, R.id.verb_meaning, R.id.verb_p1, R.id.verb_p2, R.id.verb_id};
		
		//int flag = FLAG_REGISTER_CONTENT_OBSERVER;
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(ourcontext, 
				R.layout.verb_list_item, mCursor, fromStr, toInt);

		adapter.notifyDataSetChanged();
		
		ListView lv = (ListView) rootView.findViewById(android.R.id.list);

		lv.setAdapter(adapter);
		
		// listening to single list item on click
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Launching new Activity on selecting single List Item
				Intent i = new Intent(ourcontext, VerbFlashPlay.class);

				mCursorPosition = position;
				mCursor.moveToPosition(position);
				FlashPlay(i);
				startActivity(i);
			}
		});
        return rootView;
    }
	
	 public void onListItemClick(ListView l, View v, int position, long id) {
	        // TODO Auto-generated method stub
	        super.onListItemClick(l, v, position, id);
	        
	        Intent i = new Intent(ourcontext, VerbFlashPlay.class);

			mCursorPosition = position;
			mCursor.moveToPosition(mCursorPosition);
			FlashPlay(i);
			startActivity(i);
	    }
	 
	private void FlashPlay(Intent intent) {
		/*
		 * String Id_str = mCursor.getString(0); String Name_str =
		 * mCursor.getString(1); String Meaning_str = mCursor.getString(2);
		 * String P1_str = mCursor.getString(3); String P2_str =
		 * mCursor.getString(4); String P3_str = mCursor.getString(5); String
		 * Type_str = mCursor.getString(7);
		 * 
		 * intent.putExtra("Id", Id_str); intent.putExtra("Name", Name_str);
		 * intent.putExtra("Meaning", Meaning_str); intent.putExtra("Type",
		 * Type_str);
		 * 
		 * intent.putExtra("P1", P1_str); intent.putExtra("P2", P2_str);
		 * intent.putExtra("P3", P3_str);
		 */
		intent.putExtra("flashName", "true");
		intent.putExtra("flashMeaning", "false");
		intent.putExtra("flashP1", "false");
		intent.putExtra("flashP2", "false");
		intent.putExtra("flashP3", "false");
		intent.putExtra("flashPress", "false");

		intent.putExtra("Tab", "Word");
	}
}
