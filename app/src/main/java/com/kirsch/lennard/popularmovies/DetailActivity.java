package com.kirsch.lennard.popularmovies;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(MainActivity.INTENT_MOVIE_OBJECT_KEY);
        setUpUI(movie);
    }


    private void setUpUI(Movie movie){
        ImageView moviePoster = (ImageView) findViewById(R.id.movie_Poster);
        TextView movieTitle = (TextView) findViewById(R.id.movie_Title);
        TextView movieReleaseDate = (TextView) findViewById(R.id.movie_Release_Date);
        TextView movieVoteAverage = (TextView) findViewById(R.id.movie_Vote_Average);
        TextView moviePlotSynopsis = (TextView) findViewById(R.id.movie_Plot_Synopsis);

        Picasso.with(this).load(NetworkUtils.MOVIEDB_POSTER_BASE_URL + NetworkUtils.POSTER_SIZE_W185_URL + movie.getPoster_path()).into(moviePoster);
        movieTitle.setText(movie.getTitle());
        movieReleaseDate.setText(movie.getRelease_date());
        movieVoteAverage.setText(String.valueOf(movie.getVote_average()));
        moviePlotSynopsis.setText(movie.getOverview());

    }
}
