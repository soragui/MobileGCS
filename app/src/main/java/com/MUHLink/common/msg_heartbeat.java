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

    public byte uasInfo;
    public byte run_mode;

    public byte mByteThree;
    public byte mLock_stk;
    public byte CtrlMode_stk;
    public byte statusOnSky;
    public byte stateOnReset;

    public byte mode_nav;
    public byte mode_vert;
    public byte mode_guid;

    public double ac_lon;
    public double ac_lat;

    public float ac_Vd;
    public float ac_Hdot;
    public float ac_gPsi;
    public float ac_hPsi;
    public float ac_height;

    public short flyTime;
    public byte  gpsState;
    public byte  rollstate;

    public float oilEng;
    public float voltCell;
    public float voltBatt;
    public float templiquid;
    public float tempEng[] = new float[2];
    public float ac_dot;
    public float ac_dL;
    public float freq_link;

    public msg_heartbeat(MUHLinkPacket muhLinkPacket) {
        this.sysid = muhLinkPacket.sysID;
        this.compid = muhLinkPacket.compID;
        this.msgid = MUH_MSG_ID.UAV_MSG_FCC_HEARTBEATD;
        unpack(muhLinkPacket.payload);
    }

    @Override
    public MUHLinkPacket pack() {
        return null;
    }

    @Override
    public void unpack(MUHLinkPayload payload) {
        payload.resetIndex();

        /**
         * 接受解析数据
         */
        this.uasInfo = payload.getByte();
        this.run_mode = payload.getByte();

        this.mByteThree = payload.getByte();
        this.mLock_stk = (byte)(mByteThree & 0x80);
        this.CtrlMode_stk = (byte)(mByteThree & 0x0F);
        this.statusOnSky = (byte)(mByteThree & 0x40);
        this.stateOnReset = (byte)(mByteThree & 0x20);

        this.mode_nav = payload.getByte();
        this.mode_vert = payload.getByte();
        this.mode_guid = payload.getByte();

        this.ac_lon = payload.getDouble();
        this.ac_lat = payload.getDouble();

        this.ac_Vd = payload.getShort()/10.0f;
        this.ac_height = payload.getShort()/10.0f;
        this.ac_Hdot = payload.getByte()/10.0f;
        this.ac_gPsi = payload.getShort()/100.0f;
        this.ac_hPsi = payload.getShort()/100.0f;

        this.gpsState = payload.getByte();
        this.flyTime = payload.getShort();
        this.oilEng = payload.getByte();
        this.voltCell = payload.getShort()/10.0f;
        this.voltBatt = payload.getShort()/10.0f;

        this.templiquid = payload.getByte();
        this.tempEng[0] = payload.getByte();
        this.tempEng[1] = payload.getByte();
        this.ac_dot = payload.getByte();
        this.ac_dL = payload.getShort();
        this.freq_link = payload.getByte();
        this.rollstate = payload.getByte();

        /**
         * 输出调试信息
         */
        Log.i(TAG, "uasInfo: " + this.uasInfo);
        Log.i(TAG, "run_mode: " + this.run_mode);

        Log.i(TAG, "mLock_stk: " + this.mLock_stk);
        Log.i(TAG, "CtrlMode_stk: " + this.CtrlMode_stk);
        Log.i(TAG, "statusOnSky: " + this.statusOnSky);
        Log.i(TAG, "stateOnReset: " + this.stateOnReset);

        Log.i(TAG, "mode_nav: " + this.mode_nav);
        Log.i(TAG, "mode_vert: " + this.mode_vert);
        Log.i(TAG, "mode_guid: " + this.mode_guid);

        Log.i(TAG, "ac_lon: " + this.ac_lon);
        Log.i(TAG, "ac_lat: " + this.ac_lat);
        Log.i(TAG, "ac_height: " + this.ac_height);

        Log.i(TAG, "ac_Vd: " + this.ac_Vd);
        Log.i(TAG, "ac_Hdot: " + this.ac_Hdot);
        Log.i(TAG, "ac_gPsi: " + this.ac_gPsi);
        Log.i(TAG, "ac_hPsi: " + this.ac_hPsi);
        Log.i(TAG, "gpsState: " + this.gpsState);
        Log.i(TAG, "flyTime: " + this.flyTime);

        Log.i(TAG, "oilEng: " + this.oilEng);
        Log.i(TAG, "voltCell: " + this.voltCell);
        Log.i(TAG, "voltBatt: " + this.voltBatt);

        Log.i(TAG, "templiquid: " + this.templiquid);
        Log.i(TAG, "tempEngTwo: " + this.tempEng[0]);
        Log.i(TAG, "tempEngOne: " + this.tempEng[1]);
        Log.i(TAG, "ac_dot: " + this.ac_dot);
        Log.i(TAG, "ac_dL: " + this.ac_dL);
        Log.i(TAG, "freq_link: " + this.freq_link);
        Log.i(TAG, "rollstate: " + this.rollstate);
    }
}
