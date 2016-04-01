package org.uah.core.MUHLink;

import com.MUHLink.MUHLinkPacket;

/**
 * Created by Gui Zhou on 2016/3/17.
 */
public interface MUHLinkConnectionListener {

    /**
     *  当 MUHLink 建立连接时调用
     */
    public void onConnect();

    /**
     * 当 有数据接收时调用
     * @param packet 接收的数据
     */
    public void onReceivePacket(MUHLinkPacket packet);

    /**
     *  当关闭连接时调用
     */
    public void onDisconnect();



}
