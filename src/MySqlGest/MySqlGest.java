package MySqlGest;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class MySqlGest {
    // Connection to the database:
    private Connection conn;

    // PRE: Database created is required
    public boolean getConnection(String user, String pass, String db, String address) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String anyTimeZone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String url = "jdbc:mysql://" + address + "/" + db + anyTimeZone;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            PrincipalPanel.appendToConsole("Connect!\n");
            return true;
        } catch (SQLException e) {
            PrincipalPanel.appendToConsole("Error in connection.\nMore info: " + e.getMessage() + "\n");
            return false;
        } catch (ClassNotFoundException f) {
            PrincipalPanel.appendToConsole("Error loading driver.\nMore info: " + f.getMessage() + "\n");
            return false;
        }
    }

    public boolean disconnect() {
        try {
            conn.close();
            PrincipalPanel.appendToConsole("Disconnect!\n");
            return true;
        } catch (SQLException e) {
            PrincipalPanel.appendToConsole("Error during disconnect.\nMore info: " + e.getMessage() + "\n");
            return false;
        } catch (NullPointerException f) {
            PrincipalPanel.appendToConsole("You can not disconnect if you dont get a connection.\nMore info: " + f.getMessage() + "\n");
            return false;
        }
    }

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
            PrincipalPanel.appendToConsole("Error doing query.\nMore info: " + e.getMessage() + "\n");
            return false;
        }
    }

    public boolean doUpdate(String update) {
        try {
            PreparedStatement pst = conn.prepareStatement(update);
            pst.executeUpdate();
            pst.close();
            PrincipalPanel.appendToConsole("Update Done!\n");
            return true;
        } catch (SQLException e) {
            PrincipalPanel.appendToConsole("Error doing update.\nMore info: " + e.getMessage() + "\n");
            return false;
        }
    }

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
    }
}
