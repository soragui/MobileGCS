package com.diygcs.android.fragment.deviceCfgFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diygcs.android.R;
import com.diygcs.android.fragment.BaseFragment;

/**
 * Created by Gui Zhou on 2016-05-06.
 */

public class DroneTypeSelectFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_drone_type_select, container, false);

        return v;
    }

}
