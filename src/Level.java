import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
//Level.java
//Dylan Tan and Steven Fung
// This class handles the different types of enemies for each level
public class Level {
    private ArrayList<Enemies> enemies; //the list of enemies
    private static Image FireBack, IceBack, SkyBack, WaterBack; //the level background
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
        //for each level append enemies with different names and attributes
        if (levelNum == 1){
            enemies.add(new Enemies("DarrellTheDragon",10,"Pictures/Enemies/Fire World/Fire Dragon",5,"Fire Buff","Pictures/Enemies/Enemy Attack Animation",10));
            enemies.add(new Enemies("RandyTheRacoon", 15, "Pictures/Enemies/Fire World/Raccoon", 4,"Fire Buff","Pictures/Enemies/Enemy Attack Animation",10));
            enemies.add(new Enemies("GertrudeTheGolem", 20, "Pictures/Enemies/Fire World/Golem",5,"Fire Buff","Pictures/Enemies/Enemy Attack Animation",10));
            LevelBack=FireBack;
        }
        else if(levelNum == 2){
            enemies.add(new Enemies("Beefy Mans",25,"Pictures/Enemies/Ice World/Ice Dragon",4,"Ice Buff","Pictures/Enemies/Enemy Attack Animation 2",7));
            enemies.add(new Enemies("PauleneThePenguin",25,"Pictures/Enemies/Ice World/Penguin",8,"Ice Buff","Pictures/Enemies/Enemy Attack Animation 2",7));
            enemies.add(new Enemies("Kevin", 30, "Pictures/Enemies/Ice World/Yeti",4,"Ice Buff", "Pictures/Enemies/Enemy Attack Animation 2",7));
            LevelBack=IceBack;
        }
        else if(levelNum == 3){
            enemies.add(new Enemies("Chicken Mans",30,"Pictures/Enemies/Sky World/Sky Dragon",4,"Sky Buff", "Pictures/Enemies/Enemy Attack Animation 3",22));
            enemies.add(new Enemies("Tumor",40,"Pictures/Enemies/Sky World/Sky Monster",6,"Sky Buff","Pictures/Enemies/Enemy Attack Animation 3",22));
            enemies.add(new Enemies("Kaleb",50,"Pictures/Enemies/Sky World/Sky Monster 2",8,"Sky Buff","Pictures/Enemies/Enemy Attack Animation 3",22));
            LevelBack=SkyBack;
        }
        else if(levelNum == 4){
            enemies.add(new Enemies("Mom Mans",55,"Pictures/Enemies/Water World/Water Dragon",4,"Water Buff","Pictures/Enemies/Enemy Attack Animation 4",7));
            enemies.add(new Enemies("Nemo",60,"Pictures/Enemies/Water World/Shark",7,"Water Buff","Pictures/Enemies/Enemy Attack Animation 4",7));
            enemies.add(new Enemies("WaterHorsey",65, "Pictures/Enemies/Water World/Seahorse",6,"Water Buff","Pictures/Enemies/Enemy Attack Animation 4",7));
            LevelBack=WaterBack;
        }
    }

    //setters and getters
    public ArrayList<Enemies> getLevelEnemies(){
        return enemies;
    }
    public Image getBack(){return LevelBack;}

}
