package MySqlGest;

import javax.swing.*;
import java.awt.*;

public class TableFrame extends JFrame {
    public TableFrame() {
        //Atributes:
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Table");
        setLocationRelativeTo(null);
        // Icon:
        Image image = new ImageIcon("src/Images/myPassion.jpg").getImage();
        setIconImage(image);
    }
}
