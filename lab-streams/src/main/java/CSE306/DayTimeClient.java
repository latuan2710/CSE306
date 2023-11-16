package CSE306;

import java.io.*;
import java.net.*;

public class DayTimeClient {
    public static void main(String[] args) {
        try {
            while (true) {
                Socket socket = new Socket("localhost", 13);
                socket.setSoTimeout(1000);

                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                System.out.println(reader.readLine());

                socket.close();
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
