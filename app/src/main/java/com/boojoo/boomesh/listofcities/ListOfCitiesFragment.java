package com.boojoo.boomesh.listofcities;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.boojoo.boomesh.loc.backend.citiesApi.CitiesApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOfCitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOfCitiesFragment extends Fragment {

    private static final String CLASS_TAG = ListOfCitiesFragment.class.getSimpleName();
    public static final String ARG_LOC = CLASS_TAG + "_LIST_OF_CITIES";

    private List<String> mCities;

    private ListView mCitiesListView;

    public static ListOfCitiesFragment newInstance(String param1, String param2) {
        ListOfCitiesFragment fragment = new ListOfCitiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOC, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCities = getArguments().getStringArrayList(ARG_LOC);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCities != null) {
            outState.putStringArrayList(ARG_LOC, (ArrayList<String>) mCities);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_of_cities, container, false);
        mCitiesListView = (ListView) rootView.findViewById(R.id.listOfCities);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new getListOfCititesTask().execute();
    }

    class getListOfCititesTask extends AsyncTask<Void, Void, List<String>> {
        private CitiesApi mCitiesApi;

        @Override
        protected List<String> doInBackground(Void... params) {
            if(mCitiesApi == null) {  // Only do this once
                CitiesApi.Builder builder = new CitiesApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("<<REPLACE WITH URL>>")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

                mCitiesApi = builder.build();
            }

            List<String> retCities = null;

            try {
                retCities = mCitiesApi.getListOfCities().execute().getCities();
            } catch (IOException e) {
                Log.e(CLASS_TAG, "Error: ", e);
            } finally {
                return retCities;
            }
        }

        @Override
        protected void onPostExecute(List<String> result) {
            ListOfCitiesAdapter listOfCitiesAdapter = new ListOfCitiesAdapter(result, getActivity());
            mCitiesListView.setAdapter(listOfCitiesAdapter);
        }
    }
}
