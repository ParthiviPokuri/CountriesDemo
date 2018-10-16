package com.example.praveenp.allianz.util;

import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by praveenp on 24-01-2016.
 */
public class NetworkTask implements Runnable {
    public static final String DEVICES_URL = "https://restcountries.eu/rest/v2/all";
    private NetworkListener mListener;
    private NetworkTask(NetworkListener listener){
        mListener = listener;
    }
    public static NetworkTask getNetworkTask(NetworkListener listener){
        return new NetworkTask(listener);
    }
    public String connectAndGetResponse() {
        String response = null ;
        try {
            URL url = new URL(DEVICES_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = getResponse(connection.getInputStream());
               // responseObj = new JSONObject(response);
               // responseAarray = obj.getJSONArray("devices");
                //Log.i("PRAV","responseAarray "+responseAarray);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public String getResponse(InputStream iStream){
        StringBuffer response = new StringBuffer();
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return response.toString();
    }

    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
        Log.i("PRAV","mListener "+mListener);
        if(mListener != null) {
            mListener.onTaskCompleted(connectAndGetResponse());
        }
    }
}
