package com.example.praveenp.allianz.Activity;

import android.app.Activity;

import com.example.praveenp.allianz.dao.Country;
import com.example.praveenp.allianz.dao.DeviceConfig;

import java.util.List;

/**
 * Created by praveenp on 24-01-2016.
 */
public interface DeviceListView {
    public void showProgress();

    public void hideProgress();

    public Activity getActivity();

    public void updateDeviceInfo(List<Country> configList);
}
