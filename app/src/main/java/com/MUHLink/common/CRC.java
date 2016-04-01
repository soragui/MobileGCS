package com.MUHLink.common;

/**
 * Created by ziwo5 on 2016-03-10.
 */

/**
 *  MUHLink 数据的 CRC 计算. 校验和必须先初始化，然后更具消息的每一位进行校验计算
 *
 */

public class CRC {

    private static final int CRC_INIT_VALUE = 0xffff;
    private int CRCvalue;

    /**
     *
     * @param data
     */
    public void update_checksum(int data) {
        int tmp;
        data = data & 0xff;
        tmp = data ^ (CRCvalue & 0xff);
        tmp ^= (tmp << 4) & 0xff;
        CRCvalue = ((CRCvalue >> 8) & 0xff) ^ (tmp << 8) ^ (tmp << 3)
                ^ ((tmp >> 4) & 0xf);
    }

    public void start_checksum() {
        CRCvalue = CRC_INIT_VALUE;
    }

    /**
     *
     * @return 校验和的高八位
     */
    public int getMSB() {
        return ((CRCvalue >> 8) & 0xff);
    }

    /**
     *
     * @return 校验和的低八位
     */
    public int getLSB() {
        return (CRCvalue & 0xff);
    }

    public CRC() {
        start_checksum();
    }

}
