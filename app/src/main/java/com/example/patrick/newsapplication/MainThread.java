package com.example.patrick.newsapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainThread extends AppCompatActivity {

    //Various views that need to be handled
    private ProgressBar loadingProgressBar;
    private EditText searchEditText;
    private RecyclerView jsonRecyclerView;
    private TextView errorTextView;
    private ArticleAdapter articleAdapter;
    public final static String URLKEY="url";

    private final static String TAG="Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uithread);

        //Initializes the views
        loadingProgressBar=(ProgressBar) findViewById(R.id.pb_loading_indicator);
        searchEditText=(EditText) findViewById(R.id.search_query);
        jsonRecyclerView=(RecyclerView) findViewById(R.id.news_recycler_view);
        errorTextView=(TextView)findViewById(R.id.tv_error_message);

        //Creates the layoutmanager for the recycleview pieces
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        jsonRecyclerView.setLayoutManager(layoutManager);
        jsonRecyclerView.setHasFixedSize(true);

        articleAdapter=new ArticleAdapter();
        jsonRecyclerView.setAdapter(articleAdapter);
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
            makeNewsSearchQuery();
            return true;
        }
        return onOptionsItemSelected(item);
    }

    //Triggers to call to the API and sets everything into motion
    private void makeNewsSearchQuery(){
        String newsQuery=searchEditText.getText().toString();
        new NetworkTask().execute(newsQuery);
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

    //Class that is used for calls that can't be done on the UIThread
    public class NetworkTask extends AsyncTask<String,Void,ArrayList<Article>> {


        //Turns the progress bar twirl on
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgressBar.setVisibility(View.VISIBLE);
        }

        //Makes calls to the network class to build the URL and subsequently have it retrieve information
        @Override
        protected ArrayList<Article> doInBackground(String... param) {
            String result=null;

            URL newsSearchURL=NetworkUtils.buildUrl(getResources().getString(R.string.key));
            Log.d(TAG, "url: "+newsSearchURL.toString());
            try{
                //Retrieves information from the url
                //Comes in the form of JSON
                result=NetworkUtils.getResponseFromHttpUrl(newsSearchURL);

                //Creates the article list
                ArrayList<Article> articleList=JsonUtils.getArticleFromJson(result);

                return articleList;

            }catch(IOException e){
                Log.d("MainThread","IO Exception Occurred");
                e.printStackTrace();
                return null;
            }
            catch (JSONException e){
                Log.d("MainThread","JSON Exception Occurred");
                e.printStackTrace();
                return null;
            }
        }

        //Begins flipping the switches to display the new information
        //Turns off the progress twirl
        //Displays the info into a clickable piece from the adapter
        @Override
        protected void onPostExecute(final ArrayList<Article> returnedSearchResults) {
            super.onPostExecute(returnedSearchResults);
            loadingProgressBar.setVisibility(View.INVISIBLE);
            if(returnedSearchResults==null){
                showErrorMessage();
            }
            else{
                showJSONResults();
                ArticleAdapter adapter=new ArticleAdapter(returnedSearchResults,new ArticleAdapter.ItemClickListener(){

                    @Override
                    public void onItemClick(int clickedItemIndex) {
                        String url = returnedSearchResults.get(clickedItemIndex).getArticleUrl();
                        Log.d(TAG, String.format("Url %s", url));
                        openWebPage(url);

                        //This commented out piece allows for the url to open in app and not in browser
//                        Intent intent = new Intent(MainThread.this, WebActivity.class);
//                        intent.putExtra(URLKEY, url);
//                        startActivity(intent);
                    }
                });
                jsonRecyclerView.setAdapter(adapter);
            }
        }

        //Opens up a browser on the phone to display the news article
        public void openWebPage(String url) {
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
