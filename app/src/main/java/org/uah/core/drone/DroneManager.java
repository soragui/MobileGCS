package org.uah.core.drone;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.MUHLink.MUHLinkPacket;
import com.MUHLink.common.msg_attitude;
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

    @Override
    public void notifyConnected() {

    }

    @Override
    public void notifyDisconnected() {

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
                    case MUH_MSG_ID.UAV_MSG_FCC_HEARTBEAT:
                        drone.getHeartbeatMsg(new msg_heartbeat(packet));
                        intent = new Intent(AttributeEvent.DRONE_HEARTBEAT_DATA);
                        localBroadcastManager.sendBroadcast(intent);
                        Log.i(TAG, "GET-HEARTBEAT-MSG");
                        return ;
                    case MUH_MSG_ID.UAB_MSG_FCC_ATTITUDE:
                        drone.getAttitudeMsg(new msg_attitude(packet));
                        intent = new Intent(AttributeEvent.ATTITUDE_UPDATE);
                        localBroadcastManager.sendBroadcast(intent);
                        Log.i(TAG, "GET-HEARTBEAT-MSG");
                    default:
                        return ;
                }
            }

            default:
                return ;
        }
    }
}
