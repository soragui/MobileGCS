package com.diygcs.android.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.diygcs.android.DiygcsAPP;
import com.diygcs.android.R;
import com.diygcs.android.fragment.DroneCommandFragment;
import com.diygcs.android.fragment.FlightMapFragment;
import com.diygcs.android.fragment.SimpleDataFragment;
import com.diygcs.android.maps.BaiduMapFragment;
import com.diygcs.android.maps.MapViewFragment;
import com.diygcs.android.utils.DiygcsAPPPrefs;
import com.diygcs.android.widgets.CircleTextView;

import org.uah.core.MUHLink.TCPConnection;
import org.uah.core.drone.Drone;
import org.uah.core.drone.DroneManager;

public class MainActivity extends DrawerNavigationUI implements
        MapViewFragment.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     *  无人机各种指示灯
     */
    private TextView controlStateView;
    private TextView groundSkyView;
    private TextView linkStateView;

    private FlightMapFragment mapFragment;

    private View mLocationButtonsContainer;
    private View mMapViewTypeContainer;
    private ImageButton mGoToMyLocation;
    private ImageButton mGoToDroneLocation;
    private ImageButton mapZoomin;
    private ImageButton mapZoomout;
    private ImageButton mapTypeChange;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCompVariable();

        initCompListener();

    }

    /**
     *  初始化组件变量
     */
    private void initCompVariable() {
        fragmentManager = getSupportFragmentManager();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);

            LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(R.layout.activity_main_action_bar, null);

            actionBar.setCustomView(v);

            controlStateView = (TextView)v.findViewById(R.id.control_state_textView);
            controlStateView.setBackgroundResource(R.drawable.control_state_ok);

            groundSkyView = (TextView)v.findViewById(R.id.ground_sky_textView);
            groundSkyView.setBackgroundResource(R.drawable.ground_sky_ok);

            linkStateView = (TextView)v.findViewById(R.id.link_state_textView);
            linkStateView.setBackgroundResource(R.drawable.link_state_ok);

        }

        final SlidingDrawer simpleDataDrawer = (SlidingDrawer)findViewById(R.id.simple_flight_data);
        //Only the phone layout has the sliding drawer
        if(simpleDataDrawer != null) {
            simpleDataDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
                @Override
                public void onDrawerClosed() {
                    final int slidingDrawerHeight = simpleDataDrawer.getContent().getHeight();
                    final boolean isSlidingDrawerOpened = simpleDataDrawer.isOpened();
                    updateMapViewButtonsMargin(isSlidingDrawerOpened, slidingDrawerHeight-40);
                }
            });
            simpleDataDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
                @Override
                public void onDrawerOpened() {
                    final int slidingDrawerHeight = simpleDataDrawer.getContent().getHeight();
                    final boolean isSlidingDrawerOpened = simpleDataDrawer.isOpened();
                    updateMapViewButtonsMargin(isSlidingDrawerOpened, slidingDrawerHeight-40);
                }
            });
        }

        final SlidingDrawer droneCommandDrawer = (SlidingDrawer)findViewById(R.id.drone_command);
        //Only the phone layout has the sliding drawer
        if(droneCommandDrawer != null) {
            droneCommandDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
                @Override
                public void onDrawerClosed() {
                    final int slidingDrawerHeight = droneCommandDrawer.getContent().getHeight();
                    final boolean isSlidingDrawerOpened = droneCommandDrawer.isOpened();
                    updateLocationButtonsMargin(isSlidingDrawerOpened, slidingDrawerHeight);
                }
            });
            droneCommandDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
                @Override
                public void onDrawerOpened() {
                    final int slidingDrawerHeight = droneCommandDrawer.getContent().getHeight();
                    final boolean isSlidingDrawerOpened = droneCommandDrawer.isOpened();
                    updateLocationButtonsMargin(isSlidingDrawerOpened, slidingDrawerHeight);
                }
            });
        }

        setupMapFragment();

        mLocationButtonsContainer = findViewById(R.id.location_button_container);
        mMapViewTypeContainer     = findViewById(R.id.map_view_button_container);
        mGoToMyLocation = (ImageButton)findViewById(R.id.my_location_button);
        mGoToDroneLocation = (ImageButton)findViewById(R.id.drone_location_button);
        mapTypeChange = (ImageButton)findViewById(R.id.map_type_btn);
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

        /**
         * Drone Command Fragment
         */
        DroneCommandFragment droneCommandFragment = (DroneCommandFragment)fragmentManager
                .findFragmentById(R.id.drone_command_view);
        if(droneCommandFragment == null) {
            droneCommandFragment = new DroneCommandFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.drone_command_view, droneCommandFragment)
                    .commit();
        }
    }

    /**
     *  初始化组件事件监听函数
     */
    private void initCompListener() {

        mapTypeChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mapFragment.getMapType() == BaiduMap.MAP_TYPE_NORMAL) {
                    mapFragment.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    mapTypeChange.setImageResource(R.drawable.map_satellite);
                } else {
                    mapFragment.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    mapTypeChange.setImageResource(R.drawable.map_normal);
                }
            }
        });

        mGoToMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapFragment.goToMyLocation();
            }
        });

        mapZoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mapFragment != null) {
                    mapFragment.zoomMap(0.2f);
                }
            }
        });

        mapZoomin.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if (mapFragment != null) {
                    mapFragment.zoomMap(0.2f);
                    return true;
                }
                return false;
            }
        });

        mapZoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapFragment != null) {
                    mapFragment.zoomMap(-0.2f);
                }
            }
        });

        mapZoomout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mapFragment != null) {
                    mapFragment.zoomMap(-0.2f);
                    return true;
                }
                return false;
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

    /**
     * Account for the various ui elements and update the map padding so that it
     *  remains 'vivible'.
     *
     * @param isOpened
     * @param drawerHeight
     */
    private void updateLocationButtonsMargin(boolean isOpened, int drawerHeight) {

        // Update the right margin for the my location button
        final ViewGroup.MarginLayoutParams marginLp = (ViewGroup.MarginLayoutParams) mLocationButtonsContainer
                .getLayoutParams();

        final int bottomMargin;
        if(isOpened) {
            bottomMargin = marginLp.bottomMargin + drawerHeight;

        } else {

            bottomMargin = marginLp.bottomMargin - drawerHeight;
        }

        marginLp.setMargins(marginLp.leftMargin, marginLp.topMargin, marginLp.rightMargin,
                bottomMargin);
        mLocationButtonsContainer.requestLayout();

    }


    /**
     *
     * @param isOpened
     * @param drawerHeight
     */
    private void updateMapViewButtonsMargin(boolean isOpened, int drawerHeight) {
        // Update the right margin for the my location button
        final ViewGroup.MarginLayoutParams marginLp = (ViewGroup.MarginLayoutParams) mMapViewTypeContainer
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
        mMapViewTypeContainer.requestLayout();
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
