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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    public static boolean isUser(String email) throws Exception {
        Connection con = Dbinfo.startConnection();
        String query = "Select * FROM Staff where email=?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet result = stmt.executeQuery();
        return result.next();
    }

    public static ResultSet getAllWaiters() throws Exception {
        Connection con = Dbinfo.startConnection();
        String query = "SELECT * FROM Staff where status='Waiter'";
        PreparedStatement statement = con.prepareStatement(query);
        return statement.executeQuery();
    }


    public static void insertUser(String name, String email, String password, double salary, String status) throws SQLException, NoSuchProviderException, NoSuchAlgorithmException {

        if (!isValidPassword(password)) {
            System.out.println("Password i papershtatshem");
            return;
        }
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


    public static void deleteUser(int id) throws Exception {
        Connection con = Dbinfo.startConnection();
        String query = "Delete FROM Staff where id=?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.executeUpdate();
        con.close();
    }

    public static boolean isValidPassword(String password) {

    //TODO: validim qe vyn najsen
        return password.length()>5;


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
        stmt.setString(1, email);
        ResultSet result = stmt.executeQuery();
        if (result.next())
            return result;
        else throw new Exception("katastrof");

    }
}



