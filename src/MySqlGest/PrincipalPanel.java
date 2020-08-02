package MySqlGest;

import javax.swing.*;
import java.awt.*;

public class PrincipalPanel extends JPanel {
    // Intern Atributes:
    static public JButton buttonSignIn = new JButton("Submit");
    static public JButton query = new JButton("Do query");
    static public JButton update = new JButton("Do update");
    static public JButton disconnect = new JButton("Disconnect");
    static public JButton queryLog = new JButton();
    static public JButton updateLog = new JButton();

    static public JCheckBox localhost = new JCheckBox("Localhost");
    static public JCheckBox remember = new JCheckBox("Remember");

    static public JTextField user = new JTextField();
    static public JTextField db = new JTextField();
    static public JTextField ip = new JTextField();
    static public JTextField port = new JTextField();
    static public JTextField textQuery = new JTextField();
    static public JTextField textUpdate = new JTextField();
    static public JPasswordField pass = new JPasswordField();

    static public String msj;
    static public boolean connected = false;
    static public boolean error = false;
    static public Color c;

    static JTextArea console;

    public PrincipalPanel() {
        //Graphics:
        c = Color.ORANGE;
        setBackground(c);
        setForeground(Color.WHITE);
        //Help:
        ip.setToolTipText("IP");
        port.setToolTipText("PORT");
        user.setToolTipText("USER");
        pass.setToolTipText("PASS");
        db.setToolTipText("DB");
        textQuery.setToolTipText("Write a query");
        textUpdate.setToolTipText("Write an update");
        queryLog.setToolTipText("Previous queries");
        updateLog.setToolTipText("Previous updates");
        //Adding atributes:
        add(localhost);
        add(ip);
        add(port);
        add(buttonSignIn);
        add(user);
        add(pass);
        add(db);
        add(remember);
        //Connection:
        console = new JTextArea();
        add(console);
        console.setFocusable(false);
        console.setBackground(c);
        console.setForeground(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font myFont = new Font("Comic Sans", Font.BOLD, 15); // Yep, comic sans
        g.setColor(Color.BLACK);
        g.setFont(myFont);
        console.setBackground(c);
        console.setForeground(Color.WHITE);
        if (!connected) { // First Window
            //Text:
            if (msj == null) {
                g.drawString("Welcome to MySqlGest", 65, 60);
            } else {
                g.drawString(msj, 103 - msj.length(), 60);
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
            remember.setBounds(140, 245, 100, 12);
            remember.setBackground(c);
            //Connection:
            console.setBounds(10, 325, 280, 100);
        } else {
            //Text:
            g.drawString(msj, Math.max(75 - msj.length() / 2, 1), 60);
            // Buttons:
            disconnect.setBounds(90, 250, 100, 21);
            query.setBounds(80, 120, 120, 21);
            update.setBounds(80, 180, 120, 21);
            queryLog.setBounds(210, 120, 21, 21);
            ImageIcon iconQuery = new ImageIcon("src/Images/queryIcon.jpg");
            Image img = iconQuery.getImage();
            Image newimg = img.getScaledInstance(queryLog.getWidth(), queryLog.getHeight(), java.awt.Image.SCALE_SMOOTH);
            iconQuery = new ImageIcon(newimg);
            queryLog.setIcon(iconQuery);
            updateLog.setBounds(210, 180, 21, 21);
            iconQuery = new ImageIcon("src/Images/updateIcon.jpg");
            img = iconQuery.getImage();
            newimg = img.getScaledInstance(updateLog.getWidth(), updateLog.getHeight(), java.awt.Image.SCALE_SMOOTH);
            iconQuery = new ImageIcon(newimg);
            updateLog.setIcon(iconQuery);
            //TextField:
            textQuery.setBounds(45, 90, 200, 21);
            textUpdate.setBounds(45, 150, 200, 21);
            textQuery.setHorizontalAlignment(JTextField.CENTER);
            textUpdate.setHorizontalAlignment(JTextField.CENTER);
            //Connection:
            console.setBounds(10, 300, 280, 100);
        }
    }
}
