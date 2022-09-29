package rus.nsu.fit.net.lab1.shtang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String args[]) {
        try {
            MulticastSender multicastSender = new MulticastSender();
            MulticastReceiver multicastReceiver = new MulticastReceiver();

            multicastSender.start();
            multicastReceiver.start();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            bufferedReader.readLine();

            multicastSender.disconnect();
            multicastReceiver.disconnect();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
