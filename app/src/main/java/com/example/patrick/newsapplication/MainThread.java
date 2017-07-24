package com.example.patrick.newsapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.patrick.newsapplication.database_classes.Contract;
import com.example.patrick.newsapplication.database_classes.DBHelper;
import com.example.patrick.newsapplication.database_classes.DBUtils;
import com.example.patrick.newsapplication.jobs.SchedulerUtil;

import com.example.patrick.newsapplication.utils.RefreshUtil;

public class MainThread extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Void>,ArticleAdapter.ItemClickListener{

    //Various views that need to be handled
    private ProgressBar loadingProgressBar;
    private RecyclerView jsonRecyclerView;
    private TextView errorTextView;
    private ArticleAdapter articleAdapter;
    private Cursor cursor;
    private SQLiteDatabase db;
    public final static String URLKEY="url";

    private final static String TAG="Main Activity";
    private final static int NEWS_LOADER=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uithread);

        //Sets up the shared preferences
        //Used to check if first time setup has been run
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst=prefs.getBoolean("isFirst",true);

        //Initializes the views
        loadingProgressBar=(ProgressBar) findViewById(R.id.pb_loading_indicator);
        jsonRecyclerView=(RecyclerView) findViewById(R.id.news_recycler_view);
        errorTextView=(TextView)findViewById(R.id.tv_error_message);

        //Creates the layoutmanager for the recycleview pieces
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        jsonRecyclerView.setLayoutManager(layoutManager);
        jsonRecyclerView.setHasFixedSize(true);
        articleAdapter=new ArticleAdapter(cursor,MainThread.this);

        //Checks if it is the first time being run on the phone
        //This is done through the sharedpreference isfirst check
        //done earlier
        if(isFirst){
            load();
            SharedPreferences.Editor editor=prefs.edit();
            editor.putBoolean("isFirst",false);
            editor.commit();
        }

        //Begins the scheduling of the
        SchedulerUtil.scheduleRefresh(this);
    }

    //When the app starts the database is called upon
    //The cursor gets the required info and sends it to the adapter
    @Override
    protected void onStart() {
        super.onStart();
        db=new DBHelper(MainThread.this).getReadableDatabase();
        cursor= DBUtils.getAll(db);
        articleAdapter=new ArticleAdapter(cursor,this);
        jsonRecyclerView.setAdapter(articleAdapter);
    }

    //Everyting is closed when the app is closed
    //No memory problems occur this way
    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }

    //Creates the options menu in the top right
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //Handles what happens when the search is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.search) {
            load();
        }
        return true;
    }


    //Next two functions display the correct pieces
    //or an error message if something went wrong
    private void showJSONResults(){
        errorTextView.setVisibility(View.INVISIBLE);
        jsonRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        errorTextView.setVisibility(View.VISIBLE);
        jsonRecyclerView.setVisibility(View.INVISIBLE);
    }

    //Creates and returns a new loader
    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            //
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                loadingProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                RefreshUtil.refreshArticles(MainThread.this);
                return null;
            }
        };
    }

    //When previous loader is finished
    //it draws info from the database and sends it to the adapter
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        loadingProgressBar.setVisibility(View.INVISIBLE);
        db=new DBHelper(MainThread.this).getReadableDatabase();
        cursor=DBUtils.getAll(db);

        articleAdapter=new ArticleAdapter(cursor,MainThread.this);
        jsonRecyclerView.setAdapter(articleAdapter);
        articleAdapter.notifyDataSetChanged();
    }

    //Previous loader is reset
    @Override
    public void onLoaderReset(Loader<Void> loader) {
    }

    //When an item is clicked a webpage is opened in the users preferred browser
    @Override
    public void onItemClick(int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url=cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_WEB_URL));
        Log.d(TAG,"URL: "+url);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    //Creates the loader manager and begins loading info
    //Loader manager helps manage one or more loaders
    private void load(){
        LoaderManager loaderManager=getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER,null,this).forceLoad();
    }
}
