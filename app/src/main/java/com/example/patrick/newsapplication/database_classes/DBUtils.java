package com.example.patrick.newsapplication.database_classes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.patrick.newsapplication.data_models.NewsItem;

import java.util.ArrayList;


import static com.example.patrick.newsapplication.database_classes.Contract.TABLE_ARTICLES.*;

/**
 * Created by Patrick on 7/22/2017.
 */

public class DBUtils {

    private static final String TAG="DBUtils";

    //Gets everything from the database for the cursor to use
    public static Cursor getAll(SQLiteDatabase db){
        Cursor cursor= db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED);
        return cursor;
    }

    //Inserts everything in the arraylist that was parsed earlier into the databse
    //Exception is thrown if something goes wrong
    public static void insertToDatabase(SQLiteDatabase db, ArrayList<NewsItem> articles){

        db.beginTransaction();
        try{
            for(NewsItem item: articles){
                ContentValues cv=new ContentValues();
                cv.put(COLUMN_NAME_AUTHOR,item.getArticleAuthor());
                cv.put(COLUMN_NAME_TITLE,item.getArticleTitle());
                cv.put(COLUMN_NAME_DESCRIPTION,item.getArticleDescription());
                cv.put(COLUMN_NAME_WEB_URL,item.getArticleUrl());
                cv.put(COLUMN_NAME_IMG_URL,item.getArticleImage());
                cv.put(COLUMN_NAME_PUBLISHED,item.getArticlePublishDate());
            }
            db.setTransactionSuccessful();
        }catch(Exception e){
            Log.d(TAG,"SQL Exception Has OCcurred");
            Thread.currentThread().getStackTrace();
        }
        finally{
            db.endTransaction();
            db.close();
        }
    }

    //Deletes everything from the database
    public static void deleteAllEntries(SQLiteDatabase db){
        db.delete(TABLE_NAME,null,null);
    }
}
