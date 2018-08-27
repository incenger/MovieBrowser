package com.example.android.moviebrowser;

public class MovieDetailObject {
    private String mTitle;
    private String mYear;
    private String mPosterUrl;
    private String mAgeRating;
    private String mRuntime;
    private String mPlot;
    private String mIMDBRating;

    public MovieDetailObject(String title, String year, String posterUrl, String ageRating,
                             String runtime, String plot, String imdbRating) {
        mTitle = title;
        mYear = year;
        mPosterUrl = posterUrl;
        mAgeRating = ageRating;
        mRuntime = runtime;
        mPlot = plot;
        mIMDBRating = imdbRating;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getYear() {
        return mYear;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public String getAgeRating() {
        return mAgeRating;
    }

    public String getRuntime() {
        return mRuntime;
    }

    public String getPlot() {
        return mPlot;
    }

    public String getIMDBRating() {
        return mIMDBRating;
    }
}
