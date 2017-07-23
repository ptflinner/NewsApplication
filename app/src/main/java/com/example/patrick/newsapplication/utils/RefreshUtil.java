package com.example.patrick.newsapplication.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.patrick.newsapplication.R;
import com.example.patrick.newsapplication.data_models.NewsItem;
import com.example.patrick.newsapplication.database_classes.DBHelper;
import com.example.patrick.newsapplication.database_classes.DBUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Patrick on 7/22/2017.
 */

public class RefreshUtil {
    public static final String ACTION_REFRESH ="refresh";
    public static final String TAG="RefreshUtil";


    public static void refreshArticles(Context context){

        ArrayList<NewsItem> results=null;
        URL url= NetworkUtils.buildUrl(context.getResources().getString(R.string.key));

        SQLiteDatabase db=new DBHelper(context).getWritableDatabase();

        //Deletes everything stored in the table then repopulates it
        try{
            DBUtils.deleteAllEntries(db);
            String jsonArticles=NetworkUtils.getResponseFromHttpUrl(url);
            results= JsonUtils.getArticleFromJson(jsonArticles);
            DBUtils.insertToDatabase(db,results);
        }
        catch (IOException e){
            Log.d(TAG,"IO EXCEPTOIN OCCURRED");
            e.printStackTrace();
        }
        catch (JSONException e){
            Log.d(TAG,"JSON EXCEPTION OCCURRED");
            e.printStackTrace();
        }
        finally{
            db.close();
        }
    }
}
