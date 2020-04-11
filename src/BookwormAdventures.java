
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BookwormAdventures extends JFrame {
    Timer myTimer;
    private Image bookwormIcon;
    GamePanel game;

    public static void main(String[] arguments) throws IOException{
        BookwormAdventures frame = new BookwormAdventures();
        //System.out.println(words.toString());
    }
    public BookwormAdventures() throws IOException {
        super("Bookworm Adventures");
        Image icon = Toolkit.getDefaultToolkit().getImage("bookwormIcon.png");
        setIconImage(icon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280,820);

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
    private int level = 1;
    private Level levelPog;
    private Letters letters;
    private int mx,my;
    private Rectangle[] letterSlots = new Rectangle[16];
    private boolean[] letterSlotsCondition = new boolean[16];
    private ArrayList<String> alphabet;
    private Rectangle resetButton;
    public GamePanel() throws IOException {
        addMouseListener(new clickListener());
        setSize(800,600);
        levelPog = new Level(level);
        letters = new Letters();
        int rectCounter = 0;
        for (int x = 400; x<880;x+=120){
            for (int y = 240 ; y<720; y+=120){
                letterSlots[rectCounter] = new Rectangle(x,y,120,120);
                rectCounter++;
            }
        }
        resetButton = new Rectangle(400,720,480,100);
        for(int i = 0; i<16;i++){
            letterSlotsCondition[i] = true;
        }
        alphabet = letters.random16letters();


}


    public void addNotify() {
        super.addNotify();
        ready = true;
    }


    public void paintComponent(Graphics g){
        System.out.println(mx + "," + my);
        int letterSlotCounter = 0;
        g.setColor(new Color(255,255,255));
        if (level == 1){
            g.setColor(new Color(100,100,100));
        }
        g.setColor(Color.BLACK);
        g.fillRect(0,240,1280,20);
        g.fillRect(400,240,10,480);
        g.fillRect(880,240,10,480);
        for (String s : alphabet){
            g.drawImage(Letters.getImage(s),letterSlots[letterSlotCounter].x + 20,letterSlots[letterSlotCounter].y+25,this);
            letterSlotCounter++;
        }
        for (int i = 0; i<16;i++){
            if(letterSlotsCondition[i]) {
                g.drawRect(letterSlots[i].x, letterSlots[i].y, letterSlots[i].width, letterSlots[i].height);
            }
            else{
                g.setColor(Color.WHITE);
                g.fillRect(letterSlots[i].x, letterSlots[i].y, letterSlots[i].width, letterSlots[i].height);
            }
        }
        for(int i = 0; i<16;i++){
            if (letterSlots[i].contains(mx,my)){
                g.setColor(Color.WHITE);
                g.fillRect(letterSlots[i].x,letterSlots[i].y,letterSlots[i].width,letterSlots[i].height);
                letterSlotsCondition[i] = false;
            }
        }
        g.drawRect(resetButton.x,resetButton.y,resetButton.width+10,resetButton.height);
        if(resetButton.contains(mx,my)){
            for(int i = 0; i<16;i++){
                letterSlotsCondition[i] = true;
            }
        }




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
            mx = e.getX();
            my = e.getY();

        }
    }//////////////////////////////My pussy is very flumptiusouss

}///steven have tiny dick

