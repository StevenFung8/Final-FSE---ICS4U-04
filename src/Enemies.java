import java.awt.*;

class Enemies {
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
    public int doDamage(){
        int damage = randint(0,11);
        return damage;

    }

    public int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
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
