import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class startMenu extends JFrame {

    public startMenu() {
        super("Bookworm Adventures");
        setSize(714,714);

        ImageIcon backPic = new ImageIcon("Pictures//startBackground.jpg");
        JLabel back = new JLabel(backPic);
        back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(back,1);

        ImageIcon startPic = new ImageIcon("Pictures//PlayButton.png");
        JButton startBtn = new JButton(startPic);
        startBtn.setBackground(Color.BLACK);
        startBtn.setBorder(new LineBorder(Color.BLACK));
        startBtn.addActionListener(new ClickStart());
        startBtn.setBounds((714/2)-startPic.getIconWidth()/2,(714/2)-startPic.getIconHeight()/2,startPic.getIconWidth(),startPic.getIconHeight());
        layeredPane.add(startBtn,2);

        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] arguments) {
        startMenu frame = new startMenu();
    }

    class ClickStart implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt){
            levelSelect levels = new levelSelect();
            setVisible(false);
        }
    }
}

