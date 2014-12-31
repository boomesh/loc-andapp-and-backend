package com.boojoo.boomesh.listofcities;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.boojoo.boomesh.api.Api;
import com.boojoo.boomesh.listofcities.dialogs.AddCityDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ListOfCitiesFragment extends Fragment implements ListOfCitiesActivity.OptionsMenuCallback {

    private static final String CLASS_TAG = ListOfCitiesFragment.class.getSimpleName();
    public static final String FRAG_TAG = CLASS_TAG + "_FRAGMENT";
    public static final String ARG_LOC = CLASS_TAG + "_LIST_OF_CITIES";

    private List<String> mCities;

    private ListView mCitiesListView;

    private ProgressDialog progressDialog;

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
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.progress_dialog_loading_text));
        refreshListOfCities();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void refreshListOfCities() {
        showProgressDialog();

        Api.getListOfCities(new Api.ApiCallback() {
            @Override
            public void success(Object result) {
                ListOfCitiesAdapter listOfCitiesAdapter = new ListOfCitiesAdapter((List<String>)result, getActivity());
                mCitiesListView.setAdapter(listOfCitiesAdapter);
                dismissProgressDialog();
            }

            @Override
            public void failure(Exception e, Object result) {
                dismissProgressDialog();
                Log.e(CLASS_TAG, "Error getting list of cities", e);
            }
        });
    }

    /**
     * OptionsMenuCallback methods
     */
    @Override
    public void addCitySelected() {
        AddCityDialogFragment addCityDialogFragment = new AddCityDialogFragment();
        addCityDialogFragment.setCallback(new AddCityDialogFragment.AddCityDialogFragmentCallback() {
            @Override
            public void onDonePressed(String city) {
                showProgressDialog();
                Api.addCityToList(city, (new Api.ApiCallback() {
                    @Override
                    public void success(Object result) {
                        //not dismissing progress dialog because refresh will eventually dismiss
                        refreshListOfCities();
                    }

                    @Override
                    public void failure(Exception e, Object result) {
                        dismissProgressDialog();
                        Log.e(CLASS_TAG, "Error adding city", e);
                    }
                }));
            }
        });

        addCityDialogFragment.show(getChildFragmentManager(), "add_city_dialog_frag");
    }


}
