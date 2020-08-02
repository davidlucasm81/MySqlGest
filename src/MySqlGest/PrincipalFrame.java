package MySqlGest;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class PrincipalFrame extends JFrame implements ActionListener {
    // Intern Atributes:
    private String address;
    private final PrincipalPanel panel;
    private String db;
    private LinkedList<String> queries;
    private LinkedList<String> updates;
    private boolean remember;

    public PrincipalFrame() {
        // Creating atributes:
        panel = new PrincipalPanel();
        queries = new LinkedList<>();
        updates = new LinkedList<>();
        // Initial Panel:
        add(panel);
        // ActionListeners:
        PrincipalPanel.buttonSignIn.addActionListener(this);
        PrincipalPanel.disconnect.addActionListener(this);
        PrincipalPanel.query.addActionListener(this);
        PrincipalPanel.update.addActionListener(this);
        PrincipalPanel.localhost.addActionListener(this);
        PrincipalPanel.remember.addActionListener(this);
        PrincipalPanel.queryLog.addActionListener(this);
        PrincipalPanel.updateLog.addActionListener(this);
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
                PrincipalPanel.user.setText(reader.readLine());
                PrincipalPanel.pass.setText(reader.readLine());
                PrincipalPanel.db.setText(reader.readLine());
                String address = reader.readLine();
                PrincipalPanel.ip.setText(address.substring(0, address.length() - 5));
                PrincipalPanel.port.setText(address.substring(address.length() - 4));
            } catch (IOException e) {
                PrincipalPanel.console.append("Cannot Read\n");
            }
        }
    }

    public void actionPerformed(ActionEvent e) { // It controls the response of the buttons, textfields ... etc
        // Getting Action:
        Object action = e.getSource();
        PrincipalPanel.console.setText("");
        if (PrincipalPanel.connected) { // Connected to the database
            if (PrincipalPanel.disconnect.equals(action)) { // Disconnecting code
                int cont = 0;
                do {
                    PrincipalPanel.console.append("Disconecting...\n");
                    cont++;
                }
                while (!(GUI.gest.disconnect()) && cont < 3);
                createLogs();
                System.exit(0);
            }
            if (PrincipalPanel.query.equals(action)) { // Do query
                String query = PrincipalPanel.textQuery.getText();
                if (GUI.gest.doQuery(query))
                    queries.addLast(query);
                PrincipalPanel.textQuery.setText("");
            }
            if (PrincipalPanel.update.equals(action)) { // Do update
                String update = PrincipalPanel.textUpdate.getText();
                if (GUI.gest.doUpdate(update))
                    updates.addLast(update);
                PrincipalPanel.textUpdate.setText("");
            }
            if (PrincipalPanel.queryLog.equals(action)) { // Get queryLog
                viewLogs("query");
            }
            if (PrincipalPanel.updateLog.equals(action)) { // Get updateLog
                viewLogs("update");
            }

        } else { // Disconnected database:
            if (action == PrincipalPanel.buttonSignIn) { // Connect code
                connect();
            }
            if (action == PrincipalPanel.localhost) { // Ip is localhost code
                if (address == null) {
                    PrincipalPanel.ip.setBackground(Color.DARK_GRAY.darker());
                    PrincipalPanel.port.setBackground(Color.DARK_GRAY.darker());
                    address = "localhost:3306";
                } else {
                    PrincipalPanel.ip.setBackground(Color.WHITE);
                    PrincipalPanel.port.setBackground(Color.WHITE);
                    address = null;
                }
            }
            if (action == PrincipalPanel.remember) {
                remember = !remember;
            }
        }
    }

    private void connect() { // Initial Button Code
        address = (address == null) ? PrincipalPanel.ip.getText() + ":" + PrincipalPanel.port.getText() : address;
        String user = PrincipalPanel.user.getText();
        String pass = PrincipalPanel.pass.getText();
        db = PrincipalPanel.db.getText();
        boolean res = GUI.gest.getConnection(user, pass, db, address);
        String msj;
        if (res) { // Connected code
            // Remember data
            if (remember) {
                new RememberFile(user, pass, db, address);
            }
            // Mod panel
            msj = "Connected to " + PrincipalPanel.db.getText();
            PrincipalPanel.c = Color.GREEN;
            panel.setBackground(PrincipalPanel.c);
            panel.remove(PrincipalPanel.buttonSignIn);
            panel.remove(PrincipalPanel.user);
            panel.remove(PrincipalPanel.pass);
            panel.remove(PrincipalPanel.db);
            panel.remove(PrincipalPanel.ip);
            panel.remove(PrincipalPanel.port);
            panel.remove(PrincipalPanel.localhost);
            panel.remove(PrincipalPanel.remember);
            panel.add(PrincipalPanel.disconnect);
            panel.add(PrincipalPanel.textQuery);
            panel.add(PrincipalPanel.textUpdate);
            panel.add(PrincipalPanel.query);
            panel.add(PrincipalPanel.update);
            panel.add(PrincipalPanel.queryLog);
            panel.add(PrincipalPanel.updateLog);
            setLocationRelativeTo(null);
            setSize(300, 400);
            PrincipalPanel.connected = true;
        } else { // Error in conection
            msj = "Not Connected";
            if (PrincipalPanel.error)
                PrincipalPanel.c = PrincipalPanel.c.darker();
            else {
                PrincipalPanel.c = Color.RED;
                PrincipalPanel.error = true;
            }
            panel.setBackground(PrincipalPanel.c);
        }
        PrincipalPanel.msj = msj;
    }

    private void viewLogs(String mode) { // Code to create update/queries log
        String[] mL = {db + mode + " log"};
        Object[][] mLog;
        LinkedList<String> updateList = new LinkedList<>();
        JTable table;
        File f = new File("internalFiles/" + mode + db + ".txt"); // Getting log
        if (f.exists()) {
            // Reading data:
            try {
                Scanner reader = new Scanner(f);
                while (reader.hasNextLine()) {
                    updateList.addLast(reader.nextLine());
                }
            } catch (FileNotFoundException fileNotFoundException) {
                PrincipalPanel.console.append("Cannot get" + mode + " log...\n");
            }
            // Saving data:
            mLog = new String[updateList.size()][1];
            int i = 0;
            for (String log : updateList) {
                mLog[i][0] = log;
                i++;
            }
            table = new JTable(mLog, mL);
        } else {
            table = new JTable(new Object[0][0], mL);
        }
        // Creating table:
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        TableFrame frame = new TableFrame();
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(180, 40 * Math.max(updateList.size() + 1, 2));
        frame.setLocationRelativeTo(null);
        // If you select a row it will be writting in the textfield:
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (mode.equals("query")) {
                    PrincipalPanel.textQuery.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                } else {
                    PrincipalPanel.textUpdate.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                }
            }
        });

    }

    private void createLogs() {
        if (!queries.isEmpty()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("internalFiles/query" + db + ".txt"));
                for (String query : queries) {
                    writer.write(query + "\n");
                }
                writer.close();
            } catch (IOException ioException) {
                PrincipalPanel.console.append("Error saving queries...\n");
            }
        }

        if (!updates.isEmpty()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("internalFiles/update" + db + ".txt"));
                for (String update : updates) {
                    writer.write(update + "\n");
                }
                writer.close();
            } catch (IOException ioException) {
                PrincipalPanel.console.append("Error saving updates...\n");
            }
        }
    }
}

