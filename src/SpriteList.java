import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
//SpriteList.java
//Dylan Tan & Steven Fung
//Class that reads a file containing sprite tiles and creates a list of BufferedImages;
public class SpriteList {
    private ArrayList<BufferedImage> list = new ArrayList<>() ;
    public SpriteList(String FileName , int size){
        for (int i=0 ; i<size; i++){//reading files
            String j =Integer.toString(i);
            try {
                BufferedImage sprite = ImageIO.read(new File(FileName+ "/tile00" + j + ".png"));//all files have same format
                list.add(sprite);//adding to ArrayList
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    //getter
    public ArrayList<BufferedImage> getList(){
        return list;
    }
}
