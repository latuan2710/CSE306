package CSE306;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TTT_Client {
    public static void main(String[] args) {
        String hostname = "localhost";
        while (true) {
            try (Socket socket = new Socket(hostname, 10)) {
                socket.setSoTimeout(15000);

                BufferedReader bif = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                BufferedReader terminal = new BufferedReader(new InputStreamReader(System.in));

                writeStrategy(bout, args[0]);

                String move = terminal.readLine();
                while (!(move.equals("quit"))) {
                    bout.write(move + "\r\n");
                    bout.flush();
                    readBoard(bif, terminal, bout);
                    move = terminal.readLine();
                }
                bout.write("quit" + "\r\n");
                bout.flush();

                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void writeStrategy(BufferedWriter bout, String strategy) {
        try {
            bout.write(strategy + "\r\n");
            bout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void readBoard(BufferedReader bif, BufferedReader terminal, BufferedWriter bout) {
        try {
            String encodedBoard = bif.readLine();
            System.out.println(encodedBoard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}