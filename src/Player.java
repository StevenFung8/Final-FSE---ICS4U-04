import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


//Player.java
//Dylan Tan and Steven Fung
//This is the object class for all the players stats
class Player {
    private int health,maxHealth,attackMultiplier,defense;
    private String username;
    private boolean sixup,healthup,xyz,arteryAttack,criticalAttack,healing,spikeArmor;//powerups
    private ArrayList<String> nativeBattleLogs = new ArrayList<>();
    private Animation animation;
    private boolean [] userStats = new boolean[4];//powerups unlocked
    private boolean[] skillLocks = new boolean[7];//skills unlocked

    public Player(String a,int h) throws IOException { //constructor to set base stats
        username = a;
        sixup = false;
        healthup = false;
        xyz = false;
        arteryAttack=false;
        healing=false;
        spikeArmor=false;
        SpriteList spriteList=new SpriteList("Pictures/Player",3);
        animation= new Animation(spriteList.getList());
        criticalAttack=false;
        levelMemory();
        skillMemory();
        attackMultiplier=1;
        defense=0;

        //if you complete levels you gain powerups
        if (userStats[0]){
            xyz = true;
        }
        if(userStats[1]){
            sixup = true;
        }
        if(userStats[2]){
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
        if(skillLocks[1]){//defense boost
            defense=1;
        }

        if(skillLocks[2]){
            maxHealth*=1.25;
            health*=1.25;
        }
        if(skillLocks[5]){//healing effect
            healing=true;
        }
        if(skillLocks[6]){//spiky armor
            spikeArmor=true;
        }
    }

    public void heal(){//heals player every round
        if(health<maxHealth){
            health+=1;
        }
    }
    public void resetCritical(){//resetting critical boolean
        criticalAttack=false;
    }

    public int damage(String word) { //calculates the damage dealt
        int damage = 0;
        damage += word.length();//plus 1 damage per person
        if(skillLocks[0]){
            damage +=3;
        }
        if(skillLocks[4]){//critical
            int x = randint(1,10);
            if(x==1){
                damage*=2;
            }
            criticalAttack=true;

        }
        if(skillLocks[3]){//bleed chance
            int j = randint(1,5);
            if(j==1){
                arteryAttack=true;//enemy now bleeding and will lose health every turn
            }
        }

        boolean wordXYZCondition = false;
        if(xyz){ //if you have xyz treausure (deal extra damage if there is an x y or z in the word
            for(int i = 0; i < word.length();i++){
                if (word.charAt(i) == 'x' ||word.charAt(i) == 'y' ||word.charAt(i) == 'z'){
                    damage = damage * 2;
                    wordXYZCondition = true;
                }
            }
            if(wordXYZCondition){
                nativeBattleLogs.add("Your XYZ treasure was activated");
                wordXYZCondition = false;
            }
        }
        if(sixup){ //if you have the six up treasure (deal extra if your word is 6 letters or more)
            if(word.length() >= 5){
                damage = (int) (damage * 1.5);
                nativeBattleLogs.add("Your Big Word treasure was activated");
            }
        }
        return damage;
    }


    public void levelMemory() throws FileNotFoundException {//read text file to get level completion and availability
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Text Files/levelMemory.txt")));

        String stats = inFile.nextLine();
        String [] userStats = stats.split(",");
        for(int i = 0; i<userStats.length; i++){
            this.userStats[i] = userStats[i].equals("YES");
        }

        String a = inFile.nextLine();
        String name = inFile.nextLine();
        username = name;
    }
    public void skillMemory() throws IOException{//read txt file that contains status of skills
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Text Files/skillMemory.txt")));
        String stats = inFile.nextLine();
        String [] skillStats = stats.split(",");
        System.out.println(Arrays.toString(skillStats));
        for(int i = 0; i<skillStats.length; i++){//adding to boolean array
            skillLocks[i] = skillStats[i].equals("UNLOCKED");
        }
        inFile.close();
    }
    public int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
    //getters and setters
    public boolean[] getSkillLocks(){ return skillLocks; }
    public boolean isArteryAttack() { return arteryAttack; }
    public boolean isCritical(){ return criticalAttack; }
    public boolean isSpikeArmor() { return spikeArmor; }
    public boolean isHealing() { return healing; }
    public int getHealth(){ return health; }
    public void setHealth(int value){ health = value; }
    public ArrayList<String> getNativeBattleLogs(){ return nativeBattleLogs; }
    public int getMaxHealth(){ return maxHealth; }
    public boolean getXYZ(){ return xyz; }
    public boolean getSixUp(){ return sixup; }
    public boolean getHealthUp(){ return healthup; }
    public Animation getAnimation() { return animation; }
    public int getDefense(){ return defense; }
    public String getUsername(){return username;}
    public int getAttackMultiplier(){ return attackMultiplier; }
}


