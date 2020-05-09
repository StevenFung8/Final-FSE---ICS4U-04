import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Level {
    private ArrayList<Enemies> enemies;

    private static Image FireBack, IceBack, SkyBack, WaterBack;
    private Image LevelBack;
    public Level(int levelNum){
        enemies = new ArrayList<>();

        try {
            //Loading Backgrounds
            FireBack = ImageIO.read(new File("Pictures//Backgrounds//FireWorld.jpg"));
            IceBack = ImageIO.read(new File("Pictures//Backgrounds//IceWorld.png"));
            SkyBack = ImageIO.read(new File("Pictures//Backgrounds//SkyWorld.png"));
            WaterBack = ImageIO.read(new File("Pictures//Backgrounds//WaterWorld.png"));

        }
        catch (IOException e) {
            System.out.println(e);
        }
        if (levelNum == 1){
            enemies.add(new Enemies("DarrellTheDragon",100,"Pictures/Enemies/Fire World/Fire Dragon",4));
            LevelBack=FireBack;
//            enemies.add(new Enemies("Poppy",10));
//            enemies.add(new Enemies("Steven",15));
        }
//        if(levelNum == 2){
//            enemies.add(new Enemies("Eren",5));
//            enemies.add(new Enemies("Mikasa",10));
//            enemies.add(new Enemies("Levi",15));
//        }
    }
    public ArrayList<Enemies> getLevelEnemies(){
        return enemies;
    }
    public Image getBack(){return LevelBack;}

}
