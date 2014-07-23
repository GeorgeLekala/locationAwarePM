package com.jeremyfeinstein.slidingmenu.example;

import java.util.List;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.QueryOrder;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

public class TerminateProcessFragment extends Fragment{
	
	private MobileServiceClient mClient;
	
	private String test;
	/**
	 * Mobile Service Table used to access data
	 */
	private MobileServiceTable<CommandItem> mCommandTable;
	 
	public String userId = "";
	public String userpass = "";
	public String username = "";
	public String usercommand = "";
	public String userauth = "";
	public String userlast = "";
	public static final String SENDER_ID = "197130149089";
	public List<CommandItem> mComItem;
	public CommandItem mGetUserItem;
	public SharedPreferences sharedpreferences;
	public SharedPreferences processpreferences;
	public String session_username;
	public CommandItem mItem;
	public String toast;
	private ProgressDialog ringProgressDialog;

	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View view = inflater.inflate(R.layout.terminate_process, container, false);
		
		Button btn_terminate = (Button) view.findViewById(R.id.btn_terminate);
		
        Button btn_suspend = (Button) view.findViewById(R.id.btn_suspend);
	    
		 setHasOptionsMenu(true);
			
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			try {
				
			    sharedpreferences = this.getActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE);	
			    processpreferences = this.getActivity().getSharedPreferences("process_session", Context.MODE_PRIVATE);	
		        
				mClient = new MobileServiceClient(
						"https://locationawarepm.azure-mobile.net/",
						"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
						getActivity()).withFilter(new ProgressFilter());		
				
				mCommandTable = mClient.getTable(CommandItem.class);
				
			    session_username = sharedpreferences.getAll().get("username").toString();
				mGetUserItem = new CommandItem();
				mItem = new CommandItem();
			 	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				Toast.makeText(getActivity(),"Error in TerminateProcessFragmentt: "+e1.getMessage(),Toast.LENGTH_LONG).show();
	    		
			}
			
		    // Listening Friends button click
		    btn_terminate.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
	
					try {
						Toast.makeText(getActivity(),session_username+" : "+processpreferences.getAll().get("terminate").toString(), Toast.LENGTH_LONG).show();
						
						 if(getDBUsername(session_username).getmUsername().toString()!=null)
						 {
							 	
							 	getDBUsername(session_username).setTerminateprocess("TERMINATE "+processpreferences.getAll().get("terminate").toString());
								addNewItem(getDBUsername(session_username));
						 }
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getActivity(),"btn_terminate :"+ e.getMessage(), Toast.LENGTH_LONG).show();
							
					}
				}
			});
		    
		    // Listening Friends button click
		    btn_suspend.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
	
					try {
						Toast.makeText(getActivity(),session_username+" : "+processpreferences.getAll().get("terminate").toString(), Toast.LENGTH_LONG).show();
						
						Toast.makeText(getActivity(),"Coming Soon", Toast.LENGTH_LONG).show();
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getActivity(),"btn_suspend :"+ e.getMessage(), Toast.LENGTH_LONG).show();
							
					}
				}
			});
			
		return view;
}
	
	public CommandItem getDBUsername(String uname) 
	{
		try {
			// Get the items that weren't marked as completed and add them in the
			// adapter		
			mCommandTable.where().field("username").
			eq(uname).orderBy("__createdAt", QueryOrder.Descending).
			top(1).execute(new TableQueryCallback<CommandItem>() {

				public void onCompleted(List<CommandItem> result, int count, Exception exception, ServiceFilterResponse response) {
					if (exception == null) {
			
						for (CommandItem item : result) {
						
							mItem.setMfirstname(item.getMfirstname().toString());
							mItem.setMlastname(item.getMlastname().toString());
							mItem.setMauth_level(item.getMauth_level().toString());
							mItem.setmCommand(item.getmCommand().toString());
							mItem.setmUsername(item.getmUsername().toString());
							mItem.setmPassword(item.getmPassword().toString());
							mItem.setmWorkgoup(item.getmWorkgoup().toString());
							mItem.setmWorkgoup_state(item.getmWorkgoup_state().toString());
							mItem.setComputername(item.getComputername().toString());
							
						}
						
					} else {
						Toast.makeText(getActivity(),exception.getMessage(), Toast.LENGTH_LONG).show();
						
					}
				}
			});
		
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		return mItem;
		
	}
	
	public void addNewItem(CommandItem item) {
		
		if (mClient == null) {
			return;
		}
	
		// Insert the new item
		mCommandTable.insert(item, new TableOperationCallback<CommandItem>() {

			public void onCompleted(CommandItem entity, Exception exception, ServiceFilterResponse response) {
				
				if (exception == null) {
					
					Toast.makeText(getActivity(),"Sucecessful", Toast.LENGTH_LONG).show();
					refreshFrag();
					
				} else {

					Toast.makeText(getActivity(),"Failed : " +exception.getMessage(), Toast.LENGTH_LONG).show();
				}

			}
		});

	}
	
	public void refreshFrag()
	{
	      FragmentManager manager = getActivity().getSupportFragmentManager();
          FragmentTransaction ft = manager.beginTransaction();
          Fragment newFragment = this;
          this.onDestroy();
          ft.remove(this);
          ft.replace(R.id.content_frame,newFragment);
          ft.addToBackStack(null);   
          ft.commit();
	 
	}
	
	private class ProgressFilter implements ServiceFilter {
		
		@Override
		public void handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback,final ServiceFilterResponseCallback responseCallback) {


			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
			        try {
			        	
					   ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Loading ...", true);
					    
				       ringProgressDialog.setCancelable(false);
				        
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    
				}
			});
			
			nextServiceFilterCallback.onNext(request, new ServiceFilterResponseCallback() {
				
				@Override
				public void onResponse(ServiceFilterResponse response, Exception exception) {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							try {
								
							    ringProgressDialog.dismiss();
							    
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						
						}
					});
					
					if (responseCallback != null)  responseCallback.onResponse(response, exception);
				}
			});
		}
	}


}
