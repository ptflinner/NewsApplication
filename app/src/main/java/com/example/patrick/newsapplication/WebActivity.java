package com.example.patrick.newsapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        String url="http://www.google.com";

        Intent intent=getIntent();
        if(intent.hasExtra(MainThread.URLKEY)){
            url=intent.getStringExtra(MainThread.URLKEY);
        }
        WebView myWebView=(WebView) findViewById(R.id.article_web_view);
        myWebView.loadUrl(url);
    }

}
