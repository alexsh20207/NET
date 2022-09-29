package rus.nsu.fit.net.lab1.shtang;

import java.io.IOException;
import java.net.*;

import static rus.nsu.fit.net.lab1.shtang.Const.*;

public class MulticastSender extends Thread {
    private final DatagramSocket socket;
    private final InetAddress group;
    private boolean isAlive;

    public MulticastSender() throws IOException {
        socket = new MulticastSocket();
        group = InetAddress.getByName(MULTICAST_ADDRESS);
        isAlive = true;
    }

    public void run() {
        try {
            while (isAlive) {
                    sendMsg(HELLO_MSG);
                }
            sendMsg(END_MSG);
            socket.close();
            }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMsg(String multicastMessage) throws IOException {
        byte[] buf = multicastMessage.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORT);
        socket.send(packet);
    }

    public void disconnect() {
        isAlive = false;
    }
}