import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

class Player {
    private int health,score,maxHealth,attackMultiplier;
    private String username;
    private boolean sixup,healthup,xyz;
    private ArrayList<String> nativeBattleLogs = new ArrayList<>();
    private SpriteList spriteList;
    private Animation animation;
    private boolean [] userStats = new boolean[4];
    public Player(String a,int h) throws FileNotFoundException {
        username = a;
        sixup = false;
        healthup = false;
        xyz = false;
        spriteList=new SpriteList("Pictures/Player",3);
        animation= new Animation(spriteList.getList());
        levelMemory();
        attackMultiplier=1;
//        if (getLock().SkillTree){
//            attackMultiplier=2;
//        }

        if (userStats[0]){
            xyz = true;
        }
        if(userStats[1]){
            sixup = true;
        }
        if(userStats[3]){
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

        damage *= attackMultiplier;

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
    public void levelMemory() throws FileNotFoundException {
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Text Files/levelMemory.txt")));

        String stats = inFile.nextLine();
        String [] userStats = stats.split(",");
        for(int i = 0; i<userStats.length; i++){
            this.userStats[i] = userStats[i].equals("YES");
        }
        /*
        String stats1 = inFile.nextLine();
        String [] lockStats = stats1.split(",");
        for (int i = 0; i < lockStats.length;i++){
            this.lockStats[i] = lockStats[i].equals("UNLOCKED");
        }
        inFile.close();
        */
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
    public boolean getXYZ(){
        return xyz;
    }
    public boolean getSixUp(){
        return sixup;
    }
    public boolean getHealthUp(){
        return healthup;
    }
    public Animation getAnimation() {
        return animation;
    }
    public int getAttackMultiplier(){
        return attackMultiplier;
    }
}
