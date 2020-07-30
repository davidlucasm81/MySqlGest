package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    public static void main(String[] args) {
        MFrame principal = new MFrame();
    }
}
class MFrame extends JFrame implements ActionListener {
    MPanel firstPanel = new MPanel(1);
    MPanel secondPanel = new MPanel(2);
    public MFrame(){
        // Initial Panel:
        add(firstPanel);
        firstPanel.buttonSubmit.addActionListener(this);
        // Just frame:
        setSize(400,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MySQLGest");
        setResizable(false);
        setLocationRelativeTo(null);
        // Icon:
        Image image = new ImageIcon("src/Images/myPassion.jpg").getImage();
        setIconImage(image);
    }
    public void actionPerformed(ActionEvent e){
        Object button= e.getSource();
        if(button==firstPanel.buttonSubmit){
            remove(firstPanel);
            add(secondPanel);
        }
    }
}
 class MPanel extends JPanel {
    JButton buttonSubmit = new JButton("prueba");
    private int numPanel=-1;
    public MPanel (int numPanel){
        this.numPanel=numPanel;
        switch(numPanel){
            case 1:
                setBackground(Color.ORANGE);
                add(buttonSubmit);
                break;
            case 2:
                setBackground(Color.BLACK);
                break;
        }
        setForeground(Color.WHITE);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Font myFont = new Font("Comic Sans",Font.BOLD,20); // Yep, comic sans
        g.setFont(myFont);
        switch (numPanel){
            case 1:
                g.drawString("Graphic design is my passion!",40,40);
                break;
            case 2:
                g.drawString("Or not",40,40);
                break;
        }
    }
}
