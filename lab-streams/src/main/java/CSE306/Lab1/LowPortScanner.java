package CSE306.Lab1;

import java.io.*;
import java.net.*;

public class LowPortScanner {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        for (int i = 1; i < 1024; i++) {
            try {
                Socket socket = new Socket(host, i);
                System.out.println("There is no server on port " + i + " of " + host);
                socket.close();
            } catch (UnknownHostException e) {
                System.err.println(e);
                break;
            } catch (IOException e) {

            }

        }
    }
}