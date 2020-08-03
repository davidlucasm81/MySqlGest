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
    //Database atributes:
    private String address;
    private String dbName;
    private boolean connected;
    private boolean remember;
    private LinkedList<String> queries;
    private LinkedList<String> updates;
    private LinkedList<String> tablesNames;
    //Interaction
    private final JButton buttonSignIn = new JButton("Submit");
    private final JButton query = new JButton("Do query");
    private final JButton update = new JButton("Do update");
    private final JButton disconnect = new JButton("Disconnect");
    private final JButton queryLog = new JButton();
    private final JButton updateLog = new JButton();
    private final JButton tablesButton = new JButton("Show Tables");
    private final JCheckBox localhost = new JCheckBox("Localhost");
    private final JCheckBox rememberBox = new JCheckBox("Remember");
    private final JTextField user = new JTextField();
    private final JTextField db = new JTextField();
    private final JTextField ip = new JTextField();
    private final JTextField port = new JTextField();
    private static final JTextField textQuery = new JTextField();
    private static final JTextField textUpdate = new JTextField();
    private final JPasswordField pass = new JPasswordField();

    private final JButton settings = new JButton();
    //Console
    private static JTextArea console;
    //Images:
    private final Image background;
    private ImageIcon iconQuery;
    private ImageIcon iconUpdate;
    private ImageIcon iconSettings;
    private FriendlyFrame fFrame;
    public static LinkedList<MyTable> tableList;


    public PrincipalPanel() {
        //Connection:
        console = new JTextArea();
        add(console);
        console.setFocusable(false);
        console.setOpaque(false);
        console.setForeground(Color.BLACK);
        //Graphics:
        setForeground(Color.BLACK);
        // Initial Panel:
        disconnected();
        // ActionListeners:
        buttonSignIn.addActionListener(this);
        disconnect.addActionListener(this);
        query.addActionListener(this);
        update.addActionListener(this);
        localhost.addActionListener(this);
        rememberBox.addActionListener(this);
        queryLog.addActionListener(this);
        updateLog.addActionListener(this);
        tablesButton.addActionListener(this);
        settings.addActionListener(this);
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
        //Images:
        ImageIcon image = new ImageIcon("src/Images/Mysql_logo.jpeg");
        background = image.getImage();

        iconQuery = new ImageIcon("src/Images/queryIcon.jpg");
        Image img = iconQuery.getImage();
        Image newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        iconQuery = new ImageIcon(newimg);

        iconUpdate = new ImageIcon("src/Images/updateIcon.jpg");
        Image imgU = iconUpdate.getImage();
        Image newimgU = imgU.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        iconUpdate = new ImageIcon(newimgU);

        iconSettings = new ImageIcon("src/Images/settings.jpeg");
        Image imgS = iconSettings.getImage();
        Image newimgS = imgS.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        iconSettings = new ImageIcon(newimgS);
    }

    private void disconnected() {
        //Atributes:
        address = null;
        dbName = null;
        connected = false;
        remember = false;
        //Adding atributes:
        add(localhost);
        add(ip);
        add(port);
        add(buttonSignIn);
        add(user);
        add(pass);
        add(db);
        add(rememberBox);
        rememberBox.setOpaque(false);
        localhost.setOpaque(false);
        queries = new LinkedList<>();
        updates = new LinkedList<>();
        tableList = new LinkedList<>();
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

    private void connected() {
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
            add(tablesButton);
            add(settings);
            connected = true;
            tablesNames = GUI.gest.getDatabaseInfo();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font myFont = new Font("Comic Sans", Font.BOLD, 15); // Yep, comic sans
        g.setFont(myFont);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
        if (!connected) { // First Window
            //Text:
            g.drawString("Welcome to MySqlGest", 65, 60);
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
            ip.setBounds(70, 180, 90, 20);
            port.setBounds(170, 180, 50, 20);
            //Initial Button:
            buttonSignIn.setBounds(50, 240, 80, 21);
            rememberBox.setBounds(140, 245, 100, 12);
            //Connection:
            console.setBounds(10, 300, 280, 100);
        } else { //Connected Window
            //Text:
            Dimension d = this.getSize();
            drawCenteredString("Connected to " + dbName, d.width, g);
            // Buttons:
            disconnect.setBounds(90, 250, 100, 21);
            query.setBounds(80, 120, 120, 21);
            update.setBounds(80, 180, 120, 21);
            //TextField:
            textQuery.setBounds(45, 90, 200, 21);
            textUpdate.setBounds(45, 150, 200, 21);
            textQuery.setHorizontalAlignment(JTextField.CENTER);
            textUpdate.setHorizontalAlignment(JTextField.CENTER);
            //Connection:
            console.setBounds(10, 300, 280, 100);
            queryLog.setBounds(210, 120, 20, 20);
            queryLog.setIcon(iconQuery);
            updateLog.setBounds(210, 180, 20, 20);
            updateLog.setIcon(iconUpdate);
            tablesButton.setBounds(80, 220, 120, 21);
            settings.setBounds(10,10,30,30);
            settings.setIcon(iconSettings);
        }
    }


    public void actionPerformed(ActionEvent e) { // It controls the response of the buttons, textfields ... etc
        // Getting Action:
        Object action = e.getSource();
        console.setText("");
        if (connected) { // Connected to the database
            if (disconnect.equals(action)) { // Disconnecting code
                console.append("Disconecting...\n");
                GUI.gest.disconnect();
                createLogs();
                remove(disconnect);
                remove(textQuery);
                remove(textUpdate);
                remove(query);
                remove(update);
                remove(queryLog);
                remove(updateLog);
                remove(tablesButton);
                remove(settings);
                if(fFrame!=null){
                    fFrame.setVisible(false);
                    fFrame.removeAll();
                    fFrame=null;
                }
                for(MyTable table : tableList){
                    table.remove();
                }
                tableList=null;
                disconnected();
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
            if (tablesButton.equals(action)) { // Get tables names
                String[] columName = {dbName + " tables"};
                Object[][] tLog = new String[tablesNames.size()][1];
                int i = 0;
                for (String log : tablesNames) {
                    tLog[i][0] = log;
                    i++;
                }
                tableList.addLast(new MyTable(tLog, columName, "tables"));
            }
            if(settings.equals(action)){
                if(fFrame==null)
                    fFrame = new FriendlyFrame();
                else
                    fFrame.setVisible(true);
            }


        } else { // Disconnected database:
            if (action == buttonSignIn) { // Connect code
                connected();
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
        String[] mL = {mode + "_" + dbName + " log"};
        Object[][] mLog;
        LinkedList<String> updateList = new LinkedList<>();
        File f = new File("src/internalFiles/" + mode + "_" + dbName + ".txt"); // Getting log
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
            if (!updateList.isEmpty())
                tableList.addLast(new MyTable(mLog, mL, mode));
            else
                tableList.addLast(new MyTable(new Object[1][1], mL, mode));
        } else {
            tableList.addLast(new MyTable(new Object[1][1], mL, mode));
        }
    }

    private void createLogs() {
        if (!queries.isEmpty()) {
            try {
                BufferedWriter writerQuery = new BufferedWriter(new FileWriter("src/internalFiles/query_" + dbName + ".txt"));
                for (String query : queries) {
                    writerQuery.write(query + "\n");
                }
                writerQuery.close();
            } catch (IOException ioException) {
                appendToConsole("Error saving queries...\n");
            }
        }

        if (!updates.isEmpty()) {
            try {
                BufferedWriter writerUpdate = new BufferedWriter(new FileWriter("src/internalFiles/update_" + dbName + ".txt"));
                for (String update : updates) {
                    writerUpdate.write(update + "\n");
                }
                writerUpdate.close();
            } catch (IOException ioException) {
                appendToConsole("Error saving updates...\n");
            }
        }
    }

    public static void setTextQuery(String query) {
        textQuery.setText(query);
    }

    public static void setTextUpdate(String update) {
        textUpdate.setText(update);
    }

    public static void appendToConsole(String data) {
        console.append(data);
    }

    private void drawCenteredString(String s, int width, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (width - fm.stringWidth(s)) / 2;
        g.drawString(s, x, 60);
    }
}
