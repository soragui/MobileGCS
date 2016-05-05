package com.diygcs.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diygcs.android.DiygcsAPP;
import com.diygcs.android.R;
import com.diygcs.android.utils.AttributeEvent;
import com.diygcs.android.widgets.AttitudeIndicator;

import org.uah.core.drone.Drone;
import org.w3c.dom.Text;

/**
 * Created by ziwo5 on 2016-03-01.
 */

public class SimpleDataFragment extends BaseFragment {

    private static final String TAG = SimpleDataFragment.class.getSimpleName();

    private final static IntentFilter eventFilter = new IntentFilter();
    static {
        eventFilter.addAction(AttributeEvent.ATTITUDE_UPDATE);
        eventFilter.addAction(AttributeEvent.DRONE_HEARTBEAT_DATA);
    }

    private final BroadcastReceiver eventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "GET-COST");

            final String action = intent.getAction();
            final Drone drone = getDrone();

            if(AttributeEvent.DRONE_HEARTBEAT_DATA.equals(action)) {
                Log.i(TAG, "UPDATE-SIMPLE-DATA-VIEW");
                updateHeartBeatMsg(drone);
            }

            if(AttributeEvent.ATTITUDE_UPDATE.equals(action)) {
                Log.i(TAG, "UPDATE-ATTITUDE");
                updateAttitudeMsg(drone);
            }

        }
    };

    private AttitudeIndicator attitudeIndicator;
    private TextView roll;
    private TextView yaw;
    private TextView pitch;
    private TextView groundSpeed;
    private TextView airSpeed;
    private TextView climbRate;
    private TextView altitude;
    private TextView oilMeter;
    private TextView flyTime;

    private TextView timeMinute;
    private TextView timeSecond;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "REG-RECV");

        getApp().getLocalBroadcastManager().registerReceiver(eventReceiver, eventFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.simple_flight_data_layout, container, false);
        attitudeIndicator = (AttitudeIndicator) view.findViewById(R.id.aiView);

        roll = (TextView) view.findViewById(R.id.rollValueText);
        yaw = (TextView) view.findViewById(R.id.yawValueText);
        pitch = (TextView) view.findViewById(R.id.pitchValueText);

        groundSpeed = (TextView) view.findViewById(R.id.groundSpeedValue);
        airSpeed = (TextView) view.findViewById(R.id.airSpeedValue);
        altitude = (TextView) view.findViewById(R.id.AltitudeValue);
        oilMeter = (TextView) view.findViewById(R.id.oilMeterValue);

        climbRate = (TextView) view.findViewById(R.id.climbRateValue);

        timeMinute = (TextView) view.findViewById(R.id.flightTimeMinute);
        timeSecond = (TextView) view.findViewById(R.id.flightTimeSecond);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "UN-REG");
        getApp().getLocalBroadcastManager().unregisterReceiver(eventReceiver);
    }

    private void updateHeartBeatMsg(Drone drone) {

        this.groundSpeed.setText(String.format("%3.1f", drone.ground_speed));
        this.altitude.setText(String.format("%3.1f", drone.altitude));
        this.oilMeter.setText(String.format("%2.1f", drone.oil_meter));

        this.timeMinute.setText(String.format("%2d", drone.fly_time.getMinute()));
        this.timeSecond.setText(String.format("%2d", drone.fly_time.getSecond()));
    }

    private void updateAttitudeMsg(Drone drone) {

        float r = drone.attitude.getRollAngle();
        float y = drone.attitude.getYawAngle();
        float p = drone.attitude.getPitchAngle();

        attitudeIndicator.setAttitude(r, p, y);

        roll.setText(String.format("%3.0f\u00B0", r));
        pitch.setText(String.format("%3.0f\u00B0", p));
        yaw.setText(String.format("%3.0f\u00B0", y));

    }
}
