//import javax.swing.*;
//import javax.swing.border.LineBorder;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//
//
//    public class creditMenu extends JFrame {
//        private JLayeredPane layeredPane=new JLayeredPane();
//
//        public creditMenu() {
//            super("Bookworm Adventures");
//            setSize(1280,820);
//
//            ImageIcon backPic = new ImageIcon("Pictures/StartMenu/Library2.png");
//            JLabel back = new JLabel(backPic);
//            back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
//            layeredPane.add(back,1);
//
//            JButton backBtn = new JButton("back");
//            backBtn.setBorder(new LineBorder(Color.BLACK));
//            backBtn.addActionListener(new CreditStart());
//            backBtn.setBounds(625-300/2,350,300,100);
//            layeredPane.add(backBtn,2);
////            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            setVisible(true);
//            setResizable(false);
//        }
//
//        public static void main(String[] arguments) {
//            startMenu frame = new startMenu();
//        }
//
//
//        class CreditStart implements ActionListener{
//            @Override
//            public void actionPerformed(ActionEvent evt){
//                try {
//                    BookwormAdventures game = new BookwormAdventures();
//                    Level gay = new Level(1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                setVisible(false);
//            }
//        }
//    }
//}
//
//
//}
