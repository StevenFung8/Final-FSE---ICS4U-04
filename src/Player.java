import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

class Player {
    private int health,score,maxHealth;
    private String username;
    private boolean sixup,healthup,xyz;
    private ArrayList<String> nativeBattleLogs = new ArrayList<>();
    private SpriteList spriteList;
    private Animation animation;
    public Player(String a,int h) throws FileNotFoundException {
        username = a;
        sixup = false;
        healthup = false;
        xyz = false;
        spriteList=new SpriteList("Pictures/Player",3);
        animation= new Animation(spriteList.getList());
        String[] levels = levelMemory();
        if (levels[0].equals("YES")){
            xyz = true;
        }
        if(levels[1].equals("YES")){
            sixup = true;
        }
        if(levels[2].equals("YES")){
            healthup = true;
        }
        if(healthup){
            health = h + 20;
            maxHealth = h+20;
        }
        else{
            health = h;
            maxHealth = h;
        }

    }
    public int damage(String word){
        int damage = 0;
        damage += word.length();
        boolean wordXYZCondition = false;
        if(xyz){
            for(int i = 0; i < word.length();i++){
                if (word.charAt(i) == 'x' ||word.charAt(i) == 'y' ||word.charAt(i) == 'z'){
                    damage = damage * 2;
                    wordXYZCondition = true;
                }
            }
            if(wordXYZCondition){
                nativeBattleLogs.add("This attack did twice the amount due to the XYZ treasure");
                wordXYZCondition = false;
            }
        }
        if(sixup){
            if(word.length() >= 6){
                damage = (int) (damage * 1.5);
                nativeBattleLogs.add("This attack did 1.5 times the amount due to your Big Word treasure!");
            }
         }
        return damage;
    }
    public int getHealth(){
        return health;
    }
    public void setHealth(int value){
        health = value;
    }
    public String[] levelMemory() throws FileNotFoundException {
        String [] userStats = new String[4];
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Text Files/levelMemory.txt")));
        while (inFile.hasNextLine()){
            String stats = inFile.nextLine();
            userStats = stats.split(",");
        }
        return userStats;
    }
    public int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
    public ArrayList<String> getNativeBattleLogs(){
        return nativeBattleLogs;
    }
    public int getMaxHealth(){
        return maxHealth;
    }

    public Animation getAnimation() {
        return animation;
    }
}
