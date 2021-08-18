package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EarthQuackAdapter extends ArrayAdapter{
    public EarthQuackAdapter(@NonNull Context context, ArrayList<EarthQuake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list, parent, false);
        }

        EarthQuake currentNumberPosition = (EarthQuake) getItem(position);

        TextView magnitude = listView.findViewById(R.id.magnitude);
        magnitude.setText(currentNumberPosition.get_magnitude());

        TextView location = listView.findViewById(R.id.location);
        location.setText( currentNumberPosition.get_location());

        TextView date = listView.findViewById(R.id.date);
        date.setText( currentNumberPosition.get_date());

        return listView;
    }
}
