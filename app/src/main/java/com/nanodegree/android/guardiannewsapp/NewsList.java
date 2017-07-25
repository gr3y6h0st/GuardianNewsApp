package com.nanodegree.android.guardiannewsapp;

/**
 * Created by jdifuntorum on 7/9/17.
 */

public class NewsList {

    private String mTitle;

    private String mSection;

    private String mURL_Link;

    private String mPublished_Date;

    public NewsList(String title, String section, String URL_Link, String published_Date) {
        mTitle = title;
        mSection = section;
        mURL_Link = URL_Link;
        mPublished_Date = published_Date;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getURL_Link() {
        return mURL_Link;
    }

    public String getPublished_Date() {
        return mPublished_Date;
    }
}


