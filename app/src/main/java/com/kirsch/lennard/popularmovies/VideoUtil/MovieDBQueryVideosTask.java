package com.kirsch.lennard.popularmovies.VideoUtil;

import android.content.Context;
import android.os.AsyncTask;

import com.kirsch.lennard.popularmovies.AsyncTaskInterface;
import com.kirsch.lennard.popularmovies.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MovieDBQueryVideosTask extends AsyncTask<URL, Void, String> {
    private AsyncTaskInterface<String> listener;

    private Context context;
    private int movieID;

    public MovieDBQueryVideosTask(Context context, int movieID, AsyncTaskInterface<String> listener){
        this.context = context;
        this.listener = listener;
        this.movieID = movieID;
    }

    @Override
    protected String doInBackground(URL... urls) {
        String results = null;

        try{
            results = NetworkUtils.getMovieDBData(NetworkUtils.buildVideosUrl(movieID));
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
