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

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        ArrayList<EarthQuake> earthquakes = new ArrayList<>();
        earthquakes.add(new EarthQuake("7.2", "San Francisco", "Feb 2, 2016"));
        earthquakes.add(new EarthQuake("6.2", "London", "Mar 3, 2020"));
        earthquakes.add(new EarthQuake("6.2", "Tokyo", "Mar 3, 2020"));
        earthquakes.add(new EarthQuake("6.2", "Mexico City", "Mar 3, 2020"));
        earthquakes.add(new EarthQuake("6.2", "Moscow", "Mar 3, 2020"));
        earthquakes.add(new EarthQuake("6.2", "Rio de Janeiro", "Mar 3, 2020"));
        earthquakes.add(new EarthQuake("6.2", "Paris", "Mar 3, 2020"));

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthQuackAdapter adapter = new EarthQuackAdapter(this, earthquakes);


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
}
