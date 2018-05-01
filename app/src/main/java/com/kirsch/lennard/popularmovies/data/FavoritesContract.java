package com.kirsch.lennard.popularmovies.data;

import android.provider.BaseColumns;

public class FavoritesContract {

    public static final class FavoritesEntry implements BaseColumns{
        public static final String TABLE_NAME = "favoritesList";
        public static final String COLUMN_MOVIE_ID = "movieID";
    }
}
