package com.example.android.quakereport;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<EarthQuake> fetchDataFromEarthquake (String requestURL) {
        Log.i(LOG_TAG, "Executing fetchEarthquakeData");

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

            URL url = createURL(requestURL);
            String JSONResponse = "";
            try {
                JSONResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<EarthQuake> earthQuake = extractEarthquakes(JSONResponse);
            return earthQuake;
        }


    private static String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String JSONresponse = "";
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                JSONresponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code." + urlConnection.getResponseCode());
            }
        } catch(Exception e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return JSONresponse;
    }

    private static URL createURL(String stringURL) {
        URL url;
        try {
            url = new URL(stringURL);
        } catch(Exception e) {
            return null;
        }
        return url;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();

        if(inputStream != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = br.readLine();
            while(line != null) {
                sb.append(line);
                line = br.readLine();
            }
        }
        return sb.toString();
    }

    /**
     * Return a list of {@link EarthQuake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<EarthQuake> extractEarthquakes(String earthquakeJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthQuake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            //Create the root JSONObject to convert Json string into object so that we can apply methods on it
            JSONObject root = new JSONObject(earthquakeJSON);
            JSONArray features = root.optJSONArray("features");

            for(int i = 0; i < features.length(); i++) {

                // create JSONObject currentEarthquake from features object
                JSONObject currentEarthquake = features.getJSONObject(i);

                // Fetch all the properties of the currentEarthquake and later extract the needed ones
                JSONObject properties = currentEarthquake.optJSONObject("properties");

                double magnitude = properties.optDouble("mag");
                String location = properties.optString("place");
                long d = properties.optLong("time");
                String url = properties.optString("url");

                earthquakes.add(new EarthQuake(magnitude, location, d, url));


            }
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}
