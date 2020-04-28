import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class startMenu extends JFrame implements ActionListener{
    private JLayeredPane layeredPane=new JLayeredPane();
    private JLayeredPane creditsPane=new JLayeredPane();

    public startMenu() {
        super("Bookworm Adventures");
        setSize(1280,820);

        ImageIcon backPic = new ImageIcon("Pictures/StartMenu/Background.png");
        JLabel back = new JLabel(backPic);
        back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
        layeredPane.add(back,1);

        ImageIcon startPic = new ImageIcon("Pictures/StartMenu/PlayButton.png");
        JButton startBtn = new JButton(startPic);
        startBtn.setBorder(new LineBorder(Color.BLACK));
        startBtn.addActionListener(new ClickStart());
        startBtn.setBounds(625-300/2,350,300,100);
        layeredPane.add(startBtn,2);

        ImageIcon creditsPic = new ImageIcon("Pictures/StartMenu/CreditsButton.png");
        JButton creditsBtn = new JButton(creditsPic);
        creditsBtn.setBorder(new LineBorder(Color.BLACK));
        creditsBtn.addActionListener(new CreditStart());
        creditsBtn.setBounds(625-300/2,500,300,100);
        layeredPane.add(creditsBtn,3);



        ImageIcon backPic2 = new ImageIcon("Pictures/StartMenu/Library2.png");
        JLabel back2 = new JLabel(backPic2);
        back2.setBounds(0, 0,backPic2.getIconWidth(),backPic2.getIconHeight());
        creditsPane.add(back2,2);

        ImageIcon backBtnPic = new ImageIcon("Pictures/StartMenu/BackButton.png");
        JButton backBtn = new JButton(backBtnPic);
        backBtn.setBorder(new LineBorder(Color.BLACK));
        backBtn.addActionListener(new backStart());
        backBtn.setBounds(50,50,300,100);
        creditsPane.add(backBtn,2);




        setContentPane(layeredPane);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] arguments) {
        startMenu frame = new startMenu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    class ClickStart implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt){
            try {
                BookwormAdventures game = new BookwormAdventures();
                Level gay = new Level(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setVisible(false);
        }
    }
    class CreditStart implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            setContentPane(creditsPane);
            setVisible(true);
        }
    }
    class backStart implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            setContentPane(layeredPane);
            setVisible(true);
        }
    }
}


