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
       // Movie movie = (Movie) intent.getSerializableExtra(MainActivity.INTENT_MOVIE_OBJECT_KEY);
        Movie movie = intent.getExtras().getParcelable(MainActivity.INTENT_MOVIE_OBJECT_KEY);
        setUpUI(movie);
    }

    /**
     * This method fills the Views with the details of a Movie
     * @param movie the Movie whose Details to display
     */
    private void setUpUI(Movie movie){
        ImageView moviePoster = findViewById(R.id.movie_Poster);
        TextView movieTitle = findViewById(R.id.movie_Title);
        TextView movieReleaseDateValue = findViewById(R.id.movie_Release_Date_Value);
        TextView moviePlotSynopsis = findViewById(R.id.movie_Plot_Synopsis);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        Picasso.with(this).load(NetworkUtils.MOVIEDB_POSTER_BASE_URL + NetworkUtils.POSTER_SIZE_W185_URL + movie.getPosterPath()).into(moviePoster);
        movieTitle.setText(movie.getTitle());
        movieReleaseDateValue.setText(restructureReleaseDate(movie.getReleaseDate()));
        moviePlotSynopsis.setText(movie.getOverview());
        ratingBar.setRating(movie.getVoteAverage() / 2);

    }

    /**
     *  This method changes the Date to a structure better suited for presentation
     * @param releaseDate a String of the Date in the Format retrieved from themovieDB (yyyy-mm-dd)
     * @return the Data as a pretty printed String
     */
    private String restructureReleaseDate(String releaseDate){
        String newDate = "";
        String[] split = releaseDate.split("-");
        newDate += new DateFormatSymbols().getMonths()[Integer.parseInt(split[1]) - 1];
        newDate += " " + split[0];
        return newDate;
    }
}