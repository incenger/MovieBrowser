package com.example.android.moviebrowser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private ArrayList<MovieObject> movies;

    public interface MovieAdapterOnClickHanlder {
        void onClick(String movieId, String posterUrl);
    }

    private final MovieAdapterOnClickHanlder mClickHandler;

    public MovieAdapter(MovieAdapterOnClickHanlder clickHanlder) {
        mClickHandler = clickHanlder;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mTitleTextView;
        public final TextView mYearTextView;
        public final ImageView mPosterImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mTitleTextView = view.findViewById(R.id.title_text_view);
            mYearTextView = view.findViewById(R.id.year_text_view);
            mPosterImageView = view.findViewById(R.id.poster_image_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String movieId = movies.get(position).getImdbId();
            String posterUrl = movies.get(position).getPosterUrl();
            mClickHandler.onClick(movieId, posterUrl);
        }
    }

    @NonNull
    @Override
    public MovieAdapter.MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder viewHolder, int i) {
        MovieObject movieObject = movies.get(i);
        viewHolder.mTitleTextView.setText(movieObject.getTitle());
        viewHolder.mYearTextView.setText(movieObject.getYear());
        new NetworkUtils.DownloadPosterTask(viewHolder.mPosterImageView).execute(movieObject.getPosterUrl());

    }

    @Override
    public int getItemCount() {
        if (movies == null) return 0;
        return movies.size();
    }

    public void setMovieData(ArrayList<MovieObject> movieData) {
        movies = movieData;
        notifyDataSetChanged();
    }

}