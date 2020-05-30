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
            enemies.add(new Enemies("DarrellTheDragon",10,"Pictures/Enemies/Fire World/Fire Dragon",5));
            enemies.add(new Enemies("RandyTheRacoon", 10, "Pictures/Enemies/Fire World/Raccoon", 4));
            enemies.add(new Enemies("GertrudeTheGolem", 10, "Pictures/Enemies/Fire World/Golem",5));
            LevelBack=FireBack;
        }
        else if(levelNum == 2){
            enemies.add(new Enemies("Beefy Mans",20,"Pictures/Enemies/Ice World/Ice Dragon",4));
            enemies.add(new Enemies("PauleneThePenguin",20,"Pictures/Enemies/Ice World/Penguin",8));
            enemies.add(new Enemies("Kevin", 20, "Pictures/Enemies/Ice World/Yeti",4));
            LevelBack=IceBack;
        }
        else if(levelNum == 3){
            enemies.add(new Enemies("Chicken Mans",50,"Pictures/Enemies/Sky World/Sky Dragon",4));
            enemies.add(new Enemies("Tumor",50,"Pictures/Enemies/Sky World/Sky Monster",6));
            enemies.add(new Enemies("Kaleb",60,"Pictures/Enemies/Sky World/Sky Monster 2",8));
            LevelBack=SkyBack;
        }
        else if(levelNum == 4){
            enemies.add(new Enemies("Mom Mans",80,"Pictures/Enemies/Water World/Water Dragon",4));
            enemies.add(new Enemies("Nemo",80,"Pictures/Enemies/Water World/Shark",7));
            enemies.add(new Enemies("WaterHorsey",100, "Pictures/Enemies/Water World/Seahorse",6));
            LevelBack=WaterBack;
        }
    }
    public ArrayList<Enemies> getLevelEnemies(){
        return enemies;
    }
    public Image getBack(){return LevelBack;}

}
