/*
    This class contains all the code to access to the database, do queries... etc
 */

package MySqlGest;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class MySqlGest {
    // Connection to the database:
    private Connection conn;

    /**
     * PRE: Database created is required
     *
     * This method creates the connection to the database
     *
     * @param user     The name user
     * @param pass     The password of the user
     * @param db The name of the database
     * @param address  The address (ip:port) from the database
     * @return true if no error, false otherwise
     */
    public boolean getConnection(String user, String pass, String db, String address) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String anyTimeZone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // AnyTimeZone permitied
        String url = "jdbc:mysql://" + address + "/" + db + anyTimeZone;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            PrincipalPanel.appendToConsole("Connect!\n");
            return true;
        } catch (SQLException e) {
            PrincipalPanel.appendToConsole("Error in connection.\nMore info:\n" + e.getMessage() + "\n");
            return false;
        } catch (ClassNotFoundException f) {
            PrincipalPanel.appendToConsole("Error loading driver.\nMore info:\n" + f.getMessage() + "\n");
            return false;
        }
    }

    /**
     * This method disconnects the database
     */
    public void disconnect() {
        try {
            conn.close();
            PrincipalPanel.appendToConsole("Disconnect!\n");
        } catch (SQLException e) {
            PrincipalPanel.appendToConsole("Error during disconnect.\nMore info:\n" + e.getMessage() + "\n");
        } catch (NullPointerException f) {
            PrincipalPanel.appendToConsole("You can not disconnect if you dont get a connection.\nMore info:\n" + f.getMessage() + "\n");
        }
    }

    /**
     * Sends the query to the database and calls the method "showIt"
     * @param query The query that you want to do
     * @return true if no error, false otherwise
     */
    public boolean doQuery(String query) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            showIt(rs);
            rs.close();
            st.close();
            PrincipalPanel.appendToConsole("Query Done!\n");
            return true;
        } catch (SQLException e) {
            PrincipalPanel.appendToConsole("Error doing query.\nMore info:\n" + e.getMessage() + "\n");
            return false;
        }
    }
    /**
     * Sends the update to the database
     * @param update The update that you want to do
     * @return true if no error, false otherwise
     */
    public boolean doUpdate(String update) {
        try {
            PreparedStatement pst = conn.prepareStatement(update);
            pst.executeUpdate();
            pst.close();
            PrincipalPanel.appendToConsole("Update Done!\n");
            return true;
        } catch (SQLException e) {
            PrincipalPanel.appendToConsole("Error doing update.\nMore info:\n" + e.getMessage() + "\n");
            return false;
        }
    }

    /**
     *  It gets all the names of the Database tables
     * @return A LinkedList of names
     */
    public LinkedList<String> getDatabaseInfo() {
        LinkedList<String> result = new LinkedList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Show tables");
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException throwables) {
            PrincipalPanel.appendToConsole("Error getting tables names.\nMore info:\n" + throwables.getMessage() + "\n");
        }
        return result;
    }
    /**
     *  It gets all the atributes of a table
     * @param table The table that you want to gete the atributes
     * @return A matrix of atributes
     */
    public Object[][] getAtributes(String table) {
        Object[][] result = null;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + table);
            result = new Object[rs.getMetaData().getColumnCount()][1];
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                result[i - 1][0] = rs.getMetaData().getColumnName(i);
            }
        } catch (SQLException throwables) {
            PrincipalPanel.appendToConsole("Error getting tables atributes.\nMore info:\n" + throwables.getMessage() + "\n");
        }
        return result;
    }

    /**
     * This method gets all the data from the query, saves them in a data structure and print them in a JTable
     * @param rs The resultset from the query
     * @throws SQLException Pray for not see that exception
     */
    private void showIt(ResultSet rs) throws SQLException {
        String[] columnNames = new String[rs.getMetaData().getColumnCount()];
        HashMap<Integer, LinkedList<Object>> map = new HashMap<>(); // It contains, temporary, the elements of the query
        // Getting Column Names:
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            columnNames[i - 1] = rs.getMetaData().getColumnName(i);

        }
        // Getting Data:
        int row = 0;
        while (rs.next()) {
            for (int i = 0; i < columnNames.length; i++) {
                if (!map.containsKey(row))
                    map.put(row, new LinkedList<>());
                map.get(row).addLast(rs.getObject(i + 1));
            }
            row++;
        }
        // Saving data in a matrix for the table:

        if (row != 0) {
            Object[][] data = new Object[row][columnNames.length];

            for (int i = 0; i < row; i++) {
                int j = 0;
                for (Object o : map.get(i)) {
                    data[i][j] = o;
                    j++;
                }
            }
            // Creating table:
            new MyTable(data, columnNames, null);
        } else {
            PrincipalPanel.appendToConsole("Empty table\n");
        }

    }
}
