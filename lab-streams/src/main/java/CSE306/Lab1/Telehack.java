package CSE306.Lab1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Telehack {
    public static final String SERVER = "telehack.com";
    public static final int PORT = 23;
    public static final int TIMEOUT = 15000;

    public static void main(String[] args) {
        Socket socket = null;
        Scanner sc = new Scanner(System.in);

        try {
            socket = new Socket(SERVER, PORT);
            socket.setSoTimeout(TIMEOUT);

            OutputStream out = socket.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer = new BufferedWriter(writer);

            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            char b = '\n';
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
                if ((char) c == '.' && b == '\n') {
                    break;
                }
                b = (char) c;
            }

            writer.write("eliza \r\n");
            writer.flush();
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.equals("")) {
                    break;
                }
            }

            while (true) {
                String word = sc.nextLine();
                if (word.equals("quit"))
                    break;
                define(writer, reader, word);
            }

        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            if (socket != null) {
                try {
                    sc.close();
                    socket.close();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }
    }

    private static void define(Writer writer, BufferedReader reader, String word) {
        try {
            writer.write(word + "\r\n");
            writer.flush();

            String line = "";
            int count = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.equals("")) {
                    if (count == 0) {
                        count++;
                        continue;
                    }
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
