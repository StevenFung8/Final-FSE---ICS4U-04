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
        spriteList = new SpriteList(fPath,fSize);
        atkSpriteList = new SpriteList(atkPath,atkSize);
        animation = new Animation(spriteList.getList());
        atkAnimation = new Animation(atkSpriteList.getList());
        atkAnimation.setPos(1100,100);

        worldBuff = p;
        bleeding=false;
    }

    //enemies deal damage randomly between 1 - 11
    public int doDamage(){
        int damage = randint(1,11);
        return damage;
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

    public int bleed(){
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
