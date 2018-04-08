package com.kirsch.lennard.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kirsch.lennard.popularmovies.MovieUtil.Movie;
import com.kirsch.lennard.popularmovies.VideoUtil.MovieDBQueryVideosTask;
import com.kirsch.lennard.popularmovies.VideoUtil.Video;
import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.movie_Poster) ImageView moviePoster;
    @BindView(R.id.movie_Title) TextView movieTitle;
    @BindView(R.id.movie_Release_Date_Value) TextView movieReleaseDateValue;
    @BindView(R.id.movie_Plot_Synopsis) TextView moviePlotSynopsis;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.videos_linear) LinearLayout videosLinear;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        context = this;

        Intent intent = getIntent();
        Movie movie = intent.getExtras().getParcelable(MainActivity.INTENT_MOVIE_OBJECT_KEY);

        queryExtraData(this, movie);

        setUpUI(movie);
    }

    /**
     * This method fills the Views with the details of a Movie
     * @param movie the Movie whose Details to display
     */
    private void setUpUI(Movie movie){

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
        String newDate = " ";
        String[] split = releaseDate.split("-");
        newDate += new DateFormatSymbols().getMonths()[Integer.parseInt(split[1]) - 1];
        newDate += " " + split[0];
        return newDate;
    }

    public void queryExtraData(Context context, Movie movie){
        if(NetworkUtils.isConnectedToInternet(this)){
            new MovieDBQueryVideosTask(this, movie.getId(), new MovieDBQueryVideosTaskListener()).execute();
        }
        else{
            //TODO What to do in case of no network connection
        }
    }

    public class MovieDBQueryVideosTaskListener implements AsyncTaskInterface<String> {
        @Override
        public void onTaskComplete(String results) {
            if (results != null && !results.equals("")) {
                Video[] videos = NetworkUtils.getAllVideos(results);
                fillVideosView(videos);
            }
        }
    }

    private void fillVideosView(final Video[] videos){
        for (int i = 0; i < videos.length && i < 4; i++){
            final int pos = i;
            ImageView imageView = new ImageView(this);
            imageView.setAdjustViewBounds(true);
            Picasso.with(this).load(R.drawable.play_video).into(imageView);
            videosLinear.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startVideoViewer(videos[pos]);
                }
            });
        }


    }

    private void startVideoViewer(Video video){
        Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + video.getKey());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}