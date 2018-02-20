package com.kirsch.lennard.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(MainActivity.INTENT_MOVIE_OBJECT_KEY);
        setUpUI(movie);
    }

<<<<<<< HEAD
    /*
    This method is used to fill the
     */
=======

>>>>>>> parent of 8cd3a96... Cleanup 1.0
    private void setUpUI(Movie movie){
        ImageView moviePoster = (ImageView) findViewById(R.id.movie_Poster);
        TextView movieTitle = (TextView) findViewById(R.id.movie_Title);
        TextView movieReleaseDateValue = (TextView) findViewById(R.id.movie_Release_Date_Value);
        TextView moviePlotSynopsis = (TextView) findViewById(R.id.movie_Plot_Synopsis);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        Picasso.with(this).load(NetworkUtils.MOVIEDB_POSTER_BASE_URL + NetworkUtils.POSTER_SIZE_W185_URL + movie.getPoster_path()).into(moviePoster);
        movieTitle.setText(movie.getTitle());
        movieReleaseDateValue.setText(restructureReleaseDate(movie.getRelease_date()));
        moviePlotSynopsis.setText(movie.getOverview());
        ratingBar.setRating(movie.getVote_average() / 2);

    }

    private String restructureReleaseDate(String releaseDate){
        String newDate = "";
        String[] split = releaseDate.split("-");
        newDate += new DateFormatSymbols().getMonths()[Integer.parseInt(split[1]) - 1];
        newDate += " " + split[0];
        return newDate;
    }
}
