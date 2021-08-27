/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {
    private EarthQuackAdapter adapter;


    /** Sample JSON response for a USGS query */
    private static final String USGS_REQUEST_URL =  "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        EarthQuakeAsyncTask task = new EarthQuakeAsyncTask();
        task.execute(USGS_REQUEST_URL);

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new EarthQuackAdapter(this, new ArrayList<>());

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener((parent, view, position, id) -> {

            EarthQuake earthQuake = (EarthQuake) adapter.getItem(position);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthQuake.get_url()));

            startActivity(intent);

        });

    }

    public class EarthQuakeAsyncTask extends AsyncTask<String, Void, List<EarthQuake>> {

        @Override
        protected List<EarthQuake> doInBackground(String... urls) {
            if(urls.length == 0) {
                return null;
            }
            List<EarthQuake> data = QueryUtils.fetchDataFromEarthquake(urls[0]);
            return data;
        }
        @Override
        protected void onPostExecute(List<EarthQuake> data) {
            super.onPostExecute(data);

            // Clear the adapter of previous earthquake data
            adapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                adapter.addAll(data);
            } else {
                Toast.makeText(EarthquakeActivity.this, "No data to show", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
