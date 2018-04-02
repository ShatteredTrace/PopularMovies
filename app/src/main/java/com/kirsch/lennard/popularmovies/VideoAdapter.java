package com.kirsch.lennard.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class VideoAdapter extends BaseAdapter {
    private Context mContext;
    private Video[] videos;

    public VideoAdapter(Context c, Video[] videos){
        mContext = c;
        this.videos = videos;
    }

    @Override
    public int getCount() {
        return videos.length;
    }

    @Override
    public Object getItem(int index) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if(view == null){
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) view;
        }
        Picasso.with(mContext).load(R.drawable.placeholder).into(imageView);
        return imageView;
    }
}
