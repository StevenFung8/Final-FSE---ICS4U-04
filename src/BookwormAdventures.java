import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BookwormAdventures extends JFrame {
    Timer myTimer;
    GamePanel game;
    public BookwormAdventures() {
        super("Bookworm Adventures");
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

    public static void main(String[] arguments) {
        BookwormAdventures frame = new BookwormAdventures();
    }
}

class GamePanel extends JPanel {
    private int destx,desty,boxx,boxy;
    public boolean ready=false;
    private boolean gotName=false;

    public GamePanel(){
            addMouseListener(new clickListener());
            boxx=200;
            boxy=200;
            destx=500;
            desty=200;
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
        g.fillOval(destx,desty,10,10);
        g.setColor(Color.green);
        g.fillRect(boxx,boxy,20,20);
        g.fillRect(300,300,60,60);
    }
    class clickListener implements MouseListener {
        // ------------ MouseListener ------------------------------------------
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseClicked(MouseEvent e){}
        public void mousePressed(MouseEvent e){
            destx = e.getX();
            desty = e.getY();
        }
    }//////////////////////////////My pussy is thick aff
}

