package CSE306;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DayTimeServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(13)) {
            while (true) {
                Socket connection = server.accept();
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                Date now = new Date();
                out.write(now.toString() + "\r\n");
                out.flush();
                connection.close();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
