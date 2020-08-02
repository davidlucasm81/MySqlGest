package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class PrincipalPanel extends JPanel implements ActionListener {
    // Intern Atributes:

    private String address;
    private String dbName;
    private boolean remember;


    private final LinkedList<String> queries;
    private final LinkedList<String> updates;

    private final JButton buttonSignIn = new JButton("Submit");
    private final JButton query = new JButton("Do query");
    private final JButton update = new JButton("Do update");
    private final JButton disconnect = new JButton("Disconnect");
    private final JButton queryLog = new JButton();
    private final JButton updateLog = new JButton();

    private final JCheckBox localhost = new JCheckBox("Localhost");
    private final JCheckBox rememberBox = new JCheckBox("Remember");

    private final JTextField user = new JTextField();
    private final JTextField db = new JTextField();
    private final JTextField ip = new JTextField();
    private final JTextField port = new JTextField();
    private static final JTextField textQuery = new JTextField();
    private static final JTextField textUpdate = new JTextField();
    private final JPasswordField pass = new JPasswordField();

    private String msj;
    private boolean connected = false;
    private boolean error = false;
    private Color c;

    private static JTextArea console;

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
        add(rememberBox);
        queries = new LinkedList<>();
        updates = new LinkedList<>();
        //Connection:
        console = new JTextArea();
        add(console);
        console.setFocusable(false);
        console.setBackground(c);
        console.setForeground(Color.BLACK);
        // ActionListeners:
        buttonSignIn.addActionListener(this);
        disconnect.addActionListener(this);
        query.addActionListener(this);
        update.addActionListener(this);
        localhost.addActionListener(this);
        rememberBox.addActionListener(this);
        queryLog.addActionListener(this);
        updateLog.addActionListener(this);
        // Remember code:
        File f = new File("src/internalFiles/remember.txt");
        if (f.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("src/internalFiles/remember.txt"));
                user.setText(reader.readLine());
                pass.setText(reader.readLine());
                db.setText(reader.readLine());
                String address = reader.readLine();
                ip.setText(address.substring(0, address.length() - 5));
                port.setText(address.substring(address.length() - 4));
            } catch (IOException e) {
                console.append("Cannot Read\n");
            }
        }
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
            rememberBox.setBounds(140, 245, 100, 12);
            rememberBox.setBackground(c);
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

    public void connect() {
        address = (address == null) ? ip.getText() + ":" + port.getText() : address;
        String userName = user.getText();
        String passName = String.valueOf(pass.getPassword());
        dbName = db.getText();
        boolean res = GUI.gest.getConnection(userName, passName, dbName, address);
        if (res) { // Connected code
            // Remember data
            if (remember) {
                new RememberFile(userName, passName, dbName, address);
            }
            // Mod panel
            msj = "Connected to " + db.getText();
            c = Color.GREEN;
            setBackground(c);
            remove(buttonSignIn);
            remove(user);
            remove(pass);
            remove(db);
            remove(ip);
            remove(port);
            remove(localhost);
            remove(rememberBox);
            add(disconnect);
            add(textQuery);
            add(textUpdate);
            add(query);
            add(update);
            add(queryLog);
            add(updateLog);
            connected = true;
        } else { // Error in conection
            msj = "Not Connected";
            if (error)
                c = c.darker();
            else {
                c = Color.RED;
                error = true;
            }
            setBackground(c);
        }
    }

    public void actionPerformed(ActionEvent e) { // It controls the response of the buttons, textfields ... etc
        // Getting Action:
        Object action = e.getSource();
        console.setText("");
        if (connected) { // Connected to the database
            if (disconnect.equals(action)) { // Disconnecting code
                int cont = 0;
                do {
                    console.append("Disconecting...\n");
                    cont++;
                }
                while (!(GUI.gest.disconnect()) && cont < 3);
                createLogs();
                System.exit(0);
            }
            if (query.equals(action)) { // Do query
                String query = textQuery.getText();
                if (GUI.gest.doQuery(query))
                    queries.addLast(query);
                textQuery.setText("");
            }
            if (update.equals(action)) { // Do update
                String update = textUpdate.getText();
                if (GUI.gest.doUpdate(update))
                    updates.addLast(update);
                textUpdate.setText("");
            }
            if (queryLog.equals(action)) { // Get queryLog
                viewLogs("query");
            }
            if (updateLog.equals(action)) { // Get updateLog
                viewLogs("update");
            }

        } else { // Disconnected database:
            if (action == buttonSignIn) { // Connect code
                connect();
            }
            if (action == localhost) { // Ip is localhost code
                if (address == null) {
                    ip.setBackground(Color.DARK_GRAY.darker());
                    port.setBackground(Color.DARK_GRAY.darker());
                    address = "localhost:3306";
                } else {
                    ip.setBackground(Color.WHITE);
                    port.setBackground(Color.WHITE);
                    address = null;
                }
            }
            if (action == rememberBox) {
                remember = !remember;
            }
        }
    }


    private void viewLogs(String mode) { // Code to create update/queries log
        String[] mL = {dbName + mode + " log"};
        Object[][] mLog;
        LinkedList<String> updateList = new LinkedList<>();
        File f = new File("src/internalFiles/" + mode + dbName + ".txt"); // Getting log
        if (f.exists()) {
            // Reading data:
            try {
                Scanner reader = new Scanner(f);
                while (reader.hasNextLine()) {
                    updateList.addLast(reader.nextLine());
                }
            } catch (FileNotFoundException fileNotFoundException) {
                appendToConsole("Cannot get" + mode + " log...\n");
            }
            // Saving data:
            mLog = new String[updateList.size()][1];
            int i = 0;
            for (String log : updateList) {
                mLog[i][0] = log;
                i++;
            }
            new MyTable(mLog, mL, mode);
        } else {
            new MyTable(new Object[1][1], mL, mode);
        }
    }

    private void createLogs() {
        if (!queries.isEmpty()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/internalFiles/query" + dbName + ".txt"));
                for (String query : queries) {
                    writer.write(query + "\n");
                }
                writer.close();
            } catch (IOException ioException) {
                appendToConsole("Error saving queries...\n");
            }
        }

        if (!updates.isEmpty()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/internalFiles/update" + dbName + ".txt"));
                for (String update : updates) {
                    writer.write(update + "\n");
                }
                writer.close();
            } catch (IOException ioException) {
                appendToConsole("Error saving updates...\n");
            }
        }
    }

    public static void setTextQuery (String query){
        textQuery.setText(query);
    }
    public static void setTextUpdate(String update) {
        textUpdate.setText(update);
    }
    public static void appendToConsole(String data){
        console.append(data);
    }
}
