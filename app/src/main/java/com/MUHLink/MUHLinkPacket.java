package com.MUHLink;

/**
 * Created by Gui Zhou on 2016-03-10.
 */

import com.MUHLink.Messages.MUHLinkMessage;
import com.MUHLink.Messages.MUHLinkPayload;
import com.MUHLink.common.CRC;
import com.MUHLink.common.msg_attitude;
import com.MUHLink.common.msg_heartbeat;
import com.MUHLink.enums.MUH_MSG_ID;

import java.io.Serializable;

/**
 * MUH 地面站通信协议 通用数据结构和格式低定义
 * 字节索引号    内容      值
 * 0         包同步头1    0xEB
 * 1         包同步头2    0x90
 * 2        有效数据长度  0-255
 * 3         包序列      0-255
 * 4          系统ID     0-255
 * 5          组件ID     0-255
 * 6          消息ID     0-255
 * 7-(N+7)    有效数据    N(0-255)
 * N+8        校验低位
 * N+9        校验高位
 */

public class MUHLinkPacket implements Serializable{

    /**
     * 定义两个包同步头
     */
    public static final int MUHLINK_SYNC_HEAD_1 = MUH_MSG_ID.DATALINK_STX1;
    public static final int MUHLINK_SYNC_HEAD_2 = MUH_MSG_ID.DATALINK_STX2;

    /**
     *  数据长度
     */
    public byte len;

    /**
     *  包序列
     */
    public byte seq;

    /**
     *  各种 ID
     */
    public byte sysID;
    public byte compID;
    public byte msgID;

    /**
     * 有效的消息数据
     */
    public MUHLinkPayload payload;

    /**
     * 数据校验和
     */
    public CRC crc;

    public MUHLinkPacket() {
        payload = new MUHLinkPayload();
    }

    /**
     * 检查消息是否满载
     * @return  是返回 true; 否返回 false.
     */
    public boolean payloadFilled() {
        if(payload.size() >= MUHLinkPayload.MAX_PAYLOAD_SIZE-1) {
            return true;
        }
        return (payload.size() == len);
    }

    /**
     * 生成校验和
     */
    public void generateCRC() {
        crc = new CRC();
        crc.update_checksum(len);
        crc.update_checksum(seq);
        crc.update_checksum(sysID);
        crc.update_checksum(compID);
        crc.update_checksum(msgID);

        payload.resetIndex();
        for(int i = 0; i < payload.size(); i++) {
            crc.update_checksum(payload.getByte());
        }
    }

    /**
     *  打包数据用以发送
     * @return 发送的字节数组
     */
    public byte[] encodePacket() {
        byte[] buffer = new byte[7 + len + 2];
        int i = 0;

        buffer[i++] = (byte)MUHLINK_SYNC_HEAD_1;
        buffer[i++] = (byte)MUHLINK_SYNC_HEAD_2;
        buffer[i++] = len;
        buffer[i++] = seq;
        buffer[i++] = sysID;
        buffer[i++] = compID;
        buffer[i++] = msgID;

        generateCRC();
        buffer[i++] = (byte) (crc.getLSB());
        buffer[i++] = (byte) (crc.getMSB());

        return buffer;
     }

    /**
     *
     * @return MUHLink 解包后的消息
     */
    public MUHLinkMessage unpack() {

        switch (compID) {
            /**
             *  飞控机组件ID 消息的处理
             */
            case MUH_MSG_ID.UAV_COMP_ID_FCC: {
                switch (msgID) {
                    case MUH_MSG_ID.UAV_MSG_FCC_HEARTBEAT:
                        return new msg_heartbeat(this);
                    case MUH_MSG_ID.UAB_MSG_FCC_ATTITUDE:
                        return new msg_attitude(this);
                    default:
                        return null;
                }
            }

            default:
                return null;
        }

    }

}
