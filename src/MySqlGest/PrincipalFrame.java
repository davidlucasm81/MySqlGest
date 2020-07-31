package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
                    QueryFrame.con.append("Disconecting...\n");
                    cont++;
                }
                while (!(GUI.gest.disconnect()) && cont < 3);
                    System.exit(0);


            }
            if (action == panel.query) {
                GUI.gest.doQuery(panel.textQuery.getText());
                panel.textQuery.setText("");
                GUI.query.setVisible(true);
                setLocationRelativeTo(null);
                setLocation(getX() - 550, getY() - 85);
            }
            if (action == panel.update) {
                GUI.gest.doUpdate(panel.textUpdate.getText());
                panel.textUpdate.setText("");
                GUI.query.setVisible(true);
                setLocationRelativeTo(null);
                setLocation(getX() - 550, getY() - 85);
            }
        } else {
            if (action == panel.buttonSignIn) {
                connect();
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

    private void connect() { // Initial Button Code
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
            panel.add(panel.textQuery);
            panel.add(panel.textUpdate);
            panel.add(panel.query);
            panel.add(panel.update);


            GUI.query.setVisible(true);
            setLocation(getX() - 550, getY() - 85);
            setSize(300, 500);
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

}

