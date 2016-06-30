package cams.org.androidapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("TEST: ", "OnCreate");
		
		int SDK_INT = android.os.Build.VERSION.SDK_INT;
	    if (SDK_INT > 8) 
	    {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
	                .permitAll().build();
	        StrictMode.setThreadPolicy(policy);

	    }
    	
	    /*final ListView listView = (ListView) findViewById(R.id.mobile_list);
    	String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
    	ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);
      	    	
    	listView.setAdapter(adapter);*/
		
		final Button btn1 = (Button) findViewById(R.id.button1);
		//final TextView text1 = (TextView) findViewById(R.id.textView1);
		final ListView listView = (ListView) findViewById(R.id.mobile_list);
		
		btn1.setOnClickListener(new OnClickListener (){

			@Override
			public void onClick(View v) {
				try {
					String resp = "";
										
					String link = "http://192.168.1.118:10000/user";				
					HttpClient client = new DefaultHttpClient();
					HttpGet request = new HttpGet();
					request.setURI(new URI(link));
					
					HttpResponse response = client.execute(request);
					BufferedReader in = new BufferedReader
					(new InputStreamReader(response.getEntity().getContent()));
					String data = "";
					while ((data = in.readLine()) != null){
						   resp += data + " ";
						}
					
					JSONObject json = new JSONObject(resp);
					JSONArray users = json.optJSONArray("users");
					
					String content [] = new String [users.length()];
					
					for (int i = 0; i < users.length(); i++) {
						JSONObject user = users.getJSONObject(i);
						String name = user.getString("name").toString();
						String lastname = user.getString("lastName").toString();
						
						content[i] = name + " " + lastname;					
					}
					
									
					final ArrayAdapter adapter = new ArrayAdapter <String>(getActivity(),R.layout.activity_listview, content);
					listView.setAdapter(adapter);
					
				} catch (Exception e) {
					Log.d("REQUEST-TRY", Log.getStackTraceString(e));
				}
			}
			
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d("TEST: ", "On Start");
	}

	@Override
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
	}
	
	
	public MainActivity getActivity () {return this;}
	
}
