package com.chemwater.week6day1hw;

import android.net.Uri;
import android.content.ContentUris ;
import android.provider.BaseColumns ;

public class ProviderContract {
    //Any call made to the content provider we will be using this base URI
    public static final String CONTENT_AUTHORITY = "com.chemwater.week6day1hw" ;
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY) ;


    //Database information
    public static final String DATABASE_NAME = "celebrities_db" ;
    public static final int DATABASE_VERSION = 1 ;

    //Table names
    public static final String TABLE_NAME_CELEBS = "celebrities_table" ;

    //Columns - Celebrities
    public static final String COLUMN_NAME = "name" ;
    public static final String COLUMN_MUSICIAN = "musician" ;
    public static final String COLUMN_ACTOR = "actor" ;
    public static final String COLUMN_POLITICIAN = "politician" ;



    public static final class CelebrityEntry implements BaseColumns {
        //Content URI represents the base location for the table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME_CELEBS).build() ;

        //These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + TABLE_NAME_CELEBS ;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + TABLE_NAME_CELEBS ;

        //Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id) ;
        }
    }


}