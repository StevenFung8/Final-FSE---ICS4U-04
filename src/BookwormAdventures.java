//BookwormAdventures.java
//Dylan Tan and Steven Fung
//This is the main class that loads the full game when selecting the level.
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.io.IOException;

public class BookwormAdventures extends JFrame {
    private static int level; //current level
    private static String username;
    Timer myTimer;
    private Image bookwormIcon;
    GamePanel game;
    private static Image back; //background image

    //Constructor
    public BookwormAdventures(int levelValue) throws IOException {
        super("Bookworm Adventures");
        Image icon = ImageIO.read(new File("Pictures/StartMenu/bookwormIcon.png")); //application icon
        setIconImage(icon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280,820); //size of the program
        level = levelValue; //parameter sets the level from levelSelect
        myTimer = new Timer(100, new TickListener());	 // trigger every 100 ms
        myTimer.start();
        game = new GamePanel(level,username,this);
        add(game);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] arguments) throws IOException{
        BookwormAdventures frame = new BookwormAdventures(level);
    }
    public int getLevel(){
        return level;
    } //getter and setter methods for the level
    public void setLevel(int value){
        level = value;
    }

    class TickListener implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            if(game!= null && game.ready){ //methods from GamePanel that loop (ie. methods that check for a condition)
                game.update();
                game.repaint();
                game.moveBack();
                game.checkMusic();
                try {
                    game.checkBattleLogs();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    game.checkVowels();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                game.trackMousePosition();
                game.checkLevel();
            }
        }
    }

}
//this is the graphics class and where all the features in the game are located
class GamePanel extends JPanel implements KeyListener {
    public boolean ready=false;
    private int level; //current level
    private Level newLevel; //creates a new level
    private Letters letters; //creates the sixteen letters in the grid
    private Player player; //creates the player and their stats
    private Enemies currentEnemy; //the current enemy that the player is facing
    private ArrayList<Enemies> enemiesQueue; //queue of enemies in the level
    private int mx,my,enemyCounter,nativeBattleLogsCount,px,py; //enemyCounter: tells what enemy you are currently on / nativeBattleLogsCount: used ot update battleLogs
    private Rectangle[] letterSlots = new Rectangle[16]; //all the rects in the 4x4 grid
    private boolean[] letterSlotsCondition = new boolean[16]; //checks if the squares in the grid have a letter or not
    private ArrayList<String> alphabet; //the letters in the grid
    private ArrayList<String> battleLogs = new ArrayList<>(); //the messages on the left side of the screen
    private ArrayList<String> chosenWords = new ArrayList<String>(); //the words that you have selected for that battle
    private Rectangle resetButton,submitButton,healthBar,healthBar2,nextButton,backButton,exitButton,powerUp1,powerUp2,powerUp3,enemyBuff; //many rectangles (usually for buttons)
    private String selectedWord = ""; //the current word that the user is making
    private String currentMusic;//music that is playing right now
    private static Image WoodBack, ResetBtnPic, SubmitBtnPic,WoodSign,BackBtn,ExitBtnPic,NextBtn,pixelHeart,gains,xyzPic,fireBall,breeze,iceCube,droplet,lostSign,exitSign,exitBtn,finishSign;
    private int BackVal;
    private boolean moveBack,winCondition,exitCondition,loseCondition;
    private BookwormAdventures frame;
    private Boolean animationPlaying, deathAnimationPlaying, attackAnimationPlaying1, attackAnimationPlaying2, attackAnimationPlaying3,enemyAttacking;
    private int stage;
    private SpriteList deathSpriteList, atkSpriteLst1, atkSpriteLst2, atkSpriteLst3;
    private Animation deathAnimation,attackAnimation1,attackAnimation2,attackAnimation3;
    private Point p; //current point on the mouse
    private boolean[] userStats = new boolean[4]; //list to check for level completion
    private boolean [] lockStats = new boolean [4]; //list to check for if that level has been unlocked
    private Font fantasy; //custom font
    private Sound backgroundMusic,bossSound; //music files
    private SoundEffect hurtSound,playerHurtSound,applause,thunderSound,sliceSound;//sfx for attacks
    public GamePanel(int value,String username,BookwormAdventures frame) throws IOException {
        addMouseListener(new clickListener());
        setSize(800,600);
        readLevelMemory(); //the next bit is all just loading in the files and setting starting values for the beginning of the game.
        nativeBattleLogsCount = 0;
        winCondition = false;
        exitCondition = false;
        loseCondition = false;

        newLevel = new Level(value);
        letters = new Letters();
        level = value;
        p = getMousePosition();
        player = new Player(username,100);
        enemiesQueue = newLevel.getLevelEnemies();
        int rectCounter = 0;
        enemyCounter = 0;
        for (int x = 400; x<880;x+=120){ //makes all the rectangles in the grid
            for (int y = 240 ; y<720; y+=120){
                letterSlots[rectCounter] = new Rectangle(x,y,120,120);
                rectCounter++;
            }
        }
        resetButton = new Rectangle(400,725,245,62);
        submitButton = new Rectangle(645,725,244,62);
        healthBar = new Rectangle(30,30,200,20);
        healthBar2 = new Rectangle(1050,30,200,20);
        nextButton = new Rectangle(400,490,200,100);
        backButton = new Rectangle(700,490,200,100);
        exitButton = new Rectangle(625,0,50,50);
        powerUp1 = new Rectangle(30,60,50,50);
        powerUp2 = new Rectangle(90,60,50,50);
        powerUp3 = new Rectangle(150,60,50,50);
        enemyBuff = new Rectangle(1050,75,50,50);
        stage=0;
        currentMusic = "backgroundMusic";
        deathSpriteList= new SpriteList("Pictures/Enemies/Death Animation",9);
        deathAnimation = new Animation(deathSpriteList.getList());
        atkSpriteLst1 = new SpriteList("Pictures/Enemies/Attack Animation", 5);
        atkSpriteLst2 = new SpriteList("Pictures/Enemies/Attack Animation 2",10);
        atkSpriteLst3 = new SpriteList("Pictures/Enemies/Attack Animation 3", 7);
        attackAnimation1 = new Animation(atkSpriteLst1.getList());
        attackAnimation2 = new Animation(atkSpriteLst2.getList());
        attackAnimation3 = new Animation(atkSpriteLst3.getList());
        for(int i = 0; i<16;i++){
            letterSlotsCondition[i] = true;
        }
        bossSound = new Sound("Music/bossBattle.wav",50);
        backgroundMusic = new Sound("Music/backgroundMusic.wav",50);
        hurtSound = new SoundEffect("Music/hurtSound.wav");
        hurtSound.setVolume((float) 0.05);
        playerHurtSound = new SoundEffect("Music/playerHurtSound.wav");
        playerHurtSound.setVolume((float)0.5);
        applause = new SoundEffect("Music/applause.wav");
        applause.setVolume((float)0.5);
        thunderSound = new SoundEffect("Music/thunderSound.wav");
        thunderSound.setVolume((float)0.2);
        sliceSound = new SoundEffect("Music/sliceSound.wav");
        sliceSound.setVolume((float)0.5);
        alphabet = letters.randomXletters(16);
        try {
            //Loading Interface Pics
            WoodBack = ImageIO.read(new File("Pictures/Interface/WoodBack.png"));
            ResetBtnPic = ImageIO.read(new File("Pictures/Interface/ResetBtn.png"));
            SubmitBtnPic = ImageIO.read(new File("Pictures/Interface/SubmitBtn.png"));
            ExitBtnPic = ImageIO.read(new File("Pictures/Interface/redX.png"));
            pixelHeart = ImageIO.read(new File("Pictures/Interface/pixelHeart.png"));
            gains = ImageIO.read(new File("Pictures/Interface/gains.png"));
            xyzPic = ImageIO.read(new File("Pictures/Interface/xyz.png"));
            WoodSign = ImageIO.read(new File("Pictures/Interface/sign.png"));
            BackBtn = ImageIO.read(new File("Pictures/Interface/BackBtn.png"));
            NextBtn = ImageIO.read(new File("Pictures/Interface/NextBtn.png"));
            fireBall = ImageIO.read(new File("Pictures/Interface/fireball.png"));
            iceCube = ImageIO.read(new File("Pictures/Interface/iceCube.png"));
            droplet = ImageIO.read(new File("Pictures/Interface/water.png"));
            breeze = ImageIO.read(new File("Pictures/Interface/wind.png"));
            lostSign = ImageIO.read(new File("Pictures/Interface/lost sign.png"));
            exitSign = ImageIO.read(new File("Pictures/Interface/exitLevelSign.png"));
            exitBtn = ImageIO.read(new File("Pictures/Interface/exitBtn.png"));
            finishSign = ImageIO.read(new File("Pictures/Interface/finishSign.png"));

        }
        catch (IOException e) {
            System.out.println(e);
        }
        battleLogs.add("Welcome to Bookworm Adventures");
        try{
            InputStream myStream = new BufferedInputStream(new FileInputStream("Fonts/RINGM___.TTF"));
            fantasy = Font.createFont(Font.TRUETYPE_FONT, myStream).deriveFont(13f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/RINGM___.TTF")));
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        switch (level){
            case 1:
                battleLogs.add("You are in the Fire World");
                break;
            case 2:
                battleLogs.add("You are in the Ice World");
                break;
            case 3:
                battleLogs.add("You are in the Sky World");
                break;
            case 4:
                battleLogs.add("You are in the Water World");
                break;
        }
        animationPlaying=true;
        deathAnimationPlaying=false;
        attackAnimationPlaying1=false;
        attackAnimationPlaying2=false;
        attackAnimationPlaying3=false;
        enemyAttacking = false;

        BackVal=0;
        moveBack=false;
        System.out.println(enemiesQueue);
        currentEnemy = enemiesQueue.get(enemyCounter);
        this.frame = frame;
        backgroundMusic.play();
    }
    public void setLevel(int value){ //setter method for the level
        level = value;
    }

    public void update(){
        if(animationPlaying) {
            currentEnemy.getAnimation().update();
            player.getAnimation().update();
        }
        if(deathAnimationPlaying){
            deathAnimation.playOnce();
        }
        if(attackAnimationPlaying1){
            attackAnimation1.playOnce();

        }
        if(attackAnimationPlaying2){
            attackAnimation2.playOnce();

        }
        if(attackAnimationPlaying3){
            attackAnimation3.playOnce();

        }
        if(enemyAttacking){
            currentEnemy.getAtkAnimation().moveLeft();
            if (currentEnemy.getAtkAnimation().getPosX2()<110){
                playerHurtSound.play();
                enemyAttacking=false;
                currentEnemy.getAtkAnimation().reset();
                currentEnemy.getAtkAnimation().resetPosX();


            }
        }

    }
    public void checkLevel(){ //checks to see if the player is dead or not
        if (player.getHealth() <= 0){
            loseCondition = true;
        }
    }
    public int letterSlotBoolean(boolean a){ //counts the amount of time boolean "a" appears in the grid
        int counter = 0;    //in other words counts how many empty/full slots are currently in the grid
        for(boolean l : letterSlotsCondition){
            if (l == a){
                counter ++;
            }
        }
        return counter;
    }
    public void mouseReset(){ //moves the mouse away (helps with rect collision)
        mx = 0;
        my = 0;
    }
    public void checkMusic(){ //if the music stops, the music will continue playing
        if(currentMusic.equals("backgroundMusic")){
            if (!backgroundMusic.isPlaying()){
                backgroundMusic.play();
            }
        }
        else{
            if(!bossSound.isPlaying()){
                bossSound.play();
            }
        }
    }
    public void slotReplace(){ //replacing the letters that you used to make the word
        int counter = 0;
        ArrayList <String> replacements = letters.randomXletters(letterSlotBoolean(false)); //returns number of letters based on how much empty slots there are
        for (int i = 0; i<16;i++){
            if(!letterSlotsCondition[i]){
                alphabet.set(i,replacements.get(counter)); //for each black slot, put a letter back in
                letterSlotsCondition[i] = true; //there is a letter there now, set it back to true
                counter++;
            }
        }
        selectedWord=""; //reset the selected word
        mouseReset();
    }
    public void checkBattleLogs() throws IOException { //checks the current battleLogs to see if there are missing messages from the player class
        if(player.getNativeBattleLogs().size() > nativeBattleLogsCount){
            battleLogs.add(player.getNativeBattleLogs().get(player.getNativeBattleLogs().size() - 1)); //if true, take the messages that are being sent
            //from player class and add them to the battleLogs
            nativeBattleLogsCount ++;
        }
    }

    public void editBattleLogs(String s) throws IOException { //adds messages to the logs on the left side of the screen
        if(battleLogs.size() <= 18){ //18 is the max number of lines that can fit on the side
            battleLogs.add(s);
        }
        else{ //if over 18
            int a = battleLogs.size() - 18; //count the amount of messages that are underneath (have not been displayed yet)
            for (int i = 0; i < a; i++) {
                battleLogs.remove(battleLogs.get(0)); //remove the beginning ones to make room
            }
            battleLogs.add(s); //add the ones that are waiting in queue
        }


    }
    public int randint(int low, int high){ //returns a random number
        return (int)(Math.random()*(high-low+1)+low);
    }
    public void kill() throws IOException {
        deathAnimationPlaying = true;//boom! he dies
        enemyCounter++;//move on to the next enemy
        currentEnemy.setHealth(0);//just so you don't see any negative health
        if (enemyCounter < enemiesQueue.size()) {//if there are still enemies left in the line
            currentEnemy = enemiesQueue.get(enemyCounter);//set current enemy to next one
            if(enemyCounter == enemiesQueue.size()-1){ //if last enemy of the level, play boss music
                backgroundMusic.stop();
                bossSound.play();
                currentMusic = "bossSound";
            }
            moveBack = true;
            if (player.getHealth() + 25 > player.getMaxHealth()) { //when you win a battle, gain 25 health
                player.setHealth(player.getMaxHealth());
            }
            else{
                player.setHealth(player.getHealth() +25);
            }
            editBattleLogs("You have gained 25 health after winning that battle");
            chosenWords.clear(); //clear the words that you have submitted
        }

        else { //if there aren't any enemies left
            winCondition = true;
            editBattleLogs("You have won this battle!");
            sPointAdd();
            editBattleLogs("YOU HAVE EARNED 1 SP");
        }
        editBattleLogs(enemiesQueue.get(enemyCounter-1).getName() + " has been defeated!");
    }



    public void battle(String word) throws IOException { //handles the battle when you submit a word
        attackAnimationPlaying1=false;
        attackAnimationPlaying2=false;
        attackAnimationPlaying3=false;
        enemyAttacking=false;
        attackAnimation1.reset();
        attackAnimation2.reset();
        attackAnimation3.reset();
        currentEnemy.getAtkAnimation().reset();
        currentEnemy.getAtkAnimation().resetPosX();

        if(currentEnemy.getWorldBuff().equals("Fire Buff")){ //the fire world buff (enemy deals 5 damage every battle)
            player.setHealth(player.getHealth() - 5);
        }
        int missChance = randint(0,9); //10% chance to miss your attack
        if (missChance != 9) { //if you don't miss
            int damage = 0;
            if(word.length()>0 && word.length()<=4){
                attackAnimationPlaying1=true;
                hurtSound.play();
            }
            else if(word.length()>4 && word.length()<=6){
                attackAnimationPlaying2=true;
                sliceSound.play();
            }
            else {
                attackAnimationPlaying3=true;
                thunderSound.play();
            }

            if(currentEnemy.getWorldBuff().equals("Ice Buff")) {
                damage = (int)(player.damage(word) * 0.8);//ice world buff(your attacks deal 20% less damage)
                if(player.isArteryAttack()){
                    currentEnemy.setBleeding();
                }
            }
            else{ //when not in the ice world
                damage = player.damage(word);
            }
            currentEnemy.setHealth(currentEnemy.getHealth() - damage); //take that health away from the enemy
//            hurtSound.play();
            if(currentEnemy.getWorldBuff().equals("Sky Buff")){ //sky world buff (deal that damage back to you)
                int chance = randint(0,6); //14% chance
                if (chance == 5){
                    player.setHealth(player.getHealth() - (int)(damage * 0.5)); //deals half of your damage back to you
                }
            }
            if(player.isCritical()){
                editBattleLogs("Critical Hit!!!");
                player.resetCritical();
            }
            editBattleLogs("You have dealt " + damage + " damage to the enemy!");


            if(currentEnemy.getBleeding()) {
                editBattleLogs("You struck an artery, " + currentEnemy.getName() + " is now bleeding");
                editBattleLogs(currentEnemy.getName() + " took " +currentEnemy.bleed() + " damage due to blood loss");
            }


            if (currentEnemy.getHealth() <= 0) {//if you kill an enemy
                kill();
            }
            else { //if you don't kill the enemy yet
                int stunChance = randint(0,8); //chance to stun enemy
                if(stunChance != 8) { //if you don't stun
                    int enemyDamage = currentEnemy.doDamage();
                    if(currentEnemy.getWorldBuff().equals("Water Buff")){ //water buff (deal extra damage based on how many words you have played this battle)
                        enemyDamage += chosenWords.size();
                    }
                    enemyAttacking=true;//start enemy attack animation
                    player.setHealth(player.getHealth() - enemyDamage); //your health goes down
                    editBattleLogs("The enemy has dealt " + enemyDamage + " damage to you");
                    if(player.isSpikeArmor()){
                        currentEnemy.setHealth(currentEnemy.getHealth()-1);
                    }
                    editBattleLogs("The enemy is hurt by your fashionable spike armor");
                    if(currentEnemy.getHealth()<=0) {
                        kill();
                    }


                }
                else{ //if you stun the enemy,they don't do anything
                    editBattleLogs("You have stunned your enemy");
                    editBattleLogs("They cannot deal damage to you this turn");
                }
            }
        }
        else{ //if you miss, skip your attack phase, go straight to enemy attack phase
            int enemyDamage = currentEnemy.doDamage();
            if(currentEnemy.getWorldBuff().equals("Water Buff")){ //water buff (deal extra damage based on how many words you have played this battle)
                enemyDamage += chosenWords.size();
            }
            enemyAttacking=true;
            player.setHealth(player.getHealth() - enemyDamage);
            editBattleLogs("Your attack missed");
            editBattleLogs("The enemy has dealt " + enemyDamage + " damage to you");
            if(player.isSpikeArmor()){
                currentEnemy.setHealth(currentEnemy.getHealth()-1);
            }
            editBattleLogs("The enemy is hurt by your fashionable spike armor");
        }
        if(player.isHealing()){
            player.heal();
            editBattleLogs("Youve been healed for 1 hp");
        }
//
    }

    public void addNotify() {
        super.addNotify();
        ready = true;
    }
    public void sPointAdd() throws IOException {
        Scanner file = new Scanner(new BufferedReader(new FileReader("Text Files/skillMemory.txt")));
        String s = file.nextLine();
        int n = file.nextInt();
        PrintWriter newFile = new PrintWriter(new BufferedWriter(new FileWriter("Text Files/skillMemory.txt")));
        newFile.print(s);
        newFile.println("");
        newFile.print(n+1);
        newFile.close();
    }

    public Animation getAnimation(){
        return currentEnemy.getAnimation();
    }

    public void moveBack(){
        if (moveBack) {
            BackVal -= 30;
            if(BackVal<=-1280){
                BackVal=0;
                moveBack=false;
                stage++;
                deathAnimationPlaying=false;
                deathAnimation.reset();

            }
        }

    }
    public void readLevelMemory() throws FileNotFoundException { //method that reads for level completion
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Text Files/levelMemory.txt")));
        //turns the strings in leveLMemory to be booleans
        String stats = inFile.nextLine();
        String [] userStats = stats.split(",");
        for(int i = 0; i<userStats.length; i++){
            this.userStats[i] = userStats[i].equals("YES");
        }
        String stats1 = inFile.nextLine();
        String [] lockStats = stats1.split(",");
        for (int i = 0; i < lockStats.length;i++){
            this.lockStats[i] = lockStats[i].equals("UNLOCKED");
        }
        inFile.close();
    }
    public void changeLevelMemory() throws IOException { //when you win a level
        PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("Text Files/levelMemory.txt")));
        for(int i = 0;i<4;i++){
            if (i!=3) {
                if (userStats[i]) { //if you have already completed this level
                    file.print("YES,");
                }
                else if(i == level-1){ //when you have completed the current level
                    file.print("YES,");
                }
                else{ //for the levels you haven't touched
                    file.print("NO,");
                }
            }
            else{//same as the if statement up top but without the comma, to make splitting less painful
                if (userStats[i]) {
                    file.print("YES");
                }
                else if(i == level-1){
                    file.print("YES");
                }
                else{
                    file.print("NO");
                }
            }
        }
        file.println("");//new line
        for(int a = 0;a <4 ;a++){ //this is for level availability
            if(a!=3){
                if (lockStats[a]){ //if the level has already been unlocked
                    file.print("UNLOCKED,");
                }
                else if (a == level){ //if you complete this level, you unlock the next level
                    file.print("UNLOCKED,");
                }
                else{ //if its still locked, stay locked
                    file.print("LOCKED,");
                }
            }
            else{ //same as if statement up top, but without the comma
                if (lockStats[a]){
                    file.print("UNLOCKED");
                }
                else if (a == level){
                    file.print("UNLOCKED");
                }
                else{
                    file.print("LOCKED");
                }
            }
        }
        file.println("");//new line
        file.print(player.getUsername());//save your username
        file.close();
    }
    public void checkVowels() throws IOException { //checks the current letters for vowels
        int checkVowelsCount = 0;
        for (String a: alphabet){
            if (a.toLowerCase().equals("a") || a.toLowerCase().equals("e") || a.toLowerCase().equals("i") || a.toLowerCase().equals("o") || a.toLowerCase().equals("u")){
                checkVowelsCount ++;
            }
        }
        if (checkVowelsCount == 0){ //if there are no vowals
            alphabet = letters.randomXletters(16);//reshuffle the letters
            editBattleLogs("There were no vowels and your board has been shuffled");
        }
    }
    public void trackMousePosition(){ //updates the mouse position
        Point p = MouseInfo.getPointerInfo().getLocation();
        px = p.x;
        py = p.y;
    }

    public void paintComponent(Graphics g) { //where everythign is displayed and drawn
        p = getMousePosition();
        if(p == null){ //fixes the point if the mouse if out of the window
            p = new Point(0,0);
        }
        //backgrounds
        g.drawImage(newLevel.getBack(),BackVal,0,this);
        g.drawImage(newLevel.getBack(),1280+BackVal,0,this);
        g.setColor(Color.BLACK);
        g.drawImage(WoodBack,0,260,this);
        g.drawImage(WoodBack,890,260,this);

        //draw the letters inside the grid
        for (int i = 0; i < 16; i++) {
            g.drawRect(letterSlots[i].x, letterSlots[i].y, letterSlots[i].width, letterSlots[i].height);
            if (letterSlotsCondition[i]) {
                g.drawImage(Letters.getImage("NORMAL", alphabet.get(i)), letterSlots[i].x + 20, letterSlots[i].y + 25, this);
            }
        }

        //if you select a letters, ad it to the selected word
        for (int i = 0; i < 16; i++) {
            if (letterSlots[i].contains(mx,my)) {
                letterSlotsCondition[i] = false;
                if (selectedWord.length() < letterSlotBoolean(false)) {
                    selectedWord += alphabet.get(i);
                }
            }
        }
        g.drawImage(player.getAnimation().getSprite(),100,130,null);//drawing player
        //drawing all the buttons and black bars on the screen
        g.setColor(Color.BLACK);
        g.setFont(new Font("Comic Sans",Font.PLAIN,20));
        g.drawImage(ResetBtnPic,resetButton.x,resetButton.y,this);
        g.drawImage(SubmitBtnPic,submitButton.x,submitButton.y,this);
        g.drawImage(ExitBtnPic,exitButton.x,exitButton.y,this);

        g.fillRect(0, 240, 1280, 20);
        g.fillRect(400, 240, 10, 580);
        g.fillRect(880, 240, 10, 580);
        g.fillRect(400,720,490,5);
        g.fillRect(643,720,4,100);

        //username
        g.setFont(fantasy);
        g.drawString(player.getUsername(),healthBar.x,20);

        //health bars
        g.fillRect(25,25,player.getMaxHealth()*2+10,30);
        g.fillRect(1045,25,210,30);
        g.setColor(Color.green);
        g.fillRect(healthBar.x,healthBar.y,player.getHealth()*2,healthBar.height);
        g.fillRect(healthBar2.x,healthBar2.y,200/currentEnemy.getMaxHealth() * currentEnemy.getHealth(),healthBar2.height);
        g.setColor(Color.red);
        g.fillRect(healthBar.x+player.getHealth()*2,healthBar.y,player.getMaxHealth()*2-player.getHealth()*2,healthBar.height);
        g.fillRect(healthBar2.x+(200/currentEnemy.getMaxHealth()*currentEnemy.getHealth()),healthBar2.y,200-(200/currentEnemy.getMaxHealth()*currentEnemy.getHealth()),healthBar2.height);
        g.setFont(new Font("Times New Roman",Font.BOLD,20));
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(player.getHealth()),125,47);

        if(deathAnimationPlaying) {
            g.drawImage(deathAnimation.getSprite(), deathAnimation.getSpritePosX(), deathAnimation.getSpritePosY(), null);
        }
        if(attackAnimationPlaying1){
            g.drawImage(attackAnimation1.getSprite(),attackAnimation1.getSpritePosX(),attackAnimation1.getSpritePosY(),null);
        }
        if(attackAnimationPlaying2){
            g.drawImage(attackAnimation2.getSprite(),attackAnimation2.getSpritePosX()+50,attackAnimation2.getSpritePosY(),null);
        }
        if(attackAnimationPlaying3){
            g.drawImage(attackAnimation3.getSprite(),attackAnimation3.getSpritePosX(),attackAnimation3.getSpritePosY(),null);
        }
        if(enemyAttacking){
            g.drawImage(currentEnemy.getAtkAnimation().getSprite(),currentEnemy.getAtkAnimation().getPosX2(),currentEnemy.getAtkAnimation().getPosY2(),null);
        }


        //draw the enemy's name and health
        if(currentEnemy!=null) {
            g.setColor(Color.BLACK);
            g.setFont(fantasy);
            g.drawString(currentEnemy.getName(),1050,20);

            g.setFont(new Font("Times New Roman",Font.BOLD,20));
            g.drawString(Integer.toString(currentEnemy.getHealth()),1140, 47);
        }

        //if you press the reset button, all the letters go back to their place
        if (resetButton.contains(mx, my)) {
            for (int i = 0; i < 16; i++) {
                letterSlotsCondition[i] = true;
                selectedWord = "";
                g.setColor(Color.BLACK);
            }
        }

        //drawing the word you have created beside the player
        if (selectedWord.length() > 0) {
            for (int i = 0; i < selectedWord.length(); i++) {
                g.drawImage(Letters.getImage("SMALL", String.valueOf(selectedWord.charAt(i))), 210 + 55 * i, 100, this);
            }
        }

        //if you press the submit button
        if (submitButton.contains(mx, my)) {
            if (selectedWord.length() > 1) { //don't allow one letter words (ie "a")
                if (letters.checkWord(selectedWord)) { //check to see if it is a word
                    chosenWords.add(selectedWord);
                    try {
                        battle(selectedWord); //if it is, battle
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    slotReplace();
                }
                else if (!letters.checkWord(selectedWord)) { //if its not a word
                    try {
                        editBattleLogs("That is not a valid word");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mouseReset();
                }
            }
            else{
                try {
                    editBattleLogs("That is not valid word");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mouseReset();
            }
        }

        //displaying the words that you have submitted
        if (chosenWords.size() >0){
            g.setFont(fantasy);
            for (int i = 0; i < chosenWords.size();i++){
                String word = chosenWords.get(i);
                String output = word.substring(0,1).toUpperCase() + word.substring(1); //making the word start as a capital
                g.setColor(Color.BLACK);
                g.drawString(output,950,300+20*i);
            }
        }

        //displaying the messages on the left side
        if(battleLogs.size() > 0) {
            for (int i = 0; i < battleLogs.size(); i++) {
                g.setFont(fantasy);
                g.setColor(Color.DARK_GRAY);
                if(300 + i *25 > 800){
                    battleLogs.remove(battleLogs.get(0));
                }
                String currentBattleLog = battleLogs.get(i).substring(0,13);
                switch (currentBattleLog) { //for different messages i made them different colors
                    case "Youve been he":
                        g.setColor(new Color(0, 112, 30));
                        break;
                    case "The enemy has":
                        g.setColor(Color.blue);
                        break;
                    case "Your attack m":
                        g.setColor(Color.gray);
                        break;
                    case "You have stun":
                        g.setColor(new Color(128,0,128));
                        break;
                    case "You have gain":
                        g.setColor(new Color(1,121,81));
                        break;
                    case "This attack d":
                        g.setColor(Color.magenta);
                        break;
                    case "That is not a":
                        g.setColor(Color.red);
                        break;
                    default:
                        g.setColor(Color.black);
                        break;
                }
                g.drawString(battleLogs.get(i), 20, 300 + i * 25);

            }
        }


        if (!moveBack && !winCondition) {
            g.drawImage(currentEnemy.getAnimation().getSprite(), currentEnemy.getAnimation().getSpritePosX(),
                    currentEnemy.getAnimation().getSpritePosY(), null);
        }

        //drawing players powerups
        if (player.getXYZ()){
            g.drawImage(xyzPic,powerUp1.x,powerUp1.y,this);
        }
        if(player.getSixUp()){
            g.drawImage(gains,powerUp2.x,powerUp2.y,this);
        }
        if(player.getHealthUp()){
            g.drawImage(pixelHeart,powerUp3.x,powerUp2.y,this);
        }

        //if you win, display a message that lets you go back to level select or move on to next level
        if (winCondition){
            if(level != 4) { //if its not the last level
                g.drawImage(WoodSign, 640 - (WoodSign.getWidth(null) / 2), 210, null);
                g.setColor(Color.black);
                g.drawImage(BackBtn, backButton.x, backButton.y, null);
                g.drawImage(NextBtn, nextButton.x, nextButton.y, null);
                if (level == 1) {
                    g.drawString("You have gained this powerup", 525, 460);
                    g.drawImage(xyzPic, 620, 480, this);
                    g.setFont(fantasy);
                    g.drawString("If your word contains an x or y or z your word will do two times the damage for each one of those letters", 265, 480);
                }
                if (level == 2) {
                    g.drawString("You have gained this powerup", 525, 460);
                    g.drawImage(gains, 620, 480, this);
                    g.setFont(fantasy);
                    g.drawString("If your word is six or more letters it will do 50 percent more damage", 375, 480);
                }
                if (level == 3) {
                    g.drawString("You have gained this powerup: ", 525, 460);
                    g.drawImage(pixelHeart, 620, 480, this);
                    g.setFont(fantasy);
                    g.drawString("You have gained 20 extra max Health", 500, 477);
                }
                if (backButton.contains(mx, my)) { //if you press back
                    try {
                        changeLevelMemory();
                        backgroundMusic.closeSound();
                        bossSound.closeSound();
                        LevelSelect backTo = new LevelSelect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    frame.setVisible(false);
                }
                if (nextButton.contains(mx, my)) { //if you press next
                    try {
                        changeLevelMemory();
                        backgroundMusic.closeSound();
                        bossSound.closeSound();
                        BookwormAdventures nextLevel = new BookwormAdventures(level + 1);
                        frame.setVisible(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{ //if its the last level, you have finished the game
                g.drawImage(finishSign, 250, 240, null);
                g.setColor(Color.black);
                g.drawImage(BackBtn, backButton.x, backButton.y, null);
                if (backButton.contains(mx, my)) {
                    try {
                        changeLevelMemory();
                        LevelSelect backTo = new LevelSelect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    frame.setVisible(false);
                }
            }
        }

        //if you press the exit button
        if(exitButton.contains(mx,my)) {
            exitCondition = true;
        }
        if(exitCondition){
            g.setColor(Color.BLACK);
            g.drawImage(exitSign,150,210,this);
            g.setColor(Color.GREEN);
            g.drawImage(BackBtn,backButton.x,backButton.y,this);
            g.drawImage(exitBtn,nextButton.x,nextButton.y,this);
            g.setColor(Color.WHITE);
            if(backButton.contains(mx,my)){
                mouseReset();
                exitCondition = false;
            }
            if(nextButton.contains(mx,my)){
                try {
                    backgroundMusic.closeSound();
                    bossSound.closeSound();
                    LevelSelect backTo = new LevelSelect();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                frame.setVisible(false);
            }
        }

        // display dialog box if you hover over each powerup
        if (player.getXYZ()) {
            if (powerUp1.contains(p.x, p.y)) {
                g.setFont(fantasy);
                g.setColor(Color.black);
                g.drawString("This powerup makes any word that contains an x or y or z deal twice the amount of damage", powerUp1.x + 3, powerUp1.y + powerUp1.height + 21);
            }
        }
        if(player.getSixUp()) {
            if (powerUp2.contains(p.x, p.y)) {
                g.setFont(fantasy);
                g.setColor(Color.black);
                g.drawString("This powerup makes any word that contains 5 or more letters deal 50 percent the amount of damage", powerUp2.x + 3, powerUp2.y + powerUp2.height + 21);
            }
        }
        if(player.getHealthUp()) {
            if (powerUp3.contains(p.x, p.y)) {
                g.setFont(fantasy);
                g.setColor(Color.black);
                g.drawString("This powerup gives you an extra 20 health", powerUp3.x + 3, powerUp3.y + powerUp3.height + 21);
            }
        }

        //if you lose, display a sign saying you lost
        if(loseCondition){
            player.setHealth(0);
            g.drawImage(lostSign,240,250,null);
            g.setColor(Color.black);
            g.drawImage(BackBtn,backButton.x,backButton.y,null);
            if (backButton.contains(mx,my)){
                try {
                    LevelSelect levelSelect = new LevelSelect();
                    backgroundMusic.closeSound();
                    bossSound.closeSound();
                    frame.setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //display the world buff beside the enemy
        if(currentEnemy.getWorldBuff().equals("Fire Buff")){
            g.drawImage(fireBall,enemyBuff.x,enemyBuff.y,this);
        }
        if(currentEnemy.getWorldBuff().equals("Ice Buff")){
            g.drawImage(iceCube,enemyBuff.x,enemyBuff.y,this);
        }
        if(currentEnemy.getWorldBuff().equals("Sky Buff")){
            g.drawImage(breeze,enemyBuff.x,enemyBuff.y,this);
        }
        if(currentEnemy.getWorldBuff().equals("Water Buff")){
            g.drawImage(droplet,enemyBuff.x,enemyBuff.y,this);
        }

        //if you hover over the world buff, you can see what the world buff is
        if(enemyBuff.contains(p.x,p.y)) {
            g.setColor(Color.black);
            g.setFont(fantasy);
            System.out.println(currentEnemy.getWorldBuff());
            if(currentEnemy.getWorldBuff().equals("Fire Buff")) {
                g.drawString("The enemies burn you and deal 5 extra damage per round", 600, 100);
            }
            if (currentEnemy.getWorldBuff().equals("Ice Buff")) {
                g.drawString("Your attacks deal 20 percent less damage", 600, 100);
            }
            if(currentEnemy.getWorldBuff().equals("Sky Buff")) {
                g.drawString("Your attacks have a chance to be deflected back dealing 50 percent of the damage back to you", 350, 100);
            }
            if(currentEnemy.getWorldBuff().equals("Water Buff")){
                g.drawString("The enemy deals extra damage based on the amount of words you have submitted this battle",350,100);
            }
        }
    }



    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override

    public void keyReleased(KeyEvent e) {
    }

    class clickListener implements MouseListener {
        // ------------ MouseListener ------------------------------------------
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseClicked(MouseEvent e){
//            System.out.println(player.getAttackMultiplier());

        }

        public void mousePressed(MouseEvent e){
            mx = e.getX();
            my = e.getY();

        }
    }

}