package com.jeremyfeinstein.slidingmenu.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class ProcessListFragment extends ListFragment{

		ArrayList<String> list_contents;
		public SharedPreferences processpreferences;

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			
			list_contents = new ArrayList<String>();
			processpreferences = this.getActivity().getSharedPreferences("process_session", Context.MODE_PRIVATE);	
	        
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		
			try {
				
				// Create an intermediate to connect with the Internet
				HttpClient httpClient = new DefaultHttpClient();

				// Sending a GET request to the web page that we want
				// Because of we are sending a GET request, we have to pass the values through the URL
				HttpGet httpGet = new HttpGet("https://processes.blob.core.windows.net/processes/myblob");

				// execute(); executes a request using the default context.
				// Then we assign the execution result to HttpResponse
				HttpResponse httpResponse = httpClient.execute(httpGet);
				System.out.println("httpResponse");

				// getEntity() ; obtains the message entity of this response
				// getContent() ; creates a new InputStream object of the entity.
				// Now we need a readable source to read the byte stream that comes as the httpResponse
				InputStream inputStream = httpResponse.getEntity().getContent();

				// We have a byte stream. Next step is to convert it to a Character stream
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

				// Then we have to wraps the existing reader (InputStreamReader) and buffer the input
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				// InputStreamReader contains a buffer of bytes read from the source stream and converts these into characters as needed.
				//The buffer size is 8K
				//Therefore we need a mechanism to append the separately coming chunks in to one String element
				// We have to use a class that can handle modifiable sequence of characters for use in creating String
				//StringBuilder stringBuilder = new StringBuilder();

				String bufferedStrChunk = null;

				// There may be so many buffered chunks. We have to go through each and every chunk of characters
				//and assign a each chunk to bufferedStrChunk String variable
				//and append that value one by one to the stringBuilder
				while((bufferedStrChunk = bufferedReader.readLine()) != null){
					//stringBuilder.append(bufferedStrChunk);
					if(!bufferedStrChunk.equals(""))
					{
						list_contents.add(bufferedStrChunk.substring(26).replace('(', ' ').replace(')', ' '));
					}
				
				}
	
			} catch (ClientProtocolException cpe) {
				Toast.makeText(getActivity(),"Error: "+cpe.getMessage(),Toast.LENGTH_LONG).show();
	    		cpe.printStackTrace();
			} catch (IOException ioe) {
				Toast.makeText(getActivity(),"Error: "+ioe.getMessage(),Toast.LENGTH_LONG).show();
				ioe.printStackTrace();
			}catch (Exception ioe) {
				Toast.makeText(getActivity(),"Error: "+ioe.getMessage(),Toast.LENGTH_LONG).show();
				ioe.printStackTrace();    		
			}

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
			default : //Optional
			processpreferences.edit().putString("terminate", list_contents.get(position).toString()).commit();
			newFragment = new TerminateProcessFragment();
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
