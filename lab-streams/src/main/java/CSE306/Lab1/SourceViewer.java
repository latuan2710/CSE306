package CSE306.Lab1;

import java.io.*;
import java.net.*;

public class SourceViewer {
    public static void main(String[] args) {

        try {
            String link = "https://file-examples.com/storage/fe1134defc6538ed39b8efa/2017/11/file_example_MP3_700KB.mp3";
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
    // public static void main(String[] args) {
    // try {
    // InputStream is = new FileInputStream("src\\main\\resources\\test.mp3");
    // OutputStream out = new FileOutputStream("src\\main\\resources\\test2.mp3");

    // int n;
    // while ((n = is.read()) != -1) {
    // out.write(n);
    // }
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
}
