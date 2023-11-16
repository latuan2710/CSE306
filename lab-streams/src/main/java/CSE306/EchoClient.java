package CSE306;

import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        BufferedReader terminal = new BufferedReader(new InputStreamReader(System.in));
        try {

            Socket socket = new Socket("localhost", 13);
            socket.setSoTimeout(1000);

            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            OutputStream out = socket.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer = new BufferedWriter(writer);

            String result;

            String input = terminal.readLine();
            writer.write(input + "\r\n");
            writer.flush();

            while ((result = reader.readLine()) != null) {
                System.out.println(result);
                input = terminal.readLine();
                writer.write(input + "\r\n");
                writer.flush();
            }

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
