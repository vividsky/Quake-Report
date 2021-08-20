package com.example.android.quakereport;

public class EarthQuake {
    private double _magnitude;
    private String _location;
    private long _date;
    private String url;
    EarthQuake(double magnitude, String location, long date, String url) {
        this._magnitude = magnitude;
        this._location = location;
        this._date = date;
        this.url = url;
    }

    public double get_magnitude() {
        return _magnitude;
    }

    public String get_location() {
        return _location;
    }

    public long get_date() {
        return _date;
    }

    public String get_url(){return url;}
}
