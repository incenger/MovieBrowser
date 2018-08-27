package com.example.android.moviebrowser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URL = "http://www.omdbapi.com";

    private static final String SEARCH_PARAM = "s";

    private static final String ID_PARAM = "i";

    private static final String API_PARAM = "apikey";

    private static final String API_KEY = "70f959ed";

    public static URL buildMovieDetailUrl(String movieId) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(ID_PARAM, movieId)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built url: " + url);
        return url;
    }

    public static URL buildSearchUrl(String searchQuery) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SEARCH_PARAM, searchQuery)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built url: " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

    public static class DownloadPosterTask extends AsyncTask<String, Void, Bitmap> {
        ImageView mImageView;

        DownloadPosterTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            if (strings == null) return null;
            String posterUrl = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(posterUrl).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
        }
    }
}
