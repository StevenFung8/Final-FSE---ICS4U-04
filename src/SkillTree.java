import com.sun.jdi.IntegerValue;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
class SkillTree extends JFrame {

    private JLayeredPane skillPane;
    private ImageIcon skillTree, backBtnPic;
    private JButton SBtn1, SBtn2, SBtn3, SBtn4, SBtn5, SBtn6, SBtn7;
    private Skill Skill1, Skill2, Skill3,Skill4,Skill5,Skill6,Skill7;
    private Boolean[] skillLocks;
    private int sPoints;
    private JLabel skillPoints;
    private JButton[] buttons = new JButton[7];


    public SkillTree() throws IOException {
        super("Bookworm Adventures");
        setSize(1280, 820);
        skillPane = new JLayeredPane();
        skillLocks = new Boolean[7];
        skillPoints = new JLabel();
        skillPane.add(skillPoints, Integer.valueOf(4));
        skillTree = new ImageIcon("Pictures/Backgrounds/SkillTree.png");
        backBtnPic = new ImageIcon("Pictures/StartMenu/BackButton.png");
        Skill1 = new Skill("1",583,530,"attackBoost");
        Skill2 = new Skill("2",375,345, "defenseBoost");
        Skill3 = new Skill("3", 793,345,"healthBoost");
        Skill4 = new Skill("4",270,162,"bleed");
        Skill5 = new Skill("5",480,165, "critical");
        Skill6 = new Skill("6", 690,162,"heal");
        Skill7 = new Skill("7",900,162,"spikeArmor");


        JPopupMenu popup1 = new JPopupMenu("AttackBoost");
        JMenuItem description1 = new JMenuItem("Attacks do 3 more damage");
        popup1.add(description1);
        SBtn1 = new JButton(Skill1.getIcon());
        SBtn1.setActionCommand(Skill1.getAbility());
        SBtn1.addActionListener(new ClickStart(skillPane, skillLocks));
        SBtn1.setBounds(Skill1.getRect());
        SBtn1.addMouseListener(new ClickStart(skillPane,skillLocks));
        SBtn1.setComponentPopupMenu(popup1);
        skillPane.add(SBtn1,Integer.valueOf(2));

        JPopupMenu popup2 = new JPopupMenu("defenseBoost");
        JMenuItem description2 = new JMenuItem("You take 1 less damage");
        popup2.add(description2);
        SBtn2 = new JButton(Skill2.getIcon());
        SBtn2.setActionCommand(Skill2.getAbility());
        SBtn2.addActionListener(new ClickStart(skillPane, skillLocks));
        SBtn2.addMouseListener(new ClickStart(skillPane,skillLocks));
        SBtn2.setBounds(Skill2.getRect());
        SBtn2.setComponentPopupMenu(popup2);
        skillPane.add(SBtn2,Integer.valueOf(2));

        JPopupMenu popup3 = new JPopupMenu("healthBoost");
        JMenuItem description3 = new JMenuItem("Health increased by 25%");
        popup3.add(description3);
        SBtn3 = new JButton(Skill3.getIcon());
        SBtn3.setActionCommand(Skill3.getAbility());
        SBtn3.addActionListener(new ClickStart(skillPane, skillLocks));
        SBtn3.setBounds(Skill3.getRect());
        SBtn3.addMouseListener(new ClickStart(skillPane,skillLocks));
        SBtn3.setComponentPopupMenu(popup3);
        skillPane.add(SBtn3,Integer.valueOf(2));

        JPopupMenu popup4 = new JPopupMenu("bleed");
        JMenuItem description4 = new JMenuItem("20% chance to strike an artery and make opponent bleed");
        popup4.add(description4);
        SBtn4 = new JButton(Skill4.getIcon());
        SBtn4.setActionCommand(Skill4.getAbility());
        SBtn4.addActionListener(new ClickStart(skillPane, skillLocks));
        SBtn4.setBounds(Skill4.getRect());
        SBtn4.setComponentPopupMenu(popup4);
        SBtn4.addMouseListener(new ClickStart(skillPane,skillLocks));
        skillPane.add(SBtn4,Integer.valueOf(2));

        JPopupMenu popup5 = new JPopupMenu("Critical");
        JMenuItem description5 = new JMenuItem("10% chance to deal an attack that does 2x the damage");
        popup5.add(description5);
        SBtn5 = new JButton(Skill5.getIcon());
        SBtn5.setActionCommand(Skill5.getAbility());
        SBtn5.addActionListener(new ClickStart(skillPane, skillLocks));
        SBtn5.setBounds(Skill5.getRect());
        SBtn5.setComponentPopupMenu(popup5);
        SBtn5.addMouseListener(new ClickStart(skillPane,skillLocks));
        skillPane.add(SBtn5,Integer.valueOf(2));

        JPopupMenu popup6 = new JPopupMenu("heal");
        JMenuItem description6 = new JMenuItem("Heal 4 hp per round");
        popup6.add(description6);
        SBtn6 = new JButton(Skill6.getIcon());
        SBtn6.setActionCommand(Skill6.getAbility());
        SBtn6.addActionListener(new ClickStart(skillPane, skillLocks));
        SBtn6.setBounds(Skill6.getRect());
        SBtn6.setComponentPopupMenu(popup6);
        SBtn6.addMouseListener(new ClickStart(skillPane,skillLocks));
        skillPane.add(SBtn6,Integer.valueOf(2));

        JPopupMenu popup7 = new JPopupMenu("spikeShield");
        JMenuItem description7 = new JMenuItem("Whenever you're attacked the enemy will sustain a bit of damage");
        popup7.add(description7);
        SBtn7 = new JButton(Skill7.getIcon());
        SBtn7.setActionCommand(Skill7.getAbility());
        SBtn7.addActionListener(new ClickStart(skillPane, skillLocks));
        SBtn7.setBounds(Skill7.getRect());
        SBtn7.setComponentPopupMenu(popup7);
        SBtn7.addMouseListener(new ClickStart(skillPane,skillLocks));
        skillPane.add(SBtn7,Integer.valueOf(2));

        buttons[0]=SBtn1;
        buttons[1]=SBtn2;
        buttons[2]=SBtn3;
        buttons[3]=SBtn4;
        buttons[4]=SBtn5;
        buttons[5]=SBtn6;
        buttons[6]=SBtn7;
        JLabel skillBack = new JLabel(skillTree);
        skillBack.setBounds(0,0,1280,820);
        skillPane.add(skillBack,Integer.valueOf(1));


        JButton backBtn = new JButton(backBtnPic);
        backBtn.setBorder(new LineBorder(Color.BLACK));
        backBtn.setActionCommand("back");
        backBtn.addActionListener(new ClickStart(skillPane, skillLocks));
        backBtn.setBounds(20,20,300,100);
        skillPane.add(backBtn,Integer.valueOf(2));
        skillMemory();
        skillPointsDisplay();
        for(int i=0 ; i<7; i++){
            if (skillLocks[i]){
                buttons[i].setBorder(new LineBorder(Color.BLUE,5));
            }
        }
        System.out.println(Arrays.toString(skillLocks));





        setContentPane(skillPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    public void skillMemory() throws IOException {
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Text Files/skillMemory.txt")));

        String stats = inFile.nextLine();
        String [] skillStats = stats.split(",");
        System.out.println(Arrays.toString(skillStats));
        for(int i = 0; i<skillStats.length; i++){
            skillLocks[i] = skillStats[i].equals("UNLOCKED");
        }
        sPoints = inFile.nextInt();

        inFile.close();
    }
    public void skillPointsDisplay() throws FileNotFoundException {

        String SP = "";
        try {
            FileReader readFile = new FileReader("Text Files/skillMemory.txt");
            BufferedReader readBuffer = new BufferedReader(readFile);
            readBuffer.readLine();
            SP = readBuffer.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }

        skillPoints.setText(SP + " SP");
        skillPoints.setBounds(50,400,100,60);
        skillPoints.setFont(new Font("Times New Roman",Font.BOLD,50));
        skillPoints.setForeground(Color.BLACK);


    }

    public void changeSkillMemory(int n) throws IOException{
        PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("Text Files/skillMemory.txt")));
        skillLocks[n]=true;
        buttons[n].setBorder(new LineBorder(Color.BLUE,5));
        for (int i = 0; i<6; i++){
            String s = skillLocks[i].toString();
            if (s.equals("false")){
                file.print("LOCKED,");
            }
            else if(s.equals("true")){
                file.print("UNLOCKED,");
            }
        }
        if(skillLocks[6]){
            file.print("UNLOCKED");
        }
        else {
            file.print("LOCKED");
        }
        sPoints-=1;
        file.println("");
        file.print(sPoints);
        file.close();


    }
    public static void main(String[] arguments) {
    }
    class ClickStart implements ActionListener, MouseListener {

        private JLabel text1,text2,skillBack;
        private ImageIcon textPic1,textPic2;
        private JLayeredPane skillPane;
        private Boolean[] skillLocks;

        public ClickStart(JLayeredPane jLayeredPane, Boolean[] locks){
            skillLocks = locks;
            skillPane = jLayeredPane;
            textPic1 = new ImageIcon("Pictures/SkillTree/insufficientText.png");
            text1= new JLabel(textPic1);
            text1.setBounds(340,720,textPic1.getIconWidth(),textPic1.getIconHeight());
            textPic2 = new ImageIcon("Pictures/SkillTree/prevSkillText.png");
            text2= new JLabel(textPic2);
            text2.setBounds(340,650,textPic2.getIconWidth(),textPic2.getIconHeight());
            skillBack = new JLabel(skillTree);
            skillBack.setBounds(0,0,1280,820);


        }


        @Override

        public void actionPerformed(ActionEvent evt){
            switch (evt.getActionCommand()) {
                case "back":
                    try {
                        LevelSelect levels = new LevelSelect();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Level newLevel = new Level(1);
                    setVisible(false);
                    break;
                case "attackBoost" :
                    if(!skillLocks[0]) {
                        if (sPoints > 0) {
                            try {
                                changeSkillMemory(0);
                                skillPointsDisplay();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            skillPane.add(skillBack, Integer.valueOf(1));
                            skillPane.add(text1, Integer.valueOf(3));
                        }
                    }
                    break;
                case "defenseBoost":
                    if(!skillLocks[1]) {
                        if (sPoints > 0 && skillLocks[0]) {
                            try {
                                changeSkillMemory(1);
                                skillPointsDisplay();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (sPoints == 0) {

                            skillPane.add(text1, Integer.valueOf(3));
                        } else if (!skillLocks[0]) {

                            skillPane.add(text2, Integer.valueOf(3));
                        } else {

                            skillPane.add(text1, Integer.valueOf(3));
                            skillPane.add(text2, Integer.valueOf(3));
                        }
                    }
                    System.out.println(Arrays.toString(skillLocks));
                    break;
                case "healthBoost":
                    if(!skillLocks[2]) {
                        if (sPoints > 0 && skillLocks[0]) {
                            try {
                                changeSkillMemory(2);
                                skillPointsDisplay();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else if (sPoints == 0) {//not enough sp
                            skillPane.add(text1, Integer.valueOf(3));
                        } else if (!skillLocks[0]) {//haven't unlocked prev skill
                            skillPane.add(text2, Integer.valueOf(3));
                        } else {//both
                            skillPane.add(text1, Integer.valueOf(3));
                            skillPane.add(text2, Integer.valueOf(3));
                        }
                    }
                    System.out.println(Arrays.toString(skillLocks));
                    break;
                case "bleed":
                    if(!skillLocks[3]) {
                        if (sPoints > 0 && skillLocks[1]) {
                            try {
                                changeSkillMemory(3);
                                skillPointsDisplay();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (sPoints == 0) {

                            skillPane.add(text1, Integer.valueOf(3));
                        } else if (!skillLocks[1]) {

                            skillPane.add(text2, Integer.valueOf(3));
                        } else {

                            skillPane.add(text1, Integer.valueOf(3));
                            skillPane.add(text2, Integer.valueOf(3));
                        }
                    }
                    System.out.println(Arrays.toString(skillLocks));
                    break;
                case "critical":
                    if(!skillLocks[4]) {
                        if (sPoints > 0 && skillLocks[1]) {
                            try {
                                changeSkillMemory(4);
                                skillPointsDisplay();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (sPoints == 0) {

                            skillPane.add(text1, Integer.valueOf(3));
                        } else if (!skillLocks[1]) {

                            skillPane.add(text2, Integer.valueOf(3));
                        } else {

                            skillPane.add(text1, Integer.valueOf(3));
                            skillPane.add(text2, Integer.valueOf(3));
                        }
                    }
                    System.out.println(Arrays.toString(skillLocks));
                    break;
                case "heal":
                    if(!skillLocks[5]) {
                        if (sPoints > 0 && skillLocks[2]) {
                            try {
                                changeSkillMemory(5);
                                skillPointsDisplay();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (sPoints == 0) {

                            skillPane.add(text1, Integer.valueOf(3));
                        } else if (!skillLocks[2]) {

                            skillPane.add(text2, Integer.valueOf(3));
                        } else {

                            skillPane.add(text1, Integer.valueOf(3));
                            skillPane.add(text2, Integer.valueOf(3));
                        }
                    }
                    System.out.println(Arrays.toString(skillLocks));
                    break;

                case "spikeArmor":
                    if(!skillLocks[6]) {
                        if (sPoints > 0 && skillLocks[2]) {
                            try {
                                changeSkillMemory(6);
                                skillPointsDisplay();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (sPoints == 0) {

                            skillPane.add(text1, Integer.valueOf(3));
                        } else if (!skillLocks[2]) {

                            skillPane.add(text2, Integer.valueOf(3));
                        } else {

                            skillPane.add(text1, Integer.valueOf(3));
                            skillPane.add(text2, Integer.valueOf(3));
                        }
                    }
                    System.out.println(Arrays.toString(skillLocks));
                    break;
            }
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            JButton jbutton = (JButton) mouseEvent.getSource();
            jbutton.getComponentPopupMenu().show(skillPane,jbutton.getX()+jbutton.getWidth(),jbutton.getY() + jbutton.getHeight());

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            JButton jButton = (JButton) mouseEvent.getSource();


        }
    }
}