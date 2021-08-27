package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EarthQuackAdapter extends ArrayAdapter{
    public EarthQuackAdapter(@NonNull Context context, List<EarthQuake> objects) {
        super(context, 0, objects);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list, parent, false);
        }

        EarthQuake currentEarthquake = (EarthQuake) getItem(position);


        TextView magnitude = listView.findViewById(R.id.magnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.get_magnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        //Set magnitude value in the shape of the textView
        magnitude.setText(formatMagnitude(currentEarthquake.get_magnitude()));

        TextView locationArea = listView.findViewById(R.id.locationArea);
        locationArea.setText( locationArea(currentEarthquake.get_location()));

        TextView locationPlace = listView.findViewById(R.id.locationPlace);
        locationPlace.setText(locationPlace(currentEarthquake.get_location()));

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentEarthquake.get_date());

        TextView date = listView.findViewById(R.id.dateFormat);
        date.setText( dateFormat(dateObject));

        TextView time = listView.findViewById(R.id.timeFormat);
        time.setText( timeFormat(dateObject));

        return listView;
    }



    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String dateFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormat.format(date);
    }
    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String timeFormat(Date time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(time);
    }

    private String locationArea(String location) {
        String locationArea = "Near the";

        if(location.contains(" of ")) {
            locationArea = location.substring(0, location.indexOf(" of ") + 4);
        }
        return locationArea;
    }

    private String locationPlace(String location) {
        String locationPlace;
        if(location.contains(" of ")) {
            locationPlace = location.substring(location.indexOf(" of ") + 4);
        } else {
            locationPlace = location;
        }
        return locationPlace;
    }

    private String formatMagnitude(double magnitude) {
        //Formatting the magnitude upto one decimal place; takes input as double, returns String
        DecimalFormat df = new DecimalFormat("#.#");

        //Using DecimalFormatter object to format magnitude
        return df.format(magnitude);
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeValue = (int) Math.floor(magnitude);
        switch(magnitudeValue) {
            case 0:
            case 1 :
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2 :
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3 :
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4 :
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5 :
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6 :
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7 :
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8 :
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            case 10 :
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        int colorIntegerId = ContextCompat.getColor(getContext(), magnitudeColorResourceId);
        return colorIntegerId;
    }
}
