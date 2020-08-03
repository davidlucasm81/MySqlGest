package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendlyPanel extends JPanel implements ActionListener{

    //Images:
    private final Image background;
    private JButton goToFriendly= new JButton("Friendly Mode?");
    private FriendlyFrame frame;
    public FriendlyPanel(FriendlyFrame frame){
        ImageIcon image = new ImageIcon("src/Images/myPassion.jpg");
        background = image.getImage();
        add(goToFriendly);
        goToFriendly.addActionListener(this);
        this.frame=frame;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
        goToFriendly.setBounds(10,10,175,50);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Getting Action:
        Object action = e.getSource();
        if(goToFriendly.equals(action)){
            frame.setSize(300, 425);
            frame.setLocation(frame.getX()-100,frame.getY());
            remove(goToFriendly);
        }
    }
}
