import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
public class Skill {
    private ImageIcon icon;
    private Boolean locked;
    private Rectangle rect;
    private String ability;


    public Skill(String pathNumber,int x, int y, String ability){
        icon = new ImageIcon("Pictures/SkillTree/Skill" + pathNumber + ".png");
        locked=true;
        rect= new Rectangle(x,y,115,115);
        this.ability = ability;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public Boolean getLocked() {
        return locked;
    }

    public Rectangle getRect() {
        return rect;
    }

    public String getAbility() {
        return ability;
    }
}
