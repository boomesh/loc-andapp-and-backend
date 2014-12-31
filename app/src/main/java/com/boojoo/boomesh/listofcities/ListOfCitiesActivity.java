package com.boojoo.boomesh.listofcities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class ListOfCitiesActivity extends ActionBarActivity {

    public interface OptionsMenuCallback {
        public void addCitySelected();
    }

    private static final String CLASS_TAG = ListOfCitiesActivity.class.getSimpleName();

    private OptionsMenuCallback mOptionsMenuCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            try {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                ListOfCitiesFragment listOfCitiesFragment = new ListOfCitiesFragment();
                mOptionsMenuCallback = listOfCitiesFragment;
                transaction.add(R.id.full_screen_fragment, listOfCitiesFragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
            } catch (Exception e) {
                Log.e(CLASS_TAG, "Error loading ListOfCitiesFragment", e);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_city) {
            mOptionsMenuCallback.addCitySelected();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
