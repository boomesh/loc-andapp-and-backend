package com.example.Sumeshjohn.myapplication.backend;

import java.util.List;

/**
 * The object model for the data we are sending through endpoints
 */
public class CitiesResponse {

    private List<String> mCities;

    public List<String> getCities() {
        return mCities;
    }

    public void setCities(List<String> mCities) {
        this.mCities = mCities;
    }
}