import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BookwormAdventures extends JFrame {
    Timer myTimer;
    private Image bookwormIcon;
    GamePanel game;
    public static void main(String[] arguments) {
        BookwormAdventures frame = new BookwormAdventures();
    }
    public BookwormAdventures() {
        super("Bookworm Adventures");
        Image icon = Toolkit.getDefaultToolkit().getImage("bookwormIcon.png");
        setIconImage(icon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,650);

        myTimer = new Timer(10, new TickListener());	 // trigger every 100 ms
        myTimer.start();

        game = new GamePanel();
        add(game);

        setResizable(false);
        setVisible(true);
    }

    class TickListener implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            if(game!= null && game.ready){
                    game.repaint();
            }
        }
    }

}

class GamePanel extends JPanel implements KeyListener {

    public boolean ready=false;
    private boolean gotName=false;

    public GamePanel(){
        addMouseListener(new clickListener());
        setSize(800,600);
    }

    public void addNotify() {
        super.addNotify();
        ready = true;
    }


    public void paintComponent(Graphics g){
        g.setColor(new Color(222,222,255));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(new Color(255,111,111));
        g.setColor(Color.green);
        g.fillRect(300,300,60,60);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    class clickListener implements MouseListener {
        // ------------ MouseListener ------------------------------------------
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseClicked(MouseEvent e){}
        public void mousePressed(MouseEvent e){
            //destx = e.getX();
            //desty = e.getY();
        }
    }//////////////////////////////My pussy is very flumptiusouss

}///steven have tiny dick

