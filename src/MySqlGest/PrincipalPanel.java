package MySqlGest;

import javax.swing.*;
import java.awt.*;

public class PrincipalPanel extends JPanel {
    JButton buttonSignIn = new JButton("Submit");
    JButton query = new JButton("Do query");
    JButton update = new JButton("Do update");
    JButton disconnect = new JButton("Disconnect");
    JCheckBox localhost = new JCheckBox("Localhost");

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

    public PrincipalPanel() {
        msj = "Welcome to MySqlGest";

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
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font myFont = new Font("Comic Sans", Font.BOLD, 15); // Yep, comic sans
        g.setColor(Color.BLACK);
        g.setFont(myFont);

        // Principal Text:
        g.drawString(msj, 135 - msj.length() * 4, 60);
        // First Window:
        if (!connected) {
            //Text:
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
            buttonSignIn.setBounds(100, 240, 80, 21);
        }
        // Disconnect Button:
        disconnect.setBounds(90, 220, 100, 21);

    }
}