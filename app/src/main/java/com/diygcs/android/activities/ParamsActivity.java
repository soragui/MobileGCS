package com.diygcs.android.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.diygcs.android.R;
import com.diygcs.android.fragment.SettingsFragment;

public class ParamsActivity extends DrawerNavigationUI {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        FragmentManager fm = getFragmentManager();
        Fragment settingsFragment = fm.findFragmentById(R.id.fragment_settings_layout);
        if(settingsFragment == null) {
            settingsFragment = new SettingsFragment();
            fm.beginTransaction().add(R.id.fragment_settings_layout, settingsFragment).commit();
        }

    }

    @Override
    protected int getNavigationDrawerEntryId() {
        return R.id.navigation_params;
    }
}
