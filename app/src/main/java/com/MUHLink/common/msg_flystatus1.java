package com.MUHLink.common;

import android.util.Log;

import com.MUHLink.MUHLinkPacket;
import com.MUHLink.Messages.MUHLinkMessage;
import com.MUHLink.Messages.MUHLinkPayload;
import com.MUHLink.enums.MUH_MSG_ID;

/**
 * Created by Gui Zhou on 2016-04-05.
 */
public class msg_flystatus1 extends MUHLinkMessage {

    public static final String TAG = msg_flystatus1.class.getSimpleName();

    public float theta;
    public float phi;
    public float psi;

    public float Qv;
    public float Pv;
    public float Rv;

    public float Axg;
    public float Ayg;
    public float Azg;

    public float ele_law;
    public float ail_law;
    public float rud_law;
    public float col_law;
    public float eng_law;

    public float ele_RC;
    public float ail_RC;
    public float rud_RC;
    public float col_RC;
    public float eng_RC;

    public msg_flystatus1(MUHLinkPacket muhLinkPacket) {
        this.sysid = muhLinkPacket.sysID;
        this.compid = muhLinkPacket.compID;
        this.msgid = MUH_MSG_ID.UAV_MSG_FCC_FLYSTATUS1;
        unpack(muhLinkPacket.payload);
    }

    @Override
    public MUHLinkPacket pack() {
        return null;
    }

    @Override
    public void unpack(MUHLinkPayload payload) {
        payload.resetIndex();

        this.theta = payload.getShort() * 0.01f;
        this.phi = payload.getShort() * 0.01f;
        this.psi = payload.getShort() * 0.01f;

        this.Qv = payload.getShort() * 0.01f;
        this.Pv = payload.getShort() * 0.01f;
        this.Rv = payload.getShort() * 0.01f;

        this.Axg = payload.getShort() * 0.01f;
        this.Ayg = payload.getShort() * 0.01f;
        this.Azg = payload.getShort() * 0.01f;

        this.ele_law = payload.getShort() * 0.01f;
        this.ail_law = payload.getShort() * 0.01f;
        this.rud_law = payload.getShort() * 0.01f;
        this.col_law = payload.getShort() * 0.01f;
        this.eng_law = payload.getShort() * 0.01f;

        this.ele_RC = payload.getShort() * 0.01f;
        this.ail_RC = payload.getShort() * 0.01f;
        this.rud_RC = payload.getShort() * 0.01f;
        this.col_RC = payload.getShort() * 0.01f;
        this.eng_RC = payload.getShort() * 0.01f;

        /**
         * 输出调试信息
         */
        Log.i(TAG, "Theta: " + this.theta);
        Log.i(TAG, "Psi: " + this.psi);
        Log.i(TAG, "Phi: " + this.phi);
    }
}
