import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class BookwormAdventures extends JFrame {
    private static int level;
    private static String username;
    Timer myTimer;
    private Image bookwormIcon;
    GamePanel game;
    private static Image back;


    public BookwormAdventures(int levelValue) throws IOException {
        super("Bookworm Adventures");
        Image icon = Toolkit.getDefaultToolkit().getImage("bookwormIcon.png");
        setIconImage(icon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280,820);
        level = levelValue;
        myTimer = new Timer(100, new TickListener());	 // trigger every 100 ms
        myTimer.start();
        game = new GamePanel(level,username,this);
        add(game);

        setResizable(false);
        setVisible(true);

    }
    public static void main(String[] arguments) throws IOException{
        BookwormAdventures frame = new BookwormAdventures(level);
        //System.out.println(words.toString());
    }
    public int getLevel(){
        return level;
    }
    public void setLevel(int value){
        level = value;
    }

    class TickListener implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            if(game!= null && game.ready){
                game.update();
                game.repaint();
                game.moveBack();
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

            }
        }
    }

}

class GamePanel extends JPanel implements KeyListener {
    public boolean ready=false;
    private boolean gotName=false;
    private int level;
    private Level levelPog;
    private Letters letters;
    private Player player;
    private Enemies currentEnemy;
    private ArrayList<Enemies> enemiesQueue;
    private int mx,my,enemyCounter,nativeBattleLogsCount,px,py;
    private Rectangle[] letterSlots = new Rectangle[16];
    private boolean[] letterSlotsCondition = new boolean[16];
    private ArrayList<String> alphabet;
    private ArrayList<String> battleLogs = new ArrayList<>();
    private ArrayList<String> chosenWords = new ArrayList<String>();
    private Rectangle resetButton,submitButton,healthBar,healthBar2,nextButton,backButton,exitButton,powerUp1,powerUp2,powerUp3,enemyBuff;
    private String selectedWord = "";
    private static Image WoodBack, ResetBtnPic, SubmitBtnPic,WoodSign,BackBtn,ExitBtnPic,NextBtn,pixelHeart,gains,xyzPic,fireBall,breeze,iceCube,droplet;
    private int BackVal;
    private boolean moveBack,winCondition,exitCondition,loseCondition;
    private BookwormAdventures frame;
    private Boolean animationPlaying, deathAnimationPlaying;
    private int stage;
    private SpriteList deathSpriteList;
    private Animation deathAnimation;
    private Point p;
    private boolean[] userStats = new boolean[4];
    private boolean [] lockStats = new boolean [4];

    public GamePanel(int value,String username,BookwormAdventures frame) throws IOException {
        addMouseListener(new clickListener());
        setSize(800,600);
        readLevelMemory();
        nativeBattleLogsCount = 0;
        winCondition = false;
        exitCondition = false;
        loseCondition = false;
        levelPog = new Level(value);
        letters = new Letters();
        level = value;
        p = getMousePosition();
        player = new Player(username,100);
        enemiesQueue = levelPog.getLevelEnemies();
        int rectCounter = 0;
        enemyCounter = 0;
        for (int x = 400; x<880;x+=120){
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
        deathSpriteList= new SpriteList("Pictures/Enemies/Death Animation",9);
        deathAnimation = new Animation(deathSpriteList.getList());
        for(int i = 0; i<16;i++){
            letterSlotsCondition[i] = true;
        }
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

        }
        catch (IOException e) {
            System.out.println(e);
        }
        battleLogs.add("Welcome to Bookworm Adventures");

        switch (level){
            case 1:
                battleLogs.add("You are in the Fire World!");
                break;
            case 2:
                battleLogs.add("You are in the Ice World!");
                break;
            case 3:
                battleLogs.add("You are in the Sky World");
                break;
            case 4:
                battleLogs.add("You are in the Water World!");
                break;
        }
        animationPlaying=true;
        deathAnimationPlaying=false;
        BackVal=0;
        moveBack=false;
        System.out.println(enemiesQueue);
        currentEnemy = enemiesQueue.get(enemyCounter);
        this.frame = frame;
    }
    public void setLevel(int value){
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

    }
    public int letterSlotBoolean(boolean a){
        int counter = 0;
        for(boolean l : letterSlotsCondition){
            if (l == a){
                counter ++;
            }
        }
        return counter;
    }
    public void mouseReset(){
        mx = 0;
        my = 0;
    }

    public void slotReset(){
        for(int i = 0; i<16;i++){
            letterSlotsCondition[i] = true;
        }
        selectedWord = "";
        alphabet.removeAll(alphabet);
        alphabet = letters.randomXletters(16);
        mx=0;
        my=0;
    }
    public void slotReplace(){
        int counter = 0;
        ArrayList <String> replacements = letters.randomXletters(letterSlotBoolean(false));
        for (int i = 0; i<16;i++){
            if(!letterSlotsCondition[i]){
                alphabet.set(i,replacements.get(counter));
                letterSlotsCondition[i] = true;
                counter++;
            }
        }
        selectedWord="";
        mouseReset();
    }
    public void checkBattleLogs() throws IOException {
        if(player.getNativeBattleLogs().size() > nativeBattleLogsCount){
            battleLogs.add(player.getNativeBattleLogs().get(player.getNativeBattleLogs().size() - 1));
            nativeBattleLogsCount ++;
        }
    }
    public int CountTextFileLines() throws FileNotFoundException {
        int counter = 1;
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Text Files/battleLogs.txt")));
        while(inFile.hasNextLine()){
            counter ++;
        }
        return counter;
    }

    public void editBattleLogs(String s) throws IOException {
        if(battleLogs.size() <= 18){
            battleLogs.add(s);
        }
        else{
            int a = battleLogs.size() - 18;
            for (int i = 0; i < a; i++) {
                battleLogs.remove(battleLogs.get(0));
            }
            battleLogs.add(s);
        }


    }
    public int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }

    public void battle(String word) throws IOException {
        if(currentEnemy.getWorldBuff().equals("Fire Buff")){
            player.setHealth(player.getHealth() - 5);
        }
        int missChance = randint(0,9);
        if (missChance != 9) {
            int damage = 0;
            if(currentEnemy.getWorldBuff().equals("Ice Buff")) {
                damage = (int)(player.damage(word) * 0.8);
            }
            else{
                damage = player.damage(word);
            }
            currentEnemy.setHealth(currentEnemy.getHealth() - damage);
            if(currentEnemy.getWorldBuff().equals("Sky Buff")){
                int chance = randint(0,6);
                if (chance == 5){
                    player.setHealth(player.getHealth() - damage);
                }
            }
            editBattleLogs("You have dealt " + damage + " damage to the enemy!");
            if (currentEnemy.getHealth() <= 0) {
                deathAnimationPlaying = true;
                enemyCounter++;
                currentEnemy.setHealth(0);
                if (enemyCounter < enemiesQueue.size()) {
                    currentEnemy = enemiesQueue.get(enemyCounter);
                    moveBack = true;
                    if (player.getHealth() + 40 > player.getMaxHealth()) {
                        player.setHealth(player.getMaxHealth());
                    }
                    else{
                        player.setHealth(player.getHealth() +40);
                    }
                    editBattleLogs("You have gained 40 health after winning that battle!");
                    chosenWords.clear();
                }

                else {
                    winCondition = true;
                    editBattleLogs("You have won this battle!");
                }
                editBattleLogs("This enemy has been defeated!");
            }
            else {
                int stunChance = randint(0,8);
                if(stunChance != 8) {
                    int enemyDamage = currentEnemy.doDamage();
                    if(currentEnemy.getWorldBuff().equals("Water Buff")){
                        enemyDamage += chosenWords.size();
                    }
                    player.setHealth(player.getHealth() - enemyDamage);
                    editBattleLogs("The enemy has dealt " + enemyDamage + " damage to you!");
                }
                else{
                    editBattleLogs("You have stunned your enemy!");
                    editBattleLogs("They cannot deal damage to you this turn");
                }
            }
        }
        else{
            int enemyDamage = currentEnemy.doDamage();
            if(currentEnemy.getWorldBuff().equals("Water Buff")){
                enemyDamage += chosenWords.size();
            }
            player.setHealth(player.getHealth() - enemyDamage);
            editBattleLogs("Your attack missed!");
            editBattleLogs("The enemy has dealt " + enemyDamage + " damage to you!");
        }
        if (enemyCounter > enemiesQueue.size()){
            currentEnemy = null;
            winCondition = true;
            editBattleLogs("YOU HAVE WON");

        }
    }

    public void addNotify() {
        super.addNotify();
        ready = true;
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
    public void readLevelMemory() throws FileNotFoundException {
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Text Files/levelMemory.txt")));

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
    public void changeLevelMemory() throws IOException {
        PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("Text Files/levelMemory.txt")));
        for(int i = 0;i<4;i++){
            if (i!=3) {
                if (userStats[i]) {
                    file.print("YES,");
                }
                else if(i == level-1){
                    file.print("YES,");
                }
                else{
                    file.print("NO,");
                }
            }
            else{
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
        file.println("");
        for(int a = 0;a <4 ;a++){
            if(a!=3){
                if (lockStats[a]){
                    file.print("UNLOCKED,");
                }
                else if (a == level){
                    file.print("UNLOCKED,");
                }
                else{
                    file.print("LOCKED,");
                }
            }
            else{
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
        file.println("");
        file.print(player.getUsername());
        file.close();
    }
    public void checkVowels() throws IOException {
        int checkVowelsCount = 0;
        for (String a: alphabet){
            if (a.toLowerCase().equals("a") || a.toLowerCase().equals("e") || a.toLowerCase().equals("i") || a.toLowerCase().equals("o") || a.toLowerCase().equals("u")){
                checkVowelsCount ++;
            }
        }
        if (checkVowelsCount == 0){
            alphabet = letters.randomXletters(16);
            System.out.println("NO VOWALS");
            editBattleLogs("There were no vowels, so your board has been reshuffled!");
        }
    }
    public void trackMousePosition(){
        Point p = MouseInfo.getPointerInfo().getLocation();
        px = p.x;
        py = p.y;
    }

    public void paintComponent(Graphics g) {
        p = getMousePosition();
        if(p == null){
            p = new Point(0,0);
        }
        g.drawImage(levelPog.getBack(),BackVal,0,this);
        g.drawImage(levelPog.getBack(),1280+BackVal,0,this);
        g.setColor(Color.BLACK);
        g.drawImage(WoodBack,0,260,this);
        g.drawImage(WoodBack,890,260,this);
//        g.setColor(Color.BLUE);
//        g.fillRect(0,260,400,460);
        for (int i = 0; i < 16; i++) {
            g.drawRect(letterSlots[i].x, letterSlots[i].y, letterSlots[i].width, letterSlots[i].height);
            if (letterSlotsCondition[i]) {
                g.drawImage(Letters.getImage("NORMAL", alphabet.get(i)), letterSlots[i].x + 20, letterSlots[i].y + 25, this);
            }


        }
        for (int i = 0; i < 16; i++) {
            if (letterSlots[i].contains(mx,my)) {
                letterSlotsCondition[i] = false;
                if (selectedWord.length() < letterSlotBoolean(false)) {
                    selectedWord += alphabet.get(i);
                }
            }
        }


        g.setColor(Color.BLACK);
        g.setFont(new Font("Comic Sans",Font.PLAIN,20));
        g.drawImage(ResetBtnPic,resetButton.x,resetButton.y,this);
        g.drawImage(SubmitBtnPic,submitButton.x,submitButton.y,this);
        g.drawImage(ExitBtnPic,exitButton.x,exitButton.y,this);
        g.drawImage(player.getAnimation().getSprite(),100,130,null);
        g.fillRect(0, 240, 1280, 20);
        g.fillRect(400, 240, 10, 580);
        g.fillRect(880, 240, 10, 580);
        g.fillRect(400,720,490,5);
        /*
        resetButton = new Rectangle(400,725,245,62);
        submitButton = new Rectangle(645,725,244,62);
         */
        g.fillRect(643,720,4,100);

        //username
        g.drawString(player.getUsername(),healthBar.x,20);
        g.fillRect(25,25,player.getMaxHealth()*2+10,30);
        g.fillRect(1045,25,210,30);
        g.setColor(Color.green);
        g.fillRect(healthBar.x,healthBar.y,player.getHealth()*2,healthBar.height);
        g.fillRect(healthBar2.x,healthBar2.y,currentEnemy.getHealth()*2,healthBar2.height);
        g.setColor(Color.red);
        g.fillRect(healthBar.x+player.getHealth()*2,healthBar.y,player.getMaxHealth()*2-player.getHealth()*2,healthBar.height);
        g.fillRect(healthBar2.x,healthBar2.y,currentEnemy.getHealth()*2,healthBar2.height);
        if(deathAnimationPlaying) {
            g.drawImage(deathAnimation.getSprite(), deathAnimation.getSpritePosX(), deathAnimation.getSpritePosY(), null);
        }

        g.setFont(new Font("Times New Roman",Font.BOLD,20));
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(player.getHealth()),125,47);

        if(currentEnemy!=null) {
            g.setColor(Color.BLACK);
            g.drawString(currentEnemy.getName(),1050,20);
            g.setColor(Color.green);
            g.drawString(Integer.toString(currentEnemy.getHealth()),1140, 47);
        }
        if (resetButton.contains(mx, my)) {
            for (int i = 0; i < 16; i++) {
                letterSlotsCondition[i] = true;
                selectedWord = "";
                g.setColor(Color.BLACK);
                //g.setColor(Color.WHITE);
                //g.fillRect(100, 100, 1200, 120);//need to learn how to undraw the letters
            }
        }
        if (selectedWord.length() > 0) {
            for (int i = 0; i < selectedWord.length(); i++) {
                g.drawImage(Letters.getImage("SMALL", String.valueOf(selectedWord.charAt(i))), 210 + 55 * i, 100, this);
            }
        }
        if (submitButton.contains(mx, my)) {
            if (selectedWord.length() > 1) {
                if (letters.checkWord(selectedWord)) {
                    chosenWords.add(selectedWord);
                    try {
                        battle(selectedWord);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    slotReplace();
                }
                else if (!letters.checkWord(selectedWord)) {
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
        if (chosenWords.size() >0){
            g.setFont(new Font("Comic Sans MS", Font.BOLD,20));
            for (int i = 0; i < chosenWords.size();i++){
                String word = chosenWords.get(i);
                String output = word.substring(0,1).toUpperCase() + word.substring(1);
                g.setColor(Color.BLACK);
                g.drawString(output,950,300+20*i);
            }
        }



        if(battleLogs.size() > 0) {
            for (int i = 0; i < battleLogs.size(); i++) {
                g.setFont(new Font("Times New Roman",Font.PLAIN,16));
                g.setColor(Color.DARK_GRAY);
                if(300 + i *25 > 800){
                    battleLogs.remove(battleLogs.get(0));
                }
                String currentBattleLog = battleLogs.get(i).substring(0,13);
                switch (currentBattleLog) {
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
        if (player.getXYZ()){
            g.drawImage(xyzPic,powerUp1.x,powerUp1.y,this);
        }
        if(player.getSixUp()){
            g.drawImage(gains,powerUp2.x,powerUp2.y,this);
        }
        if(player.getHealthUp()){
            g.drawImage(pixelHeart,powerUp3.x,powerUp2.y,this);
        }
        if (winCondition){
            if(level != 4) {
                g.drawImage(WoodSign, 640 - (WoodSign.getWidth(null) / 2), 210, null);
                g.setColor(Color.black);
                g.drawRect(backButton.x, backButton.y, backButton.width, backButton.height);
                g.drawRect(nextButton.x, nextButton.y, nextButton.width, nextButton.height);
                g.drawImage(BackBtn, backButton.x, backButton.y, null);
                g.drawImage(NextBtn, nextButton.x, nextButton.y, null);


                if (level == 1) {
                    g.drawString("You have gained this powerup: ", 475, 375);
                    g.drawImage(xyzPic, 625, 390, this);
                    g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
                    g.drawString("If your word contains x,y, or z, your word will do two times the damage for each one of those letters", 400, 455);
                }
                if (level == 2) {
                    g.drawString("You have gained this powerup: ", 475, 375);
                    g.drawImage(gains, 625, 390, this);
                    g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
                    g.drawString("If your word is six or more letters, it will do 50% more damage", 450, 455);
                }
                if (level == 3) {
                    g.drawString("You have gained this powerup: ", 475, 375);
                    g.drawImage(pixelHeart, 625, 390, this);
                    g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
                    g.drawString("You have gained 20 extra max Health!", 450, 455);
                }
                if (backButton.contains(mx, my)) {
                    try {
                        changeLevelMemory();
                        LevelSelect backTo = new LevelSelect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    frame.setVisible(false);
                }
                if (nextButton.contains(mx, my)) {
                    try {
                        changeLevelMemory();
                        BookwormAdventures nextLevel = new BookwormAdventures(level + 1);
                        frame.setVisible(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                System.out.println("you have won the game");
            }
        }

        if(exitButton.contains(mx,my)) {
            exitCondition = true;
        }
        if(exitCondition){
            g.setColor(Color.BLACK);
            g.fillRect(340,210,600,400);
            g.setColor(Color.GREEN);
            g.drawRect(backButton.x,backButton.y,backButton.width,backButton.height);
            g.drawRect(nextButton.x,nextButton.y,nextButton.width,nextButton.height);
            g.setFont(new Font("Times New Roman",Font.BOLD,25));
            g.setColor(Color.WHITE);
            g.drawString("Do you want to exit this level?",500,350);
            g.drawString("NO",backButton.x+30,backButton.y+30);
            g.drawString("YES",nextButton.x+30,nextButton.y+30);
            if(backButton.contains(mx,my)){
                mouseReset();
                exitCondition = false;
            }
            if(nextButton.contains(mx,my)){
                try {
                    LevelSelect backTo = new LevelSelect();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                frame.setVisible(false);
            }
        }
        if (player.getXYZ()) {
            if (powerUp1.contains(p.x, p.y)) {
                g.setColor(Color.white);
                g.fillRect(powerUp1.x, powerUp1.y + powerUp1.height + 5, 725, 25);
                g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                g.setColor(Color.black);
                g.drawString("This powerup makes any word that contains an x,y, or z deal twice the amount of damage", powerUp1.x + 3, powerUp1.y + powerUp1.height + 21);
            }
        }
        if(player.getSixUp()) {
            if (powerUp2.contains(p.x, p.y)) {
                g.setColor(Color.white);
                g.fillRect(powerUp2.x, powerUp2.y + powerUp2.height + 5, 760, 25);
                g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                g.setColor(Color.black);
                g.drawString("This powerup makes any word that contains 5 or more letters deal 50% the amount of damage", powerUp2.x + 3, powerUp2.y + powerUp2.height + 21);
            }
        }
        if(player.getHealthUp()) {
            if (powerUp3.contains(p.x, p.y)) {
                g.setColor(Color.white);
                g.fillRect(powerUp3.x, powerUp3.y + powerUp3.height + 5, 350, 25);
                g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                g.setColor(Color.black);
                g.drawString("This powerup gives you an extra 20 health!", powerUp3.x + 3, powerUp3.y + powerUp3.height + 21);
            }
        }
        if(loseCondition){
            g.drawImage(WoodSign,640-(WoodSign.getWidth(null)/2),210,null);
            g.setColor(Color.black);
            g.drawRect(backButton.x,backButton.y,backButton.width,backButton.height);
            g.drawImage(BackBtn,backButton.x,backButton.y,null);
        }
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
        if(enemyBuff.contains(p.x,p.y)) {
            g.setColor(Color.black);
            System.out.println(currentEnemy.getWorldBuff());
            if(currentEnemy.getWorldBuff().equals("Fire Buff")) {
                g.fillRect(595,85,375,27);
                g.setColor(Color.white);
                g.drawString("The enemies burn you, dealing 5 extra damage per round", 600, 100);
            }
            if (currentEnemy.getWorldBuff().equals("Ice Buff")) {
                g.fillRect(595,85,375,27);
                g.setColor(Color.white);
                g.drawString("Encased in ice, your attacks deal 20% less damage", 600, 100);
            }
            if(currentEnemy.getWorldBuff().equals("Sky Buff")) {
                g.fillRect(345,85,575,27);
                g.setColor(Color.white);
                g.drawString("Your attacks have a chance to be deflected back ,dealing 50% of the damage back to you", 350, 100);
            }
            if(currentEnemy.getWorldBuff().equals("Water Buff")){
                g.fillRect(345,85,580,27);
                g.setColor(Color.white);
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


        }

        public void mousePressed(MouseEvent e){
            mx = e.getX();
            my = e.getY();

        }
    }

}