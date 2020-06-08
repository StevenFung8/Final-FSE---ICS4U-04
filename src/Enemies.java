import java.awt.*;
// Enemies.java
//Dylan Tan and Steven Fung
//Object Class for the enemies in each level

//constructor
class Enemies {
    //Enemies have a name, health,sprites,and their world buff
    private String name;
    private int health,maxHealth;
    private Animation animation;;
    private SpriteList spriteList;
    private String worldBuff;
    public Enemies(String s,int h,String fPath,int fSize, String p){
        name = s;
        health = h;
        maxHealth = h;
        spriteList = new SpriteList(fPath,fSize);
        animation = new Animation(spriteList.getList());
        worldBuff = p;
    }

    //enemies deal damage randomly between 1 - 11
    public int doDamage(){
        int damage = randint(1,11);
        return damage;
    }
    public int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }

    //rest are getter and setter methods
    public int getHealth(){
        return health;
    }
    public Animation getAnimation() {
        return animation;
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
    public String getWorldBuff(){
        return worldBuff;
    }
}
