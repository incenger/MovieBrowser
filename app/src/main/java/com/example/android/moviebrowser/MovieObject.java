package com.example.android.moviebrowser;

public class MovieObject {
    private String mTitle;
    private String mYear;
    private String mImdbId;
    private String mPosterURL;

    public MovieObject(String title, String year, String imdbid, String posterURL) {
        mTitle = title;
        mYear = year;
        mImdbId = imdbid;
        mPosterURL = posterURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getYear() {
        return mYear;
    }

    public String getImdbId() {
        return mImdbId;
    }

    public String getPosterUrl() {
        return mPosterURL;
    }
}
