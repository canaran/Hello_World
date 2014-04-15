package com.example.hello_world;
import java.net.*;
import java.io.*;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.*;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.*;
import android.app.*;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.*;


public class MainActivity extends ActionBarActivity {
	TextView t;
	String res;
	Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.fragment_main);
        Example e = new Example();
        e.execute();
        
        
        spinner = (Spinner) findViewById(R.id.spinner1);
     // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.day, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        
        
        t = (TextView) findViewById(R.id.textView1);
        //System.out.println(t.getId());
        res = "-1";
        try {
        	res = e.get();
        } catch(Exception e1) {
        	e1.printStackTrace();
        }
        spinner.setOnItemSelectedListener(new SpinnerActivity());
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        
        /*res = getDayTabldotMenu("Tuesday", res);
        t.setText(res);*/
        //t.showContextMenu();
        //final Dialog d = new Dialog(MainActivity.this);
    }

    public class SpinnerActivity implements OnItemSelectedListener {
        
        public void onItemSelected(AdapterView<?> parent, View view, 
                int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        	System.out.println("Selected");
        	System.out.println(String.valueOf(parent.getItemAtPosition(pos)));
        	String a;
        	if(String.valueOf(parent.getItemAtPosition(pos)).compareTo("Monday") == 0) {
        		a = getDayTabldotMenu("Monday", res);
                t.setText(a);
        	}else if(String.valueOf(parent.getItemAtPosition(pos)).compareTo("Tuesday") == 0) {
        		a = getDayTabldotMenu("Tuesday", res);
                t.setText(a);
        	}else if(String.valueOf(parent.getItemAtPosition(pos)).compareTo("Wednesday") == 0) {
        		a = getDayTabldotMenu("Wednesday", res);
                t.setText(a);
        	}else if(String.valueOf(parent.getItemAtPosition(pos)).compareTo("Thursday") == 0) {
        		a = getDayTabldotMenu("Thursday", res);
                t.setText(a);
        	}else if(String.valueOf(parent.getItemAtPosition(pos)).compareTo("Friday") == 0) {
        		a = getDayTabldotMenu("Friday", res);
                t.setText(a);
        	}else if(String.valueOf(parent.getItemAtPosition(pos)).compareTo("Saturday") == 0) {
        		a = getDayTabldotMenu("Saturday", res);
                t.setText(a);
        	}else if(String.valueOf(parent.getItemAtPosition(pos)).compareTo("Sunday") == 0) {
        		a = getDayTabldotMenu("Sunday", res);
                t.setText(a);
        	}
        		
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        	System.out.println("Not Selected");
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    
    
    
    private class Example extends AsyncTask<Void, Void, String> {
    	String result,url;
    	ProgressDialog pdLoading;
    	
    	TextView t;
    	
    	Example() {
    		result ="";
    		url = "http://kafemud.bilkent.edu.tr/monu_eng.html";
    		pdLoading = new ProgressDialog(MainActivity.this);
    	}
    	protected void onPreExecute() {
    		super.onPreExecute();
    		 pdLoading.setMessage("\tLoading...");
    	     pdLoading.show();
    	}
    	
        @Override
        protected String doInBackground(Void... params) {
            try {
                result = getResults(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
            
        }

        @Override
        protected void onPostExecute(String res) {  
        	super.onPostExecute(res);
        	 //pdLoading.setMessage(result);
             //pdLoading.show();
        	
        	//System.out.println(result);
        	pdLoading.dismiss();
        
            // Displays the HTML string in the UI via a WebView
            
        }
        
    }
    public String getResults(String url) throws IOException{
    	URL u = new URL( url);// Construct the URL
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();			// Open the connection
		conn.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));	// Get the stream

		// Construct the string to return from the reponse
		StringBuilder response = new StringBuilder();
		String line = "";
		while ((line = br.readLine()) != null)
		    response.append(line+"\n");

		br.close();				// Close the reader
		conn.disconnect();			// Close the connection
		return response.toString();
    }
    
    private String getDayTabldotMenu(String day, String htmlResponse) {
    	String str = htmlResponse.substring(htmlResponse.indexOf(day));
    	String str2 = str.substring(str.indexOf("<i>")+3);
    	String result = str2.substring(0, str2.indexOf("<")).trim();
    	
    	return result;
    }

}
