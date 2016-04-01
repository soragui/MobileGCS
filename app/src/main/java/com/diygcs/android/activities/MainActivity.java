package com.diygcs.android.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.preference.Preference;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SlidingDrawer;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.diygcs.android.DiygcsAPP;
import com.diygcs.android.R;
import com.diygcs.android.fragment.FlightMapFragment;
import com.diygcs.android.fragment.SimpleDataFragment;
import com.diygcs.android.maps.BaiduMapFragment;
import com.diygcs.android.maps.MapViewFragment;
import com.diygcs.android.utils.DiygcsAPPPrefs;

import org.uah.core.MUHLink.TCPConnection;
import org.uah.core.drone.Drone;
import org.uah.core.drone.DroneManager;

public class MainActivity extends DrawerNavigationUI implements
        MapViewFragment.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     *  Handle to the app preferences.
     */

    private FlightMapFragment mapFragment;

    private View mLocationButtonsContainer;
    private ImageButton mGoToMyLocation;
    private ImageButton mGoToDroneLocation;
    private ImageButton mapZoomin;
    private ImageButton mapZoomout;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);

            LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(R.layout.activity_main_action_bar, null);

            //ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            //        ViewGroup.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(v);
        }

        final SlidingDrawer slidingDrawer = (SlidingDrawer)findViewById(R.id.simple_flight_data);
        //Only the phone layout has the sliding drawer
        if(slidingDrawer != null) {
            slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
                @Override
                public void onDrawerClosed() {
                    final int slidingDrawerHeight = slidingDrawer.getContent().getHeight();
                    final boolean isSlidingDrawerOpened = slidingDrawer.isOpened();
                    updateLocationButtonsMargin(isSlidingDrawerOpened, slidingDrawerHeight-40);
                }
            });
            slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
                @Override
                public void onDrawerOpened() {
                    final int slidingDrawerHeight = slidingDrawer.getContent().getHeight();
                    final boolean isSlidingDrawerOpened = slidingDrawer.isOpened();
                    updateLocationButtonsMargin(isSlidingDrawerOpened, slidingDrawerHeight-40);
                }
            });
        }

        setupMapFragment();

        mLocationButtonsContainer = findViewById(R.id.location_button_container);
        mGoToMyLocation = (ImageButton)findViewById(R.id.my_location_button);
        mGoToDroneLocation = (ImageButton)findViewById(R.id.drone_location_button);
        mapZoomin = (ImageButton)findViewById(R.id.map_zoom_in);
        mapZoomout = (ImageButton)findViewById(R.id.map_zoom_out);

        /*
        * Add the simple flight data fragment
        * */

        SimpleDataFragment simpleDataFragment = (SimpleDataFragment)fragmentManager.findFragmentById
                (R.id.simple_flight_data_view);
        if(simpleDataFragment == null) {
            simpleDataFragment = new SimpleDataFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.simple_flight_data_view, simpleDataFragment)
                    .commit();
        }

        mGoToMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mGoToDroneLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(drone.latitude, drone.longitude);
                if(mapFragment != null) {
                    mapFragment.goToLocation(latLng, 100);
                }
            }
        });
    }

    /*
    *  Account for the various ui elements and update the map padding so that it
    *  remains 'vivible'.
    * */
    private void updateLocationButtonsMargin(boolean isOpened, int drawerHeight) {

        // Update the right margin for the my location button
        final ViewGroup.MarginLayoutParams marginLp = (ViewGroup.MarginLayoutParams) mLocationButtonsContainer
                .getLayoutParams();

        final int topMargin;
        if(isOpened) {
            topMargin = marginLp.bottomMargin + drawerHeight;
            marginLp.bottomMargin += 30;
        } else {
            marginLp.bottomMargin -= 30;
            topMargin = marginLp.bottomMargin;
        }

        marginLp.setMargins(marginLp.leftMargin, topMargin, marginLp.rightMargin,
                marginLp.bottomMargin);
        mLocationButtonsContainer.requestLayout();

    }

    /**
     * Used to setup the flight screen map fragment. Before attempting to
     * initialize the map fragment, this checks if the Google Play Services
     * binary is installed and up to date.
     */
    private void setupMapFragment() {
        if (mapFragment == null ) {
            mapFragment = (FlightMapFragment) fragmentManager.findFragmentById(R.id.flight_map_fragment);

            if (mapFragment == null) {
                mapFragment = new FlightMapFragment();
                fragmentManager.beginTransaction().add(R.id.flight_map_fragment, mapFragment).commit();
            }
        }
    }

    /**
     * 编辑菜单
     * */
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.flight_data_menu, menu);

        final MenuItem toggleConnectionItem = menu.findItem(R.id.menu_connection);
        if(mDroneManager.isconnect()) {
            toggleConnectionItem.setTitle(R.string.menu_disconnection);
            //Log.i(TAG, "DISCONN");
        } else {
            toggleConnectionItem.setTitle(R.string.menu_connection);
            //Log.i(TAG, "CONN");
        }

        return true;
    }
    */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        final MenuItem toggleConnectionItem = menu.findItem(R.id.menu_connection);
        if(mDroneManager.isconnect()) {
            toggleConnectionItem.setTitle(R.string.menu_disconnection);
            menu.setGroupEnabled(R.id.menu_group_connected, true);
            menu.setGroupVisible(R.id.menu_group_connected, true);
            Log.i(TAG, "DISCONN");
        } else {
            toggleConnectionItem.setTitle(R.string.menu_connection);
            menu.setGroupEnabled(R.id.menu_group_connected, false);
            menu.setGroupVisible(R.id.menu_group_connected, false);
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected int getNavigationDrawerEntryId() {
        return R.id.home_screen;
    }
}
