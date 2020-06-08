import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Animation {
    private int currentFrame;               // animations current frame
    private int val,val2;                   //controls change in current frame //val1 for looping//val2 for playing once
    private int posX,posY;                  //position of sprite
    private int posX2,posY2;                //position for atk sprites
    private int OGposX2;                    //original atk sprite location
    private List<BufferedImage> frames = new ArrayList<BufferedImage>();    // Arraylist of frames

    public Animation(ArrayList<BufferedImage> frames) {
        this.frames.addAll(frames);
        posX=1200-frames.get(currentFrame).getWidth();
        posY=250-frames.get(currentFrame).getHeight();
        this.currentFrame = 0;
        this.val=-1;
        this.val2=1;
    }
    public void setPos(int x, int y){//for atkAnimations
        posX2=OGposX2=x;
        posY2=y;
    }
    public void reset() {//reset values to ensure they start properly
        val=-1;
        val2=1;
        currentFrame = 0;
    }
    public void resetPosX(){//atk animation reset position
        posX2=OGposX2;
    }
    public void moveLeft(){//moving the atk
        if(posX2>90){//while it isn't in players area will move left
            posX2-=40;
            update();
        }
        else{//last frame is blank so disappears after reaching dest.
            currentFrame=frames.size()-1;
        }
    }
    public void update() {//updating current frame
        if (currentFrame>=frames.size()-1 || currentFrame<=0){
            val*=-1;//looping
        }
        currentFrame+=val;
    }
    public void playOnce(){//changes current frame until it reaches last frame
        if (currentFrame==frames.size()-1){
            val2=0;
        }
        else {
            currentFrame += val2;
        }
    }
    //getters
    public int getPosX2() { return posX2; }
    public int getPosY2() { return posY2; }
    public BufferedImage getSprite() { return frames.get(currentFrame); }
    public int getSpritePosY(){
        return posY;
    }
    public int getSpritePosX(){
        return posX;
    }
}