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
            QueryFrame.con.append("Connect!\n");
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
            QueryFrame.con.append("Disconnect!\n");
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
            QueryFrame.con.append("Query Done!\n");
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
            QueryFrame.con.append("Update Done!\n");
            return true;
        } catch (SQLException e) {
            QueryFrame.con.append("Error doing update.\nMore info: " + e.getMessage() + "\n");
            return false;
        }
    }
    private void showIt(ResultSet rs) throws SQLException {
        String columnNames="";
        QueryFrame.con.append("         ");
        String space="                         ";
        for(int i=1;i<=rs.getMetaData().getColumnCount();i++) {
            String name=rs.getMetaData().getColumnName(i);
            int number=(name.length()%2==0)?name.length() : name.length()+1;
            String newSpace=space.substring(number/2);
            columnNames+=newSpace+name+newSpace;
        }
        QueryFrame.con.append(columnNames+"\n");
        while (rs.next()) {
            QueryFrame.con.append("Row: "+rs.getRow());
            for(int i=1;i<=rs.getMetaData().getColumnCount();i++){
                String element=rs.getObject(i).toString();
                int number=(element.length()%2==0)?element.length() : element.length()+1;
                String newSpace=space.substring(number/2);
                QueryFrame.con.append(newSpace+element+newSpace);
            }
            QueryFrame.con.append("\n");
        }

    }
}
