package CSE306;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class TicTacToeServer {
    public static void main(String[] args) {

        TicTacToeBoard board = new TicTacToeBoard();

        try (ServerSocket server = new ServerSocket(13)) {
            Socket connection = server.accept();

            Writer out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out = new BufferedWriter(out);

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            out.write(board.displayBoard() + "\n\r");
            out.flush();

            String readLine;
            while (!((readLine = reader.readLine()).equals("quit"))) {
                int client_input = Integer.valueOf(readLine);
                if (board.checkInput(client_input)) {
                    board.tickMatrix(client_input, "x");

                    if (board.checkWin("x")) {
                        out.write(board.displayBoard() + "\n");
                        out.write("-----You Win!!-----\t\n\r");
                        out.flush();
                        break;
                    }

                    if (board.checkDraw()) {
                        out.write(board.displayBoard() + "\n");
                        out.write("-----It is Draw-----\t\n\r");
                        out.flush();
                        break;
                    } else {
                        int server_input = board.getInputForServer();
                        board.tickMatrix(server_input, "o");

                        if (board.checkWin("o")) {
                            out.write(board.displayBoard() + "\n");
                            out.write("-----Server Win!!-----\t\n\r");
                            out.flush();
                            break;
                        }
                    }
                } else {
                    out.write("-----It is invalid move-----\n");
                }

                out.write(board.displayBoard() + "\n\r");
                out.flush();

            }

            connection.close();
            server.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
