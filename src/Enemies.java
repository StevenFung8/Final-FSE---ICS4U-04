import java.awt.*;
// Enemies.java
//Dylan Tan and Steven Fung
//Object Class for the enemies in each level


class Enemies {
    //Enemies have a name, health,sprites,and their world buff
    private String name;
    private int health,maxHealth;
    private Animation animation, atkAnimation;
    private SpriteList spriteList, atkSpriteList;
    private String worldBuff;
    private Boolean bleeding;
    public Enemies(String s,int h,String fPath,int fSize, String p,String atkPath, int atkSize){
        name = s;
        health = h;
        maxHealth = h;
        SpriteList spriteList = new SpriteList(fPath, fSize);
        SpriteList atkSpriteList = new SpriteList(atkPath, atkSize);
        animation = new Animation(spriteList.getList());
        atkAnimation = new Animation(atkSpriteList.getList());
        atkAnimation.setPos(1100,150-atkAnimation.getSprite().getHeight()/2);
        worldBuff = p;
        bleeding=false;
    }

    //enemies deal damage randomly, scaling as the level move on
    public int doDamage(){
        int damage = 0;
        if(worldBuff.equals("Fire Buff")) {
            damage = randint(1, 10);
        }
        if(worldBuff.equals("Ice Buff")){
            damage = randint(3,12);
        }
        if(worldBuff.equals("Sky Buff")){
            damage = randint(5,13);
        }
        if(worldBuff.equals("Water Buff")){
            damage = randint(6,10);
        }
        return damage;
    }

    public void setBleeding() {
        bleeding=true;
    }
    public int bleed(){//if bleeding then will lose health from 1 - 3
            int x = randint(1,3);
            health-=x;
            return x;
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
    public Animation getAtkAnimation(){
        return  atkAnimation;
    }
    public void setHealth(int value){
        health = value;
    }
    public void setBleeding() {
        bleeding=true;
    }
    public Boolean getBleeding() {
        return bleeding;
    }
    public int getMaxHealth() {
        return maxHealth;
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
