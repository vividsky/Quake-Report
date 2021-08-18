package com.example.android.quakereport;

public class EarthQuake {
    private String _magnitude;
    private String _location;
    private String _date;
    EarthQuake(String magnitude, String location, String date) {
        this._magnitude = magnitude;
        this._location = location;
        this._date = date;
    }

    public String get_magnitude() {
        return _magnitude;
    }

    public String get_location() {
        return _location;
    }

    public String get_date() {
        return _date;
    }
}
