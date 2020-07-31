package MySqlGest;

import java.sql.*;

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
            return true;
        } catch (SQLException e) {
            QueryFrame.con.append("Error in connection.\nMore info: " + e.getMessage() + "\n");
            return false;
        }
        catch(ClassNotFoundException f){
            QueryFrame.con.append("Error loading driver.\nMore info: " + f.getMessage() + "\n");
            return false;
        }
    }
    public boolean disconnect() {
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            QueryFrame.con.append("Error during disconnect.\nMore info: " + e.getMessage() + "\n");
            return false;
        } catch (NullPointerException f) {
            QueryFrame.con.append("You can not disconnect if you dont get a connection.\nMore info: " + f.getMessage() + "\n");
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
            return true;
        } catch (SQLException e) {
            QueryFrame.con.append("Error doing query.\nMore info: " + e.getMessage() + "\n");
            return false;
        }
    }
    public boolean doUpdate(String update) {
        try {
            PreparedStatement pst = conn.prepareStatement(update);
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            QueryFrame.con.append("Error doing update.\nMore info: " + e.getMessage() + "\n");
            return false;
        }
    }
    private void showIt(ResultSet rs) throws SQLException {
        while (!rs.isAfterLast()) {
            String row = "";
            QueryFrame.con.append(rs.getRow() + " " + row + "\n");
        }
    }
}
