package com.boojoo.boomesh.api;

import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.boojoo.boomesh.loc.backend.citiesApi.CitiesApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sumeshjohn on 2014-12-31.
 */
public class Api {
    public interface ApiCallback {
        public void success(Object result);

        public void failure(Exception e, Object result);
    }

    //never references this variable directly
    private static CitiesApi mCitiesApiService;

    private static final String CLASS_TAG = Api.class.getSimpleName();

    private static final CitiesApi getCitiesApiService() {
        if (mCitiesApiService == null) {  // Only do this once
            CitiesApi.Builder builder = new CitiesApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("<<PROVIDE ENDPOINT HERE>>")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            mCitiesApiService = builder.build();
        }
        return mCitiesApiService;
    }

    /*
     * Methods to call the API
     */
    public static void getListOfCities(ApiCallback callback) {
        new getListOfCititesTask().execute(callback);
    }

    public static void addCityToList(String cityName, ApiCallback callback) {
        new addCityToListTask().execute(new Pair<>(cityName, callback));
    }

    /*
     * AsyncTasks associated with api methods
     */
    private static class getListOfCititesTask extends AsyncTask<ApiCallback, Void, List<String>> {
        private ApiCallback apiCallback;

        @Override
        protected List<String> doInBackground(ApiCallback... params) {

            List<String> retCities = null;
            apiCallback = params[0];
            try {
                retCities = getCitiesApiService().getListOfCities().execute().getCities();
            } catch (IOException e) {
                apiCallback.failure(e, retCities);
            } finally {
                return retCities;
            }
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            apiCallback.success(result);
        }
    }

    private static class addCityToListTask extends AsyncTask<Pair<String, ApiCallback>, Void, Void> {
        private ApiCallback apiCallback;

        @Override
        protected Void doInBackground(Pair<String, ApiCallback>... params) {
            Pair<String, ApiCallback> pair = params[0];
            apiCallback = pair.second;

            try {
                getCitiesApiService().addCityToList(pair.first).execute();
            } catch (IOException e) {
                apiCallback.failure(e, null);
            } finally {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            apiCallback.success(null);
        }
    }
}
