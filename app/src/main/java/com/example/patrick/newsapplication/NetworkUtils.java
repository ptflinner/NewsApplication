package com.example.patrick.newsapplication;

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
    private final static String PARAM_QUERY ="";
    private final static String PARAM_SORT="";

    public static URL makeURL(String key,String searchQuery, String sortBy){

        String apiBase=NEWS_BASE_URL+key;
        Uri uri=Uri.parse(apiBase);

        URL url= null;

        try{
            String urlString=uri.toString();
            Log.d(TAG, "Url: "+urlString);
            url=new URL(uri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
        try{
            InputStream in=urlConnection.getInputStream();
            Scanner input=new Scanner(in);

            input.useDelimiter("\\A");
            Log.d(TAG, "Input: "+input);

            return (input.hasNext())?input.next():null;}
        finally{
            urlConnection.disconnect();
        }
    }
}
