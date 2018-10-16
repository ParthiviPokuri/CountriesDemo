package com.example.praveenp.logitechtestapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.praveenp.logitechtestapp.R;
import com.example.praveenp.logitechtestapp.adapter.DeviceAdapter;
import com.example.praveenp.logitechtestapp.dao.Country;
import com.example.praveenp.logitechtestapp.dao.DeviceConfig;
import com.example.praveenp.logitechtestapp.dao.Language;
import com.example.praveenp.logitechtestapp.listeners.CountrySelectionListener;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends Activity implements DeviceListView, CountrySelectionListener {

    private ProgressDialog mProgressDialog ;
    private DeviceListPresenter mPresenter;
    private ListView mListView;
    private DeviceAdapter mAdapter;
    private List<Country> mCountryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_list);

        initView();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait....");

        mPresenter = new DeviceListPresenter();
        mPresenter.attachView(this);
        mPresenter.requestDeviceData();

    }

    private void initView(){
        mListView = (ListView) findViewById(R.id.devices_list);
        mListView.setEmptyView(findViewById(R.id.emptyview));
        mAdapter = new DeviceAdapter(this,new ArrayList());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                Country country = mCountryList.get(postion);
                Intent i = new Intent(DeviceListActivity.this,DetailsActivity.class);
                i.putExtra("COUNTRY_NAME",country.getName());
                i.putExtra("COUNTRY_FLAG",country.getFlag());
                StringBuffer lngStr = new StringBuffer();
                for (Language language : country.getLanguages()){
                   lngStr.append(", "+language.getName());
                }
                i.putExtra("COUNTRY_LANG", lngStr.toString());
                i.putExtra("COUNTRY_CALLING_CODE", country.getCallingCodes().get(0));
                startActivity(i);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        hideProgress();
        super.onPause();
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void showProgress() {
        if (mProgressDialog != null && !isFinishing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isProgressDialogShowing()){
                    mProgressDialog.dismiss();
                }
            }
        });

    }

    @Override
    public Activity getActivity() {

        return this;
    }

    public boolean isProgressDialogShowing() {
        if (mProgressDialog != null) {
            return mProgressDialog.isShowing();
        }
        return false;
    }

    @Override
    public void updateDeviceInfo(final List<Country> configList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCountryList = configList;
                mAdapter.updateDeviceInfo(mCountryList);
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCountrySelect(Country country) {

    }
}
