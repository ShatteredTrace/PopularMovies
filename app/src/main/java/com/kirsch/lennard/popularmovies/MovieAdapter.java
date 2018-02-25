package com.kirsch.lennard.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
          //  imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
           // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) view;
        }
        Picasso.with(mContext).load(NetworkUtils.MOVIEDB_POSTER_BASE_URL + NetworkUtils.POSTER_SIZE_W185_URL + movies[position].getPosterPath()).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageView);
        return imageView;
    }
}