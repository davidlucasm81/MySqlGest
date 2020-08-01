package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Scanner;

public class PrincipalFrame extends JFrame implements ActionListener {
    String address = null;
    PrincipalPanel panel = new PrincipalPanel();
    String db;


    private boolean remember = false;

    public PrincipalFrame() {
        // Initial Panel:
        add(panel);
        panel.buttonSignIn.addActionListener(this);
        panel.disconnect.addActionListener(this);
        panel.query.addActionListener(this);
        panel.update.addActionListener(this);
        panel.localhost.addActionListener(this);
        panel.remember.addActionListener(this);
        // Just frame:
        setSize(300, 425);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MySQLGest");
        setResizable(false);
        setLocationRelativeTo(null);
        // Icon:
        Image image = new ImageIcon("src/Images/myPassion.jpg").getImage();
        setIconImage(image);

        // Remember code:
        File f = new File("internalFiles/remember.txt");
        if (f.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("internalFiles/remember.txt"));
                panel.user.setText(reader.readLine());
                panel.pass.setText(reader.readLine());
                panel.db.setText(reader.readLine());
                String address = reader.readLine();
                panel.ip.setText(address.substring(0, address.length() - 5));
                panel.port.setText(address.substring(address.length() - 4, address.length()));
            } catch (IOException e) {
                PrincipalPanel.con.append("Cannot Read\n");
            }
        }

    }

    public void actionPerformed(ActionEvent e) {
        Object action = e.getSource();
        PrincipalPanel.con.setText("");
        if (panel.connected) {
            if (action == panel.disconnect) {
                int cont = 0;
                do {
                    PrincipalPanel.con.append("Disconecting...\n");
                    cont++;
                }
                while (!(GUI.gest.disconnect()) && cont < 3);
                System.exit(0);


            }
            if (action == panel.query) {
                String query = panel.textQuery.getText();
                GUI.gest.doQuery(query);
                panel.textQuery.setText("");
            }
            if (action == panel.update) {
                String update = panel.textUpdate.getText();
                GUI.gest.doUpdate(update);
                panel.textUpdate.setText("");
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
            if (action == panel.remember) {
                remember = !remember;
            }
        }
    }

    private void connect() { // Initial Button Code
        address = (address == null) ? panel.ip.getText() + ":" + panel.port.getText() : address;
        String user = panel.user.getText();
        String pass = panel.pass.getText();
        db = panel.db.getText();
        boolean res = GUI.gest.getConnection(user, pass, db, address);
        String msj;
        if (res) {

            if (remember) {
                RememberFile file = new RememberFile(user, pass, db, address);
            }

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
            panel.remove(panel.remember);

            panel.add(panel.disconnect);
            panel.add(panel.textQuery);
            panel.add(panel.textUpdate);
            panel.add(panel.query);
            panel.add(panel.update);
            panel.add(panel.queryLog);
            panel.add(panel.updateLog);


            setLocationRelativeTo(null);
            setSize(300, 600);
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

