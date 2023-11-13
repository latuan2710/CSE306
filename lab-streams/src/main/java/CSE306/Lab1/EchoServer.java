package CSE306.Lab1;

import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(13)) {
            Socket connection = server.accept();

            Writer out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out = new BufferedWriter(out);

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String readLine;
            while (!(readLine = reader.readLine()).equals("quit")) {
                out.write("Server: " + readLine + "\r\n");
                out.flush();
            }

            connection.close();
            server.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
