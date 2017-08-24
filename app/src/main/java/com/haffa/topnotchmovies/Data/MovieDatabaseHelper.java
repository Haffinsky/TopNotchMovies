package com.haffa.topnotchmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rafal on 8/19/2017.
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    public static final String TITLE = "movieTitle";
    public static final String BACKDROP_PATH = "backdropPath";
    public static final String MOVIE_ID = "movieID";
    static final String DATABASE_NAME = "movies.db";
    static final String TABLE_NAME = "movies";
    static final int DATABASE_VERSION = 1;
    static final String ID = "id";
    //tables in the db
    public static final String MOVIE_DETAIL_TABLE_NAME = "moviedetails";
    public static final String POSTER_PATH = "posterPath";
    public static final String OVERVIEW = "overview";
    public static final String VOTE = "vote";

    public static final String CAST_TABLE_NAME = "cast";
    public static final String ACTOR_NAME = "actorName";
    public static final String CHARACTER_NAME = "characterName";
    public static final String GENDER = "gender";
    public static final String PROFILE_PATH = "profilePath";

    public static final String SIMILAR_TABLE_NAME = "similar";
    public static final String FAVORITE_TABLE_NAME = "favorite";

    public static final String SEARCH_RESULTS_TABLE_NAME = "searchresults";

    String SQL_CREATE_SEARCH_RESULTS_TABLE = "CREATE TABLE IF NOT EXISTS " + SEARCH_RESULTS_TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            TITLE + " TEXT UNIQUE, " +
            BACKDROP_PATH + " TEXT NOT NULL, " +
            MOVIE_ID + " TEXT NOT NULL " + " );";

    String SQL_DROP_SEARCH_RESULTS_TABLE = "DROP TABLE IF EXISTS " + SEARCH_RESULTS_TABLE_NAME;

    String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FAVORITE_TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            MOVIE_ID + " TEXT UNIQUE, " +
            BACKDROP_PATH + " TEXT UNIQUE, " +
            TITLE + " TEXT NOT NULL " + " );";

    String SQL_DROP_FAVORITE_TABLE = "DROP TABLE IF EXISTS " + FAVORITE_TABLE_NAME;


    String SQL_CREATE_SIMILAR_TABLE =   "CREATE TABLE " + SIMILAR_TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            POSTER_PATH + " TEXT UNIQUE, " +
            TITLE + " TEXT NOT NULL " + " );";

    String SQL_DROP_SIMILAR_TABLE = "DROP TABLE IF EXISTS " + SIMILAR_TABLE_NAME;

     String SQL_CREATE_CAST_TABLE =   "CREATE TABLE " + CAST_TABLE_NAME + " (" +
             ID + " INTEGER PRIMARY KEY, " +
             ACTOR_NAME + " TEXT UNIQUE, " +
             CHARACTER_NAME + " TEXT NOT NULL, " +
             GENDER + " TEXT NOT NULL, " +
             PROFILE_PATH + " TEXT NOT NULL " + " );";

    String SQL_DROP_CAST_TABLE = "DROP TABLE IF EXISTS " + CAST_TABLE_NAME;


    String SQL_CREATE_DETAIL_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " + MOVIE_DETAIL_TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            TITLE + " TEXT UNIQUE, " +
            BACKDROP_PATH + " TEXT NOT NULL, " +
            OVERVIEW + " TEXT NOT NULL, " +
            MOVIE_ID + " TEXT NOT NULL, " +
            VOTE + " TEXT NOT NULL, " +
            POSTER_PATH + " TEXT NOT NULL " + " );";


    String SQL_DROP_DETAIL_MOVIE_TABLE = "DROP TABLE IF EXISTS " + MOVIE_DETAIL_TABLE_NAME;

    String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            TITLE + " TEXT UNIQUE, " +
            BACKDROP_PATH + " TEXT NOT NULL, " +
            MOVIE_ID + " TEXT NOT NULL " + " );";

    String SQL_DROP_MOVIE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_DETAIL_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CAST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SIMILAR_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SEARCH_RESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_DETAIL_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_CAST_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_SIMILAR_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_FAVORITE_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_SEARCH_RESULTS_TABLE);
        onCreate(sqLiteDatabase);
    }
}
