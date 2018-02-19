package com.kirsch.lennard.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //    Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
    }

    private void lauchDetailActivity(int position){
        Intent intent = new Intent();
    }

    private void fillGridView(){
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new MovieAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


/*
    Requirements
    TODO #0 - Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.

    TODO #1 - UI contains an element (i.e a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.

    TODO #2 - UI contains a screen for displaying the details for a selected movie.

    TODO #3 - Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.

    User Interface - Function
    TODO #4 - When a user changes the sort criteria (“most popular and highest rated”) the main view gets updated correctly.

    TODO #5 - When a movie poster thumbnail is selected, the movie details screen is launched.

    Network API Implementation
    TODO #6 - In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.


    You app will:

    Present the user with a grid arrangement of movie posters upon launch.
    Allow your user to change sort order via a setting:
        The sort order can be by most popular or by highest-rated

    Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
        original title
        movie poster image thumbnail
        A plot synopsis (called overview in the api)
        user rating (called vote_average in the api)
        release date

 */