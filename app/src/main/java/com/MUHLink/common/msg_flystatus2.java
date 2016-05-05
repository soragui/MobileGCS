package com.MUHLink.common;

import com.MUHLink.MUHLinkPacket;
import com.MUHLink.Messages.MUHLinkMessage;
import com.MUHLink.Messages.MUHLinkPayload;
import com.MUHLink.enums.MUH_MSG_ID;

/**
 * Created by Gui Zhou on 2016-04-05.
 */
public class msg_flystatus2 extends MUHLinkMessage {

    public double gps_lon;
    public double gps_lat;
    public float  gps_alt;
    public float gps_Vd;
    public float gps_Hdot;
    public float gps_track;

    public byte gps_freq;
    public byte imu_freq;
    public byte hmc_freq;

    public short data_rpm;

    public float Vx_var;
    public float Vy_var;
    public float Hdot_var;
    public float height_var;
    public float R_var;
    public float psi_var;
    public float ac_dY;

    public msg_flystatus2(MUHLinkPacket muhLinkPacket) {
        this.sysid = muhLinkPacket.sysID;
        this.compid = muhLinkPacket.compID;
        this.msgid = MUH_MSG_ID.UAV_MSG_FCC_FLYSTATUS2;
        unpack(muhLinkPacket.payload);
    }


    @Override
    public MUHLinkPacket pack() {
        return null;
    }

    @Override
    public void unpack(MUHLinkPayload payload) {

    }
}
