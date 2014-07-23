package com.jeremyfeinstein.slidingmenu.example;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RandomList extends ListFragment{
	
	ArrayList<String> list_contents;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		list_contents = new ArrayList<String>();
		list_contents.add("Dashboard");
		list_contents.add("Profile");
		list_contents.add("Power Control");
		list_contents.add("Schedule Task");
		list_contents.add("Process Control");
		list_contents.add("Reports");
		list_contents.add("Sign out");
		
		return inflater.inflate(R.layout.list, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new ContentArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list_contents));
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
	    Fragment newFragment = null;
		switch(position){
		case 0:
	         Intent i = new Intent(getActivity(), MainActivity.class);
	         i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(i);
			break;
		case 1:
			newFragment = new UserFragment();
			break;
		case 2:
			newFragment = new PowerFragment();
			break;
		case 3:
			newFragment = new ScheduleFragment();
			break;
		case 4:
		     newFragment = new ProcessFragement();
		 	break;
		case 5:
			 newFragment = new ReportFragement();
			 break;
		case 6:
			  i = new Intent(getActivity(), LoginActivity.class);
		      i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	          startActivity(i);

		}
		if (newFragment != null){
			switchFragment(newFragment);}
		
	
	}
	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment);
		} 
	}

}
