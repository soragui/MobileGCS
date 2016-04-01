package com.MUHLink.common;

import android.util.Log;

import com.MUHLink.MUHLinkPacket;
import com.MUHLink.Messages.MUHLinkMessage;
import com.MUHLink.Messages.MUHLinkPayload;
import com.MUHLink.enums.MUH_MSG_ID;

/**
 * Created by Gui Zhou on 2016-03-10.
 */

public class msg_heartbeat extends MUHLinkMessage {

    private static final String TAG = msg_heartbeat.class.getSimpleName();

    public byte type_num;
    public byte contral_mode;
    public byte fly_state;
    public byte fly_mode;

    public float longitude;
    public float latitude;
    public float altitude;

    public float ground_speed;
    public float vertical_speed;
    public float line_angle;
    public float flight_angle;

    public float cpu_ratio;
    public float voltage;
    public float oil_meter;

    public short fly_time;


    public msg_heartbeat(MUHLinkPacket muhLinkPacket) {
        this.sysid = muhLinkPacket.sysID;
        this.compid = muhLinkPacket.compID;
        this.msgid = MUH_MSG_ID.UAV_MSG_FCC_HEARTBEAT;
        unpack(muhLinkPacket.payload);
    }

    @Override
    public MUHLinkPacket pack() {
        return null;
    }

    @Override
    public void unpack(MUHLinkPayload payload) {
        payload.resetIndex();

        this.type_num = payload.getByte();
        this.contral_mode = payload.getByte();
        this.fly_state = payload.getByte();
        this.fly_mode = payload.getByte();

        this.longitude = payload.getFloat();
        this.latitude = payload.getFloat();
        this.altitude = payload.getFloat();

        this.vertical_speed = payload.getFloat();
        this.ground_speed = payload.getFloat();
        this.line_angle = payload.getFloat();
        this.flight_angle = payload.getFloat();

        this.cpu_ratio = payload.getFloat();
        this.voltage = payload.getFloat();
        this.oil_meter = payload.getFloat();

        this.fly_time = payload.getShort();

        Log.i(TAG, "type_num " + type_num);
        Log.i(TAG, "contral_mode " + contral_mode);
        Log.i(TAG, "fly_state " + fly_state);
        Log.i(TAG, "fly_mode " + fly_mode);

        Log.i(TAG, "Longitude " + longitude);
        Log.i(TAG, "Latitude " + latitude);
        Log.i(TAG, "Altitude " + altitude);

        Log.i(TAG, "vertical_speed " + vertical_speed);
        Log.i(TAG, "ground_speed " + ground_speed);
        Log.i(TAG, "line_angle " + line_angle);
        Log.i(TAG, "flight_angle " + flight_angle);

        Log.i(TAG, "cpu_ratio " + cpu_ratio);
        Log.i(TAG, "oil_meter " + oil_meter);
        Log.i(TAG, "voltage " + voltage);

        Log.i(TAG, "Fly_time " + fly_time );
    }
}
