package com.example.patrick.newsapplication.utils;

import com.example.patrick.newsapplication.data_models.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Patrick on 6/19/2017.
 */

public class JsonUtils {
    private final static String RN_ARTICLE="articles";
    private final static String RN_NO_ERROR="ok";
    private final static String RN_ERROR="error";
    private final static String RN_TITLE="title";
    private final static String RN_AUTHOR="author";
    private final static String RN_DESCRIPTION="description";
    private final static String RN_URL="url";
    private final static String RN_IMAGE_URL="urlToImage";
    private final static String RN_PUBLISH="publishedAt";

    //Parses the returned json to a string array
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

    //Parses the returned json to an NewsItem object
    public static ArrayList<NewsItem> getArticleFromJson(String newsJsonString) throws JSONException{
        ArrayList<NewsItem> newsItemArray =new ArrayList<>();

        JSONObject newsJSON=new JSONObject(newsJsonString);
        JSONArray newsArray=newsJSON.getJSONArray(RN_ARTICLE);

        for(int i =0;i<newsArray.length();i++){
            JSONObject newsArticles=newsArray.getJSONObject(i);

            String articleTitle=newsArticles.getString(RN_TITLE);
            String articleAuthor=newsArticles.getString(RN_AUTHOR);
            String articleDescription=newsArticles.getString(RN_DESCRIPTION);
            String articleUrl=newsArticles.getString(RN_URL);
            String articleImage=newsArticles.getString(RN_IMAGE_URL);
            String articlePublished=newsArticles.getString(RN_PUBLISH);

            newsItemArray.add(new NewsItem(articleTitle,articleDescription,
                    articleAuthor,articleUrl,articleImage,articlePublished));
        }
        return newsItemArray;
    }
}
