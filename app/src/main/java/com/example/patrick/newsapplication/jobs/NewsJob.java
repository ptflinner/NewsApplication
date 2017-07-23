package com.example.patrick.newsapplication.jobs;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.patrick.newsapplication.data_models.NewsItem;
import com.example.patrick.newsapplication.database_classes.DBHelper;
import com.example.patrick.newsapplication.database_classes.DBUtils;
import com.example.patrick.newsapplication.utils.JsonUtils;
import com.example.patrick.newsapplication.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Patrick on 7/22/2017.
 */

public class NewsJob extends JobService{
    AsyncTask mBackGroundTask;
    private static final String TAG="NewsJob";

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mBackGroundTask=new AsyncTask() {

            //Tells the user that the database is going to be refreshed
            @Override
            protected void onPreExecute() {
                Toast.makeText(NewsJob.this, "News Refreshed", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            //Populates the database with items from the news API
            //Calls upon the various important utils for getting/parsing the api info
            @Override
            protected Object doInBackground(Object[] objects) {
                ArrayList<NewsItem> results=null;
                URL url= NetworkUtils.buildUrl("");
                Context context=NewsJob.this;

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
                return null;
            }

            //Job is done. Tells JobManager program has finished executing
            @Override
            protected void onPostExecute(Object o) {
                jobFinished(jobParameters,false);
                super.onPostExecute(o);
            }
        };
        mBackGroundTask.execute();

        return true;
    }

    //Called if system believes the job needs to stop immediately without
    //Calling upon jobFinished
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if(mBackGroundTask!=null)mBackGroundTask.cancel(false);

        return true;
    }
}
