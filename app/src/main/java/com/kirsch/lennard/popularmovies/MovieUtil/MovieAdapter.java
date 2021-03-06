package com.kirsch.lennard.popularmovies.MovieUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kirsch.lennard.popularmovies.MovieUtil.Movie;
import com.kirsch.lennard.popularmovies.NetworkUtils;
import com.kirsch.lennard.popularmovies.R;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends BaseAdapter{
    private Context mContext;
    private Movie[] movies;

    public MovieAdapter(Context c, Movie[] movies){
        mContext = c;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int index) {
        return null;
    }

    @Override
    public long getItemId(int index) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) view;
        }
        Picasso.with(mContext).load(NetworkUtils.MOVIEDB_POSTER_BASE_URL + NetworkUtils.POSTER_SIZE_W185_URL + movies[position].getPosterPath()).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageView);
        return imageView;
    }
}