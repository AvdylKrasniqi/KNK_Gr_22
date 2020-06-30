package StateClasses;

import javax.xml.transform.Result;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

public class Dbinfo {
    // duhet options qitu
    private static Connection con;
    private static  String firstConnection;
    private static String username;
    private static String password;


    public static ResultSet executeQuery(String query) throws SQLException {
        Connection con = startConnection();
        PreparedStatement stmt =con.prepareStatement(query) ;
    ResultSet results = stmt.executeQuery();
        return results;
    }
//
//    FileReader reader=new FileReader("db.properties");
//
//    Properties p=new Properties();
//    p.load(reader);

    public static void readCredentials()
    {
        try {
          FileReader reader = new FileReader("src/StateClasses/Dbinfo.properties");
            Properties properties =new Properties();
            properties.load(reader);
            firstConnection=properties.getProperty("firstConnection");
            username=properties.getProperty("username");
            password=properties.getProperty("password");
        }
        catch(Exception e )
        {
            System.out.println(e.getMessage());
        }

    }

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
