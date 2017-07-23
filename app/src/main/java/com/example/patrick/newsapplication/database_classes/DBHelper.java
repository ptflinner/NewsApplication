package com.example.patrick.newsapplication.database_classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Patrick on 7/22/2017.
 */

public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="articles.db";
    private static final String TAG="dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryString="CREATE TABLE " + Contract.TABLE_ARTICLES.TABLE_NAME+ " ("+
                Contract.TABLE_ARTICLES._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE+" TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR+" TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION+" TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_WEB_URL+" TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_IMG_URL+" TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED+" DATE "+
                "); ";
    }

    //Used for when the database is updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+Contract.TABLE_ARTICLES.TABLE_NAME+" if exists;");
    }
}
