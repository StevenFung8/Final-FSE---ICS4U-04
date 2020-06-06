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

    public SkillTree() throws FileNotFoundException {
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

        skillTree = new ImageIcon("Pictures/Backgrounds/SkillTree.png");
        backBtnPic = new ImageIcon("Pictures/StartMenu/BackButton.png");
        Skill1 = new Skill("1",583,530,"attackBoost");
        Skill2 = new Skill("2",375,345, "defenseBoost");
        Skill3 = new Skill("3", 793,345,"healthBoost");

        SBtn1 = new JButton(Skill1.getIcon());
        SBtn1.setActionCommand(Skill1.getAbility());
        SBtn1.addActionListener(new ClickStart(this));
        SBtn1.setBounds(Skill1.getRect());
        skillPane.add(SBtn1,Integer.valueOf(2));

        SBtn2 = new JButton(Skill2.getIcon());
        SBtn2.setActionCommand(Skill2.getAbility());
        SBtn2.addActionListener(new ClickStart(this));
        SBtn2.setBounds(Skill2.getRect());
        skillPane.add(SBtn2,Integer.valueOf(2));

        SBtn3 = new JButton(Skill3.getIcon());
        SBtn3.setActionCommand(Skill3.getAbility());
        SBtn3.addActionListener(new ClickStart(this));
        SBtn3.setBounds(Skill3.getRect());
        skillPane.add(SBtn3,Integer.valueOf(2));

        JLabel skillBack = new JLabel(skillTree);
        skillBack.setBounds(0,0,1280,820);
        skillPane.add(skillBack,Integer.valueOf(1));


        JButton backBtn = new JButton(backBtnPic);
        backBtn.setBorder(new LineBorder(Color.BLACK));
        backBtn.setActionCommand("back");
        backBtn.addActionListener(new ClickStart(this));
        backBtn.setBounds(50,700,300,100);
        skillPane.add(backBtn,Integer.valueOf(2));





        setContentPane(skillPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    public  Boolean getLock1() {
        return lock1;
    }

    public Boolean getLock2() {
        return lock2;
    }

    public Boolean getLock3() {
        return lock3;
    }

    public Boolean getLock4() {
        return lock4;
    }

    public  Boolean getLock5() {
        return lock5;
    }

    public  Boolean getLock6() {
        return lock6;
    }

    public  Boolean getLock7() {
        return lock7;
    }

    public static void main(String[] arguments) {}
    class ClickStart implements ActionListener {
        private SkillTree parent;
        public ClickStart(SkillTree parent){
            this.parent=parent;
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
                    lock1=true;
                    break;
                case "defenseBoost":
                    lock2=true;
                    break;
                case "healthBoost":
                    lock3=true;
                    break;


            }
        }
    }
}