package com.diygcs.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.diygcs.android.R;

/**
 * Created by ziwo5 on 2016-03-07.
 */
public class DiygcsAPPPrefs {

    private static final String DEFAULT_CONNECTION_TYPE = "TCP";
    private static final String DEFAULT_TCP_SERVER_IP = "192.168.1.107";
    private static final String DEFAULT_TCP_SERVER_PORT = "5763";
    private static final String DEFAULT_UDP_SERVER_PORT = "12345";

    /*
     * Public for legacy usage
     */
    public SharedPreferences prefs;
    private Context context;

    public DiygcsAPPPrefs(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     *
     * @return 选择的连接方式
     */
    public int getConnectionParameterType() {
        return Integer.parseInt(prefs.getString(context.getString(R.string.
                pref_connection_type_key), DEFAULT_CONNECTION_TYPE));
    }

    public int getUdpServerPort() {
        return Integer.parseInt(prefs.getString(context.getString(R.string.pref_udp_server_port_key),
                DEFAULT_UDP_SERVER_PORT));
    }

    public String getTcpServerIp(){
        return prefs.getString(context.getString(R.string.pref_server_ip_key),
                DEFAULT_TCP_SERVER_IP);
    }

    public void setTcpServerIp(String serverIp) {
        prefs.edit().putString(context.getString(R.string.pref_server_ip_key), serverIp).apply();
    }

    public int getTcpServerPort(){
        return Integer.parseInt(prefs.getString(context.getString(R.string.pref_server_port_key),
                DEFAULT_TCP_SERVER_PORT));
    }

    public void setTcpServerPort(int serverPort) {
        prefs.edit().putString(context.getString(R.string.pref_server_port_key),
                String.valueOf(serverPort)).apply();
    }

}
