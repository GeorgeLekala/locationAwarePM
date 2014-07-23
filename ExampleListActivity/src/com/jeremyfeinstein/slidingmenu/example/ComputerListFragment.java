package com.jeremyfeinstein.slidingmenu.example;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.MenuItem;

public class ComputerListFragment extends SherlockListFragment{

	public SharedPreferences sharedpreferences;
	public ArrayList<String> listcontents;
		
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			
			 View view = inflater.inflate(R.layout.list, container, false);	
			 setHasOptionsMenu(true);
		
			try {
				
			    sharedpreferences = this.getActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE);	
			 	listcontents = new ArrayList<String>();
			 	listcontents.add(sharedpreferences.getAll().get("computername").toString());
		
			 	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				Toast.makeText(getActivity(),"Error in Computer List: "+e1.getMessage(),Toast.LENGTH_LONG).show();
	    		
			}
			
			return view;
		}


	    @Override
	    public boolean onOptionsItemSelected(MenuItem item){
	        switch(item.getItemId())
	        {
	        case R.id.github:
	            Toast.makeText(getSherlockActivity(), "Refreshing...", Toast.LENGTH_SHORT).show();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	    
		@Override
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);
			setListAdapter(new ContentArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listcontents));
		}
				
		@Override
		public void onListItemClick(ListView l, View v, int position, long id){
		    Fragment newFragment = null;
			switch(position){
			default:
				newFragment = new ProcessFragement();
				break;

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
