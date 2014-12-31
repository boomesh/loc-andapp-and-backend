/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.Sumeshjohn.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "citiesApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.loc.boomesh.boojoo.com", ownerName = "backend.loc.boomesh.boojoo.com", packagePath = ""))
public class MyEndpoint {

    private List<String> mCitites = Collections.EMPTY_LIST;

    @ApiMethod(name = "getListOfCities")
    public CitiesResponse getListOfCities() {
        mCitites = new ArrayList<String>(Arrays.asList("Toronto", "Noronto", "Horonto", "Rowronto", "Showronto", "Whoaronto", "Mo'ronto", "Joeronto", "Yo,ronto", "Fo'Sho'ronto", "Joeronto"));
        CitiesResponse response = new CitiesResponse();
        response.setCities(mCitites);
        return response;
    }

    @ApiMethod(name = "addCityToList")
    public void addCityToTopOfList(@Named("city") String city) {
        mCitites.add(0, city);
    }

}
