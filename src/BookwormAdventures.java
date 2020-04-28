import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BookwormAdventures extends JFrame {
    private static int level;
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
        game = new GamePanel(level);
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
    private ArrayList<Enemies> enemiesQueue = new ArrayList<>();
    private int mx,my,enemyCounter;
    private Rectangle[] letterSlots = new Rectangle[16];
    private boolean[] letterSlotsCondition = new boolean[16];
    private ArrayList<String> alphabet = new ArrayList<>();
    private ArrayList<String> battleLogs = new ArrayList<>();
    private ArrayList<String> chosenWords = new ArrayList<String>();
    private Rectangle resetButton,submitButton;
    private String selectedWord = "";
    private static Image FireBack, IceBack, SkyBack, WaterBack;
    private Animation FireDragonIdle;
    private SpriteList FireDragonList;
    private Boolean animationPlaying;

    public GamePanel(int value) throws IOException {
        addMouseListener(new clickListener());
        setSize(800,600);
        levelPog = new Level(value);
        letters = new Letters();
        player = new Player("StyleDaddy",100);
        enemiesQueue = levelPog.getLevelEnemies();
        currentEnemy = enemiesQueue.get(enemyCounter);
        int rectCounter = 0;
        enemyCounter = 0;
        for (int x = 400; x<880;x+=120){
            for (int y = 240 ; y<720; y+=120){
                letterSlots[rectCounter] = new Rectangle(x,y,120,120);
                rectCounter++;
            }
        }
        resetButton = new Rectangle(400,720,240,100);
        submitButton = new Rectangle(650,720,240,100);
        for(int i = 0; i<16;i++){
            letterSlotsCondition[i] = true;
        }
        alphabet = letters.randomXletters(16);
        try {
            //Loading Backgrounds
            FireBack = ImageIO.read(new File("Pictures//Backgrounds//FireWorld.jpg"));
            IceBack = ImageIO.read(new File("Pictures//Backgrounds//IceWorld.png"));
            SkyBack = ImageIO.read(new File("Pictures//Backgrounds//SkyWorld.png"));
            WaterBack = ImageIO.read(new File("Pictures//Backgrounds//WaterWorld.png"));
        }
        catch (IOException e) {
            System.out.println(e);
        }
        battleLogs.add("Welcome to Bookworm Adventures");
        animationPlaying=false;
        FireDragonList = new SpriteList("Pictures/Enemies/Fire World/Fire Dragon",4);
        FireDragonIdle = new Animation(FireDragonList.getList(),5);

    }
    public void setLevel(int value){
        level = value;
    }

    public void update(){
        if(animationPlaying) {
            FireDragonIdle.update();
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
    public void editBattleLogs(String s){
        if(battleLogs.size() <= 19){
            battleLogs.add(s);
        }
        else{
            battleLogs.remove(battleLogs.get(0));
            battleLogs.add(s);
        }
    }
    public int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }

    public void battle(String word){
        int damage = player.damage(word);
        int enemyDamage = randint(0,5);
        currentEnemy.setHealth(currentEnemy.getHealth()-damage);
        player.setHealth(player.getHealth()- enemyDamage);
        if (currentEnemy.getHealth() <=0){
            enemyCounter++;
            if (enemyCounter < enemiesQueue.size()){
                currentEnemy = enemiesQueue.get(enemyCounter);
            }
            else{
                System.out.println("you have won");
                editBattleLogs("You have won this battle!");
            }
        }
        editBattleLogs("You have dealt " + damage + " damage to the enemy!");
        editBattleLogs("The enemy has dealt " + enemyDamage + " damage to you!");
        if (enemyCounter > enemiesQueue.size()){
            currentEnemy = null;
            System.out.println("you have won");
            editBattleLogs("YOU HAVE WON");
        }
    }

    public void addNotify() {
        super.addNotify();
        ready = true;
    }
    public Animation getAnimation(){
        return FireDragonIdle;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,0,1280,820);
        if (level == 1) {
            g.setColor(Color.white);
        }
        g.setColor(Color.BLACK);
        g.fillRect(0, 240, 1280, 20);
        g.fillRect(400, 240, 10, 480);
        g.fillRect(880, 240, 10, 480);
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
        g.setFont(new Font("Times New Roman",Font.BOLD,30));
        g.fillRect(100,180,50,50);
        g.drawString(Integer.toString(player.getHealth()),100,100);


        g.setColor(Color.BLACK);
        g.setFont(new Font("Comic Sans",Font.PLAIN,20));
        g.drawRect(resetButton.x, resetButton.y, resetButton.width + 10, resetButton.height);
        g.drawString("RESET",resetButton.x+resetButton.width/2-20,resetButton.y+resetButton.height/2);
        g.drawRect(submitButton.x, submitButton.y, submitButton.width + 10, submitButton.height);
        g.drawString("SUBMIT WORD",submitButton.x+submitButton.width/2-40,submitButton.y+submitButton.height/2);
        if (resetButton.contains(mx, my)) {
            for (int i = 0; i < 16; i++) {
                letterSlotsCondition[i] = true;
                selectedWord = "";
                g.setColor(Color.WHITE);
                //g.setColor(Color.WHITE);
                //g.fillRect(100, 100, 1200, 120);//need to learn how to undraw the letters
            }
        }
        if (selectedWord.length() > 0) {
            for (int i = 0; i < selectedWord.length(); i++) {
                g.drawImage(Letters.getImage("SMALL", String.valueOf(selectedWord.charAt(i))), 200 + 55 * i, 100, this);
            }
        }
        if (submitButton.contains(mx, my)) {
            if (selectedWord.length() > 1) {
                if (letters.checkWord(selectedWord)) {
                    g.setColor(Color.GREEN);
                    g.fillRect(200, 400, 50, 50);
                    chosenWords.add(selectedWord);
                    battle(selectedWord);
                    slotReplace();
                }
                else if (!letters.checkWord(selectedWord)) {
                    editBattleLogs("That is not a valid word");
                    mouseReset();
                }
            }
            else{
                editBattleLogs("That is not valid word");
                System.out.println("gay");
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


        if(currentEnemy!=null) {
            g.drawString(currentEnemy.getName(),1000,100);
            g.setColor(Color.red);
            g.drawString(Integer.toString(currentEnemy.getHealth()),1150,100);
        }
        if(battleLogs.size() > 0) {
            for (int i = 0; i < battleLogs.size(); i++) {
                g.setFont(new Font("Times New Roman",Font.PLAIN,15));
                g.setColor(Color.DARK_GRAY);
                if(300 + i *25 > 800){
                    battleLogs.remove(battleLogs.get(0));

                }
                g.drawString(battleLogs.get(i), 20, 300 + i * 25);
            }
        }
        g.drawImage(FireDragonIdle.getSprite(),FireDragonIdle.getSpritePosX(),FireDragonIdle.getSpritePosY(),null);
        //System.out.println("image drawn");
        //g.drawImage(FireDragonIdle.getSprite2(), 200, 50,null);
    }
    public void setGameLevel(int value){
        level = value;
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
            animationPlaying = !animationPlaying;

        }

        public void mousePressed(MouseEvent e){
            mx = e.getX();
            my = e.getY();




        }
    }

}