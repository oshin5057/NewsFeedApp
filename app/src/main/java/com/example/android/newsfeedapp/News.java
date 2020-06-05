package com.example.android.newsfeedapp;

public class News {
    private String mAuthor;
    private String mSection;
    private String mTitle;
    private String mDate;
    private String mUrl;

    public News(String author, String section, String title, String date, String url){

        mAuthor = author;
        mSection = section;
        mTitle = title;
        mDate = date;
        mUrl = url;

    }

    public String getAuthor(){
        return mAuthor;
    }
    public String getSection(){
        return mSection;
    }
    public String getTitle(){
        return mTitle;
    }
    public String getDate(){
        return mDate;
    }
    public String getUrl(){
        return mUrl;
    }
}
