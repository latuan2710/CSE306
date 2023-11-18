package CSE306;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class TicTacToeClient {
    public static void main(String[] args) {
        BufferedReader terminal = new BufferedReader(new InputStreamReader(System.in));
        try {

            Socket socket = new Socket("localhost", 13);
            socket.setSoTimeout(10000);

            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            OutputStream out = socket.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer = new BufferedWriter(writer);

            String input = "";
            while (!input.equals("quit")) {
                int c;
                boolean isClose = false;
                while ((c = reader.read()) != -1) {
                    System.out.print((char) c);

                    if ((char) c == '\t') {
                        isClose = true;
                        break;
                    }

                    if ((char) c == '\r') {
                        break;
                    }
                }

                if (isClose) {
                    break;
                }

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