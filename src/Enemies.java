import java.awt.*;

class Enemies {
    private String name;
    private int health,damage;
    private Animation animation;;
    private SpriteList spriteList;
    public Enemies(String s,int h,String fPath,int fSize ){
        name = s;
        health = h;


        spriteList = new SpriteList(fPath,fSize);
        animation = new Animation(spriteList.getList());
    }
    public int getDamage(){
        return damage;
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
}
