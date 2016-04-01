package com.diygcs.android.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.diygcs.android.R;

public class FlightActivity extends DrawerNavigationUI {

    private static final String TAG = FlightActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem toggleConnectionItem = menu.findItem(R.id.menu_connection);
        if(mDroneManager.isconnect()) {
            toggleConnectionItem.setTitle(R.string.menu_disconnection);
            Log.i(TAG, "DISCONN");
        } else {
            toggleConnectionItem.setTitle(R.string.menu_connection);
            Log.i(TAG, "CONN");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_connection:
                toggleDroneConnection();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getNavigationDrawerEntryId() {
        return R.id.navigation_flight_data;
    }

}
