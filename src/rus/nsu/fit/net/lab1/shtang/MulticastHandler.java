package rus.nsu.fit.net.lab1.shtang;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class MulticastHandler {
    private final Map<Integer, InetAddress> members;
    private final Map<Integer, Boolean> isMemberAlive;

    public MulticastHandler() {
        members = new HashMap<>();
        isMemberAlive = new HashMap<>();
    }

    public void addMember(InetAddress inetAddress, int port) {
        members.put(port, inetAddress);
        setStatus(port, true);
    }

    public void setStatus(int port, boolean val) {
        isMemberAlive.put(port, val);
    }

    public boolean isItMember(int port) {
        return members.containsKey(port);
    }

    public boolean getStatus(int port) {
        if (isItMember(port)) {
            return isMemberAlive.get(port);
        }
        return false;
    }
    public void work() {
        for (Map.Entry<Integer, Boolean> member: isMemberAlive.entrySet()) {
            if (!member.getValue()) {
                removeMember(member.getKey());
            }
            else {
                member.setValue(false);
            }
        }
        printMembers();
    }

    public void removeMember(int port) {
        if (members.containsKey(port)) {
            members.remove(port);
            isMemberAlive.remove(port);
        }
    }
    public void printMembers() {
        System.out.println(members);
    }
}
