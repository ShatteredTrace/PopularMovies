package com.kirsch.lennard.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.kirsch.lennard.popularmovies.MovieUtil.Movie;
import com.kirsch.lennard.popularmovies.ReviewUtil.Review;
import com.kirsch.lennard.popularmovies.VideoUtil.Video;

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
    public static final String MOVIEDB_BASE_MOVIE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String MOVIEDB_BASE_VIDEOS_URL = "/videos?api_key=";
    public static final String MOVIEDB_BASE_REVIEWS_URL = "/reviews?api_key=";
    public static final String MOVIEDB_BASE_DETAIL_URL = "?api_key=";
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

    public static final String JSON_VIDEO_ID_KEY = "id";
    public static final String JSON_VIDEO_ISO_639_1_KEY = "iso_639_1";
    public static final String JSON_VIDEO_ISO_3166_1_KEY = "iso_3166_1";
    public static final String JSON_VIDEO_KEY_KEY = "key";
    public static final String JSON_VIDEO_NAME_KEY = "name";
    public static final String JSON_VIDEO_SITE_KEY = "site";
    public static final String JSON_VIDEO_SIZE_KEY = "size";
    public static final String JSON_VIDEO_TYPE_KEY = "type";

    public static final String JSON_REVIEW_ID_KEY = "id";
    public static final String JSON_REVIEW_AUTHOR_KEY = "author";
    public static final String JSON_REVIEW_CONTENT_KEY = "content";
    public static final String JSON_REVIEW_URL_KEY = "url";

    public static final String JSON_MOVIE_GENRES_KEY = "genres";

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

    public static Movie getMovieDetailFromJSON(String json){
        try {
            JSONObject movieJSON = new JSONObject(json);

            String poster_path = movieJSON.optString(JSON_POSTER_PATH_KEY);
            boolean adult = movieJSON.optBoolean(JSON_ADULT_KEY);
            String overview = movieJSON.optString(JSON_OVERVIEW_KEY);
            String release_date = movieJSON.optString(JSON_RELEASE_DATE_KEY);


                int [] genre_ids = new int[0];


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

    public static Video getVideoFromJSON(JSONObject videoJSON){
        String id = videoJSON.optString(JSON_VIDEO_ID_KEY);
        String iso_639_1 = videoJSON.optString(JSON_VIDEO_ISO_639_1_KEY);
        String iso_3166_1 = videoJSON.optString(JSON_VIDEO_ISO_3166_1_KEY);
        String key = videoJSON.optString(JSON_VIDEO_KEY_KEY);
        String name = videoJSON.optString(JSON_VIDEO_NAME_KEY);
        String site = videoJSON.optString(JSON_VIDEO_SITE_KEY);
        int size = videoJSON.optInt(JSON_VIDEO_SIZE_KEY);
        String type = videoJSON.optString(JSON_VIDEO_TYPE_KEY);

        return new Video(id, iso_639_1, iso_3166_1, key, name, site, size, type);
    }

    public static final Review getReviewFromJSON(JSONObject reviewJSON){
        String id = reviewJSON.optString(JSON_REVIEW_ID_KEY);
        String author = reviewJSON.optString(JSON_REVIEW_AUTHOR_KEY);
        String content= reviewJSON.optString(JSON_REVIEW_CONTENT_KEY);
        String url = reviewJSON.optString(JSON_REVIEW_URL_KEY);

        return new Review(id, author, content, url);
    }

    /**
     * This Method connects to the given URL and retrieves the Data
     * @param url The url from which to retrive Data
     * @return A String of the Data retrieved from theMovieDB
     * @throws IOException required
     */
    public static String getMovieDBData(URL url) throws IOException{
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

    public static URL buildVideosUrl(int movieID){
        URL url = null;
        try {
            url = new URL(MOVIEDB_BASE_MOVIE_URL + movieID + MOVIEDB_BASE_VIDEOS_URL + APIKey);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildReviewsUrl(int movieID){
        URL url = null;
        try {
            url = new URL(MOVIEDB_BASE_MOVIE_URL + movieID + MOVIEDB_BASE_REVIEWS_URL + APIKey);
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

    public static Video[] getAllVideos(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray(JSON_RESULTS_KEY);

            Video[] videos = new Video[results.length()];

            for(int i = 0; i < results.length(); i++){
                videos[i] = getVideoFromJSON(results.getJSONObject(i));
            }

            return videos;

        } catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }
    
    public static Review[] getAllReviews(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray(JSON_RESULTS_KEY);

            Review[] reviews = new Review[results.length()];

            for (int i = 0; i < results.length(); i++){
                reviews[i] = getReviewFromJSON(results.getJSONObject(i));
            }

            return reviews;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method queries a single Movie from theMovieDB and returns it as a new Movie Object
     * @param ID the Movie ID of the movie to query
     * @return The full Movie Object
     */
    public static Movie getMovieFromID(int ID){
        URL url = null;
        String movieJSON = "";
        try {
            url = new URL(MOVIEDB_BASE_MOVIE_URL + ID + MOVIEDB_BASE_DETAIL_URL + APIKey);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        try {
            movieJSON = getMovieDBData(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getMovieDetailFromJSON(movieJSON);
    }

    /**
     * This method checks if the device is connected to the internet and returns the result
     * @return whether or not the device is connected to the internet
     */
    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}