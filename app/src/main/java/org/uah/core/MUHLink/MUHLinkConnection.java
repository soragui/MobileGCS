package org.uah.core.MUHLink;

import android.util.Log;

import com.MUHLink.MUHLinkPacket;
import com.MUHLink.Parser;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Gui Zhou on 2016-03-07.
 */
public abstract class MUHLinkConnection {

    private static final String TAG = MUHLinkConnection.class.getSimpleName();
    private static final int READ_BUFFER_SIZE = 256;

    /*
    * MuhLink connection states.
    * */
    public static final int MUHLINK_DISCONNECTED = 0;
    public static final int MUHLINK_CONNECTING   = 1;
    public static final int MUHLINK_CONNECTED    = 2;

    private Thread mTaskThread;

    private final AtomicInteger mConnectionStatus = new AtomicInteger(MUHLINK_DISCONNECTED);

    /**
     *  多个共享连接端口的进程，用来分发接收到的数据包
     */
    private final ConcurrentHashMap<String, MUHLinkConnectionListener> mListeners =
            new ConcurrentHashMap<String, MUHLinkConnectionListener>();

    /**
     *  为要发送的数据包排队，如果没有数据发送这个进程将阻塞
     */
    private final LinkedBlockingQueue<MUHLinkPacket> mPacketsToSend =
            new LinkedBlockingQueue<MUHLinkPacket>();

    /*
    *  Listen for incoming data on the mavlink connection.
    * */
    private final Runnable mConnectingTask = new Runnable() {
        @Override
        public void run() {
            Thread sendingThread = null;

            try {
                /*
                 * 打开链接
                 **/
                openConnection();
                mConnectionStatus.set(MUHLINK_CONNECTED);
                reportConnect();
                Log.i(TAG, "CONN-THREAD");

                /**
                 *  启动发送消息进程
                 */
                sendingThread = new Thread(mSendingTask, "MUHLinkConnection-Sending Thread");
                sendingThread.start();
                Log.i(TAG, "SENDING-THREAD");

                final Parser parser = new Parser();


                final byte[] readBuffer = new byte[READ_BUFFER_SIZE];

                while (mConnectionStatus.get() == MUHLINK_CONNECTED) {
                    int bufferSize = readDataBlock(readBuffer);
                    Log.i(TAG, "RECV-SOME-DATA: BUFSIZE = " + bufferSize);
                    handleData(parser, bufferSize, readBuffer);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(sendingThread != null && sendingThread.isAlive()) {
                    sendingThread.interrupt();
                }

                disconnect();
            }


        }

        private void handleData(Parser parser, int buffersize, byte[] buffer) {
            if(buffersize < 1) {
                return ;
            }

            //Log.i(TAG, "Handler-Data: " + buffersize);
            for(int i = 0; i < buffersize; i++) {
                MUHLinkPacket receivedPacket = parser.datalink_parser_char(buffer[i] & 0x00ff);
                Log.i(TAG, "H");
                if(receivedPacket != null) {
                    /**
                     *  真正处理数据的地方
                     */
                    Log.i(TAG, "Realy Handler Data!");
                    reportReceivedPack(receivedPacket);
                }
            }
        }
    };

    /*
    *  Blocks until there's packet(s) to send, then dispatch them.
    * */
    private final Runnable mSendingTask = new Runnable() {
        @Override
        public void run() {
            int msgSeqNumer = 0;

            try {
                while(isConnected()) {
                    final MUHLinkPacket packet = mPacketsToSend.take();
                    //Log.i(TAG, "ERROR");
                    packet.seq = (byte)msgSeqNumer;
                    byte[] buffer = packet.encodePacket();

                    try {
                        sendBuffer(buffer);
                    } catch (IOException e) {
                        Log.i(TAG, e.getMessage());
                    }

                }
            } catch (InterruptedException e) {
                //Log.i(TAG, e.getMessage());
            } finally {
                disconnect();
            }
        }
    };

    /*
    * Establish a muhlink connection.
    * */
    public void connect() {
        if(mConnectionStatus.compareAndSet(MUHLINK_DISCONNECTED, MUHLINK_CONNECTING)) {
            mTaskThread = new Thread(mConnectingTask, "MuhLinkConnection-Connecting Thread");
            mTaskThread.start();
            Log.i(TAG, "MUHCONN");
        }
    }

    /**
     * Disconnect a muhlink connection.
     */
    public void disconnect() {
        if(mConnectionStatus.get() == MUHLINK_DISCONNECTED || mTaskThread == null) {
            return ;
        }

        try {
            mConnectionStatus.set(MUHLINK_DISCONNECTED);
            if(mTaskThread.isAlive() && !mTaskThread.isInterrupted()) {
                mTaskThread.interrupt();
                Log.i(TAG, "DISCONN-THREAD");
            }

            closeConnectoin();
            reportDisconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {

        if(mConnectionStatus.get() == MUHLINK_CONNECTED) {
            return true;
        }

        if(mConnectionStatus.get() == MUHLINK_DISCONNECTED) {
            return false;
        }

        return false;
    }

    /**
     * 提供发送数据的队列
     * @param packet
     */
    public void sendMuhPacket(MUHLinkPacket packet) {
        if(!mPacketsToSend.offer(packet)) {
            Log.i(TAG, "Unable to send muhlink packet. Packet queue is full!");
        }
    }

    /**
     *  添加一个监听者
     * @param tag
     * @param listener
     */
    public void addMUHLinkConnectionListener(String tag, MUHLinkConnectionListener listener) {
        mListeners.put(tag, listener);

        if(isConnected())
            listener.onConnect();
    }

    /**
     *  移除一个监听者
     * @param tag
     */
    public void removeMUHLinkConnectionListener(String tag) {
        mListeners.remove(tag);
    }

    protected abstract void openConnection() throws IOException;

    protected abstract void closeConnectoin() throws IOException;

    protected abstract int readDataBlock(byte[] buffer) throws IOException;

    protected abstract void sendBuffer(byte[] buffer) throws IOException;

    /**
     *  用来通知接口监听者已经成功连接
     */
    private void reportConnect() {
        for(MUHLinkConnectionListener listener : mListeners.values()) {
            listener.onConnect();
        }
    }

    /**
     *  用来通知监听者已经成功断开连接
     */
    private void reportDisconnect() {
        if(mListeners.isEmpty())
            return;
        for(MUHLinkConnectionListener listener : mListeners.values()) {
            listener.onDisconnect();
        }
    }

    /**
     *  用来通知接口监听者接收到数据包了
     * @param packet 接收到的数据包
     */
    private void reportReceivedPack(MUHLinkPacket packet) {
        if(mListeners.isEmpty())
            return ;

        for(MUHLinkConnectionListener listener : mListeners.values()) {
            listener.onReceivePacket(packet);
        }
    }

}
