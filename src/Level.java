import java.util.*;

public class Level {
    private ArrayList<Enemies> enemies;
    public Level(int levelNum){
        enemies = new ArrayList<Enemies>();
        if (levelNum == 1){
            enemies.add(new Enemies("Yuno",5));
            enemies.add(new Enemies("Poppy",10));
            enemies.add(new Enemies("Steven",15));
        }
        if(levelNum == 2){
            enemies.add(new Enemies("Eren",5));
            enemies.add(new Enemies("Mikasa",10));
            enemies.add(new Enemies("Levi",15));
        }
    }
    public ArrayList<Enemies> getLevelEnemies(){
        return enemies;
    }
}
