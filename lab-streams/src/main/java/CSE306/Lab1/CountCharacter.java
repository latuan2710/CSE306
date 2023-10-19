package CSE306.Lab1;

import java.io.*;

public class CountCharacter {
    public static void main(String[] args) throws IOException {
        File file = new File("src\\main\\resources\\doc.txt");
        InputStream inputStream = new FileInputStream(file);

        int count = 0;
        while (inputStream.read() >= 0) {
            count++;
        }
        inputStream.close();
        System.out.println(count);
    }
}
