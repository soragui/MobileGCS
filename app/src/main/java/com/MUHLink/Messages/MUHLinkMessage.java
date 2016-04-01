package com.MUHLink.Messages;

import com.MUHLink.MUHLinkPacket;

import java.io.Serializable;

/**
 * Created by ziwo5 on 2016-03-10.
 */

/**
 *  这是所有MUHLink消息的通用接口
 */

public abstract class MUHLinkMessage implements Serializable {

    public int sysid;
    public int compid;
    public int msgid;

    public abstract MUHLinkPacket pack();
    public abstract void unpack(MUHLinkPayload payload);

}
