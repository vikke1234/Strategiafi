package org.uoh.distributed.peer.game.utils;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

public class MulticastHandler {

    private static MulticastHandler instance;

    private final MulticastSocket multicastSocket;
    private final InetAddress multicastGroupAddress;
    private final int multicastGroupPort;

    public MulticastSocket getMulticastSocket() {
        return multicastSocket;
    }
    private MulticastHandler(InetAddress multicastGroupAddress, int multicastGroupPort) throws IOException {
        this.multicastSocket = new MulticastSocket(multicastGroupPort);
        this.multicastGroupAddress = multicastGroupAddress;
        this.multicastGroupPort = multicastGroupPort;
        InetAddress group = InetAddress.getByName(multicastGroupAddress.getHostAddress());
        multicastSocket.joinGroup(group);
    }

    public static MulticastHandler getInstance(InetAddress multicastGroupAddress, int multicastGroupPort) throws IOException {
        if (instance == null) {
            instance = new MulticastHandler(multicastGroupAddress, multicastGroupPort);
        }
        return instance;
    }

    public void sendMulticastMessage(String message) throws IOException {
        byte[] messageData = message.getBytes();
        DatagramPacket packet = new DatagramPacket(messageData, messageData.length, multicastGroupAddress, multicastGroupPort);
        multicastSocket.send(packet);
    }

}