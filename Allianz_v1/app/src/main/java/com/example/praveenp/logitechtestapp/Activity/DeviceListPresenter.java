package com.example.praveenp.logitechtestapp.Activity;

import android.widget.Toast;

import com.example.praveenp.logitechtestapp.dao.Countries;
import com.example.praveenp.logitechtestapp.dao.Country;
import com.example.praveenp.logitechtestapp.dao.DeviceConfig;
import com.example.praveenp.logitechtestapp.util.NetworkListener;
import com.example.praveenp.logitechtestapp.util.NetworkTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by praveenp on 24-01-2016.
 */
public class DeviceListPresenter implements NetworkListener {
    DeviceListView mView;
    List<DeviceConfig> mConfigList = new ArrayList<DeviceConfig>();
    Thread mThread;
    public void attachView(DeviceListView view){
        mView = view;
    }
    public void requestDeviceData(){
        mView.showProgress();
        if(mThread == null){
            mThread = new Thread(NetworkTask.getNetworkTask(this));
            mThread.start();
        }

    }
    @Override
    public void onTaskCompleted(String response) {
        if(response == null || response.length() == 0){
            mView.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mView.getActivity(), "Unable to get Response...!", Toast.LENGTH_LONG);
                }
            });
        }

        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Country>>(){}.getType();
        List<Country> enums = gson.fromJson(response, collectionType);
        mView.updateDeviceInfo(enums);
        mView.hideProgress();
    }

}
