/*
    The main panel, it controlls everything. Below I will describe all of this class
 */
package MySqlGest;
import javax.swing.*;
import javax.swing.border.Border;
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
    public static LinkedList<MyTable> tableList;
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
    private static final JTextArea textQuery = new JTextArea();
    private static final JTextArea textUpdate = new JTextArea();
    private final JPasswordField pass = new JPasswordField();
    private final JButton settings = new JButton();
    //Console
    private static JTextArea console;
    //Images:
    private final Image background;
    private ImageIcon iconQuery;
    private ImageIcon iconUpdate;
    private ImageIcon iconSettings;
    //FriendlyFrame:
    private FriendlyFrame fFrame;
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

    /**
     * Disconnected contains all the "disconnect" code. It adds initial buttons
     * removes intern atributes, and save the queries/updates list
     */
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
    /**
     * Connected contains all the "connect" code. It takes care of connecting to the database,
     * adds MySqlGest buttons and removes login buttons.
     */
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
            connected = true;
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
            // TextArea:
            textQuery.setLineWrap(true);
            textUpdate.setLineWrap(true);
            textQuery.setFont(new Font("Calibri", Font.PLAIN, 15));
            textUpdate.setFont(new Font("Calibri", Font.PLAIN, 15));
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            textQuery.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(1, 1, 1, 1)));
            textUpdate.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 1, 1, 1)));
            tablesNames = GUI.gest.getDatabaseInfo();
        }
    }
    /**
     * This method "paint" the background, locate the buttons and set texts
     *
     * @param g Graphics context in which to paint
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font myFont = new Font("Comic Sans", Font.BOLD, 15); // Yep, comic sans
        g.setFont(myFont);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
        if (!connected) { // First Window
            //Text:
            g.drawString("Welcome to MySqlGest", 65, 40);
            g.drawString("User: ", 25, 75);
            g.drawString("Pass: ", 25, 105);
            g.drawString("DB: ", 25, 135);
            g.drawString("IP:", 25, 175);
            g.drawString(":", 163, 175);
            //Initial TextField:
            user.setBounds(70, 60, 150, 20);
            pass.setBounds(70, 90, 150, 20);
            db.setBounds(70, 120, 150, 20);
            //Address TextField:
            localhost.setBounds(100, 190, 90, 12);
            ip.setBounds(70, 160, 90, 20);
            port.setBounds(170, 160, 50, 20);
            //Initial Button:
            buttonSignIn.setBounds(50, 220, 80, 21);
            rememberBox.setBounds(140, 225, 100, 12);
            //Connection:
            console.setBounds(10, 300, 280, 100);
        } else { //Connected Window
            //Text:
            Dimension d = this.getSize();
            drawCenteredString("Connected to " + dbName, d.width, g);
            // Buttons:
            disconnect.setBounds(90, 300, 100, 21);
            query.setBounds(80, 130, 120, 21);
            update.setBounds(80, 230, 120, 21);
            //TextField:
            textQuery.setBounds(45, 50, 200, 60);
            textUpdate.setBounds(45, 160, 200, 60);
            //Connection:
            console.setBounds(10, 330, 280, 100);
            queryLog.setBounds(210, 130, 20, 20);
            queryLog.setIcon(iconQuery);
            updateLog.setBounds(210, 230, 20, 20);
            updateLog.setIcon(iconUpdate);
            tablesButton.setBounds(80, 270, 120, 21);
            settings.setBounds(10,10,30,30);
            settings.setIcon(iconSettings);
        }
    }
    /**
     * This method have soo much code, so:
     *      - If you are connected in the database:
     *          -> Disconnect Button: Removes MySqlGest buttons, call "disconnect" method, and closes open tables
     *          -> Query Button: Realizes the query of the Query Text Area and clean it
     *          -> Update Button: Realizes the update of the Update Text Area and clean it
     *          -> QueryLog Button: Calls "viewLog" method, creating the previous queries table
     *          -> UpdateLog Button: Calls "viewLog" method, creating the previous updates table
     *          -> TablesButton Button: Creates the table "Database Tables Name"
     *          -> Setings Button: It opens the FriendlyPanel
     *      - If you are disconnected:
     *          -> SignIn Button: Calls "connected" method
     *          -> Localhost CheckBox: If you mark it, sets the ip and port to "localhost:3306", and clears it otherwise
     *          -> RememberBox CheckBox: Actives the remember code
     * @param e The action who invoked the method
     */
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
            if (buttonSignIn.equals(action)) { // Connect code
                connected();
            }
            if (localhost.equals(action)) { // Ip is localhost code
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
            if (rememberBox.equals(action)) {
                remember = !remember;
            }
        }
    }

    /**
     *  It create the tables to see the previous queries/updates
     * @param mode It marks if it's queries or updates
     */
    private void viewLogs(String mode) {
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

    /**
     * When you are going to disconnect from the database, this method save the previous queries/updates logs
     */
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
        g.drawString(s, x, 30);
    }
}
