package org.uah.core.MUHLink;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by ziwo5 on 2016-03-07.
 */
public class TCPConnection extends MUHLinkConnection {

    private static final String TAG = TCPConnection.class.getSimpleName();

    private static final int CONNECTION_TIMEOUT = 20 * 1000;

    private Socket socket;
    private BufferedInputStream muhIn;
    private BufferedOutputStream muhOut;

    private String serverIP;
    private int    serverPort;

    public TCPConnection() {

    }

    public TCPConnection(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public void setConnectionProp(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    @Override
    protected void openConnection() throws IOException {
        getTCPStream();
    }

    @Override
    protected void closeConnectoin() throws IOException {
        if(socket != null) {
            socket.close();
        }
    }

    @Override
    protected int readDataBlock(byte[] buffer) throws IOException {
        return muhIn.read(buffer);
    }

    @Override
    protected void sendBuffer(byte[] buffer) throws IOException {
        if(muhOut != null) {
            muhOut.write(buffer);
            muhOut.flush();
        }
    }

    private void getTCPStream() throws IOException {
        InetAddress serverAddr = InetAddress.getByName(serverIP);
        socket = new Socket();
        Log.i(TAG, this.serverIP + " " + this.serverPort);
        socket.connect(new InetSocketAddress(serverAddr, serverPort), CONNECTION_TIMEOUT);
        muhOut = new BufferedOutputStream((socket.getOutputStream()));
        muhIn = new BufferedInputStream(socket.getInputStream());
    }
}
