package com.diygcs.android.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.diygcs.android.maps.BaiduMapFragment;

/**
 * Created by ziwo5 on 2016-03-04.
 */
public class FlightMapFragment extends BaiduMapFragment {

    private static final String TAG = FlightMapFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view =  super.onCreateView(layoutInflater, viewGroup, bundle);

        return view;
    }

    public void goToMyLocation() {
        mLocClient.requestLocation();
    }

    /**
     *  以动画的形式放大、缩小地图
     * @param amount 3-20 正缩小、负放大
     */
    public void zoomMap(float amount) {
        getBaiduMap().animateMapStatus(MapStatusUpdateFactory.zoomBy(amount));
    }

    public void goToLocation(LatLng latLng, int direction) {
        MyLocationData locationData = new MyLocationData.Builder()
                .accuracy(200)
                .direction(direction).latitude(latLng.latitude)
                .longitude(latLng.longitude).build();
        getBaiduMap().setMyLocationData(locationData);
    }
}
