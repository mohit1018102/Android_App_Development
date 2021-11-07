package com.technomaniacs.android.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.technomaniacs.android.quakereport.utility.QueryUtils;
import com.technomaniacs.android.quakereport.utility.common.Earthquake;
import com.technomaniacs.android.quakereport.utility.common.EarthquakeArrayAdapter;
import com.technomaniacs.android.quakereport.utility.common.EarthquakeLoader;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private EarthquakeArrayAdapter earthquakeArrayAdapter;

    /* Views */
    TextView mEmptyStateTextView;
    View loadingIndicator;
    FloatingActionButton reload;


    /* Handler */
    Handler handler = new Handler(Looper.getMainLooper()) {               // handler associate to main thread looper
        @Override
        public void handleMessage(@NonNull Message msg) {                // handles the message
            super.handleMessage(msg);

            String response = msg.getData().getString("result");
            ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes(response);
            if (earthquakes.size() == 0) {
                mEmptyStateTextView.setVisibility(View.VISIBLE);
                mEmptyStateTextView.setText(R.string.no_record);
            }
            uiUpdate(earthquakes);
            loadingIndicator.setVisibility(View.GONE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();                                       // init Views
        loadEarthQuakeView();


    }

    private void loadEarthQuakeView()
    {
        if(checkConnectivity())                            // check Internet Connectivity
        {
            mEmptyStateTextView.setVisibility(View.GONE);
            loadingIndicator.setVisibility(View.VISIBLE);  //Set Visible till, data loading is completed
            getEarthquakeData();                           // load data
        }
        else
        {    mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }





    private void initViews() {
        mEmptyStateTextView = findViewById(R.id.empty_view);
        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        SharedPreferences sharedPrefs = getSharedPreferences("app",MODE_PRIVATE);
        sharedPrefs.registerOnSharedPreferenceChangeListener(this);

    }


    private boolean checkConnectivity() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {

            return false;
        }

    }



    private String getUrl()
    {
        SharedPreferences sharedPrefs = getSharedPreferences("app",MODE_PRIVATE);
        String minMagnitude = sharedPrefs.getString(
                "minmag",
                "5");

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "100");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", "time");
        return  uriBuilder.toString();
    }


    private void getEarthquakeData()
    {   String url=getUrl();
        Thread thread = new Thread() {                              // worker thread or background thread
            @Override
            public void run() {                                    // can't update ui from here
                super.run();
                String response = QueryUtils.fetchData(url);
                Message msg = new Message();                      // Message creation
                Bundle data = new Bundle();
                data.putString("result", response);
                msg.setData(data);
                handler.sendMessage(msg);                         // push msg to main thread message queue.
            }
        };
        thread.start();
    }




    private void uiUpdate(ArrayList<Earthquake> earthquakes)
    {
        ListView list=findViewById(R.id.list);
        earthquakeArrayAdapter=new EarthquakeArrayAdapter(this,earthquakes);
        list.setAdapter(earthquakeArrayAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!checkConnectivity()) {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                    return;
                }

                Earthquake earthquake=earthquakes.get(position);
                Uri earthquakeUri = Uri.parse(earthquake.getmUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settingmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();


            if (id == R.id.action_settings) {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
            }
            else if(id==R.id.action_reload)
            {
                loadEarthQuakeView();
            }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
       loadEarthQuakeView();
    }
}