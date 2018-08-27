package com.example.android.moviebrowser;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHanlder {


    private RecyclerView mSearchResultRecyclerView;
    private MovieAdapter mMovieAdapter;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchResultRecyclerView = findViewById(R.id.search_result_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSearchResultRecyclerView.setLayoutManager(layoutManager);
        mSearchResultRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mSearchResultRecyclerView.setAdapter(mMovieAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_action).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint(getString(R.string.query_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchMovie(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    public class FetchMovieData extends AsyncTask<String, Void, ArrayList<MovieObject>> {
        @Override
        protected ArrayList<MovieObject> doInBackground(String... strings) {
            if (strings.length == 0) return null;

            String query = strings[0];
            URL searchUrl = NetworkUtils.buildSearchUrl(query);

            try {
                String response = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                return JsonUtils.getMovieObjectFromJson(getBaseContext(), response);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieObject> results) {
            if (results == null) ;
            else {
                mMovieAdapter.setMovieData(results);

            }
        }
    }

    private void searchMovie(String query) {
        if (TextUtils.isEmpty(query)) return;
        String convertedQuery = QueryUtils.convertNormalQueryToParamQuery(query);
        new FetchMovieData().execute(convertedQuery);

    }

    @Override
    public void onClick(String movieId, String posterUrl) {
        Context context = this;
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("ID", movieId);
        intent.putExtra("POSTER", posterUrl);
        startActivity(intent);

    }

}
