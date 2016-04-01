package com.MUHLink;

import android.util.Log;

import com.MUHLink.enums.MUH_MSG_ID;

/**
 * Created by Gui Zhou on 2016-03-10.
 */

public class Parser {

    private static final String TAG = Parser.class.getSimpleName();

    /**
     *  解包状态
     */
    enum Datalink_states {
        DATALINK_PARSE_STATE_UNINIT,
        DATALINK_PARSE_STATE_IDLE,
        DATALINK_PARSE_STATE_GOT_STX1,
        DATALINK_PARSE_STATE_GOT_STX2,
        DATALINK_PARSE_STATE_GOT_LENGTH,
        DATALINK_PARSE_STATE_GOT_SEQ,
        DATALINK_PARSE_STATE_GOT_SYSID,
        DATALINK_PARSE_STATE_GOT_COMPID,
        DATALINK_PARSE_STATE_GOT_MSGID,
        DATALINK_PARSE_STATE_GOT_PAYLOAD,
        DATALINK_PARSE_STATE_GOT_CRC1
    }

    Datalink_states state = Datalink_states.DATALINK_PARSE_STATE_UNINIT;

    static boolean msg_received;
    private MUHLinkPacket m;

    /**
     * 这是一个比较方便的解析数据的方法。一次解析一个字符，如果成功将返回
     * 解析的整个数据包，并记录相关失败的信息。
     *
     * @param c 要解析的字符
     * @return 成功返回解析的包
     */
    public MUHLinkPacket datalink_parser_char(int c) {
        msg_received = false;

        /**
         *  根据不同的状态开始解析数据并打包
         */
        switch (state) {
            case DATALINK_PARSE_STATE_UNINIT:
            case DATALINK_PARSE_STATE_IDLE:

                Log.i(TAG, "AT_STX1 " + c);
                if(c == MUH_MSG_ID.DATALINK_STX1) {
                    state = Datalink_states.DATALINK_PARSE_STATE_GOT_STX1;
                    Log.i(TAG, "GOT_STX1");
                    m = new MUHLinkPacket();
                }
                break;
            case DATALINK_PARSE_STATE_GOT_STX1:
                if(c == MUH_MSG_ID.DATALINK_STX2) {
                    state = Datalink_states.DATALINK_PARSE_STATE_GOT_STX2;
                }
                break;
            case DATALINK_PARSE_STATE_GOT_STX2:
                m.len = (byte)c;
                Log.i(TAG, "Length = " + m.len);
                state = Datalink_states.DATALINK_PARSE_STATE_GOT_LENGTH;
                break;
            case DATALINK_PARSE_STATE_GOT_LENGTH:
                m.seq = (byte)c;
                Log.i(TAG, "Seq = " + m.seq);
                state = Datalink_states.DATALINK_PARSE_STATE_GOT_SEQ;
                break;
            case DATALINK_PARSE_STATE_GOT_SEQ:
                m.sysID = (byte)c;
                Log.i(TAG, "sysID = " + m.sysID);
                state = Datalink_states.DATALINK_PARSE_STATE_GOT_SYSID;
                break;
            case DATALINK_PARSE_STATE_GOT_SYSID:
                m.compID = (byte)c;
                Log.i(TAG, "compID = " + m.compID);
                state = Datalink_states.DATALINK_PARSE_STATE_GOT_COMPID;
                break;
            case DATALINK_PARSE_STATE_GOT_COMPID:
                m.msgID = (byte)c;
                Log.i(TAG, "msgID = " + m.msgID);
                state = Datalink_states.DATALINK_PARSE_STATE_GOT_MSGID;
                break;
            case DATALINK_PARSE_STATE_GOT_MSGID:
                m.payload.add((byte)c);
                Log.i(TAG, "ADD msg: " + c);
                if(((byte)m.payload.size()) == m.len) {
                    Log.i(TAG, "LEN- " + m.len);
                    state = Datalink_states.DATALINK_PARSE_STATE_GOT_PAYLOAD;
                }
                break;
            case DATALINK_PARSE_STATE_GOT_PAYLOAD:
                m.generateCRC();
                Log.i(TAG, "GET-CRC-LSB:" + m.crc.getLSB());
                if(c != m.crc.getLSB()) {
                    msg_received = false;
                    Log.i(TAG, "CRC-LSB-FAILE " + c);
                    state = Datalink_states.DATALINK_PARSE_STATE_IDLE;
                } else {
                    state = Datalink_states.DATALINK_PARSE_STATE_GOT_CRC1;
                }
                break;
            case DATALINK_PARSE_STATE_GOT_CRC1:
                if(c != m.crc.getMSB()) {
                    msg_received = false;
                    state = Datalink_states.DATALINK_PARSE_STATE_IDLE;
                } else {
                    msg_received = true;
                    state = Datalink_states.DATALINK_PARSE_STATE_UNINIT;
                }
                break;


        }

        if(msg_received) {
            return m;
        } else {
            return null;
        }
    }
}
