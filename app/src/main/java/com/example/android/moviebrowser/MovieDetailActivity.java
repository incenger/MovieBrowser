package com.example.android.moviebrowser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView posterImageView;
    private TextView titleTextView;
    private TextView infoTextView;
    private TextView plotTextView;
    private TextView ratingTextView;
    private MovieDetailObject movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        titleTextView = findViewById(R.id.movie_detail_title_tv);
        infoTextView = findViewById(R.id.movie_detail_film_info_tv);
        plotTextView = findViewById(R.id.movie_detail_description_tv);
        ratingTextView = findViewById(R.id.movie_detail_rating_tv);
        posterImageView = findViewById(R.id.movie_detail_poster);

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra("ID")) {
                String movieId = intent.getStringExtra("ID");
                new FetchMovieDetail().execute(movieId);
            }
            if (intent.hasExtra("POSTER")) {
                new NetworkUtils.DownloadPosterTask(posterImageView)
                        .execute(intent.getStringExtra("POSTER"));
            }
        }

    }



    public class FetchMovieDetail extends AsyncTask<String , Void, MovieDetailObject> {
        @Override
        protected MovieDetailObject doInBackground(String... strings) {
            if (strings.length == 0) return null;

            String movieId = strings[0];
            URL idUrl = NetworkUtils.buildMovieDetailUrl(movieId);

            try {
                String response = NetworkUtils.getResponseFromHttpUrl(idUrl);
                return JsonUtils.getMovieDetailObjectFromJson(getBaseContext(), response);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieDetailObject movieDetailObject) {
            movie = movieDetailObject;
            String title = movieDetailObject.getTitle() + " " + movieDetailObject.getYear();
            titleTextView.setText(title);
            String info = movieDetailObject.getAgeRating() + ", " + movieDetailObject.getRuntime();
            infoTextView.setText(info);
            plotTextView.setText(movieDetailObject.getPlot());
            ratingTextView.setText(movieDetailObject.getIMDBRating());
        }
    }


}
