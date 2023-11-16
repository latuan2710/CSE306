package CSE306;

import java.net.*;

public class OReillyByName {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("eiu.edu.vn");
            System.out.println(address);
        } catch (UnknownHostException ex) {
            System.out.println("Could not find eiu.edu.vn");
        }
    }
}
