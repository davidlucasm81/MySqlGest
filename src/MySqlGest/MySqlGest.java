package MySqlGest;

import com.mysql.cj.protocol.Resultset;

import javax.management.Query;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class MySqlGest {
    // Var:
    private Connection conn;

    // PRE: Database created is required
    public boolean getConnection(String user, String pass, String db, String address) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://" + address + "/" + db;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            PrincipalPanel.con.append("Connect!\n");
            return true;
        } catch (SQLException e) {
            PrincipalPanel.con.append("Error in connection.\nMore info: " + e.getMessage() + "\n");
            return false;
        } catch (ClassNotFoundException f) {
            PrincipalPanel.con.append("Error loading driver.\nMore info: " + f.getMessage() + "\n");
            return false;
        }
    }

    public boolean disconnect() {
        try {
            conn.close();
            PrincipalPanel.con.append("Disconnect!\n");
            return true;
        } catch (SQLException e) {
            PrincipalPanel.con.append("Error during disconnect.\nMore info: " + e.getMessage() + "\n");
            return false;
        } catch (NullPointerException f) {
            PrincipalPanel.con.append("You can not disconnect if you dont get a connection.\nMore info: " + f.getMessage() + "\n");
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
            PrincipalPanel.con.append("Query Done!\n");
            return true;
        } catch (SQLException e) {
            PrincipalPanel.con.append("Error doing query.\nMore info: " + e.getMessage() + "\n");
            return false;
        }
    }

    public boolean doUpdate(String update) {
        try {
            PreparedStatement pst = conn.prepareStatement(update);
            pst.executeUpdate();
            pst.close();
            PrincipalPanel.con.append("Update Done!\n");
            return true;
        } catch (SQLException e) {
            PrincipalPanel.con.append("Error doing update.\nMore info: " + e.getMessage() + "\n");
            return false;
        }
    }

    private void showIt(ResultSet rs) throws SQLException {
        String columnNames[] = new String[rs.getMetaData().getColumnCount()];
        HashMap<Integer, LinkedList<Object>> map = new HashMap<>();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            columnNames[i-1] = rs.getMetaData().getColumnName(i);
        }

        int row = 0;
        while (rs.next()) {

            for (int i = 0; i < columnNames.length; i++) {
                if(!map.containsKey(row))
                    map.put(row,new LinkedList<>());
                map.get(row).addLast(rs.getObject(i+1));
            }

            row++;
        }
        Object[][] data = new Object[row][columnNames.length];
       for(int i=0;i<row;i++){
           int j=0;
           for(Object o : map.get(i)){
               data[i][j]=o;
               j++;
           }
       }

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        TableFrame frame = new TableFrame();

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(200*rs.getMetaData().getColumnCount(),40*row);


    }
}
