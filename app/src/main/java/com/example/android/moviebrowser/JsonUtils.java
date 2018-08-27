package com.example.android.moviebrowser;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static ArrayList<MovieObject> getMovieObjectFromJson(Context context, String json) throws JSONException {
        final String RESPONSE_KEY = "Response";
        final String ACCEPTED = "True";
        final String NOT_ACCEPTED = "False";
        final String TITLE_KEY = "Title";
        final String YEAR_KEY = "Year";
        final String IMDB_ID_KEY = "imdbID";
        final String POSTER_KEY = "Poster";
        final String SEARCH_RESULT_KEY = "Search";

        ArrayList<MovieObject> movieObjects = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        // check the response of the search
        String searchStatus = jsonObject.getString(RESPONSE_KEY);
        Log.d(TAG, "getMovieObjectFromJson: search status" + searchStatus);
        // if the search not success, return null
        if (searchStatus.equals(NOT_ACCEPTED)) return null;

        JSONArray results = jsonObject.getJSONArray(SEARCH_RESULT_KEY);
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            String title = result.getString(TITLE_KEY);
            String year = result.getString(YEAR_KEY);
            String posterUrl = result.getString(POSTER_KEY);
            String imdbId = result.getString(IMDB_ID_KEY);
            Log.d(TAG, "getMovieObjectFromJson: title" + title);

            MovieObject movieObject = new MovieObject(title, year, imdbId, posterUrl);
            movieObjects.add(movieObject);
        }
        return movieObjects;
    }

    public static MovieDetailObject getMovieDetailObjectFromJson(Context context, String json)
        throws JSONException {
        final String TITLE_KEY = "Title";
        final String YEAR_KEY = "Year";
        final String POSTER_URL = "Poster";
        final String AGE_RATING_KEY = "Rated";
        final String RUNTIME_KEY = "Runtime";
        final String PLOT_KEY = "Plot";
        final String IMDB_RATING_KEY = "imdbRating";
        final String RESPONSE_KEY = "Response";
        final String ACCEPTED = "True";
        final String NOT_ACCEPTED = "False";

        MovieDetailObject movieDetailObject = null;
        JSONObject jsonObject = new JSONObject(json);

        String responseStatus = jsonObject.getString(RESPONSE_KEY);
        if (responseStatus.equals(NOT_ACCEPTED)) return null;

        String ageRating = jsonObject.getString(AGE_RATING_KEY);
        String runtime = jsonObject.getString(RUNTIME_KEY);
        String plot = jsonObject.getString(PLOT_KEY);
        String imdbRating = jsonObject.getString(IMDB_RATING_KEY);
        String title = jsonObject.getString(TITLE_KEY);
        String year = jsonObject.getString(YEAR_KEY);
        String posterUrl = jsonObject.getString(POSTER_URL);

        movieDetailObject = new MovieDetailObject(title, year, posterUrl, ageRating, runtime, plot, imdbRating);
        return movieDetailObject;

    }
}
