import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class levelSelect extends JFrame {
    public levelSelect(){
        super("Bookworm Adventures");
        setSize(1280,820);
        JLayeredPane layeredPane = new JLayeredPane();


        ImageIcon oneIcon = new ImageIcon("Pictures/Backgrounds/FireWorld.jpg");
        Image onePic = oneIcon.getImage();
        Image smallOnePic = onePic.getScaledInstance(300,169, Image.SCALE_SMOOTH);
        oneIcon = new ImageIcon(smallOnePic);
        JButton oneBtn = new JButton(oneIcon);
        oneBtn.setBorder(new LineBorder(Color.BLACK));
        oneBtn.addActionListener(new levelSelect.loadLevel());
        oneBtn.setBounds(300,150,300,169);
        layeredPane.add(oneBtn,2);
        oneBtn.setActionCommand("Button 1");

        ImageIcon twoIcon = new ImageIcon("Pictures/Backgrounds/IceWorld.png");
        Image twoPic = twoIcon.getImage();
        Image smallTwoPic = twoPic.getScaledInstance(300,169, Image.SCALE_SMOOTH);
        twoIcon = new ImageIcon(smallTwoPic);
        JButton twoBtn = new JButton(twoIcon);
        twoBtn.setBorder(new LineBorder(Color.BLACK));
        twoBtn.addActionListener(new levelSelect.loadLevel());
        twoBtn.setBounds(650,150,300,169);
        layeredPane.add(twoBtn,2);
        twoBtn.setActionCommand("Button 2");

        ImageIcon threeIcon = new ImageIcon("Pictures/Backgrounds/SkyWorld.png");
        Image threePic = threeIcon.getImage();
        Image smallThreePic = threePic.getScaledInstance(300,169, Image.SCALE_SMOOTH);
        threeIcon = new ImageIcon(smallThreePic);
        JButton threeBtn = new JButton(threeIcon);
        threeBtn.setBorder(new LineBorder(Color.BLACK));
        threeBtn.addActionListener(new levelSelect.loadLevel());
        threeBtn.setBounds(300,450,300,169);
        layeredPane.add(threeBtn,2);
        threeBtn.setActionCommand("Button 3");

        ImageIcon fourIcon = new ImageIcon("Pictures/Backgrounds/WaterWorld.png");
        Image fourPic = fourIcon.getImage();
        Image smallFourPic = fourPic.getScaledInstance(300,169, Image.SCALE_SMOOTH);
        fourIcon = new ImageIcon(smallFourPic);
        JButton fourBtn = new JButton(fourIcon);
        fourBtn.setBorder(new LineBorder(Color.BLACK));
        fourBtn.addActionListener(new levelSelect.loadLevel());
        fourBtn.setBounds(650,450,300,169);
        layeredPane.add(fourBtn,2);
        fourBtn.setActionCommand("Button 4");

        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
    public static void main (String[]args){
        levelSelect levels = new levelSelect();
    }
    class loadLevel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt){
            try {
                switch (evt.getActionCommand()) {
                    case "Button 1" :
                        BookwormAdventures game = new BookwormAdventures(1);
                    case "Button 2":
                        BookwormAdventures game2 = new BookwormAdventures(2);
                    case "Button 3":
                        BookwormAdventures game3 = new BookwormAdventures(3);
                    case "Button 4":
                        BookwormAdventures game4 = new BookwormAdventures(4);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            setVisible(false);
        }
    }

}
