import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


//LevelSelect.java
//Dylan Tan and Steven Fung
//This is the class that manages the level select screen

class LevelSelect extends JFrame {
    //for this we didn't have a game panel, instead we opted to use JButtons and JLabels to do the job
    private boolean [] userStats = new boolean[4]; //reads for level completion
    private boolean [] lockStats = new boolean[4];//reads for level accessibility
    private boolean writeName = false;
    private String username;
    private Font fantasy;
    private Sound titleMusic;
    public LevelSelect() throws IOException {
        super("Bookworm Adventures");
        setSize(1280, 820);
        JLayeredPane layeredPane = new JLayeredPane();//make a new pane
        levelMemory();//read from levelMemory
        titleMusic = new Sound("Music/titleScreen.wav",50); //start up some music
        titleMusic.play();
        Image icon = ImageIO.read(new File("Pictures/StartMenu/bookwormIcon.png")); //change the icon
        setIconImage(icon);
        if(writeName){ //if there is no username
            writeUsername(username);
        }
        try{//load custom text
            InputStream myStream = new BufferedInputStream(new FileInputStream("Fonts/RINGM___.TTF"));
            fantasy = Font.createFont(Font.TRUETYPE_FONT, myStream).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/RINGM___.TTF")));
        }
        catch (FontFormatException e) {
            e.printStackTrace();}

        ImageIcon backPic = new ImageIcon("Pictures/Backgrounds/LevelSelectBack.png");
        JLabel back = new JLabel(backPic);
        back.setBounds(0,0,1280,820);
        layeredPane.add(back,Integer.valueOf(1));

        //each button has this chunk of code
        //button for level one
        ImageIcon oneIcon = new ImageIcon("Pictures/Backgrounds/FireWorld.jpg"); //load the picture
        Image onePic = oneIcon.getImage(); //set the picture
        Image smallOnePic = onePic.getScaledInstance(300, 169, Image.SCALE_SMOOTH);//resize the picture
        oneIcon = new ImageIcon(smallOnePic);
        JButton oneBtn = new JButton(oneIcon);//set the button as this picture
        JLabel levelOneLabel = new JLabel(); //make a label to display level number
        levelOneLabel.setText("1");
        levelOneLabel.setBounds(310,155,25,50);
        levelOneLabel.setFont(fantasy);
        levelOneLabel.setForeground(Color.BLACK);
        layeredPane.add(levelOneLabel, Integer.valueOf(3)); //set the number as the top layer
        if(!lockStats[0]){ //if the level isn't unlocked make it red
            oneBtn.setBorder(BorderFactory.createLineBorder(Color.RED,4));
        }
        else {
            if (userStats[0]) { //if its unlocked and completed make it green
                oneBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
            }
            else { //if its unlocked but not completed make it black
                oneBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            }
        }
        oneBtn.addActionListener(new LevelSelect.loadLevel());
        oneBtn.setBounds(300, 160, 300, 169);
        layeredPane.add(oneBtn, Integer.valueOf(2));
        oneBtn.setActionCommand("Button 1"); //set command for switch

        //button for level 2 (check button 1 for comments)
        ImageIcon twoIcon = new ImageIcon("Pictures/Backgrounds/IceWorld.png");
        Image twoPic = twoIcon.getImage();
        Image smallTwoPic = twoPic.getScaledInstance(300, 169, Image.SCALE_SMOOTH);
        twoIcon = new ImageIcon(smallTwoPic);
        JButton twoBtn = new JButton(twoIcon);
        JLabel levelTwoLabel = new JLabel();
        levelTwoLabel.setText("2");
        levelTwoLabel.setBounds(660,155,25,50);
        levelTwoLabel.setFont(fantasy);
        levelTwoLabel.setForeground(Color.BLACK);
        layeredPane.add(levelTwoLabel,Integer.valueOf(3));
        if(!lockStats[1]){
            twoBtn.setBorder(BorderFactory.createLineBorder(Color.RED,4));
        }
        else {
            if (userStats[1]) {
                twoBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
            }
            else {
                twoBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            }
        }
        twoBtn.addActionListener(new LevelSelect.loadLevel());
        twoBtn.setBounds(650, 160, 300, 169);
        layeredPane.add(twoBtn, Integer.valueOf(2));
        twoBtn.setActionCommand("Button 2");


        //button for level 3 (check button 1 for comments)
        ImageIcon threeIcon = new ImageIcon("Pictures/Backgrounds/SkyWorld.png");
        Image threePic = threeIcon.getImage();
        Image smallThreePic = threePic.getScaledInstance(300, 169, Image.SCALE_SMOOTH);
        threeIcon = new ImageIcon(smallThreePic);
        JButton threeBtn = new JButton(threeIcon);
        JLabel levelThreeLabel = new JLabel();
        levelThreeLabel.setText("3");
        levelThreeLabel.setBounds(310,450,25,50);
        levelThreeLabel.setFont(fantasy);
        levelThreeLabel.setForeground(Color.BLACK);
        layeredPane.add(levelThreeLabel,Integer.valueOf(3));
        if(!lockStats[2]){
            threeBtn.setBorder(BorderFactory.createLineBorder(Color.RED,4));
        }
        else {
            if (userStats[2]) {
                threeBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
            }
            else {
                threeBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            }
        }
        threeBtn.addActionListener(new LevelSelect.loadLevel());
        threeBtn.setBounds(300, 450, 300, 169);
        layeredPane.add(threeBtn, Integer.valueOf(2));
        threeBtn.setActionCommand("Button 3");

        //button for level 4 (check button 1 for comments)
        ImageIcon fourIcon = new ImageIcon("Pictures/Backgrounds/WaterWorld.png");
        Image fourPic = fourIcon.getImage();
        Image smallFourPic = fourPic.getScaledInstance(300, 169, Image.SCALE_SMOOTH);
        fourIcon = new ImageIcon(smallFourPic);
        JButton fourBtn = new JButton(fourIcon);
        JLabel levelFourLabel = new JLabel();
        levelFourLabel.setText("4");
        levelFourLabel.setBounds(660,450,25,50);
        levelFourLabel.setFont(fantasy);
        levelFourLabel.setForeground(Color.BLACK);
        layeredPane.add(levelFourLabel,Integer.valueOf(3));
        if(!lockStats[3]){
            fourBtn.setBorder(BorderFactory.createLineBorder(Color.RED,4));
        }
        else {
            if (userStats[3]) {
                fourBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
            }
            else {
                fourBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            }
        }
        fourBtn.addActionListener(new LevelSelect.loadLevel());
        fourBtn.setBounds(650, 450, 300, 169);
        layeredPane.add(fourBtn, Integer.valueOf(2));
        fourBtn.setActionCommand("Button 4");

        //button for back button (check button 1 for comments)
        ImageIcon backBtnPic = new ImageIcon("Pictures/StartMenu/BackButton.png");
        JButton backBtn = new JButton(backBtnPic);
        backBtn.setBorder(new LineBorder(Color.BLACK));
        backBtn.setActionCommand("back");
        backBtn.addActionListener(new LevelSelect.loadLevel());
        backBtn.setBounds(50,50,300,100);
        layeredPane.add(backBtn,Integer.valueOf(2));

        //button for resetting progress (check button 1 for comments)
        ImageIcon newBtnPic = new ImageIcon("Pictures/A.png");
        JButton newBtn = new JButton(newBtnPic);
        newBtn.setBorder(new LineBorder(Color.BLACK));
        JLabel newLabel = new JLabel();
        newLabel.setText("reset");
        newLabel.setBounds(1010,650,100,100);
        newLabel.setFont(fantasy);
        newLabel.setForeground(Color.BLACK);
        layeredPane.add(newLabel,Integer.valueOf(3));
        newBtn.setActionCommand("new");
        newBtn.addActionListener(new LevelSelect.loadLevel());
        newBtn.setBounds(1000,650,100,100);
        layeredPane.add(newBtn,Integer.valueOf(3));

        //button for skillTree (check button 1 for comments)
        ImageIcon skillBtnPic = new ImageIcon("Pictures/SkillTree/treebtn.png");
        JButton skillBtn = new JButton(skillBtnPic);
        skillBtn.setBorder(new LineBorder(Color.DARK_GRAY));
        skillBtn.setActionCommand("skill");
        skillBtn.addActionListener(new LevelSelect.loadLevel());
        skillBtn.setBounds(1000,50,100,100);
        layeredPane.add(skillBtn,Integer.valueOf(3));


        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
//        LevelSelect levels = new LevelSelect();
    }
    class loadLevel implements ActionListener { //this sets the action for each button
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                System.out.println(evt.getActionCommand());
                switch (evt.getActionCommand()) { //switch to check the action command (see which button was pressed
                    case "Button 1": //for each button
                        if(lockStats[0]) { // if you unlocked the level
                            BookwormAdventures game = new BookwormAdventures(1); //load level
                            titleMusic.stop();
                            titleMusic.closeSound();
                            setVisible(false);
                        }
                        break;
                    case "Button 2": //level 2
                        if(lockStats[1]) {
                            BookwormAdventures game2 = new BookwormAdventures(2);
                            titleMusic.stop();
                            titleMusic.closeSound();
                            setVisible(false);
                        }
                        break;
                    case "Button 3": //level 3
                        if(lockStats[2]) {
                            BookwormAdventures game3 = new BookwormAdventures(3);
                            titleMusic.stop();
                            titleMusic.closeSound();
                            setVisible(false);
                        }
                        break;
                    case "Button 4": //level 4
                        if(lockStats[3]) {
                            BookwormAdventures game4 = new BookwormAdventures(4);
                            titleMusic.stop();
                            titleMusic.closeSound();
                            setVisible(false);
                        }
                        break;
                    case "back": //back button
                        StartMenu startMenu = new StartMenu();
                        titleMusic.stop();
                        titleMusic.closeSound();
                        setVisible(false);
                        break;
                    case "new": //reset progress
                        clearLevelMemory();
                        LevelSelect level = new LevelSelect();
                        titleMusic.stop();
                        titleMusic.closeSound();
                        setVisible(false);
                    case "skill": //skill tree
                        SkillTree sTree = new SkillTree();
                        titleMusic.stop();
                        titleMusic.closeSound();
                        setVisible(false);
                        break;


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void levelMemory() throws IOException { //read the text file for level completion and availability
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

        if(!inFile.hasNextLine()){
            username = JOptionPane.showInputDialog("Name:");
            writeName = true;

        }
        inFile.close();

    }
    public void clearLevelMemory() throws IOException { //resets your progress to default
        PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("Text Files/levelMemory.txt")));
        for (int i = 0; i<3;i++){
            file.print("NO,");
        }
        file.print("NO");
        file.println("");
        file.print("UNLOCKED,");
        for (int i = 0; i<2;i++){
            file.print("LOCKED,");
        }
        file.print("LOCKED");
        file.close();
    }

    public void writeUsername(String s) throws IOException { //write the players username
        PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("Text Files/levelMemory.txt",true)));
        file.println("");
        file.print(s);
        file.close();
    }

}
