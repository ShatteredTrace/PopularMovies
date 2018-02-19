package com.kirsch.lennard.popularmovies;

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkUtils {
    final String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=";
    final String APIKey = "";

    public static void stuff(String json){
            try {
                JSONObject jsonObject = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
}
