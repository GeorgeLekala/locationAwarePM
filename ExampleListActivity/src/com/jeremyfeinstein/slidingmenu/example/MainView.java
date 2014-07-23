package com.jeremyfeinstein.slidingmenu.example;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.crittercism.NotificationActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Activity;


public class MainView extends Fragment{
	
	//private final static String CHECK_URI = "http://192.168.190.1/EmployeeService/EmployeeInfo.svc/CheckParent";
    String jsondata;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
			View view = inflater.inflate(R.layout.main, container, false);
		  
		  	//final SessionManagement session = new SessionManagement(getActivity());

		  	//Creating all buttons instances	  
			Button btn_user = (Button) view.findViewById(R.id.main_btn_user);
		    Button btn_power = (Button) view.findViewById(R.id.main_btn_shutdown);
			Button btn_schedule = (Button) view.findViewById(R.id.main_btn_sleep);
			Button btn_process = (Button) view.findViewById(R.id.main_btn_wake);
			Button btn_reports = (Button) view.findViewById(R.id.main_btn_reports);
			Button btn_signout = (Button) view.findViewById(R.id.main_btn_signout);

		    btn_user.setOnClickListener(new View.OnClickListener() {
		      
		  	@Override
			public void onClick(View view) {
				// Launching News Feed Screen
		    	Fragment newFragment = new UserFragment();
		  		FragmentManager fm = getFragmentManager();
		  		FragmentTransaction ft = fm.beginTransaction();	
		  		ft.replace(R.id.content_frame, newFragment);
		  		ft.addToBackStack(null)
		  		.commit();
		  		return;
			}
		    });
		    
		   // Listening Friends button click
		    btn_power.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// Launching News Feed Screen
			    	Fragment newFragment = new WorkgroupListFragment();
			  		FragmentManager fm = getFragmentManager();
			  		FragmentTransaction ft = fm.beginTransaction();	
			  		ft.replace(R.id.content_frame, newFragment);
			  		ft.addToBackStack(null)
			  		.commit();
			  		return;
				}
			});
		    
		    // Listening Messages button click
		    btn_schedule.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					 //Launching News Feed Screen
				    Fragment newFragment = new ScheduleFragment();
			  		FragmentManager fm = getFragmentManager();
			  		FragmentTransaction ft = fm.beginTransaction();	
			  		ft.replace(R.id.content_frame, newFragment);
			  		ft.addToBackStack(null)
			  		.commit();
			 
				
				}
			});
		    
		    // Listening to Places button click
		    btn_process.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// Launching News Feed Screen
				    Fragment newFragment = new ComputerListFragment();
			  		FragmentManager fm = getFragmentManager();
			  		FragmentTransaction ft = fm.beginTransaction();	
			  		ft.replace(R.id.content_frame, newFragment);
			  		ft.addToBackStack(null)
			  		.commit();
			  		return;
				}
			});
		    
		    // Listening to Events button click
		    btn_reports.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// Launching News Feed Screen
				    Fragment newFragment = new ReportFragement();
			  		FragmentManager fm = getFragmentManager();
			  		FragmentTransaction ft = fm.beginTransaction();	
			  		ft.replace(R.id.content_frame, newFragment);
			  		ft.addToBackStack(null)
			  		.commit();
			  		return;
				}
			});
		    
		    // Listening to Photos button click
		    btn_signout.setOnClickListener(new View.OnClickListener() {
				
		          @Override
			      public void onClick(View v) {
			            
			    	//session.logoutUser();
			  		Intent i = new Intent(getActivity(), LoginActivity.class);
			  	    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
			
			      }
			});
		    
		
		    return view;
	}


	/*  public void createNotification(View view,String readString ) {
	    // Prepare intent which is triggered if the
	    // notification is selected
	   Intent intent = new Intent(getActivity(), Accept.class);
	   PendingIntent pIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

	    // Build notification
	    // Actions are just fake
	    Notification noti = new Notification.Builder(getActivity())
	        .setContentTitle("Parent Request ")
	        .setContentText(readString).setSmallIcon(R.drawable.hireme)
	        .setContentIntent(pIntent).build();
	    getActivity();
		NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
	    // Hide the notification after its selected
	    noti.flags |= Notification.FLAG_AUTO_CANCEL;

	    notificationManager.notify(0, noti);

	  }*/
}
