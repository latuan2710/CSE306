package CSE306.Lab1;

import java.io.*;
import java.net.*;

public class SourceViewer {
    public static void main(String[] args) {

        try {
            String link = "https://file-examples.com/storage/fe1734aff46541d35a76822/2017/11/file_example_WAV_1MG.wav";
            URL url = new URL(link);
            InputStream is = url.openStream();
            String[] part = link.split("/");
            OutputStream out = new FileOutputStream("src\\main\\resources\\" + part[part.length - 1]);

            int n;
            while ((n = is.read()) != -1) {
                out.write(n);
            }
            is.close();
            out.close();
            System.out.println("Successful");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
