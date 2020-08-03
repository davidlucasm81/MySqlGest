/*
     FriendlyPanel is the main panel for a FriendlyFrame
 */
package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendlyPanel extends JPanel implements ActionListener {

    //Images:
    private final Image background;
    private final JButton goToFriendly;
    private final FriendlyFrame frame;

    /**
     * @param frame It's the frame who create the panel
     */
    public FriendlyPanel(FriendlyFrame frame) {
        ImageIcon image = new ImageIcon("src/Images/myPassion.jpg");
        background = image.getImage();
        goToFriendly = new JButton("Friendly Mode?");
        add(goToFriendly);
        goToFriendly.addActionListener(this);
        this.frame = frame;
    }

    /**
     * This method "paint" the background and locate the button
     *
     * @param g Graphics context in which to paint
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
        goToFriendly.setBounds(10, 10, 175, 50);

    }

    /**
     * If the action was made by the button, the panel will be resized, relocated and the button will be removed
     *
     * @param e The action who invoked the method
     */
    public void actionPerformed(ActionEvent e) {
        // Getting Action:
        Object action = e.getSource();
        if (goToFriendly.equals(action)) {
            frame.setSize(300, 425);
            frame.setLocation(frame.getX() - 100, frame.getY());
            remove(goToFriendly);
        }
    }
}
