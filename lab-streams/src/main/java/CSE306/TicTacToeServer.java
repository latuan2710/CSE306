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
        List<String> matrix = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        try (ServerSocket server = new ServerSocket(13)) {
            Socket connection = server.accept();

            Writer out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out = new BufferedWriter(out);

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            out.write(displayBoard(matrix) + "\n\r");
            out.flush();

            String readLine;
            while (!((readLine = reader.readLine()).equals("quit"))) {
                int client_input = Integer.valueOf(readLine);
                if (numbers.contains((Integer) client_input)) {
                    matrix = tickMatrix(matrix, client_input, "x");
                    numbers.remove((Integer) client_input);
                    if (checkWin(matrix, "x")) {
                        out.write(displayBoard(matrix) + "\n");
                        out.write("-----You Win!!-----\t\n\r");
                        out.flush();
                        break;
                    }

                    if (checkDraw(numbers)) {
                        out.write(displayBoard(matrix) + "\n");
                        out.write("-----It is Draw-----\t\n\r");
                        out.flush();
                        break;
                    } else {
                        int server_input = getRandomElement(numbers);
                        matrix = tickMatrix(matrix, server_input, "o");
                        numbers.remove((Integer) server_input);
                        if (checkWin(matrix, "o")) {
                            out.write(displayBoard(matrix) + "\n");
                            out.write("-----Server Win!!-----\t\n\r");
                            out.flush();
                            break;
                        }
                    }
                } else {
                    out.write("-----It is invalid move-----\n");
                }

                out.write(displayBoard(matrix) + "\n\r");
                out.flush();

            }

            connection.close();
            server.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    static String displayBoard(List<String> matrix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= matrix.size(); i++) {
            stringBuilder.append(matrix.get(i - 1) + "  ");
            if (i % 3 == 0 && i != 9)
                stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    static List<String> tickMatrix(List<String> matrix, int index, String tick) {
        matrix.set((index - 1), tick);
        return matrix;
    }

    static int getRandomElement(List<Integer> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    static boolean checkDraw(List<Integer> list) {
        if (list.isEmpty()) {
            return true;
        }
        return false;
    }

    static boolean checkWin(List<String> matrix, String tick) {
        List<Integer> indexs = new ArrayList<>();

        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(i).equals(tick)) {
                indexs.add(i);
            }
        }

        System.out.println(indexs);

        int count = 0, i = 0, j = 0;
        while (count < 3) {
            if (indexs.contains(i) && indexs.contains(i + 1) && indexs.contains(i + 2))
                return true;
            if (indexs.contains(j) && indexs.contains(j + 3) && indexs.contains(j + 6))
                return true;
            count++;
            i += 3;
            j++;
        }

        if (indexs.contains(4) && indexs.contains(4) && indexs.contains(8))
            return true;
        if (indexs.contains(2) && indexs.contains(4) && indexs.contains(6))
            return true;

        return false;
    }
}
