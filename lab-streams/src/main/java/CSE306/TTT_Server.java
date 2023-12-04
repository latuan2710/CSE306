package CSE306;

import java.io.*;
import java.net.*;

public class TTT_Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(10)) {
            while (true) {
                Socket connection = server.accept();
                try {
                    ServerThread serverThread = new ServerThread(connection);
                    serverThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    connection.close();
                }
            }
        }
    }

    static class ServerThread extends Thread {

        private Socket connection;

        public ServerThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                TTT_Board board = null;
                board = getStrategy(board, in.readLine());

                board.initialize();

                while (true) {
                    String move = in.readLine();

                    if (move.equals("quit")) {
                        this.connection.close();
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
                                                out.write("Let's play again!" + " \r\n ");
                                                out.flush();
                                                board.initialize();
                                            }
                                        } else {
                                            out.write(board.encodeBoard() + " *** ");
                                            out.write("I won!" + " *** ");
                                            out.write("Let's play again!" + " \r\n ");
                                            out.flush();
                                            board.initialize();
                                        }
                                    } else {
                                        out.write(board.encodeBoard() + " *** ");
                                        out.write("It's a draw!" + " *** ");
                                        out.write("Let's play again!" + " \r\n ");
                                        out.flush();
                                        board.initialize();
                                    }
                                } else {
                                    out.write(board.encodeBoard() + " *** ");
                                    out.write("You won!" + " *** ");
                                    out.write("Let's play again!" + " \r\n ");
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

        private TTT_Board getStrategy(TTT_Board board, String strategy) {
            if (strategy.equals("left")) {
                board = new TTT_BoardLeft();
            } else if (strategy.equals("right")) {
                board = new TTT_BoardRight();
            }
            return board;
        }
    }
}