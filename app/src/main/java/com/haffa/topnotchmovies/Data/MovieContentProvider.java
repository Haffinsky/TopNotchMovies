package com.haffa.topnotchmovies.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Rafal on 8/20/2017.
 */

public class MovieContentProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "com.haffa.topnotchmovies";

    public static final String PATH_MOVIES = "movies";
    public static final int MOVIES = 100;
    public static final int MOVIES_ID = 101;

    public static final String PATH_DETAIL_MOVIES = "moviedetails";
    public static final int DETAIL_MOVIES = 200;
    public static final int DETAIL_MOVIES_ID = 201;

    public static final String PATH_CAST = "cast";
    public static final int CAST = 300;
    public static final int CAST_ID = 301;

    public static final String PATH_SIMILAR = "similar";
    public static final int SIMILAR = 400;
    public static final int SIMILAR_ID = 401;

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private MovieDatabaseHelper movieDatabaseHelper;

    public static UriMatcher buildUriMatcher() {

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES, MOVIES);
        matcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES + "/#", MOVIES_ID);

        matcher.addURI(CONTENT_AUTHORITY, PATH_DETAIL_MOVIES, DETAIL_MOVIES);
        matcher.addURI(CONTENT_AUTHORITY, PATH_DETAIL_MOVIES + "/#", DETAIL_MOVIES_ID);

        matcher.addURI(CONTENT_AUTHORITY, PATH_CAST, CAST);
        matcher.addURI(CONTENT_AUTHORITY, PATH_CAST + "/#", CAST_ID);

        matcher.addURI(CONTENT_AUTHORITY, PATH_SIMILAR, SIMILAR);
        matcher.addURI(CONTENT_AUTHORITY, PATH_SIMILAR + "/#", SIMILAR_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        movieDatabaseHelper = new MovieDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        SQLiteQueryBuilder detailQueryBuilder = new SQLiteQueryBuilder();
        SQLiteQueryBuilder castQueryBuilder = new SQLiteQueryBuilder();
        SQLiteQueryBuilder similarQueryBuilder = new SQLiteQueryBuilder();


        queryBuilder.setTables(MovieDatabaseHelper.TABLE_NAME);
        detailQueryBuilder.setTables(MovieDatabaseHelper.MOVIE_DETAIL_TABLE_NAME);
        castQueryBuilder.setTables(MovieDatabaseHelper.CAST_TABLE_NAME);
        similarQueryBuilder.setTables(MovieDatabaseHelper.SIMILAR_TABLE_NAME);

        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = movieDatabaseHelper.getWritableDatabase();

        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case MOVIES:
                cursor = queryBuilder.query(sqLiteDatabase, strings, s, strings1,
                        null, null, s1);
                break;
            case DETAIL_MOVIES:
                cursor = detailQueryBuilder.query(sqLiteDatabase, strings, s, strings1,
                        null, null, s1);
                break;
            case CAST:
                cursor = castQueryBuilder.query(sqLiteDatabase, strings, s, strings1,
                        null, null, s1);
                break;
            case SIMILAR:
                cursor = similarQueryBuilder.query(sqLiteDatabase, strings, s, strings1,
                        null, null, s1);
                break;
            case MOVIES_ID:
                queryBuilder.appendWhere(MovieDatabaseHelper.ID + "=" + uri.getLastPathSegment());
                break;
            case DETAIL_MOVIES_ID:
                detailQueryBuilder.appendWhere(MovieDatabaseHelper.ID + "=" + uri.getLastPathSegment());
                break;
            case CAST_ID:
                castQueryBuilder.appendWhere(MovieDatabaseHelper.ID + "=" + uri.getLastPathSegment());
                break;
            case SIMILAR_ID:
                similarQueryBuilder.appendWhere(MovieDatabaseHelper.ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int uriType = uriMatcher.match(uri);
        Uri returnUri;
        SQLiteDatabase sqLiteDatabase = movieDatabaseHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case MOVIES:
                id = sqLiteDatabase.insert(MovieDatabaseHelper.TABLE_NAME, null, contentValues);
                returnUri = Uri.parse(PATH_MOVIES + "/" + id);
                break;
            case DETAIL_MOVIES:
                id = sqLiteDatabase.insert(MovieDatabaseHelper.MOVIE_DETAIL_TABLE_NAME, null, contentValues);
                returnUri = Uri.parse(PATH_DETAIL_MOVIES + "/" + id);
                break;
            case CAST:
                id = sqLiteDatabase.insert(MovieDatabaseHelper.CAST_TABLE_NAME, null, contentValues);
                returnUri = Uri.parse(PATH_CAST  + "/" + id);
                break;
            case SIMILAR:
                id = sqLiteDatabase.insert(MovieDatabaseHelper.SIMILAR_TABLE_NAME, null, contentValues);
                returnUri = Uri.parse(PATH_SIMILAR  + "/" + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
