package Models;

import StateClasses.Dbinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SalesModel {

    public static void insertSales(int tableId, double amount) throws Exception {
        Connection con = Dbinfo.startConnection();
        String query = "INSERT INTO Sales (amount,tavolina_id) values(?,?)";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setDouble(1, amount);
        statement.setInt(2, tableId);
        int affectedRows = statement.executeUpdate();
        if (affectedRows != 1)
            throw new Exception("Gabim ne shtim");
    }

    public static ResultSet getSales(Date from, Date to) throws SQLException {
        Connection con = Dbinfo.startConnection();
        String query = "SELECT * FROM Sales WHERE data >= ? and data <= ?";
        PreparedStatement statement = con.prepareStatement(query);
       //TODO:fix date format 
        System.out.println((java.sql.Date) from);
        System.out.println( to);
        statement.setDate(1,new java.sql.Date(from.getDate()) );
        statement.setDate(2,new java.sql.Date(to.getDate()));
        return statement.executeQuery();

    }

}
