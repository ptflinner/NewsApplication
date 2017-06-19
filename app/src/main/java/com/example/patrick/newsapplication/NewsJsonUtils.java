package com.example.patrick.newsapplication;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Patrick on 6/19/2017.
 */

public class NewsJsonUtils {
    private final static String RN_ARTICLE="articles";
    private final static String RN_NO_ERROR="ok";
    private final static String RN_ERROR="error";
    private final static String RN_TITLE="title";
    private final static String RN_AUTHOR="author";

    public static String[] getNewsStringFromJson(String newsJsonString)throws JSONException{
        String parsedNewsInformation[]=null;
        JSONObject newsJson=new JSONObject(newsJsonString);
        JSONArray newsArray=newsJson.getJSONArray(RN_ARTICLE);

        parsedNewsInformation=new String[newsArray.length()];

        for(int i=0;i<newsArray.length();i++){

            JSONObject newsArticles=newsArray.getJSONObject(i);

            String articleTitle=newsArticles.getString(RN_TITLE);
            String articleAuthor=newsArticles.getString(RN_AUTHOR);

            parsedNewsInformation[i]=articleTitle+" By: "+articleAuthor;

        }
        return parsedNewsInformation;
    }
}
