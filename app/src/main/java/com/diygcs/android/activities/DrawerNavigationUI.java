package com.diygcs.android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.diygcs.android.DiygcsAPP;
import com.diygcs.android.R;
import com.diygcs.android.utils.DiygcsAPPPrefs;

import org.uah.core.drone.Drone;
import org.uah.core.drone.DroneManager;

public abstract class DrawerNavigationUI extends AppCompatActivity {

    /*
    *  Activates the navigation drawer when the home button clicked.
    * */
    private ActionBarDrawerToggle mDrawerToggle;

    /*
    *  Navigation drawer used to access the different sections of the app
    * */

    private DrawerLayout mDrawerLayout;
    private NavDrawerViewHolder mNavViewsHolder;

    /**
     * Clicking on an entry in the open navigation drawer updates this intent.
     * When the navigation drawer closes, the intent is used to navigate to the desired location.
     */
    private Intent mNavigationIntent;


    /**
     * 共用的全局变量
     */
    protected DiygcsAPPPrefs mAppPrefs;
    protected SharedPreferences sharedPref;
    protected DiygcsAPP mdpApp;
    protected DroneManager mDroneManager;
    protected Drone drone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context context = getApplicationContext();
        mAppPrefs = new DiygcsAPPPrefs(context);
        sharedPref = mAppPrefs.prefs;
        mdpApp = (DiygcsAPP)getApplication();
        mDroneManager = mdpApp.getDroneManager();
        drone = mdpApp.getDrone();

        mDrawerLayout = (DrawerLayout)getLayoutInflater().inflate(
                R.layout.nav_drawer_layout, null);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                if(mNavigationIntent != null) {
                    startActivity(mNavigationIntent);
                    mNavigationIntent = null;
                }
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    /**
     * Intercepts the call to 'setContentView', and wrap the passed layout
     * within a DrawerLayout object. This way, the children of this class don't
     * have to do anything to benefit from the navigation drawer.
     *
     * @param layoutResID
     *            layout resource for the activity view
     */
    @Override
    public void setContentView(int layoutResID) {
        final View contentView = getLayoutInflater().inflate(layoutResID, mDrawerLayout, false);
        mDrawerLayout.addView(contentView, 0);
        setContentView(mDrawerLayout);

        initNavigationDrawer();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.flight_data_menu, menu);

        menu.setGroupEnabled(R.id.menu_group_connected, false);
        menu.setGroupVisible(R.id.menu_group_connected, false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if(mDrawerToggle != null) {
            // Sync the toggle state after onRestoreInstanceState has occurred.
            mDrawerToggle.syncState();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNavigationDrawer();
    }

    /**
     * Initializes the navigation drawer.
     */
    private void initNavigationDrawer() {
        final View containerView = findViewById(R.id.nav_drawer_container);
        if(containerView != null){
            mNavViewsHolder = new NavDrawerViewHolder(containerView);
        }
    }

    private void updateNavigationDrawer(){
        if(mNavViewsHolder == null){
            return;
        }

        final Context context = getApplicationContext();
        final int navDrawerEntryId = getNavigationDrawerEntryId();

        setupNavigationEntry(navDrawerEntryId, mNavViewsHolder.mHomeScreen, new Intent(context,
                MainActivity.class));

        setupNavigationEntry(navDrawerEntryId, mNavViewsHolder.mFlightData, new Intent(context,
                FlightActivity.class));

        setupNavigationEntry(navDrawerEntryId, mNavViewsHolder.mRoute, new Intent(context,
                RouteActivity.class));

        setupNavigationEntry(navDrawerEntryId, mNavViewsHolder.mParams, new Intent(context,
                ParamsActivity.class));

        setupNavigationEntry(navDrawerEntryId, mNavViewsHolder.mSettings, new Intent(context,
                MissionActivity.class));

        setupNavigationEntry(navDrawerEntryId, mNavViewsHolder.mCalibration, new Intent(context,
                CalibraActivity.class));

    }

    private void setupNavigationEntry(int currentEntryId, TextView navView,
                                      final Intent clickIntent){
        if(navView == null){
            return;
        }

        if(currentEntryId == navView.getId()){
            //Bold the entry label
            navView.setTypeface(null, Typeface.BOLD);
            navView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
        }
        else{
            navView.setTypeface(null, Typeface.NORMAL);
            navView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickIntent != null) {
                        mNavigationIntent = clickIntent;
                    }
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
        }
    }

    /**
     * Holder class for the navigation entry views in the navigation drawer.
     * They are stored here to avoid re-instantiating through 'findViewById' which can be a bit
     * costly.
     */
    private static class NavDrawerViewHolder {

        final TextView mHomeScreen;
        final TextView mFlightData;
        final TextView mRoute;

        final TextView mSettings;

        final TextView mParams;
        final TextView mCalibration;

        private NavDrawerViewHolder(View containerView){
            mHomeScreen = (TextView) containerView.findViewById(R.id.home_screen);
            mFlightData = (TextView) containerView.findViewById(R.id.navigation_flight_data);
            mRoute = (TextView) containerView.findViewById(R.id.navigation_route);
            mSettings = (TextView) containerView.findViewById(R.id.navigation_settings);
            mParams = (TextView) containerView.findViewById(R.id.navigation_params);
            mCalibration = (TextView) containerView.findViewById(R.id.navigation_calibration);
        }
    }

    protected void toggleDroneConnection() {
        if(mDroneManager.isconnect()) {
            mDroneManager.disconnect();
        } else {
            mDroneManager.connect(mAppPrefs.getConnectionParameterType());
        }
    }

    protected abstract int getNavigationDrawerEntryId();

}
