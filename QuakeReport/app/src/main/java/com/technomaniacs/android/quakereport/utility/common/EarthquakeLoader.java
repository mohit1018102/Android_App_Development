package com.technomaniacs.android.quakereport.utility.common;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.technomaniacs.android.quakereport.utility.QueryUtils;

import java.net.URL;
import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake> > {

    String mUrl;
    public EarthquakeLoader(@NonNull Context context,String url) {
        super(context);
        mUrl=url;
    }


    @Nullable
    @Override
    public ArrayList<Earthquake> loadInBackground() {

        String response=QueryUtils.fetchData(mUrl);
        ArrayList<Earthquake> earthquakes=QueryUtils.extractEarthquakes(response);
        return earthquakes;
    }
}
