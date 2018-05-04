package com.kirsch.lennard.popularmovies.MovieUtil;

import android.content.Context;
import android.os.AsyncTask;

import com.kirsch.lennard.popularmovies.AsyncTaskInterface;
import com.kirsch.lennard.popularmovies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDBQueryFavoritesTask extends AsyncTask<URL, Void, Movie[]> {
    private AsyncTaskInterface<Movie[]> listener;
    private ArrayList<Integer> movieIDs;
    private Context context;

    public MovieDBQueryFavoritesTask(Context context, AsyncTaskInterface<Movie[]> listener, ArrayList<Integer> movieIDs){
        this.context = context;
        this.listener = listener;
        this.movieIDs = movieIDs;
    }

    @Override
    protected Movie[] doInBackground(URL... urls) {
        Movie[] movies = new Movie[movieIDs.size()];

            for(int i = 0; i < movieIDs.size(); i++){
                movies[i] = NetworkUtils.getMovieFromID(movieIDs.get(i));
        }
        return movies;
    }

    @Override
    protected void onPostExecute(Movie[] results) {
        listener.onTaskComplete(results);
    }
}