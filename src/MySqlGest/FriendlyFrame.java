package MySqlGest;

import javax.swing.*;
import java.awt.*;

public class FriendlyFrame extends JFrame {
    FriendlyPanel panel;
    public FriendlyFrame(){
        // Panel:
        panel=new FriendlyPanel(this);
        add(panel);
        // Just frame:
        setSize(200, 100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Friendly GUI");
        setResizable(false);
        setLocationRelativeTo(null);
        setLocation(getX()-250,getY()-162);
        // Icon:
        Image image = new ImageIcon("src/Images/myPassion.jpg").getImage();
        setIconImage(image);
    }


}
