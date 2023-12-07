package CSE306;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TTT_Client {
    private static String board;
    private static final String init = "-.-.-.-.-.-.-.-.-";

    public static void main(String[] args) {
        BufferedReader terminal = new BufferedReader(new InputStreamReader(System.in));
        board = init;

        try {
            String move = terminal.readLine();

            while (!(move.equals("quit"))) {
                Socket socket = new Socket("localhost", 10);
                socket.setSoTimeout(15000);

                BufferedReader bif = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                // bout.write(args[0] + "\r\n");
                // bout.flush();

                bout.write(board + "\n" + move + "\r\n");
                bout.flush();

                // bout.write(move + "\r\n");
                // bout.flush();

                readBoard(bif);

                bout.write("quit" + "\r\n");
                bout.flush();
                socket.close();

                move = terminal.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void readBoard(BufferedReader bif) {
        try {
            String line = bif.readLine();
            String status = line.split("#")[0];
            String new_board = line.split("#")[1];

            switch (status) {
                case "200":
                    board = new_board;
                    System.out.println(new_board);
                    break;
                case "201":
                    board = init;
                    System.out.println(new_board + "It's a draw! *** Let's play again! ***");
                    break;
                case "202":
                    board = init;
                    System.out.println(new_board + "I won! *** Let's play again! ***");
                    break;
                case "203":
                    board = init;
                    System.out.println(new_board + "You won! *** Let's play again! ***");
                    break;
                case "204":
                    System.out.println("Occupied cell!");
                    break;
                case "205":
                    System.out.println("Wrong input!");
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}