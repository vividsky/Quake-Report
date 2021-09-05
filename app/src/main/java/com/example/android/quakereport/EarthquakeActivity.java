
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthQuake>> {

    private ListView earthquakeListView;
    private TextView mEmptyStateTextView;
    private ProgressBar progressBar;
    private EarthQuackAdapter adapter;
    private static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();

    /** Sample JSON response for a USGS query */
    private final String USGS_REQUEST_URL =  "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        Log.i(LOG_TAG, "*************************Executing onCreate**************************");

//        // Create a list of earthquake locations.
//        EarthQuakeAsyncTask task = new EarthQuakeAsyncTask();
//        task.execute(USGS_REQUEST_URL);

        progressBar = findViewById(R.id.progress_bar);


        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new EarthQuackAdapter(this, new ArrayList<>());

        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = findViewById(R.id.list);

        mEmptyStateTextView = findViewById(R.id.oops_moment);
        earthquakeListView.setEmptyView(mEmptyStateTextView);





        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener((parent, view, position, id) -> {

            EarthQuake earthQuake = (EarthQuake) adapter.getItem(position);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthQuake.get_url()));

            startActivity(intent);

        });
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @NonNull
    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int id, @Nullable Bundle bundle) {
        Log.i(LOG_TAG, "************************Executing onCreateLoader************************");

        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<EarthQuake>> loader, List<EarthQuake> data) {
        Log.i(LOG_TAG, "************************Executing onLoadFinished************************");

        progressBar.setVisibility(View.GONE);

        if(!isNetworkConnected()) {
            mEmptyStateTextView.setText(R.string.noInternet);
        } else {
            mEmptyStateTextView.setText(R.string.contentNotFound);
        }

        //Clear the adapter of previous earthquake data
        adapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        } else {
//            Toast.makeText(EarthquakeActivity.this, "No data to show", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        Log.i(LOG_TAG, "Executing onLoaderReset");
            adapter.clear();
    }

    //    public class EarthQuakeAsyncTask extends AsyncTask<String, Void, List<EarthQuake>> {
//
//        @Override
//        protected List<EarthQuake> doInBackground(String... urls) {
//            if(urls.length == 0) {
//                return null;
//            }
//            List<EarthQuake> data = QueryUtils.fetchDataFromEarthquake(urls[0]);
//            return data;
//        }
//        @Override
//        protected void onPostExecute(List<EarthQuake> data) {
//            super.onPostExecute(data);
//
//            // Clear the adapter of previous earthquake data
//            adapter.clear();
//
//            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//            // data set. This will trigger the ListView to update.
//            if (data != null && !data.isEmpty()) {
//                adapter.addAll(data);
//            } else {
//                Toast.makeText(EarthquakeActivity.this, "No data to show", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }

    private static class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuake>> {
        String url;
        public EarthquakeLoader(@NonNull Context context, String url) {

            super(context);
            this.url = url;
        }

        @Nullable
        @Override
        public List<EarthQuake> loadInBackground() {
            Log.i(LOG_TAG, "************************Executing loadInBackground************************");
            if(url == null) {
                return null;
            }
            List<EarthQuake> data = QueryUtils.fetchDataFromEarthquake(url);
            return data;
        }
    }

}

