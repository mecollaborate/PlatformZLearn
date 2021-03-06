package com.platformz.learn;

import java.io.File;

import com.platformz.learn.R;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppendVerbFragment extends ListFragment {

	private Context ourcontext;
	
	File mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
	SQLController dbcon;
	
	public AppendVerbFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		Add();
        View rootView = inflater.inflate(R.layout.fragment_pages, container, false);
         
        return rootView;
    }
	
	private void Add()
	{
		String fileName = mPath + "/verbs.csv";
		
		ourcontext = getActivity().getApplicationContext();
		dbcon = new SQLController(ourcontext);
		dbcon.open();
		
		dbcon.readFileInDBVerbs(fileName);
	}
}
