import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


//Sound.java
//Dylan Tan and Steven Fung
//This class plays the sound files. Most of this class was taken from online sources (https://www.geeksforgeeks.org/play-audio-file-using-java/) and friends
public class Sound {
    private static ArrayList<Sound> madeSounds = new ArrayList<>();
    private static boolean isMuted;
    private AudioInputStream inputStream;
    private Clip clip;
    private String filePath;
    FloatControl volume;
    private boolean wasPaused;
    private float originalGain;

    public Sound(String filePath, int volumeLevel){
        this.filePath = filePath;
        try {
            clip = AudioSystem.getClip();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        loadClip();
        volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        setVolume(volumeLevel);
        madeSounds.add(this);
    }

    private void loadClip(){
        try{
            inputStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip.open(inputStream);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Sound error");
            e.printStackTrace();
        }
    }
    public void play(){
        clip.setMicrosecondPosition(0);
        clip.start();
    }

    public void resume(){
        clip.start();
    }

    public void stop(){
        clip.stop();
    }

    public void closeSound(){
        clip.close();
    }

    public boolean isPlaying(){
        return clip.isActive();
    }

    public void setVolume(int volumeLevel){
        float range = volume.getMaximum() - volume.getMinimum();
        float gain = (float)(range * (volumeLevel/100.0)) + volume.getMinimum();
        volume.setValue(gain);
    }
    public void setGain(float gain){
        volume.setValue(gain);
    }

    public void forceMute(){
        originalGain = getGain();
        setVolume(0);
    }

    // Getters
    public float getGain(){
        return volume.getValue();
    }

    public static void pauseAll(){
        for(Sound sound: madeSounds){
            if(sound.isPlaying()){
                sound.stop();
                sound.wasPaused = true;
            }
        }
    }

    public static void resumeAll(){
        for(Sound sound: madeSounds){
            if(sound.wasPaused){
                sound.resume();
                sound.wasPaused = false;
            }
        }
    }


    public static void toggleVolume(){
        if(!isMuted){
            for(Sound sound: madeSounds){
                sound.originalGain = sound.getGain();
                sound.setVolume(0);
            }
            isMuted = true;
        }
        else{
            for(Sound sound: madeSounds){
                sound.setGain(sound.originalGain);
            }
            isMuted = false;
        }
    }
    public static boolean isMuted(){
        return isMuted;
    }
}