package org.uah.core.MUHLink;

import com.MUHLink.MUHLinkPacket;

/**
 * Created by Gui Zhou on 2016/3/17.
 */
public class MUHLinkStream {

    public interface MUHLinkOutputStream {

        void sendMuhPacket(MUHLinkPacket packet);

        boolean isConnected();

        void openConnection(int connectionType);

        void closeConnection();
    }

    public interface MuhLinkInputStream {

        public void notifyConnected();

        public void notifyDisconnected();

        public void notifyReceivedData(MUHLinkPacket packet);

    }

}
