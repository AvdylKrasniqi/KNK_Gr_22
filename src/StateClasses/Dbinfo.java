package StateClasses;

import javax.xml.transform.Result;
import java.sql.*;

public class Dbinfo {
    // duhet options qitu
    private static Connection con;
    private static final String firstConnection = "jdbc:mysql://185.67.178.114:3306/art_knk_db";
    private static final String username = "art_knk";
    private static final String password = "OyRKDSix1BfEk0+vqgKqTbOqxYz3RVsX7R0HOL7+";


    public static ResultSet executeQuery(String query) throws SQLException {
        Connection con = startConnection();
        PreparedStatement stmt =con.prepareStatement(query) ;
    ResultSet results = stmt.executeQuery();
        return results;
    }
        
//    public static ResultSet executeIdQuery(String query,int id )
//    {
//
//    }

    public static String getConnectionName() {
        return Dbinfo.firstConnection;
    }

    public static String getConnectionUsername() {
        return Dbinfo.username;
    }

    public static String getPassword() {
        return Dbinfo.password;
    }

    public static Connection startConnection() throws SQLException {
        con = DriverManager.getConnection(firstConnection, username, password);
        return con;
    }


}
