import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class LevelSelect extends JFrame {
    private boolean [] userStats = new boolean[4];
    private boolean [] lockStats = new boolean[4];
    public LevelSelect() throws FileNotFoundException {
        super("Bookworm Adventures");
        setSize(1280, 820);
        JLayeredPane layeredPane = new JLayeredPane();
        levelMemory();
        System.out.println(Arrays.toString(userStats));
        System.out.println(Arrays.toString(lockStats));
        ImageIcon backPic = new ImageIcon("Pictures/Backgrounds/LevelSelectBack.png");
        JLabel back = new JLabel(backPic);
        back.setBounds(0,0,1280,820);
        layeredPane.add(back,Integer.valueOf(1));

        ImageIcon oneIcon = new ImageIcon("Pictures/Backgrounds/FireWorld.jpg");
        Image onePic = oneIcon.getImage();
        Image smallOnePic = onePic.getScaledInstance(300, 169, Image.SCALE_SMOOTH);
        oneIcon = new ImageIcon(smallOnePic);
        JButton oneBtn = new JButton(oneIcon);
        if(!lockStats[0]){
            oneBtn.setBorder(BorderFactory.createLineBorder(Color.RED,4));
        }
        else {
            if (userStats[0]) {
                oneBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
            }
            else {
                oneBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            }
        }
        oneBtn.addActionListener(new LevelSelect.loadLevel());
        oneBtn.setBounds(300, 160, 300, 169);
        layeredPane.add(oneBtn, Integer.valueOf(2));
        oneBtn.setActionCommand("Button 1");


        ImageIcon twoIcon = new ImageIcon("Pictures/Backgrounds/IceWorld.png");
        Image twoPic = twoIcon.getImage();
        Image smallTwoPic = twoPic.getScaledInstance(300, 169, Image.SCALE_SMOOTH);
        twoIcon = new ImageIcon(smallTwoPic);
        JButton twoBtn = new JButton(twoIcon);
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



        ImageIcon threeIcon = new ImageIcon("Pictures/Backgrounds/SkyWorld.png");
        Image threePic = threeIcon.getImage();
        Image smallThreePic = threePic.getScaledInstance(300, 169, Image.SCALE_SMOOTH);
        threeIcon = new ImageIcon(smallThreePic);
        JButton threeBtn = new JButton(threeIcon);
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


        ImageIcon fourIcon = new ImageIcon("Pictures/Backgrounds/WaterWorld.png");
        Image fourPic = fourIcon.getImage();
        Image smallFourPic = fourPic.getScaledInstance(300, 169, Image.SCALE_SMOOTH);
        fourIcon = new ImageIcon(smallFourPic);
        JButton fourBtn = new JButton(fourIcon);
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

        ImageIcon backBtnPic = new ImageIcon("Pictures/StartMenu/BackButton.png");
        JButton backBtn = new JButton(backBtnPic);
        backBtn.setBorder(new LineBorder(Color.BLACK));
        backBtn.setActionCommand("back");
        backBtn.addActionListener(new LevelSelect.loadLevel());
        backBtn.setBounds(50,50,300,100);
        layeredPane.add(backBtn,Integer.valueOf(2));


        ImageIcon newBtnPic = new ImageIcon("Pictures/A.png");
        JButton newBtn = new JButton(newBtnPic);
        newBtn.setBorder(new LineBorder(Color.BLACK));
        newBtn.setActionCommand("new");
        newBtn.addActionListener(new LevelSelect.loadLevel());
        newBtn.setBounds(1000,650,100,100);
        layeredPane.add(newBtn,Integer.valueOf(3));

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
    class loadLevel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                System.out.println(evt.getActionCommand());
                switch (evt.getActionCommand()) {
                    case "Button 1":
                        if(lockStats[0]) {
                            BookwormAdventures game = new BookwormAdventures(1);
                            setVisible(false);
                        }
                        break;
                    case "Button 2":
                        if(lockStats[1]) {
                            BookwormAdventures game2 = new BookwormAdventures(2);
                            setVisible(false);
                        }
                        break;
                    case "Button 3":
                        if(lockStats[2]) {
                            BookwormAdventures game3 = new BookwormAdventures(3);
                            setVisible(false);
                        }
                        break;
                    case "Button 4":
                        if(lockStats[3]) {
                            BookwormAdventures game4 = new BookwormAdventures(4);
                            setVisible(false);
                        }
                        break;
                    case "back":
                        StartMenu startMenu = new StartMenu();
                        setVisible(false);
                        break;
                    case "new":
                        clearLevelMemory();
                        LevelSelect level = new LevelSelect();
                        setVisible(false);
                    case "skill":
                        SkillTree sTree = new SkillTree();
                        break;


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void levelMemory() throws FileNotFoundException {
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
    public void clearLevelMemory() throws IOException {
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

}
