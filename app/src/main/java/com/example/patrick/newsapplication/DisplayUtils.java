package com.example.patrick.newsapplication;

import java.util.ArrayList;

/**
 * Created by Patrick on 6/21/2017.
 */

public class DisplayUtils {
    public static String[] returnAuthors(ArrayList<Article> articles){
        String[] articleArray=new String[articles.size()];

        for(int i=0;i<articles.size();i++){
            Article currentArticle=articles.get(i);
            articleArray[i]=currentArticle.getArticleAuthor();
        }

        return articleArray;
    }

    public static String[] returnTitles(ArrayList<Article> articles){
        String[] articleArray=new String[articles.size()];

        for(int i=0;i<articles.size();i++){
            Article currentArticle=articles.get(i);
            articleArray[i]=currentArticle.getArticleTitle();
        }

        return articleArray;
    }

    public static String[] returnAuthorsTitles(ArrayList<Article> articles){
        String[] articleArray=new String[articles.size()];

        for(int i=0;i<articles.size();i++){
            Article currentArticle=articles.get(i);
            articleArray[i]=currentArticle.getArticleTitle()+"\n\nBy: "+currentArticle.getArticleAuthor();
        }

        return articleArray;
    }

    public static String[] returnAuthorsTitlesDescriptions(ArrayList<Article> articles){
        String[] articleArray=new String[articles.size()];

        for(int i=0;i<articles.size();i++){
            Article currentArticle=articles.get(i);
            articleArray[i]=currentArticle.getArticleTitle()+"\nBy: "+
            currentArticle.getArticleAuthor()+"\n\nDescription: "+currentArticle.getArticleDescription();
        }

        return articleArray;
    }

}
