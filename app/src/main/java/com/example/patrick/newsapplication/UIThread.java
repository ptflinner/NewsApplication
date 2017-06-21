package com.example.patrick.newsapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

public class UIThread extends AppCompatActivity {

    private ProgressBar loadingProgressBar;
    private EditText searchEditText;
    private TextView JSONTextView;
    private TextView errorTextView;
    private final static String TAG="Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uithread);

        loadingProgressBar=(ProgressBar) findViewById(R.id.pb_loading_indicator);
        searchEditText=(EditText) findViewById(R.id.search_query);
        JSONTextView=(TextView) findViewById(R.id.tv_search_results_json);
        errorTextView=(TextView)findViewById(R.id.tv_error_message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.search) {
            makeNewsSearchQuery();
            return true;
        }
        return onOptionsItemSelected(item);
    }

    private void makeNewsSearchQuery(){
        String newsQuery=searchEditText.getText().toString();
        new NetworkTask().execute("");
    }

    private void showJSONResults(){
        errorTextView.setVisibility(View.INVISIBLE);
        JSONTextView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        errorTextView.setVisibility(View.VISIBLE);
        JSONTextView.setVisibility(View.INVISIBLE);
    }
    public class NetworkTask extends AsyncTask<String,Void,String[]> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... param) {
            String result=null;

            URL newsSearchURL=NetworkUtils.buildUrl(getResources().getString(R.string.key));
            Log.d(TAG, "url: "+newsSearchURL.toString());
            try{
                result=NetworkUtils.getResponseFromHttpUrl(newsSearchURL);

                String jsonNewsData[]=NewsJsonUtils.getNewsStringFromJson(result);
                return jsonNewsData;
            }catch(IOException e){
                Log.d("UIThread","IO Exception Occurred");
                e.printStackTrace();
                return null;
            }
            catch (JSONException e){
                Log.d("UIThread","JSON Exception Occurred");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] returnedSearchResults) {
            super.onPostExecute(returnedSearchResults);
            loadingProgressBar.setVisibility(View.INVISIBLE);
            if(returnedSearchResults==null){
                showErrorMessage();
            }
            else{
                showJSONResults();
                for(String articleString:returnedSearchResults){
                    JSONTextView.append((articleString+"\n\n\n"));
                }
            }

        }
    }
}
