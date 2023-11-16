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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TicTacToeServer {
    public static void main(String[] args) {
        String[][] matrix = { { "1", "2", "3" }, { "4", "5", "6" }, { "7", "8", "9" } };
        List<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }

        try (ServerSocket server = new ServerSocket(13)) {
            Socket connection = server.accept();

            Writer out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out = new BufferedWriter(out);

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            out.write(display(matrix) + "\r\n");
            out.flush();

            String readLine;
            while (!((readLine = reader.readLine()).equals("quit"))) {
                int client_input = Integer.valueOf(readLine);
                numbers.remove(Integer.valueOf(client_input));

                if (numbers.isEmpty()) {
                    matrix = tickMatrix(matrix, client_input, "x");
                    out.write(display(matrix) + "\r\n");
                    out.flush();
                    break;
                }

                int server_input = getRandomElement(numbers);
                numbers.remove(Integer.valueOf(server_input));

                matrix = tickMatrix(matrix, client_input, "X");
                matrix = tickMatrix(matrix, server_input, "O");

                out.write(display(matrix) + "\r\n");
                out.flush();
            }

            connection.close();
            server.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    static String display(String[][] matrix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String[] row : matrix)
            stringBuilder.append(Arrays.toString(row) + "\n");
        return stringBuilder.toString();
    }

    static String[][] tickMatrix(String[][] matrix, int index, String tick) {
        if (index >= 1 && index <= 3)
            matrix[0][index - 1] = tick;
        else if (index >= 4 && index <= 6)
            matrix[1][index - 4] = tick;
        else if (index >= 7 && index <= 9)
            matrix[2][index - 7] = tick;

        return matrix;
    }

    static int getRandomElement(List<Integer> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
