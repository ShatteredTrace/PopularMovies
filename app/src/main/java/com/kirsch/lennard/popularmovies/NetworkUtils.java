package com.kirsch.lennard.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    public static final String MOVIEDB_BASE_POPULARITY_URL = "http://api.themoviedb.org/3/movie/popular?api_key=";
    public static final String MOVIEDB_BASE_AVERAGE_VOTE_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=";
    public static final String APIKey = BuildConfig.API_KEY;
    public static final String MOVIEDB_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String POSTER_SIZE_W185_URL = "w500/";

    public static final String JSON_RESULTS_KEY = "results";
    public static final String JSON_POSTER_PATH_KEY = "poster_path";
    public static final String JSON_ADULT_KEY = "adult";
    public static final String JSON_OVERVIEW_KEY = "overview";
    public static final String JSON_RELEASE_DATE_KEY = "release_date";
    public static final String JSON_GENRE_IDS_KEY = "genre_ids";
    public static final String JSON_ID_KEY = "id";
    public static final String JSON_ORIGINAL_TITLE_KEY = "original_title";
    public static final String JSON_ORIGINAL_LANGUAGE_KEY = "original_language";
    public static final String JSON_TITLE_KEY = "title";
    public static final String JSON_BACKDROP_PATH_KEY = "backdrop_path";
    public static final String JSON_POPULARITY_KEY = "popularity";
    public static final String JSON_VOTE_COUNT_KEY = "vote_count";
    public static final String JSON_VIDEO_KEY = "video";
    public static final String JSON_VOTE_AVERAGE_KEY = "vote_average";

    /**
     * This method created a Movie Object and fills it with Data from
     * the given JSON String
     * @param json the JSON String retrieved from themovieDB
     * @param index the index of the movie to retrieve from the JSON Data
     * @return A Movie Object filled with the retrieved Data
     */
    public static Movie getMovieFromJSON(String json, int index){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray results = jsonObject.getJSONArray(JSON_RESULTS_KEY);
                JSONObject movieJSON = results.getJSONObject(index);

                String poster_path = movieJSON.optString(JSON_POSTER_PATH_KEY);
                boolean adult = movieJSON.optBoolean(JSON_ADULT_KEY);
                String overview = movieJSON.optString(JSON_OVERVIEW_KEY);
                String release_date = movieJSON.optString(JSON_RELEASE_DATE_KEY);

                JSONArray genre_idsJSON = movieJSON.optJSONArray(JSON_GENRE_IDS_KEY);
                int[] genre_ids = new int[genre_idsJSON.length()];
                for (int i = 0; i < genre_idsJSON.length(); i++){
                    genre_ids[i] = genre_idsJSON.getInt(i);
                }

                int id = movieJSON.optInt(JSON_ID_KEY);
                String original_title = movieJSON.optString(JSON_ORIGINAL_TITLE_KEY);
                String original_language = movieJSON.optString(JSON_ORIGINAL_LANGUAGE_KEY);
                String title = movieJSON.optString(JSON_TITLE_KEY);
                String backdrop_path = movieJSON.optString(JSON_BACKDROP_PATH_KEY);
                int popularity = (int) movieJSON.optInt(JSON_POPULARITY_KEY);
                int vote_count = movieJSON.optInt(JSON_VOTE_COUNT_KEY);
                boolean video = movieJSON.optBoolean(JSON_VIDEO_KEY);
                float vote_average = BigDecimal.valueOf(movieJSON.optDouble(JSON_VOTE_AVERAGE_KEY)).floatValue();

                return new Movie(poster_path, adult, overview, release_date, genre_ids, id, original_title, original_language
                , title, backdrop_path, popularity, vote_count, video, vote_average);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
    }

    /**
     * This Method connects to the given URL and retrieves the Data
     * @param url The url from which to retrive Data
     * @return A String of the Movie Data retrieved from theMovieDB
     * @throws IOException required
     */
    public static String getMovieData(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }
            else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * This method builds the URL from which to retrieve data
     * @param sortByPopularity weather or not to sort by popularity. If the Value is false,
     *                         it will be sorted by average vote.
     * @return the bult URL
     */
    public static URL buildUrl(boolean sortByPopularity){
        URL url = null;
        try {
            if(sortByPopularity) {
                url = new URL(MOVIEDB_BASE_POPULARITY_URL + APIKey);
            } else {
                url = new URL(MOVIEDB_BASE_AVERAGE_VOTE_URL + APIKey);
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method gets all Movie Objects from a given JSON String
     * @param json The JSON String containing Data for Movies from themovieDB
     * @return An array of Movie Objects, filled with Data
     */
    public static Movie[] getAllMovies(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray(JSON_RESULTS_KEY);

            Movie[] movies = new Movie[results.length()];

            for (int i = 0; i < results.length(); i++){
                movies[i] = getMovieFromJSON(json, i);
            }
            return movies;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
