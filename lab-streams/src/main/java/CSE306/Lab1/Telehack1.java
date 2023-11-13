package CSE306.Lab1;

import java.io.*;
import java.net.*;

public class Telehack1 {
    public static final String SERVER = "telehack.com";
    public static final int PORT = 23;
    public static final int TIMEOUT = 15000;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
            readFirst(writer, reader, "");

            while (true) {
                String word = br.readLine();
                if (word.equals("quit"))
                    break;
                readEliza(writer, reader, word);
            }

        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            if (socket != null) {
                try {
                    br.close();
                    socket.close();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }
    }

    private static void readFirst(Writer writer, BufferedReader reader, String word) {
        try {
            writer.write(word + "\r\n");
            writer.flush();

            int c;
            int count = 0;
            while ((c = reader.read()) != -1) {
                if ((char) c == '\r') {
                    if (count == 2) {
                        break;
                    }
                    count++;
                }
                System.out.print((char) c);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static void readEliza(Writer writer, BufferedReader reader, String word) {
        try {
            writer.write(word + "\r\n");
            writer.flush();

            int c;
            int count = 0;
            while ((c = reader.read()) != -1) {
                if ((char) c == '\r') {
                    if (count == 3) {
                        break;
                    }
                    count++;
                }
                if (count == 2)
                    System.out.print((char) c);
            }
            System.out.println("(Eliza)\n");
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
