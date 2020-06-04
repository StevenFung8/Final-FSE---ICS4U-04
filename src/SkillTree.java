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
    private ImageIcon SPic1, SPic2, SPic3, SPic4, SPic5, SPic6, SPic7, skillTree, backBtnPic;
    private JButton SBtn1, SBtn2, SBtn3, SBtn4, SBtn5, SBtn6, SBtn7;
    private Skill Skill1;

    public SkillTree() throws FileNotFoundException {
        super("Bookworm Adventures");
        setSize(1280, 820);
        skillPane = new JLayeredPane();
        skillTree = new ImageIcon("Pictures/Backgrounds/SkillTree.png");
        backBtnPic = new ImageIcon("Pictures/StartMenu/BackButton.png");
        Skill1 = new Skill("1");
        SPic1 = new ImageIcon("Pictures/SkillTree/Skill1.png");
        SPic2 = new ImageIcon("Pictures/SkillTree/Skill2.png");
        SPic3 = new ImageIcon("Pictures/SkillTree/Skill3.png");
        SPic4 = new ImageIcon("Pictures/SkillTree/Skill4.png");
        SPic5 = new ImageIcon("Pictures/SkillTree/Skill5.png");
        SPic6 = new ImageIcon("Pictures/SkillTree/Skill6.png");
        SPic7 = new ImageIcon("Pictures/SkillTree/Skill7.png");

        SBtn1 = new JButton(SPic1);
        SBtn2 = new JButton(SPic2);
        SBtn3 = new JButton(SPic3);
        SBtn4 = new JButton(SPic4);
        SBtn5 = new JButton(SPic5);
        SBtn6 = new JButton(SPic6);
        SBtn7 = new JButton(SPic7);

        SBtn1.setBounds(600,600,100,100);
        SBtn2.setBounds(600,600,100,100);
        SBtn3.setBounds(600,600,100,100);
        SBtn4.setBounds(600,600,100,100);
        SBtn5.setBounds(600,600,100,100);
        SBtn6.setBounds(600,600,100,100);
        SBtn7.setBounds(600,600,100,100);




        SBtn1.setActionCommand("S1");
        SBtn2.setActionCommand("S2");
        SBtn3.setActionCommand("S3");
        SBtn4.setActionCommand("S4");
        SBtn5.setActionCommand("S5");
        SBtn6.setActionCommand("S6");
        SBtn7.setActionCommand("S7");

        SBtn1.addActionListener(new ClickStart(this));
        SBtn2.addActionListener(new ClickStart(this));
        SBtn3.addActionListener(new ClickStart(this));
        SBtn4.addActionListener(new ClickStart(this));
        SBtn5.addActionListener(new ClickStart(this));
        SBtn6.addActionListener(new ClickStart(this));
        SBtn7.addActionListener(new ClickStart(this));


        skillPane.add(SBtn1,Integer.valueOf(2));
        skillPane.add(SBtn2,Integer.valueOf(2));
        skillPane.add(SBtn3,Integer.valueOf(2));
        skillPane.add(SBtn4,Integer.valueOf(2));
        skillPane.add(SBtn5,Integer.valueOf(2));
        skillPane.add(SBtn6,Integer.valueOf(2));
        skillPane.add(SBtn7,Integer.valueOf(2));



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
                    }
                    Level newLevel = new Level(1);
                    setVisible(false);
                    break;
                case "S1":



            }
        }
    }
}