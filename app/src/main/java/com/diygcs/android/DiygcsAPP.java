package com.diygcs.android;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.baidu.mapapi.SDKInitializer;
import com.diygcs.android.utils.DiygcsAPPPrefs;

import org.uah.core.MUHLink.MUHLinkConnection;
import org.uah.core.MUHLink.TCPConnection;
import org.uah.core.drone.Drone;
import org.uah.core.drone.DroneManager;

/**
 * Created by Gui Zhou on 2016-03-07.
 */
public class DiygcsAPP extends Application {

    private LocalBroadcastManager localBroadcastManager;
    private MUHLinkConnection droneConn;
    private DiygcsAPPPrefs mdiyPref;
    private DroneManager droneManager;
    private Drone drone;

    @Override
    public void onCreate() {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate();

        final Context context = getApplicationContext();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        drone = new Drone();
        mdiyPref = new DiygcsAPPPrefs(context);
        //droneConn = new TCPConnection(mdiyPref.getTcpServerIp(), mdiyPref.getTcpServerPort());
        droneManager = new DroneManager(context, droneConn, drone, localBroadcastManager);
    }

    public DroneManager getDroneManager() {
        return this.droneManager;
    }

    public Drone getDrone() {
        return this.drone;
    }

    public LocalBroadcastManager getLocalBroadcastManager() {
        return localBroadcastManager;
    }

}
