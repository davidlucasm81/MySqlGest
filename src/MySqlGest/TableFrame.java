package MySqlGest;

import javax.swing.*;
import java.awt.*;

public class TableFrame extends JFrame {

    public TableFrame() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Query");
        setLocationRelativeTo(null);
        // Icon:
        Image image = new ImageIcon("src/Images/myPassion.jpg").getImage();
        setIconImage(image);
    }
}
