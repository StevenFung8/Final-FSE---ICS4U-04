import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.FloatControl;
public class SoundEffect {
    Clip clip;

    public SoundEffect(String soundFile) {

        try {
            File file = new File(soundFile);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
    public void play() {
            clip.setFramePosition(0);
            clip.start();

     }
    public void stop(){
        clip.stop();
    }
    public void closeSound(){ clip.close(); }

    public void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
    public boolean isPlaying(){
        return clip.isActive();
    }
}
