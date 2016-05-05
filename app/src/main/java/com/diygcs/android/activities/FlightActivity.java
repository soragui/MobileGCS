package com.diygcs.android.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.diygcs.android.DiygcsAPP;
import com.diygcs.android.R;
import com.diygcs.android.utils.AttributeEvent;

import org.uah.core.drone.Drone;

public class FlightActivity extends DrawerNavigationUI {

    private static final String TAG = FlightActivity.class.getSimpleName();

    private final static IntentFilter eventFilter = new IntentFilter();
    static {
        eventFilter.addAction(AttributeEvent.DRONE_HEARTBEAT_DATA);
        eventFilter.addAction(AttributeEvent.DRONE_FLYSTATUS1_DATA);
        eventFilter.addAction(AttributeEvent.DRONE_FLYSTATUS2_DATA);
    }

    private final BroadcastReceiver eventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            final Drone drone = diygcsAPP.getDrone();

            if(AttributeEvent.DRONE_HEARTBEAT_DATA.equals(action)) {
                Log.i(TAG, "GET-HEARTBEAT");
                updateHeartBeat(drone);
            }

            if(AttributeEvent.DRONE_FLYSTATUS1_DATA.equals(action)) {
                updateFlystatus1(drone);
            }

            if(AttributeEvent.DRONE_FLYSTATUS2_DATA.equals(action)) {
                updateFlystatus2(drone);
            }
        }
    };

    /**
     * 所有要显示的数据列表
     */
    private TextView flightState;
    private TextView contralMode;
    private TextView flightMode;

    private TextView longitude;
    private TextView latitude;
    private TextView altitude;

    private TextView roll;
    private TextView pitch;
    private TextView yaw;

    private TextView oilMeter;
    private TextView flyTime;
    private TextView cpuRatio;

    private DiygcsAPP diygcsAPP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        initComp();

        /**
         * 注册事件监听
         */
        diygcsAPP.getLocalBroadcastManager().registerReceiver(eventReceiver, eventFilter);
    }

    /**
     *  初始化所有显示的数据组件
     */

    private void initComp() {

        diygcsAPP = (DiygcsAPP)getApplication();

        flightState = (TextView)findViewById(R.id.flightStateText);
        contralMode = (TextView)findViewById(R.id.contralModeText);
        flightMode = (TextView)findViewById(R.id.flightModeText);

        longitude = (TextView)findViewById(R.id.LongitudeText);
        latitude = (TextView)findViewById(R.id.LatitudeText);
        altitude = (TextView)findViewById(R.id.AltitudeText);

        roll = (TextView)findViewById(R.id.rollText);
        pitch = (TextView)findViewById(R.id.pitchText);
        yaw = (TextView)findViewById(R.id.yawText);

        oilMeter = (TextView)findViewById(R.id.oilMeterText);
        flyTime = (TextView)findViewById(R.id.fligtTimeText);
        cpuRatio = (TextView)findViewById(R.id.cpuRatioText);
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
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 解除事件监听注册
         */
        diygcsAPP.getLocalBroadcastManager().unregisterReceiver(eventReceiver);
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

    private void updateHeartBeat(Drone drone) {

        longitude.setText(String.format("%3.6f", drone.longitude));
        latitude.setText(String.format("%3.6f", drone.latitude));
        altitude.setText(String.format("%3.2f", drone.altitude));

        flyTime.setText(String.format("%dm%ds", drone.fly_time.getMinute()
            , drone.fly_time.getSecond()));

    }

    private void updateFlystatus1(Drone drone) {
        roll.setText(String.format("%3.2f°", drone.psi));
        yaw.setText(String.format("%3.2f°", drone.theta));
        pitch.setText(String.format("%3.2f°", drone.phi));
    }

    private void updateFlystatus2(Drone drone) {

    }

}
