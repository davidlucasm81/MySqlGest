package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    static MySqlGest gest;
    public static void main(String[] args) {
        gest = new MySqlGest();
        PrincipalFrame principal = new PrincipalFrame();
    }
}
class PrincipalFrame extends JFrame implements ActionListener {
    PrincipalPanel panel = new PrincipalPanel();
    public PrincipalFrame(){
        // Initial Panel:
        add(panel);
        panel.buttonSignIn.addActionListener(this);
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
        Object action= e.getSource();
        if(action==panel.buttonSignIn){ // Initial Button Code
            boolean res= GUI.gest.getConnection(panel.user.getText(),panel.pass.getText(),panel.db.getText());
            String msj;
            if(res){
                msj="Connected";
                panel.c=Color.GREEN;
                panel.setBackground(panel.c);
                panel.remove(panel.buttonSignIn);
                panel.remove(panel.user);
                panel.remove(panel.pass);
                panel.remove(panel.db);
            }
            else{
                msj="Not Connected";
                panel.c=Color.RED;
                panel.setBackground(panel.c);
            }
            panel.msj=msj;
        }
    }
}
 class PrincipalPanel extends JPanel {
    JButton buttonSignIn = new JButton("Submit");
    JTextField user = new JTextField();
    JTextField pass = new JTextField();
    JTextField db = new JTextField();
    String msj;
    Color c;
    public PrincipalPanel(){
        msj="Welcome to MySqlGest";
        c=Color.ORANGE;
        setBackground(c);
        add(buttonSignIn);
        setForeground(Color.WHITE);
        add(user);
        add(pass);
        add(db);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Font myFont = new Font("Comic Sans",Font.BOLD,20); // Yep, comic sans
        g.setFont(myFont);
        g.drawString(msj,60,60);

        //Initial TextField:
        user.setBounds(60,80,150,20);
        pass.setBounds(60,110,150,20);
        db.setBounds(60,140,150,20);


        }
    }

