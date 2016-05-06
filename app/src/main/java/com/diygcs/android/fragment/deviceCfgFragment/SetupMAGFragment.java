package com.diygcs.android.fragment.deviceCfgFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diygcs.android.R;
import com.diygcs.android.fragment.BaseFragment;

/**
 * Created by Gui Zhou on 2016-04-12.
 */
public class SetupMAGFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setup_mag_main, container, false);

        return v;
    }
}
