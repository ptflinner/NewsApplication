package com.example.patrick.newsapplication;

/**
 * Created by Patrick on 6/21/2017.
 */

public class Article {
    String articleTitle;
    String articleDescription;
    String articleAuthor;
    String articleUrl;
    String articleImage;
    String articlePublishDate;

    public Article(){
        this.articleTitle = "";
        this.articleDescription = "";
        this.articleAuthor = "";
        this.articleUrl = "";
        this.articleImage = "";
        this.articlePublishDate = "";
    }
    public Article(String articleTitle, String articleDescription,
                   String articleAuthor, String articleUrl, String articleImage, String articlePublishDate) {
        this.articleTitle = articleTitle;
        this.articleDescription = articleDescription;
        this.articleAuthor = articleAuthor;
        this.articleUrl = articleUrl;
        this.articleImage = articleImage;
        this.articlePublishDate = articlePublishDate;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(String articleImage) {
        this.articleImage = articleImage;
    }

    public String getArticlePublishDate() {
        return articlePublishDate;
    }

    public void setArticlePublishDate(String articlePublishDate) {
        this.articlePublishDate = articlePublishDate;
    }
}
