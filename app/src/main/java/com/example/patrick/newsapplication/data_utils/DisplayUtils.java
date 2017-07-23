package com.example.patrick.newsapplication.data_utils;

import java.util.ArrayList;

/**
 * Created by Patrick on 6/21/2017.
 */

public class DisplayUtils {
    public static String[] returnAuthors(ArrayList<NewsItem> newsItems){
        String[] articleArray=new String[newsItems.size()];

        for(int i = 0; i< newsItems.size(); i++){
            NewsItem currentNewsItem = newsItems.get(i);
            articleArray[i]= currentNewsItem.getArticleAuthor();
        }

        return articleArray;
    }

    public static String[] returnTitles(ArrayList<NewsItem> newsItems){
        String[] articleArray=new String[newsItems.size()];

        for(int i = 0; i< newsItems.size(); i++){
            NewsItem currentNewsItem = newsItems.get(i);
            articleArray[i]= currentNewsItem.getArticleTitle();
        }

        return articleArray;
    }

    public static String[] returnAuthorsTitles(ArrayList<NewsItem> newsItems){
        String[] articleArray=new String[newsItems.size()];

        for(int i = 0; i< newsItems.size(); i++){
            NewsItem currentNewsItem = newsItems.get(i);
            articleArray[i]= currentNewsItem.getArticleTitle()+"\n\nBy: "+ currentNewsItem.getArticleAuthor();
        }

        return articleArray;
    }

    public static String[] returnAuthorsTitlesDescriptions(ArrayList<NewsItem> newsItems){
        String[] articleArray=new String[newsItems.size()];

        for(int i = 0; i< newsItems.size(); i++){
            NewsItem currentNewsItem = newsItems.get(i);
            articleArray[i]= currentNewsItem.getArticleTitle()+"\nBy: "+
            currentNewsItem.getArticleAuthor()+"\n\nDescription: "+ currentNewsItem.getArticleDescription();
        }

        return articleArray;
    }

}
