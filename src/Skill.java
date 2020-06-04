import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
public class Skill {
    private ImageIcon icon;
    private Boolean locked;


    public Skill(String pathNumber){
        icon = new ImageIcon("Pictures/SkillTree/Skill" + pathNumber + ".png");
        locked=true;
    }

}
