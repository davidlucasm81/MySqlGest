package MySqlGest;

import java.sql.*;

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
            System.err.println("Error in connection.\nMore info: "+e.getMessage());
            return false;
        }


    }

    public boolean disconnect(){
        try{
            conn.close();
            return true;
        }
        catch (SQLException e){
            System.err.println("Error during disconnect.\nMore info: "+e.getMessage());
            return false;
        }
        catch (NullPointerException f){
            System.err.println("You can not disconnect if you dont get a connection.\nMore info: "+f.getMessage());
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
            System.err.println("Error doing query.\nMore info: "+e.getMessage());
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
            System.err.println("Error doing update.\nMore info: "+e.getMessage());
            return false;
        }

    }


    private void showIt(ResultSet rs) throws SQLException {
        while(!rs.isAfterLast()){
            String row="";
            System.out.println(rs.getRow()+" "+row);
        }
    }


}
