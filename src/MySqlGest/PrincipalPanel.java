package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PrincipalPanel extends JPanel {
    JButton buttonSignIn = new JButton("Submit");
    JButton query = new JButton("Do query");
    JButton update = new JButton("Do update");
    JButton disconnect = new JButton("Disconnect");

    JCheckBox localhost = new JCheckBox("Localhost");
    JCheckBox remember = new JCheckBox("Remember");

    JTextField user = new JTextField();
    JTextField pass = new JTextField();
    JTextField db = new JTextField();
    JTextField ip = new JTextField();
    JTextField port = new JTextField();

    JTextField textQuery = new JTextField();
    JTextField textUpdate = new JTextField();


    String msj;
    boolean connected = false;
    boolean error = false;
    Color c;

    static JTextArea con;
    public PrincipalPanel() {

        c = Color.ORANGE;
        setBackground(c);
        setForeground(Color.WHITE);

        ip.setToolTipText("IP");
        port.setToolTipText("PORT");
        user.setToolTipText("USER");
        pass.setToolTipText("PASS");
        db.setToolTipText("DB");

        add(localhost);
        add(ip);
        add(port);

        add(buttonSignIn);
        add(user);
        add(pass);
        add(db);
        add(remember);
        //Connection
        con = new JTextArea();
        add(con);
        con.setFocusable(false);
        con.setBackground(c);
        con.setForeground(Color.BLACK);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font myFont = new Font("Comic Sans", Font.BOLD, 15); // Yep, comic sans
        g.setColor(Color.BLACK);
        g.setFont(myFont);
        con.setBackground(c);
        // First Window:
        if (!connected) {
            //Text:
            if(msj==null){
                g.drawString("Welcome to MySqlGest", 65, 60);
            }
            else{
                g.drawString(msj, 103-msj.length(), 60);
            }
            g.drawString("User: ", 25, 95);
            g.drawString("Pass: ", 25, 125);
            g.drawString("DB: ", 25, 155);
            g.drawString("IP:", 25, 195);
            g.drawString(":", 163, 195);
            //Initial TextField:
            user.setBounds(70, 80, 150, 20);
            pass.setBounds(70, 110, 150, 20);
            db.setBounds(70, 140, 150, 20);


            //Address TextField:
            localhost.setBounds(100, 210, 90, 12);
            localhost.setBackground(c);
            ip.setBounds(70, 180, 90, 20);
            port.setBounds(170, 180, 50, 20);

            //Initial Button:
            buttonSignIn.setBounds(50, 240, 80, 21);
            remember.setBounds(140,245,100,12);
            remember.setBackground(c);

            //Connection:
            con.setBounds(10,325,280,100);
        }
        else{
            //Text:
            g.drawString(msj, 83-msj.length()*2, 60);
            // Buttons:
            disconnect.setBounds(90, 250, 100, 21);
            query.setBounds(80,120,120,21);
            update.setBounds(80,180,120,21);

            //TextField:
            textQuery.setBounds(45,90,200,21);
            textUpdate.setBounds(45,150,200,21);

            textQuery.setHorizontalAlignment(JTextField.CENTER);
            textUpdate.setHorizontalAlignment(JTextField.CENTER);

            //Connection:
            con.setBounds(10,500,280,100);

        }

    }
}
