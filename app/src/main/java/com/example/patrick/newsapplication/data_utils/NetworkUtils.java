package com.example.patrick.newsapplication.data_utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Patrick on 6/16/2017.
 */

public class NetworkUtils {
    private final static String TAG="Network Utils";
    private final static String NEWS_BASE_URL = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=";
    private final static String PARAM_QUERY ="source";
    private final static String PARAM_SORT="sortBy";

    //Builds the URL from various queries
    //More queries will be added later possibly
    public static URL buildUrl(String key){

        String apiBase=NEWS_BASE_URL+key;
        Uri builtUri=Uri.parse(apiBase).buildUpon().appendQueryParameter(PARAM_SORT,"latest")
                .appendQueryParameter(PARAM_QUERY,"the-next-web").build();

        URL url= null;

        try{
            String urlString=builtUri.toString();
            Log.d(TAG, "Url: "+urlString);
            url=new URL(builtUri.toString());

        }catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        }
        return url;
    }

    //Receives the JSON from the server/URL request
    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");

            boolean hasInput = input.hasNext();
            if (hasInput) {
                return input.next();
            } else {
                return null;
            }
        }finally{
            urlConnection.disconnect();
        }
    }
}
