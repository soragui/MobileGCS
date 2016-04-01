package org.uah.core.MUHLink;

import android.content.Context;
import android.util.Log;

import com.MUHLink.MUHLinkPacket;
import com.diygcs.android.utils.DiygcsAPPPrefs;

/**
 * Created by Gui Zhou on 2016/3/17.
 */
public class MUHLinkClient implements MUHLinkStream.MUHLinkOutputStream{

    private static final String TAG = MUHLinkClient.class.getSimpleName();

    private final MUHLinkConnectionListener mConnectionListener = new MUHLinkConnectionListener() {

        @Override
        public void onConnect() {
            listener.notifyConnected();
        }

        @Override
        public void onReceivePacket(MUHLinkPacket packet) {
            listener.notifyReceivedData(packet);
        }

        @Override
        public void onDisconnect() {
            listener.notifyDisconnected();
        }
    };

    protected DiygcsAPPPrefs mAppPrefs;
    private final MUHLinkStream.MuhLinkInputStream listener;
    private MUHLinkConnection droneConn;
    private final Context context;

    public MUHLinkClient(Context context, MUHLinkStream.MuhLinkInputStream listener, MUHLinkConnection droneConn) {
        this.context = context;
        this.listener = listener;
        this.droneConn = droneConn;

        mAppPrefs = new DiygcsAPPPrefs(context);
    }

    @Override
    public void sendMuhPacket(MUHLinkPacket packet) {

    }

    @Override
    public boolean isConnected() {
        if(droneConn != null)
            return droneConn.isConnected();
        else
            return false;
    }

    @Override
    public void openConnection(int connectionType) {

        if(connectionType == ConnectionType.TYPE_TCP) {
            Log.i(TAG, "CONN_VIA_TCP");
            droneConn = new TCPConnection(mAppPrefs.getTcpServerIp(), mAppPrefs.getTcpServerPort());
            //droneConn.connect();
        } else if(connectionType == ConnectionType.TYPE_UDP) {
            Log.i(TAG, "CONN_VIA_UDP");
            droneConn = new UDPConnection(mAppPrefs.getUdpServerPort());
        }

        if(!droneConn.isConnected())
            droneConn.connect();

        droneConn.addMUHLinkConnectionListener(TAG, mConnectionListener);
    }

    @Override
    public void closeConnection() {
        droneConn.disconnect();
    }
}
