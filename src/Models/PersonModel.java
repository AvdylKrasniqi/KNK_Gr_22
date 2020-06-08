package Models;

import Helpers.PasswordGenerator;
import StateClasses.Dbinfo;
import StateClasses.LoggedUser;

import javax.xml.transform.Result;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonModel {
    public static String getRole(String email) throws Exception {
        Connection conn = Dbinfo.startConnection();
        String query = "Select status from Staff where email=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            conn.close();
            return result.getString(1);
        } else {
            conn.close();
            throw new Exception("User doesnt exist");
        }
    }

    // problem mi bo krejt 1 funksion se po ki me bind shit po duhet per secilin query funksion
    public static String statusEnumToString(LoggedUser.Status status) throws Exception {
        if (status == LoggedUser.Status.Admin)
            return "Admin";
        else if (status == LoggedUser.Status.Waiter)
            return "Kamarier";
        else throw new Exception("Something went wrong");
    }



    public static boolean isUser(String email) throws  Exception
    {Connection con = Dbinfo.startConnection();
    String query="Select * FROM Staff where email=?";
    PreparedStatement stmt=con.prepareStatement(query);
    stmt.setString(1,email);
        ResultSet result = stmt.executeQuery();
        return result.next();
    }
    public static LoggedUser.Status finalRole(String email) throws Exception {
        String role =getRole(email);
        return  statusStringToEnum(role);
    }


    public static void insertUser(String name, String email, String password, double salary, String status) throws SQLException, NoSuchProviderException, NoSuchAlgorithmException {
        Connection con = Dbinfo.startConnection();
        String saltedPassword = PasswordGenerator.generateSaltedPassword(password);
        String query = "insert into Staff(name,email,password,salary,status) values(?,?,?,?,?);";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, saltedPassword);
        stmt.setDouble(4, salary);
        stmt.setString(5, status);
        stmt.executeUpdate();
        con.close();

    }

    public static LoggedUser.Status statusStringToEnum(String status) throws Exception {
        if (status.equals("Admin"))
            return LoggedUser.Status.Admin;
        else if (status.equals("Waiter"))
            return LoggedUser.Status.Waiter;
        else throw new Exception("Gabim teknik babo");
    }

    public static void deleteUser(int id) throws Exception {
        Connection con = Dbinfo.startConnection();
        String query = "Delete FROM Staff where id=?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.executeUpdate();
        con.close();
    }


    public static void deleteUser(String email) throws SQLException {
        Connection con = Dbinfo.startConnection();
        String query = "Delete FROM Staff where email=?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.executeUpdate();
        con.close();
    }


    public static ResultSet returnInfo(String email) throws Exception {
        Connection conn = Dbinfo.startConnection();
        String query = "Select * from Staff where email=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            conn.close();
            return result;
        } else {
            conn.close();
            throw new Exception("User does not exist");
        }
    }
}
