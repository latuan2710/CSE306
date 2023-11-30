package CSE306;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.ParseException;

public class Teacher_TicTacToeClient2 {
    public static void main(String[] args) throws ParseException {
        String hostname = "localhost";
        Socket socket = null;
        Teacher_Board board = null;

        if (args[0].equals("left")) {
            board = new Teacher_BoardLeft();
        } else {
            board = new Teacher_BoardRight();
        }

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
                bout.write(board.board);
                bout.write(move + "\r\n");
                bout.flush();
                boolean flag = readBoard(bif, terminal, bout);
                if (!flag)
                    break;

                move = terminal.readLine();
            }
            bout.write("quit" + "\r\n");

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

    static boolean readBoard(BufferedReader bif, BufferedReader terminal, BufferedWriter bout) {
        try {
            String encodedBoard = bif.readLine();
            System.out.println(encodedBoard);
            if (encodedBoard.contains("strategy")) {
                String strategy = terminal.readLine();

                if (strategy.equals("quit"))
                    return false;

                bout.write(strategy + "\r\n");
                bout.flush();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}