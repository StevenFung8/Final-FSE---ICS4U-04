import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame implements ActionListener{
    private JLayeredPane layeredPane=new JLayeredPane();
    private JLayeredPane creditsPane=new JLayeredPane();


    public StartMenu() {
        super("Bookworm Adventures");
        setSize(1280,820);

        ImageIcon backPic = new ImageIcon("Pictures/StartMenu/Background.png");
        JLabel back = new JLabel(backPic);
        back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
        layeredPane.add(back,Integer.valueOf(1));


        ImageIcon startPic = new ImageIcon("Pictures/StartMenu/PlayButton.png");
        JButton startBtn = new JButton(startPic);
        startBtn.setBorder(new LineBorder(Color.BLACK));
        startBtn.setActionCommand("Start");
        startBtn.addActionListener(new ClickStart(this));
        startBtn.setBounds(625-300/2,175,300,100);
        layeredPane.add(startBtn,Integer.valueOf(2));

        ImageIcon creditsPic = new ImageIcon("Pictures/StartMenu/CreditsButton.png");
        JButton creditsBtn = new JButton(creditsPic);
        creditsBtn.setBorder(new LineBorder(Color.BLACK));
        creditsBtn.setActionCommand("Credits");
        creditsBtn.addActionListener(new ClickStart(this));
        creditsBtn.setBounds(625-300/2,325,300,100);
        layeredPane.add(creditsBtn,Integer.valueOf(2));

        ImageIcon exitPic = new ImageIcon("Pictures/StartMenu/ExitButton.png");
        JButton exitBtn = new JButton(exitPic);
        exitBtn.setBorder(new LineBorder(Color.BLACK));
        exitBtn.setActionCommand("Exit");
        exitBtn.addActionListener(new ClickStart(this));
        exitBtn.setBounds(625-300/2,475,300,100);
        layeredPane.add(exitBtn,Integer.valueOf(2));



        ImageIcon backPic2 = new ImageIcon("Pictures/StartMenu/Library2.png");
        JLabel back2 = new JLabel(backPic2);
        back2.setBounds(0, 0,backPic2.getIconWidth(),backPic2.getIconHeight());
        creditsPane.add(back2,Integer.valueOf(1));

        ImageIcon backBtnPic = new ImageIcon("Pictures/StartMenu/BackButton.png");
        JButton backBtn = new JButton(backBtnPic);
        backBtn.setBorder(new LineBorder(Color.BLACK));
        backBtn.setActionCommand("back");
        backBtn.addActionListener(new ClickStart(this));
        backBtn.setBounds(50,50,300,100);
        creditsPane.add(backBtn,Integer.valueOf(2));

        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] arguments) {
        StartMenu frame = new StartMenu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    class ClickStart implements ActionListener {
        private StartMenu parent;
        public ClickStart(StartMenu parent){
            this.parent=parent;
        }
        @Override

        public void actionPerformed(ActionEvent evt){
            switch (evt.getActionCommand()) {
                case "Start":
                    //LevelSelect levels = new LevelSelect(parent);
                    Level gay = new Level(1);
                    setVisible(false);
                    break;
                case "Credits":
                    setContentPane(creditsPane);
                    setVisible(true);
                    break;
                case "back":
                    setContentPane(layeredPane);
                    setVisible(true);
                    break;
                case "Exit":
                    System.exit(1);
            }
        }
    }
}


