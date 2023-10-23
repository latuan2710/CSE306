package CSE306.Lab1;

import java.io.*;

public class CountCharacter {
    public static void main(String[] args) throws IOException {
        String file1 = "src\\main\\resources\\doc.txt";
        String file2 = "src\\main\\resources\\doc2.txt";

        Reader reader = new InputStreamReader(new FileInputStream(new File(file1)), "UTF-8");
        Writer writer = new OutputStreamWriter(new FileOutputStream(new File(file2)), "UTF-8");

        int c;
        while ((c = reader.read()) != -1) {
            writer.write((char) c);
        }

        reader.close();
        writer.close();

    }
}
