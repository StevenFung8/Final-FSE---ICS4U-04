import com.sun.jdi.IntegerValue;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

class SkillTree extends JFrame {

    private JLayeredPane skillPane;
    private ImageIcon skillTree, backBtnPic;
    private JButton SBtn1, SBtn2, SBtn3, SBtn4, SBtn5, SBtn6, SBtn7;
    private Skill Skill1, Skill2, Skill3;
    private Boolean lock1, lock2, lock3, lock4, lock5, lock6, lock7;
    private Boolean[] skillLocks;
    private int sPoints;

    public SkillTree() throws IOException {
        super("Bookworm Adventures");
        setSize(1280, 820);
        skillPane = new JLayeredPane();
        lock1 =false;
        lock2 =false;
        lock3 =false;
        lock4 =false;
        lock5 =false;
        lock6 =false;
        lock7 =false;
        skillLocks = new Boolean[7];

        skillTree = new ImageIcon("Pictures/Backgrounds/SkillTree.png");
        backBtnPic = new ImageIcon("Pictures/StartMenu/BackButton.png");
        Skill1 = new Skill("1",583,530,"attackBoost");
        Skill2 = new Skill("2",375,345, "defenseBoost");
        Skill3 = new Skill("3", 793,345,"healthBoost");

        SBtn1 = new JButton(Skill1.getIcon());
        SBtn1.setActionCommand(Skill1.getAbility());
        SBtn1.addActionListener(new ClickStart(skillPane));
        SBtn1.setBounds(Skill1.getRect());
        skillPane.add(SBtn1,Integer.valueOf(2));

        SBtn2 = new JButton(Skill2.getIcon());
        SBtn2.setActionCommand(Skill2.getAbility());
        SBtn2.addActionListener(new ClickStart(skillPane));
        SBtn2.setBounds(Skill2.getRect());
        skillPane.add(SBtn2,Integer.valueOf(2));

        SBtn3 = new JButton(Skill3.getIcon());
        SBtn3.setActionCommand(Skill3.getAbility());
        SBtn3.addActionListener(new ClickStart(skillPane));
        SBtn3.setBounds(Skill3.getRect());
        skillPane.add(SBtn3,Integer.valueOf(2));

        JLabel skillBack = new JLabel(skillTree);
        skillBack.setBounds(0,0,1280,820);
        skillPane.add(skillBack,Integer.valueOf(1));


        JButton backBtn = new JButton(backBtnPic);
        backBtn.setBorder(new LineBorder(Color.BLACK));
        backBtn.setActionCommand("back");
        backBtn.addActionListener(new ClickStart(skillPane));
        backBtn.setBounds(50,700,300,100);
        skillPane.add(backBtn,Integer.valueOf(2));
        skillMemory();
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
    public void clearSkillMemory() throws IOException {
        PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("Text Files/skillMemory.txt")));
        for (int i = 0; i<6;i++){
            file.print("LOCKED,");
        }
        file.print("LOCKED");
        file.close();
    }
    public void changeSkillMemory(int n) throws IOException{
        PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("Text Files/skillMemory.txt")));
        skillLocks[n]=true;
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
        else if(!skillLocks[6]){
            file.print("LOCKED");
        }
        sPoints-=1;
        file.println("");
        file.print(sPoints);
        file.close();


    }
    public static void main(String[] arguments) {}
    class ClickStart implements ActionListener {

        private JLabel text1;
        private ImageIcon textPic1;
        private JLayeredPane skillPane;
        public ClickStart(JLayeredPane jLayeredPane){
            skillPane = jLayeredPane;
            textPic1 = new ImageIcon("Pictures/SkillTree/insufficientText.png");
            text1= new JLabel(textPic1);
//            text1.setBounds(640-);
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
                    if(sPoints>0) {
                        try {
                            changeSkillMemory(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{

                        skillPane.add(text1, Integer.valueOf(3));
                    }
                    break;
                case "defenseBoost":
                    try {
                        changeSkillMemory(1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "healthBoost":
                    try {
                        changeSkillMemory(2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;


            }
        }

    }
}