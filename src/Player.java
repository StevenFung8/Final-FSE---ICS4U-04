import java.util.ArrayList;

class Player {
    private int health,score;
    private String username;
    private ArrayList<String> powerUps;
    private Animation animation;
    private SpriteList spriteList;
    public Player(String a,int h){
        username = a;
        health = h;
        spriteList = new SpriteList("Pictures/Player",3);
        animation = new Animation(spriteList.getList());

    }
    public int damage(String word){
        return word.length();
    }
    public int getHealth(){
        return health;
    }
    public void setHealth(int value){
        health = value;
    }
    public Animation getAnimation() { return animation; }

}
