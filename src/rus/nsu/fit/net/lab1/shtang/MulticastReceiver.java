package rus.nsu.fit.net.lab1.shtang;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Timer;
import java.util.TimerTask;

import static rus.nsu.fit.net.lab1.shtang.Const.*;

public class MulticastReceiver extends Thread {
    private final MulticastSocket socket;
    private final InetAddress group;
    private final MulticastHandler multicastHandler;
    private Boolean isAlive;

    public MulticastReceiver() throws IOException {
        socket = new MulticastSocket(PORT);
        group = InetAddress.getByName(MULTICAST_ADDRESS);
        socket.joinGroup(group);
        this.multicastHandler = new MulticastHandler();
        isAlive = true;
    }

    public void run() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                multicastHandler.work();
            }
        }, TIMER_DELAY,TIMER_PERIOD);

        while (isAlive) {
            byte[] buf = new byte[BUF_SIZE];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String received = new String(packet.getData(), OFFSET_VALUE, packet.getLength());
            if (received.equals(END_MSG)) {
                multicastHandler.removeMember(packet.getPort());
            }
            else if (!multicastHandler.isItMember(packet.getPort())) {
                multicastHandler.addMember(packet.getAddress(), packet.getPort());
            }
            else if (!multicastHandler.getStatus(packet.getPort())) {
                multicastHandler.setStatus(packet.getPort(), true);
            }
        }
        timer.cancel();
        socket.close();
    }

    public void disconnect() {
        isAlive = false;
    }
}