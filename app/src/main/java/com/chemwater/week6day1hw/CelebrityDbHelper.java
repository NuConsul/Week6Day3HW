package com.chemwater.week6day1hw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import static com.chemwater.week6day1hw.ProviderContract.COLUMN_ACTOR;
import static com.chemwater.week6day1hw.ProviderContract.COLUMN_MUSICIAN;
import static com.chemwater.week6day1hw.ProviderContract.COLUMN_NAME;
import static com.chemwater.week6day1hw.ProviderContract.COLUMN_POLITICIAN;
import static com.chemwater.week6day1hw.ProviderContract.DATABASE_NAME;
import static com.chemwater.week6day1hw.ProviderContract.DATABASE_VERSION;
import static com.chemwater.week6day1hw.ProviderContract.TABLE_NAME_CELEBS;


public class CelebrityDbHelper extends SQLiteOpenHelper {
    public CelebrityDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION) ;
    }

    public CelebrityDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION) ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createCelebrityTable(db) ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db) ;
    }


    //Create Celebrity Table
    private void createCelebrityTable(SQLiteDatabase db) {
        String createCelebrityTableQuery = " CREATE TABLE " + TABLE_NAME_CELEBS + " ("
                + COLUMN_NAME + " TEXT, "
                + COLUMN_ACTOR + " TEXT, "
                + COLUMN_MUSICIAN + " TEXT, "
                + COLUMN_POLITICIAN + " TEXT)" ;
        db.execSQL(createCelebrityTableQuery) ;
    }

    //insert into Celebrity Table
    public long addNewCelebrity(Celebrities celebrities) {
        SQLiteDatabase database = this.getWritableDatabase() ;
        ContentValues contentValues = new ContentValues() ;

        contentValues.put(COLUMN_NAME, celebrities.getName()) ;
        contentValues.put(COLUMN_ACTOR, celebrities.getActor()) ;
        contentValues.put(COLUMN_MUSICIAN, celebrities.getMusician()) ;
        contentValues.put(COLUMN_POLITICIAN, celebrities.getPolitician()) ;

        return database.insert(TABLE_NAME_CELEBS, null, contentValues) ;
    }

    //update Celebrity Table
    public long viewCelebrity(Celebrities celebrities) {
        SQLiteDatabase database = this.getWritableDatabase() ;
        ContentValues contentValues = new ContentValues() ;
        String whereClause = " WHERE " + COLUMN_NAME + " = \"" + celebrities.getName() + "\"" ;

        contentValues.put(COLUMN_NAME, celebrities.getName()) ;
        contentValues.put(COLUMN_ACTOR, celebrities.getActor()) ;
        contentValues.put(COLUMN_MUSICIAN, celebrities.getMusician()) ;
        contentValues.put(COLUMN_POLITICIAN, celebrities.getPolitician()) ;

        return database.update(TABLE_NAME_CELEBS, contentValues, whereClause, null) ;
    }

    //delete from Celebrity Table
    public long deleteCelebrity(String celebrityName) {
        SQLiteDatabase database = this.getWritableDatabase() ;

        return database.delete(TABLE_NAME_CELEBS, " WHERE " + COLUMN_NAME + " = \"" + celebrityName + "\"", null) ;
    }

    //Retrieve all celebrities
    public ArrayList<Celebrities> viewAllCelebrities() {
        SQLiteDatabase database = this.getReadableDatabase() ;
        String selectionQuery = "SELECT * FROM " + TABLE_NAME_CELEBS ;
        ArrayList<Celebrities> celebritiesArrayList = new ArrayList<>() ;

        Cursor cursor = database.rawQuery(selectionQuery, null) ;

        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString((cursor.getColumnIndex(COLUMN_NAME))) ;
                String actor = cursor.getString((cursor.getColumnIndex(COLUMN_ACTOR))) ;
                String musician = cursor.getString((cursor.getColumnIndex(COLUMN_MUSICIAN))) ;
                String politician = cursor.getString((cursor.getColumnIndex(COLUMN_POLITICIAN))) ;

                celebritiesArrayList.add(new Celebrities(name, actor, musician, politician)) ;
            } while (cursor.moveToNext()) ;
        }

        cursor.close() ;
        return celebritiesArrayList ;
    }

    //Retrieve one celebrity
    public Celebrities retrieveOneCelebrity(String celebrityName) {
        SQLiteDatabase database = this.getReadableDatabase() ;
        String selectionQuery = "SELECT * FROM " + TABLE_NAME_CELEBS + " WHERE " + COLUMN_NAME
                + " = \"" + celebrityName + "\"" ;

        Celebrities returnCelebrity = null ;

        Cursor cursor = database.rawQuery(selectionQuery, null) ;

        if(cursor.moveToFirst()) {
            String name = cursor.getString((cursor.getColumnIndex(COLUMN_NAME))) ;
            String actor = cursor.getString((cursor.getColumnIndex(COLUMN_ACTOR))) ;
            String musician = cursor.getString((cursor.getColumnIndex(COLUMN_MUSICIAN))) ;
            String politician = cursor.getString((cursor.getColumnIndex(COLUMN_POLITICIAN))) ;

            returnCelebrity = new Celebrities(name, actor, musician, politician) ;
        }

        cursor.close() ;
        return  returnCelebrity ;
    }

}
