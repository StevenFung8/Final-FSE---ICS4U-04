import java.util.ArrayList;

class Player {
    private int health,score;
    private String username;
    private ArrayList<String> powerUps;
    public Player(String a,int h){
        username = a;
        health = h;
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
}
