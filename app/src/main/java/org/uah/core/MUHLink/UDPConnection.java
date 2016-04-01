package org.uah.core.MUHLink;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Gui Zhou on 2016-03-30.
 */
public class UDPConnection extends MUHLinkConnection {

    private static final String TAG = UDPConnection.class.getSimpleName();

    private DatagramSocket socket;
    private int serverPort;

    private int hostPort;
    private InetAddress hostAdd;

    public UDPConnection(int serverPort) {
        this.serverPort = serverPort;
    }

    private void getUdpStream() throws IOException {
        Log.i(TAG, "Port: " + serverPort);
        socket = new DatagramSocket(serverPort);
        socket.setBroadcast(true);
        socket.setReuseAddress(true);
    }

    @Override
    protected void closeConnectoin() throws IOException {
        if(socket != null)
            socket.close();
    }

    @Override
    protected void openConnection() throws IOException {
        getUdpStream();
    }

    @Override
    protected void sendBuffer(byte[] buffer) throws IOException {
        try {
            if (hostAdd != null) { // We can't send to our sister until they
                // have connected to us
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, hostAdd, hostPort);
                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int readDataBlock(byte[] buffer) throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        hostAdd = packet.getAddress();
        hostPort = packet.getPort();
        return packet.getLength();
    }
}
