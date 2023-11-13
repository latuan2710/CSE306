package CSE306.Lab1;

import java.io.*;
import java.net.*;
import javax.sound.sampled.*;

import javazoom.jl.player.Player;

public class DownloadAndPlay {
    public static void main(String[] args) {

        // String link = "https://www.tanbinhtech.com:8443/sample1.wav";

        // PlayFileInStorage("src\\main\\resources\\sample1.wav");zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz
        // DownloadFile(link);
        // DownloadThenPlay(link);
        // DownloadAndPlay2(link);
        playFileOnline("http://ice10.outlaw.fm/KIEV2");
    }

    static void PlayFileInStorage(String link) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(link));
            Clip clip = AudioSystem.getClip();

            clip.open(ais);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e) {
        }
    }

    static String DownloadFile(String link) {

        try {
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

            return "src\\main\\resources\\" + part[part.length - 1];
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

    static void DownloadThenPlay(String link) {
        PlayFileInStorage(DownloadFile(link));
    }

    static void DownloadAndPlay2(String link) {
        try {
            URL url = new URL(link);
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            AudioFormat audioFormat = ais.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            byte[] bufferBytes = new byte[4096];
            int readBytes = -1;

            while ((readBytes = ais.read(bufferBytes)) != -1) {
                sourceDataLine.write(bufferBytes, 0, readBytes);
            }

            // sourceDataLine.drain();
            // sourceDataLine.close();
            // ais.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void playFileOnline(String link) {
        try {
            URL url = new URL(link);
            Player mp3Player = new Player(url.openStream());
            mp3Player.play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
