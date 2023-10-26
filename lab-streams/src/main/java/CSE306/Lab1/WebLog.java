package CSE306.Lab1;

import java.io.*;
import java.net.InetAddress;

public class WebLog {
    public static void main(String[] args) {

        try {
            String file = "src\\main\\resources\\webLog.txt";
            Reader reader = new InputStreamReader(new FileInputStream(new File(file)), "UTF-8");
            BufferedReader br = new BufferedReader(reader);

            while (br.ready()) {
                String entry = br.readLine();
                int index = entry.indexOf(" ");
                String ip = entry.substring(0, index);

                InetAddress in = InetAddress.getByName(ip);
                System.out.println(in.getHostName() + entry.substring(index));
            }

        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
