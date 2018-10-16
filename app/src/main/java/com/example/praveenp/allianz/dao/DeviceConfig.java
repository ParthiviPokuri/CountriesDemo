package com.example.praveenp.allianz.dao;

/**
 * Created by praveenp on 24-01-2016.
 */
public class DeviceConfig {

    private String deviceType;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String model;
    private String name;

    private void DeviceConfig(){

    }

    public static DeviceConfig newInstance(){
        return new DeviceConfig();
    }
}
