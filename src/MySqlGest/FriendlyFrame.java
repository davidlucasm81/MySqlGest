/*
    It's a frame for the "Friendly Mode" of MySqlGest. Friendly Mode is (ofc)
    a mode that you can do queries, updates... with a graphic interface.
 */
package MySqlGest;
import javax.swing.*;
import java.awt.*;
public class FriendlyFrame extends JFrame {
    public FriendlyFrame() {
        // Panel:
        add(new FriendlyPanel(this));
        // Just frame:
        setSize(200, 100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Friendly GUI");
        setResizable(false);
        setLocationRelativeTo(null);
        setLocation(getX() - 250, getY() - 162);
        // Icon:
        Image image = new ImageIcon("src/Images/myPassion.jpg").getImage();
        setIconImage(image);
    }
}
