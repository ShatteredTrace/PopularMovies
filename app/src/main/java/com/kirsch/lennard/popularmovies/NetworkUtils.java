package com.kirsch.lennard.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkUtils {
    final String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=";
    final String APIKey = "";

    public static Movie getMovieFromJSON(String json, int index){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray results = jsonObject.getJSONArray("results");
                JSONObject movieJSON = results.getJSONObject(index);

                String poster_path = movieJSON.getString("poster_path");
                boolean adult = movieJSON.getBoolean("adult");
                String overview = movieJSON.getString("overview");
                String release_date = movieJSON.getString("release_date");

                JSONArray genre_idsJSON = movieJSON.getJSONArray("genre_ids");
                int[] genre_ids = new int[genre_idsJSON.length()];
                for (int i = 0; i < genre_idsJSON.length(); i++){
                    genre_ids[i] = genre_idsJSON.getInt(i);
                }

                int id = movieJSON.getInt("id");
                String original_title = movieJSON.getString("original_title");
                String original_language = movieJSON.getString("original_language");
                String title = movieJSON.getString("title");
                String backdrop_path = movieJSON.getString("backdrop_path");
                int popularity = (int) movieJSON.getInt("popularity");
                int vote_count = movieJSON.getInt("vote_count");
                boolean video = movieJSON.getBoolean("video");
                int vote_average = (int) movieJSON.getInt("vote_average");

                return new Movie(poster_path, adult, overview, release_date, genre_ids, id, original_title, original_language
                , title, backdrop_path, popularity, vote_count, video, vote_average);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
    }
}
