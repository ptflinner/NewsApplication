package com.example.patrick.newsapplication.jobs;

import com.example.patrick.newsapplication.utils.RefreshUtil;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import android.os.AsyncTask;
import android.widget.Toast;

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
                RefreshUtil.refreshArticles(NewsJob.this);
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
