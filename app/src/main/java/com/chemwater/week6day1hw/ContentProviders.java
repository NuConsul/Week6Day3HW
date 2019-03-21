package com.chemwater.week6day1hw;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.MultiAutoCompleteTextView;


public class ContentProviders extends ContentProvider {

    //Data persistence object
    CelebrityDbHelper celebrityDbHelper ;

    //paths switch values
    public static final int NAME = 500 ;
    public static final int NAME_ID = 501 ;

    //URI Matcher
    private static final UriMatcher sUriMatcher = buildUriMatcher() ;

    public static UriMatcher buildUriMatcher() {
        String content = ProviderContract.CONTENT_AUTHORITY ;

        //All paths to the UriMatcher have a corresponding code to return
        //when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH) ;
        matcher.addURI(content, ProviderContract.TABLE_NAME_CELEBS, NAME) ;
        matcher.addURI(content, ProviderContract.TABLE_NAME_CELEBS + "/#", NAME_ID) ;

        return matcher ;
    }

    @Override
    public boolean onCreate() {
        celebrityDbHelper = new CelebrityDbHelper(getContext());
        return true ;
    }

    @Nullable
    @Override
    public Cursor query(@Nullable Uri uri,@Nullable String[] projection, @Nullable String selection,@Nullable String[] selectionargs, @Nullable String sortOrder) {
        final SQLiteDatabase db = celebrityDbHelper.getWritableDatabase() ;
        Cursor retCursor ;
        long _id ;
        switch(sUriMatcher.match(uri)) {
            case NAME:
                retCursor = db.query(
                        ProviderContract.TABLE_NAME_CELEBS,
                        projection,
                        selection,
                        selectionargs,
                        null, null,
                        sortOrder
                ) ;
                break ;

            case NAME_ID:
                _id = ContentUris.parseId(uri) ;
                retCursor = db.query(ProviderContract.TABLE_NAME_CELEBS,
                        projection, ProviderContract.COLUMN_NAME + "=?",
                        new String[]{String.valueOf(_id)}, null, null, sortOrder) ;
                break ;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri) ;
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri) ;
        return retCursor ;
    }

    @Nullable
    @Override
    public String getType(@Nullable Uri uri) {
        switch(sUriMatcher.match(uri)) {
            case NAME:
                return ProviderContract.CelebrityEntry.CONTENT_TYPE ;

            case NAME_ID:
                return ProviderContract.CelebrityEntry.CONTENT_ITEM_TYPE ;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri) ;

        }
    }


    @Nullable
    @Override
    public Uri insert(@Nullable Uri uri,@Nullable ContentValues values) {
        final SQLiteDatabase db = celebrityDbHelper.getWritableDatabase() ;
        long _id ;
        Uri returnUri ;

        switch(sUriMatcher.match(uri)) {
            case NAME:
                _id = db.insert(ProviderContract.TABLE_NAME_CELEBS, null, values) ;
                if(_id > 0) {
                    returnUri = ProviderContract.CelebrityEntry.buildMovieUri(_id) ;
                } else {
                    throw new UnsupportedOperationException("Unable to insert row into: " + uri) ;
                }
                break ;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri) ;

        }

        getContext().getContentResolver().notifyChange(uri, null) ;
        return returnUri ;
    }



    @Override
    public int delete(@Nullable  Uri uri, @Nullable String selection,@Nullable String[] selectionArgs) {
        final SQLiteDatabase db = celebrityDbHelper.getWritableDatabase() ;
        int rowDeleted = 0 ;
        switch(sUriMatcher.match(uri)) {
            case NAME:
                rowDeleted = db.delete(ProviderContract.TABLE_NAME_CELEBS, selection, selectionArgs) ;
                break ;

            default:
                throw new UnsupportedOperationException("Unkown uri: " + uri) ;
        }
        if(selection == null || rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null) ;
        }
        return rowDeleted ;
    }



    @Override
    public int update(@Nullable Uri uri, @Nullable ContentValues values,@Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = celebrityDbHelper.getWritableDatabase() ;
        int rows ;

        switch(sUriMatcher.match(uri)) {
            case NAME:
                rows = db.update(ProviderContract.TABLE_NAME_CELEBS, values, selection, selectionArgs) ;
                break ;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri) ;
        }

        if(rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null) ;
        }

        return rows ;
    }



}