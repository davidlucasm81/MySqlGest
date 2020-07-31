package MySqlGest;

import javax.swing.*;
import java.awt.*;

public class QueryFrame extends JFrame {
    static JTextArea con;
    public QueryFrame() {
        // Frame:
        setSize(800, 500);
        setVisible(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("ConsoleGest");
        setResizable(false);
        setLocationRelativeTo(null);
        // Icon:
        Image image = new ImageIcon("src/Images/myPassion.jpg").getImage();
        setIconImage(image);
        // "Console":
        con = new JTextArea();
        add(con);
    }
}
