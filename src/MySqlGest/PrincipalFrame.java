package MySqlGest;

import javax.swing.*;
import java.awt.*;

public class PrincipalFrame extends JFrame {

    public PrincipalFrame() {
        // Initial Panel:
        add(new PrincipalPanel());
        // Just frame:
        setSize(300, 425);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MySQLGest");
        setResizable(false);
        setLocationRelativeTo(null);
        // Icon:
        Image image = new ImageIcon("src/Images/myPassion.jpg").getImage();
        setIconImage(image);
    }


}

