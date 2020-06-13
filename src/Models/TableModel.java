package Models;

import StateClasses.Dbinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableModel {

    public static ResultSet getTables() throws SQLException {
        Connection con = Dbinfo.startConnection();
        String query = "SELECT * FROM Tavolina";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet result = statement.executeQuery();

//        con.close();
        return result;
    }

    public static void removeTable(int x,int y) throws SQLException {
        Connection con = Dbinfo.startConnection();
        String query = "DELETE FROM Tavolina where x=? and y=?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, x);
        statement.setInt(2, y);
        statement.executeUpdate();
        con.close();
    }

    public static void insertTable(int x, int y) throws Exception {
        Connection con = Dbinfo.startConnection();
        String query = "INSERT INTO Tavolina (x,y) values(?,?)";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, x);
        statement.setInt(2, y);
        statement.executeUpdate();
        con.close();
    }

}
