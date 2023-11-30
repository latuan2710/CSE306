package CSE306;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.ParseException;

public class Teacher_TicTacToeClient {
    public static void main(String[] args) throws ParseException {
        String hostname = "localhost";
        Socket socket = null;
        try {
            socket = new Socket(hostname, 10);
            socket.setSoTimeout(15000);
            InputStream in = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bif = new BufferedReader(reader);

            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bout = new BufferedWriter(out);

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
            // socket.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    System.out.println("WHY");
                }
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
            if (encodedBoard.contains("strategy")) {
                String strategy = terminal.readLine();
                bout.write(strategy + "\r\n");
                bout.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}