package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalFrame extends JFrame implements ActionListener {
    String address = null;
    PrincipalPanel panel = new PrincipalPanel();

    public PrincipalFrame() {
        // Initial Panel:
        add(panel);
        panel.buttonSignIn.addActionListener(this);
        panel.disconnect.addActionListener(this);
        panel.query.addActionListener(this);
        panel.update.addActionListener(this);
        panel.localhost.addActionListener(this);
        // Just frame:
        setSize(300, 325);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MySQLGest");
        setResizable(false);
        setLocationRelativeTo(null);
        // Icon:
        Image image = new ImageIcon("src/Images/myPassion.jpg").getImage();
        setIconImage(image);
    }

    public void actionPerformed(ActionEvent e) {
        Object action = e.getSource();
        if (panel.connected) {
            if (action == panel.disconnect) {
                int cont = 0;
                do {
                    System.out.println("Disconecting...");
                    cont++;
                }
                while (!(GUI.gest.disconnect()) && cont < 3);
                System.exit(0);
            }
            if (action == panel.query) {

            }
            if (action == panel.update) {

            }
        } else {
            if (action == panel.buttonSignIn) { // Initial Button Code
                address = (address == null) ? panel.ip.getText() + ":" + panel.port.getText() : address;
                boolean res = GUI.gest.getConnection(panel.user.getText(), panel.pass.getText(), panel.db.getText(), address);
                String msj;
                if (res) {
                    msj = "Connected to " + panel.db.getText();
                    panel.c = Color.GREEN;
                    panel.setBackground(panel.c);

                    panel.remove(panel.buttonSignIn);
                    panel.remove(panel.user);
                    panel.remove(panel.pass);
                    panel.remove(panel.db);
                    panel.remove(panel.ip);
                    panel.remove(panel.port);
                    panel.remove(panel.localhost);

                    panel.add(panel.disconnect);
                    GUI.query.setVisible(true);
                    setSize(300, 500);
                    setLocation(0, 180);
                    panel.connected = true;
                } else {
                    msj = "Not Connected";
                    if (panel.error)
                        panel.c = panel.c.darker();
                    else {
                        panel.c = Color.RED;
                        panel.error = true;
                    }
                    panel.setBackground(panel.c);
                }
                panel.msj = msj;
            }
            if (action == panel.localhost) {
                if (address == null) {
                    panel.ip.setBackground(Color.DARK_GRAY.darker());
                    panel.port.setBackground(Color.DARK_GRAY.darker());
                    address = "localhost:3306";
                } else {
                    panel.ip.setBackground(Color.WHITE);
                    panel.port.setBackground(Color.WHITE);
                    address = null;
                }

            }
        }
    }
}