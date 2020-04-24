import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Animation {

    private int currentFrame;               // animations current frame
    private int val;
    private int totalFrames;                // total amount of frames for your animation

    private boolean stopped;                // has animations stopped

    private List<BufferedImage> frames = new ArrayList<BufferedImage>();    // Arraylist of frames

    public Animation(ArrayList<BufferedImage> frames, int frameDelay) {

        this.stopped = true;

        for (int i = 0; i < frames.size(); i++) {
            this.frames.add(frames.get(i));
        }


        this.currentFrame = 0;
        this.val=-1;
        this.totalFrames = this.frames.size();

    }

    public void start() {
        if (!stopped) {
            return;
        }
        if (frames.size() == 0) {
            return;
        }
        stopped = false;
    }
    public void stop() {
        if (frames.size() == 0) {
            return;
        }

        stopped = true;
    }
    public void restart() {
        if (frames.size() == 0) {
            return;
        }
        stopped = false;
        currentFrame = 0;
    }
    public void reset() {
        this.stopped = true;

        this.currentFrame = 0;
    }


    public BufferedImage getSprite() {
        return frames.get(currentFrame);
    }
    public int getSpritePosY(){
        return 250-frames.get(currentFrame).getHeight();
    }
    public int getSpritePosX(){
        return 1280-frames.get(currentFrame).getWidth();
    }

    public void update() {
        if (currentFrame>=frames.size()-1 || currentFrame<=0){
            val*=-1;
        }
        currentFrame+=val;
//        System.out.println("updated to frame " + currentFrame);

    }
}