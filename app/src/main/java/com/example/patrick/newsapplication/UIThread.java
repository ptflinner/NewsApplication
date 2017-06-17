package com.example.patrick.newsapplication;

import android.os.AsyncTask;
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

import java.io.IOException;
import java.net.URL;

public class UIThread extends AppCompatActivity {

    private ProgressBar progress;
    private EditText search;
    private TextView textView;
    private final static String TAG="Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uithread);

        progress=(ProgressBar) findViewById(R.id.progressBar);
        search=(EditText) findViewById(R.id.searchQuery);
        textView=(TextView) findViewById(R.id.displayJSON);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber=item.getItemId();

        if(itemNumber==R.id.search){

//            String s=search.getText().toString();
//            NetworkTask task=new NetworkTask(s);
//            task.execute();
            String textShow="Search Clicked";
            Toast.makeText(this,textShow, Toast.LENGTH_SHORT).show();
            return true;
        }
        return onOptionsItemSelected(item);
    }


    public class NetworkTask extends AsyncTask<URL,Void,String> {
        String query;

        NetworkTask(String query){
            this.query=query;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            query = search.getText().toString();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String result=null;
            URL url=NetworkUtils.makeURL(query,"stars");
            Log.d(TAG, "url: "+url.toString());
            try{
                result=NetworkUtils.getResponsePromptUrl(url);
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.setVisibility(View.GONE);
            if(s==null){
                textView.setText("Sorry, no text was received");
            }
            else{
                textView.setText(s);
            }

        }
    }
}
