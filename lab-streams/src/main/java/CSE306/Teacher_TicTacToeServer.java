package CSE306;

import java.io.*;
import java.net.*;

public class Teacher_TicTacToeServer extends Thread {
    private final static int PORT = 10;
    private static Teacher_Board board = null;

    @Override
    public void run() {
        super.run();
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            try (Socket connection = server.accept()) {

                if (args[0].equals("left"))
                    board = new Teacher_BoardLeft();
                else
                    board = new Teacher_BoardRight();

                Writer out = new OutputStreamWriter(connection.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                board.initialize();

                while (true) {
                    String move = in.readLine();
                    if (move.equals("quit")) {
                        break;
                    } else {
                        int cell = Character.getNumericValue(move.charAt(0));
                        if (cell >= 0 && cell < 9) {
                            boolean empty = board.checkMove(cell);
                            if (empty) {
                                board.updateBoard(cell);
                                if (board.checkStatus('o') == 0) {
                                    if (board.checkBoard() == 0) {
                                        board.makeMove();
                                        if (board.checkStatus('x') == 0) {
                                            if (board.checkBoard() == 0) {
                                                out.write(board.encodeBoard() + "\r\n");
                                                out.flush();
                                            } else {
                                                out.write(board.encodeBoard() + " *** ");
                                                out.write("It's a draw!" + " *** ");
                                                out.write("Let's play again!" + " *** " + "\r\n");
                                                out.flush();
                                                board.initialize();
                                            }
                                        } else {
                                            out.write(board.encodeBoard() + " *** ");
                                            out.write("I won!" + " *** ");
                                            out.write("Let's play again!" + " *** " + "\r\n");
                                            out.flush();
                                            board.initialize();
                                        }
                                    } else {
                                        out.write(board.encodeBoard() + " *** ");
                                        out.write("It's a draw!" + " *** ");
                                        out.write("Let's play again!" + " *** " + "\r\n");
                                        out.flush();
                                        board.initialize();
                                    }
                                } else {
                                    out.write(board.encodeBoard() + " *** ");
                                    out.write("You won!" + " *** ");
                                    out.write("Let's play again!" + " *** " + "\r\n");
                                    out.flush();
                                    board.initialize();
                                }
                            } else {
                                out.write("Occupied cell!" + "\r\n");
                                out.flush();
                            }
                        } else {
                            out.write("Wrong input!" + "\r\n");
                            out.flush();
                        }
                    }
                }
                connection.close();
            }
            server.close();
        }
    }
}
