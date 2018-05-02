package com.kirsch.lennard.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.kirsch.lennard.popularmovies.MovieUtil.Movie;
import com.kirsch.lennard.popularmovies.MovieUtil.MovieAdapter;
import com.kirsch.lennard.popularmovies.MovieUtil.MovieDBQueryFavoritesTask;
import com.kirsch.lennard.popularmovies.MovieUtil.MovieDBQueryTask;
import com.kirsch.lennard.popularmovies.data.FavoritesContract;
import com.kirsch.lennard.popularmovies.data.FavoritesDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String INTENT_MOVIE_OBJECT_KEY = "movieObject";
    private boolean sortByPopularity = true;
    private SQLiteDatabase mdB;
    FavoritesDbHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDB();

        queryMovieDB();
    }

    /**
    This method launches the DetailActivity which displays details about a Movie
    @param movie the Movie to display in Detail
     */
    private void lauchDetailActivity(Movie movie){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(INTENT_MOVIE_OBJECT_KEY, movie );
        startActivity(intent);
    }

    /**
     * This method fills the Gridview of the Main Screen with movie-posters
     * @param movies the Array of movies whose Posters to display
     */
    private void fillGridView(final Movie[] movies){
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new MovieAdapter(this, movies));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                lauchDetailActivity(movies[position]);
            }
        });
    }

    public class MovieDBQueryTaskListener implements AsyncTaskInterface<String> {
        @Override
        public void onTaskComplete(String results) {
            if (results != null && !results.equals("")) {
                Movie[] movies = NetworkUtils.getAllMovies(results);
                fillGridView(movies);
            }
        }
    }

    public class MovieDBQueryFavoritesTaskListener implements AsyncTaskInterface<Movie[]> {
        @Override
        public void onTaskComplete(Movie[] result) {
            if(result != null){
                fillGridView(result);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.settings_main_sort_popularity){
            if (!sortByPopularity){
                sortByPopularity = true;
                queryMovieDB();
            } else {
                queryMovieDB();
            }

        } else if( id == R.id.settings_main_sort_vote){
            if(sortByPopularity){
                sortByPopularity = false;
                queryMovieDB();
            } else {
                queryMovieDB();
            }
        } else if( id == R.id.settings_main_favorites){
            queryFavorites();
        }
        return super.onOptionsItemSelected(item);
    }

    public void queryMovieDB(){
        if(NetworkUtils.isConnectedToInternet(this)){
            new MovieDBQueryTask(this, sortByPopularity, new MovieDBQueryTaskListener()).execute();
        }
        else{
            //TODO What to do in case of no network connection
        }
    }

    public void queryFavorites(){
        Cursor cursor = getAllFavorites();
        ArrayList<Integer> movieIDs = new ArrayList<>();
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID));
                    movieIDs.add(id);
                } while (cursor.moveToNext());
            }else {
                Toast.makeText(this, "No Favorites saved", Toast.LENGTH_SHORT).show();
            }
            if (NetworkUtils.isConnectedToInternet(this)) {
                new MovieDBQueryFavoritesTask(this, new MovieDBQueryFavoritesTaskListener(), movieIDs).execute();
            }
        }
    }

    public void setupDB(){
        dbHelper = new FavoritesDbHelper(this);
        mdB = dbHelper.getWritableDatabase();
    }

    private Cursor getAllFavorites(){
        return mdB.query(FavoritesContract.FavoritesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FavoritesContract.FavoritesEntry._ID);
    }
}

//region Requirements STAGE 1
/*
    COMPLETED #0 - Movies are displayed in the main layout via a grid of their corresponding movie
    poster thumbnails.

    COMPLETED #1 - UI contains an element (i.e a spinner or settings menu) to toggle the sort order
    of the movies by: most popular, highest rated.

    COMPLETED #2 - UI contains a screen for displaying the details for a selected movie.

    COMPLETED #3 - Movie details layout contains title, release date, movie poster, vote average,
    and plot synopsis.

    User Interface - Function
    COMPLETED #4 - When a user changes the sort criteria (“most popular and highest rated”) the
    main view gets updated correctly.

    COMPLETED #5 - When a movie poster thumbnail is selected, the movie details screen is launched.

    Network API Implementation
    COMPLETED #6 - In a background thread, app queries the /movie/popular or /movie/top_rated API
    for the sort criteria specified in the settings menu.


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
//endregion

//region Requirements STAGE 2
/*  TODOs - USER INTERFACE - LAYOUT

    COMPLETED #7 - Movie Details layout contains a section for displaying trailer videos and user reviews.

    TODOs - USER INTERFACE - FUNCTION

    COMPLETED #8 - When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.

    COMPLETED #9 - When a trailer is selected, app uses an Intent to launch the trailer.

    COMPLETED #10 - In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite.


    TODOs - NETWORK API IMPLEMENTATION

    COMPLETED #11 - App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie.
1
    COMPLETED #120 - App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.

    TODOs - DATA PERSISTENCE

    COMPLETED #13 - The titles and IDs of the user’s favorite movies are stored in a native SQLite database and are exposed via a ContentProvider. This ContentProvider is updated whenever the user favorites or unfavorites a movie. No other persistence libraries are used.

    COMPLETED #14 - When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the ContentProvider.


    SUGGESTIONS

    OPT - Extend the favorites ContentProvider to store the movie poster, synopsis, user rating, and release date, and display them even when offline.

    OPT - Implement sharing functionality to allow the user to share the first trailer’s YouTube URL from the movie details screen.

*/
//endregion