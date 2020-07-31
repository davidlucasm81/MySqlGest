package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    static MySqlGest gest;
    static QueryFrame query;
    public static void main(String[] args) {
        gest = new MySqlGest();
        PrincipalFrame principal = new PrincipalFrame();
        query = new QueryFrame();
    }
}
class PrincipalFrame extends JFrame implements ActionListener {
    PrincipalPanel panel = new PrincipalPanel();
    public PrincipalFrame(){
        // Initial Panel:
        add(panel);
        panel.buttonSignIn.addActionListener(this);
        panel.disconnect.addActionListener(this);
        panel.query.addActionListener(this);
        panel.update.addActionListener(this);
        // Just frame:
        setSize(300,500);
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
        if(panel.connected){
            if(action==panel.disconnect){
                int cont=0;
                do{
                    System.out.println("Disconecting...");
                    cont++;
                }
                while(!(GUI.gest.disconnect()) && cont<3);
                System.exit(0);
            }
            if(action==panel.query){

            }
            if(action==panel.update){

            }
        }
        else{
            if(action==panel.buttonSignIn){ // Initial Button Code
                boolean res= GUI.gest.getConnection(panel.user.getText(),panel.pass.getText(),panel.db.getText());
                String msj;
                if(res){
                    msj="Connected to "+panel.db.getText();
                    panel.c=Color.GREEN;
                    panel.setBackground(panel.c);
                    panel.remove(panel.buttonSignIn);
                    panel.remove(panel.user);
                    panel.remove(panel.pass);
                    panel.remove(panel.db);
                    panel.add(panel.disconnect);
                    GUI.query.setVisible(true);
                    setLocation(0,180);
                    panel.connected=true;
                }
                else{
                    msj="Not Connected";
                    if(panel.error)
                        panel.c=panel.c.darker();
                    else{
                        panel.c=Color.RED;
                        panel.error=true;
                    }
                    panel.setBackground(panel.c);
                }
                panel.msj[0]=msj;
        }
        }
    }
}
 class PrincipalPanel extends JPanel {
    JButton buttonSignIn = new JButton("Submit");
    JButton query = new JButton("Do query");
    JButton update = new JButton("Do update");
    JButton disconnect = new JButton("Disconnect");




    JTextField user = new JTextField();
    JTextField pass = new JTextField();
    JTextField db = new JTextField();
    JTextField textQuery = new JTextField();
    JTextField textUpdate = new JTextField();


    String [] msj = new String[4];
    boolean connected=false;
    boolean error=false;
    Color c;
    public PrincipalPanel(){
        msj[0]="Welcome to MySqlGest";
        msj[1]="User: ";
        msj[2]="Pass: ";
        msj[3]="DB: ";
        c=Color.ORANGE;
        setBackground(c);
        setForeground(Color.WHITE);
        add(buttonSignIn);
        add(user);
        add(pass);
        add(db);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Font myFont = new Font("Comic Sans",Font.BOLD,15); // Yep, comic sans
        g.setFont(myFont);

        // Text:
        g.drawString(msj[0],135-msj[0].length()*4,60);
        if(!connected) {
            g.drawString(msj[1], 25, 95);
            g.drawString(msj[2], 25, 125);
            g.drawString(msj[3], 25, 155);
        }
            //Initial TextField:
            user.setBounds(70,80,150,20);
            pass.setBounds(70,110,150,20);
            db.setBounds(70,140,150,20);

            buttonSignIn.setBounds(100,180,80,21);

            disconnect.setBounds(90,220,100,21);

        }
}
class QueryFrame extends JFrame {

    public QueryFrame(){
        // Frame:
        setSize(800,500);
        setVisible(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("ConsoleGest");
        setResizable(false);
        setLocationRelativeTo(null);
        // Icon:
        Image image = new ImageIcon("src/Images/myPassion.jpg").getImage();
        setIconImage(image);

    }

}

