package com.MUHLink.common;

import com.MUHLink.MUHLinkPacket;
import com.MUHLink.Messages.MUHLinkMessage;
import com.MUHLink.Messages.MUHLinkPayload;

/**
 * Created by Gui Zhou on 2016-03-19.
 */
public class msg_attitude extends MUHLinkMessage {

    private static final String TAG = msg_attitude.class.getSimpleName();

    public float rollAngle;
    public float yawAngle;
    public float pitchAngle;

    public msg_attitude(MUHLinkPacket packet) {
        this.sysid = packet.sysID;
        this.compid = packet.compID;
        this.msgid = packet.sysID;
        this.unpack(packet.payload);
    }

    @Override
    public MUHLinkPacket pack() {
        return null;
    }

    @Override
    public void unpack(MUHLinkPayload payload) {
        payload.resetIndex();

        this.rollAngle = payload.getFloat();
        this.yawAngle = payload.getFloat();
        this.pitchAngle = payload.getFloat();
    }
}
