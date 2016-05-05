package com.diygcs.android.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.diygcs.android.DiygcsAPP;

import org.uah.core.drone.Drone;
import org.uah.core.drone.DroneManager;

/**
 * Created by Gui Zhou on 2016-04-14.
 */
public class BaseFragment extends Fragment {

    /**
     *  Fragment 共用的全局变量
     */
    private DiygcsAPP mdpApp;
    protected DroneManager mDroneManager;

    protected Drone getDrone() {
        return mdpApp.getDrone();
    }

    protected DiygcsAPP getApp() {
        return this.mdpApp;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mdpApp = (DiygcsAPP) activity.getApplication();
    }
}
