package com.kirsch.lennard.popularmovies.MovieUtil;

import android.content.Context;
import android.os.AsyncTask;

import com.kirsch.lennard.popularmovies.AsyncTaskInterface;
import com.kirsch.lennard.popularmovies.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MovieDBQueryTask extends AsyncTask<URL, Void, String> {
    private AsyncTaskInterface<String> listener;

    private boolean sortByPopularity = true;
    private Context context;

    public MovieDBQueryTask(Context context, Boolean sortByPopularity, AsyncTaskInterface<String> listener){
        this.sortByPopularity = sortByPopularity;
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(URL... urls) {
        String results = null;

        try{
            results = NetworkUtils.getMovieDBData(NetworkUtils.buildUrl(sortByPopularity));
        } catch (IOException e){
            e.printStackTrace();
        }
        return results;
    }

    @Override
    protected void onPostExecute(String results) {
        listener.onTaskComplete(results);
    }
}
