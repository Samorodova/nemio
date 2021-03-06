package manager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundManager {

    private Clip background;
    private long clipTime = 0;

    public SoundManager() {
        background = getClip(loadAudio("background"));
    }

    private AudioInputStream loadAudio(String url) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream("/media/audio/" + url + ".wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            return AudioSystem.getAudioInputStream(bufferedIn);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    private Clip getClip(AudioInputStream stream) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void resumeBackground(){
        background.setMicrosecondPosition(clipTime);
        background.start();
    }

    public void pauseBackground(){
        clipTime = background.getMicrosecondPosition();
        background.stop();
    }

    public void restartBackground() {
        clipTime = 0;
        resumeBackground();
    }

    public void playCoin() {
        Clip clip = getClip(loadAudio("coin"));
        clip.start();

    }

    public void playFood() {
        Clip clip = getClip(loadAudio("food"));
        clip.start();

    }


    public void playGameOver() {
        Clip clip = getClip(loadAudio("gameOver"));
        clip.start();

    }

    public void playStomp() {
        Clip clip = getClip(loadAudio("stomp"));
        clip.start();

    }

    public void playNemioDies() {

        Clip clip = getClip(loadAudio("nemioDies"));
        clip.start();

    }


}