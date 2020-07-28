import java.sql.*;
import com.mysql.jdbc.*;
public class MySqlGest {

    // Var:

    private Connection conn;

    // PRE: Database created is required
    public boolean getConnection (String user, String pass, String db) {
        String driver = "com.mysql.jdbc.Driver";
        String serverAddress = "localhost:3306";
        String url = "jdbc:mysql://"+serverAddress+"/"+db;
        try{
            conn = DriverManager.getConnection(url,user,pass);
            return true;
        }
        catch (SQLException e){
            System.err.println("Error in connection/n More info: "+e.getMessage());
            return false;
        }


    }

    public boolean desconnect (){
        try{
            conn.close();
            return true;
        }
        catch (SQLException e){
            System.err.println("Error during disconnect/n More info: "+e.getMessage());
            return false;
        }

    }

    public boolean doQuery (String query){
        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            showIt(rs);
            rs.close();
            st.close();
            return true;

        }
        catch (SQLException e){
            System.err.println("Error doing query/n More info: "+e.getMessage());
            return false;
        }

    }

    public boolean doUpdate (String update){
        try{
            PreparedStatement pst = conn.prepareStatement(update);
            pst.executeUpdate();
            pst.close();
            return true;
        }
        catch (SQLException e){
            System.err.println("Error doing update/n More info: "+e.getMessage());
            return false;
        }

    }


    private void showIt(ResultSet rs) {
        //TO-DO
    }


}
