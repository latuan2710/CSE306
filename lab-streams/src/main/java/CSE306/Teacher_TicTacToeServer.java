package CSE306;

import java.io.*;
import java.net.*;

public class Teacher_TicTacToeServer {
    private final static int PORT = 10;

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket connection = server.accept();
                Teacher_TicTacToeServerThread serverThread = new Teacher_TicTacToeServerThread(connection);
                serverThread.start();
            }
        }
    }
}