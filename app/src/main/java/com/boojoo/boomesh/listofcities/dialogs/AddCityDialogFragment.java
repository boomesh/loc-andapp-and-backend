package com.boojoo.boomesh.listofcities.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.boojoo.boomesh.listofcities.R;

/**
 * Created by Sumeshjohn on 2014-12-31.
 */
public class AddCityDialogFragment extends DialogFragment {

    public interface AddCityDialogFragmentCallback {
        public void onDonePressed(String city);
    }

    private static final String CLASS_TAG = AddCityDialogFragment.class.getSimpleName();

    private static final String ARG_CITY = CLASS_TAG + "_CITY";

    private String mCity;

    private AddCityDialogFragmentCallback mCallback;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            String savedCity = savedInstanceState.getString(ARG_CITY);
            if (!TextUtils.isEmpty(savedCity)) {
                mCity = savedCity;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!TextUtils.isEmpty(mCity)) {
            outState.putString(ARG_CITY, mCity);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_add_city, container, false);
        ((EditText) rootView.findViewById(R.id.city_edit_text)).setText(mCity);
        ((EditText) rootView.findViewById(R.id.city_edit_text)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (mCallback != null) {
                        mCallback.onDonePressed(v.getText().toString());
                    }
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        getDialog().setTitle(R.string.enter_city_text);
        return rootView;
    }

    public void setCallback(AddCityDialogFragmentCallback callback) {
        mCallback = callback;
    }
}
