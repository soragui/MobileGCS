package org.uah.core.drone;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.MUHLink.MUHLinkPacket;
import com.MUHLink.common.msg_attitude;
import com.MUHLink.common.msg_flystatus1;
import com.MUHLink.common.msg_flystatus2;
import com.MUHLink.common.msg_heartbeat;
import com.MUHLink.enums.MUH_MSG_ID;
import com.diygcs.android.utils.AttributeEvent;

import org.uah.core.MUHLink.MUHLinkClient;
import org.uah.core.MUHLink.MUHLinkConnection;
import org.uah.core.MUHLink.MUHLinkStream;
import org.uah.core.MUHLink.TCPConnection;

/**
 * Created by Gui Zhou on 2016/3/17.
 */

public class DroneManager implements MUHLinkStream.MuhLinkInputStream {

    private static final String TAG = DroneManager.class.getSimpleName();

    private final LocalBroadcastManager localBroadcastManager;
    private final MUHLinkClient droneClient;
    private final MUHLinkConnection droneConn;
    private final Context context;
    private final Drone drone;
    private Intent intent;

    public DroneManager(Context context, MUHLinkConnection droneConn, Drone drone, LocalBroadcastManager localBroadcastManager) {
        this.context = context;
        this.droneConn = droneConn;
        this.drone = drone;
        this.localBroadcastManager = localBroadcastManager;

        droneClient = new MUHLinkClient(context, this, this.droneConn);
    }

    public void connect(int connectionType) {
        droneClient.openConnection(connectionType);
    }

    public void disconnect() {
        droneClient.closeConnection();
    }

    public boolean isconnect() {
        return droneClient.isConnected();
    }

    public void sendBroadCast(String broadName) {
        intent = new Intent(broadName);
        localBroadcastManager.sendBroadcast(intent);
    }

    public void sendMuhPacket(MUHLinkPacket packet) {
        droneClient.sendMuhPacket(packet);
    }

    @Override
    public void notifyConnected() {
        sendBroadCast(AttributeEvent.DRONE_CONNECTED);
    }

    @Override
    public void notifyDisconnected() {
        sendBroadCast(AttributeEvent.DRONE_DISCONNECTED);
    }

    @Override
    public void notifyReceivedData(MUHLinkPacket packet) {
        Log.i(TAG, "Receive-Data Success.");
        switch (packet.compID) {
            /**
             *  飞控机组件ID 消息的处理
             */
            case MUH_MSG_ID.UAV_COMP_ID_FCC: {
                switch (packet.msgID) {
                    case MUH_MSG_ID.UAV_MSG_FCC_HEARTBEATD:
                        drone.getHeartbeatMsg(new msg_heartbeat(packet));
                        sendBroadCast(AttributeEvent.DRONE_HEARTBEAT_DATA);
                        Log.i(TAG, "GET-HEARTBEAT-MSG");
                        return ;
                    case MUH_MSG_ID.UAV_MSG_FCC_FLYSTATUS1:
                        drone.getFlyStatus1(new msg_flystatus1(packet));
                        sendBroadCast(AttributeEvent.DRONE_FLYSTATUS1_DATA);
                        Log.i(TAG, "GET-FLYSTATUS1-MSG");
                        return;
                    case MUH_MSG_ID.UAV_MSG_FCC_FLYSTATUS2:
                        drone.getFlyStatus2(new msg_flystatus2(packet));
                        sendBroadCast(AttributeEvent.DRONE_FLYSTATUS2_DATA);
                        Log.i(TAG, "GET-FLYSTATUS2-MSG");
                        return;
                    default:
                        return ;
                }
            }

            default:
                return ;
        }
    }
}
