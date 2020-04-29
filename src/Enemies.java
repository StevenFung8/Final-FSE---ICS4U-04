import java.awt.*;

class Enemies {
    private String name;
    private int health,damage;
    private Animation animation;
    public Enemies(String s,int h){
        name = s;
        health = h;
    }
    public int getDamage(){
        return damage;
    }
    public int getHealth(){
        return health;
    }
    public void setHealth(int value){
        health = value;
    }
    public String getName(){
        return name;
    }
    @Override
    public String toString(){
        return name + " has " + health + " health.";
    }
}
