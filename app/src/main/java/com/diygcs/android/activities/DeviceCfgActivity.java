package com.diygcs.android.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.diygcs.android.R;
import com.diygcs.android.fragment.AnalogChannelFragment;
import com.diygcs.android.fragment.ControlSetupFragment;
import com.diygcs.android.fragment.RCTransmitFragment;
import com.diygcs.android.fragment.SelfFlySetupFragment;
import com.diygcs.android.fragment.SetupMAGFragment;
import com.diygcs.android.utils.AttributeEvent;

public class DeviceCfgActivity extends DrawerNavigationUI {

    private static final String TAG = DeviceCfgActivity.class.getSimpleName();

    /**
     * 事件监听注册
     */
    private static final IntentFilter eventFilter = new IntentFilter();
    static {
        eventFilter.addAction(AttributeEvent.DRONE_CONNECTED);
        eventFilter.addAction(AttributeEvent.DRONE_DISCONNECTED);
    }

    private final BroadcastReceiver eventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(AttributeEvent.DRONE_CONNECTED.equals(action)) {
                Log.i(TAG, "DRONE_CONN");
            }

            if(AttributeEvent.DRONE_DISCONNECTED.equals(action)) {
                Log.i(TAG, "DRONE_DIS");
            }
        }
    };

    /**
     *  飞机、通信状态指示灯
     */
    private TextView controlStateView;
    private TextView groundSkyView;
    private TextView linkStateView;

    private Button droneSetupBtn;
    private Button setupTypeBtn;
    private Button setupMagBtn;
    private Button rcTransmitBtn;
    private Button signalCaliBtn;
    private Button selfFlyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_cfg);

        initCompVariable();

        initCompListener();

    }

    void initCompVariable() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);

            LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(R.layout.activity_devicecfg_action_bar, null);

            actionBar.setCustomView(v);

            controlStateView = (TextView)v.findViewById(R.id.control_state_textView);
            controlStateView.setBackgroundResource(R.drawable.control_state_ok);

            groundSkyView = (TextView)v.findViewById(R.id.ground_sky_textView);
            groundSkyView.setBackgroundResource(R.drawable.ground_sky_ok);

            linkStateView = (TextView)v.findViewById(R.id.link_state_textView);
            linkStateView.setBackgroundResource(R.drawable.link_state_ok);
        }

        mdpApp.getLocalBroadcastManager().registerReceiver(eventReceiver, eventFilter);

        droneSetupBtn = (Button)findViewById(R.id.drone_type_select_btn);
        setupTypeBtn  = (Button)findViewById(R.id.setup_type_select_btn);
        setupMagBtn   = (Button)findViewById(R.id.setup_mag_btn);
        rcTransmitBtn = (Button)findViewById(R.id.rc_transmit_cali_btn);
        signalCaliBtn = (Button)findViewById(R.id.signal_cali_btn);
        selfFlyBtn    = (Button)findViewById(R.id.self_fly_btn);
    }

    void initCompListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(mDroneManager.isconnect()) {
            //menu.setGroupEnabled(R.id.menu_group_deviceConfig, true);
            //menu.setGroupVisible(R.id.menu_group_deviceConfig, true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        final MenuItem toggleConnectionItem = menu.findItem(R.id.menu_connection);
        if(mDroneManager.isconnect()) {
            toggleConnectionItem.setTitle(R.string.menu_disconnection);
            //menu.setGroupEnabled(R.id.menu_group_deviceConfig, true);
            //menu.setGroupVisible(R.id.menu_group_deviceConfig, true);
        } else {
            toggleConnectionItem.setTitle(R.string.menu_connection);
            //menu.setGroupEnabled(R.id.menu_group_deviceConfig, false);
            //menu.setGroupVisible(R.id.menu_group_deviceConfig, false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_connection:
                toggleDroneConnection();
                break;
            case R.id.menu_magneto_cali:
                handleFragment(R.id.menu_magneto_cali);
                break;
            case R.id.menu_rc_transmit_cali:
                handleFragment(R.id.menu_rc_transmit_cali);
                break;
            case R.id.menu_self_fly:
                handleFragment(R.id.menu_self_fly);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mdpApp.getLocalBroadcastManager().unregisterReceiver(eventReceiver);
    }

    private void handleFragment(int configScreenId) {
        final Fragment currentFragment = getCurrentFragment();

        if(currentFragment == null || getIdForFragmnet(currentFragment) != configScreenId) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.configuration_screen, getFragmentForId(configScreenId))
                    .commit();
        }
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.configuration_screen);
    }

    private Fragment getFragmentForId(int fragmentId) {
        final Fragment fragment;

        switch(fragmentId) {
            case R.id.menu_magneto_cali:
                fragment = new SetupMAGFragment();
                break;
            case R.id.menu_rc_transmit_cali:
                fragment = new RCTransmitFragment();
                break;
            case R.id.menu_self_fly:
                fragment = new SelfFlySetupFragment();
                break;
            default:
                fragment = new SetupMAGFragment();
                break;
        }

        return fragment;
    }

    private int getIdForFragmnet(Fragment fragment) {
        if(fragment instanceof SetupMAGFragment) {
            return R.id.menu_magneto_cali;
        } else if(fragment instanceof RCTransmitFragment) {
            return R.id.menu_rc_transmit_cali;
        } else if(fragment instanceof SelfFlySetupFragment) {
            return R.id.menu_self_fly;
        } else {
            return R.id.menu_magneto_cali;
        }
    }

    @Override
    protected int getNavigationDrawerEntryId() {
        return R.id.navigation_calibration;
    }
}
