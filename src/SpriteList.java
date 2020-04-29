import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class SpriteList {
    private int size;
    private ArrayList<BufferedImage> list = new ArrayList<>() ;

    public SpriteList(String FileName , int size){
        this.size=size;
        for (int i=0 ; i<size; i++){
            String j =Integer.toString(i);
            try {
                BufferedImage sprite = ImageIO.read(new File(FileName+ "/tile00" + j + ".png"));
                list.add(sprite);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public ArrayList<BufferedImage> getList(){
        return list;
    }
    public BufferedImage bufferedImage(int n){
        return list.get(n);
    }
}
