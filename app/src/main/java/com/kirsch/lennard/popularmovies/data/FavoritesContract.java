package com.kirsch.lennard.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoritesContract {

    public static final String AUTHORITY = "com.kirsch.lennard.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVORITES = "favorites";

    public static final class FavoritesEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "favoritesList";
        public static final String COLUMN_MOVIE_ID = "movieID";
        public static final String COLUMN_MOVIE_NAME = "movieName";
    }
}
