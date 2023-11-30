package CSE306;

import java.io.*;
import java.net.*;

public class Teacher_TicTacToeServer2 {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(10)) {
            while (true) {
                Socket connection = server.accept();
                Teacher_TicTacToeServerThread serverThread = new Teacher_TicTacToeServerThread(connection);
                serverThread.start();
            }
        }
    }

    static class Teacher_TicTacToeServerThread extends Thread {

        private Socket connection;

        public Teacher_TicTacToeServerThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                Teacher_Board board = null;
                board = getStrategy(board, in.readLine());

                board.initialize();

                while (true) {
                    String move = in.readLine();

                    if (move.equals("left")) {
                        board = new Teacher_BoardLeft();
                        continue;
                    } else if (move.equals("right")) {
                        board = new Teacher_BoardRight();
                        continue;
                    }

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
                                                out.write("Let's play again!" + " *** ");
                                                out.write("Input your strategy: " + "\r\n");
                                                out.flush();
                                                board.initialize();
                                            }
                                        } else {
                                            out.write(board.encodeBoard() + " *** ");
                                            out.write("I won!" + " *** ");
                                            out.write("Let's play again!" + " *** ");
                                            out.write("Input your strategy: " + "\r\n");
                                            out.flush();
                                            board.initialize();
                                        }
                                    } else {
                                        out.write(board.encodeBoard() + " *** ");
                                        out.write("It's a draw!" + " *** ");
                                        out.write("Let's play again!" + " *** ");
                                        out.write("Input your strategy: " + "\r\n");
                                        out.flush();
                                        board.initialize();
                                    }
                                } else {
                                    out.write(board.encodeBoard() + " *** ");
                                    out.write("You won!" + " *** ");
                                    out.write("Let's play again!" + " *** ");
                                    out.write("Input your strategy: " + "\r\n");
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Teacher_Board getStrategy(Teacher_Board board, String move) {
            if (move.equals("left")) {
                board = new Teacher_BoardLeft();
            } else if (move.equals("right")) {
                board = new Teacher_BoardRight();
            }
            return board;
        }
    }
}