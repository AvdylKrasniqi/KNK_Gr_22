package Models;

import StateClasses.Dbinfo;

import java.sql.*;
import java.time.LocalDate;

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
    public static ResultSet getSales(LocalDate from, LocalDate to) throws SQLException {
        Connection con = Dbinfo.startConnection();
        String query = "SELECT * FROM Sales WHERE data >= ? and data <= ?";
        PreparedStatement statement = con.prepareStatement(query);
        //TODO:fix date format
//
        statement.setDate(1, java.sql.Date.valueOf(from));
        // per me bo query inclusive
        statement.setDate(2, java.sql.Date.valueOf(to.plusDays(1)));
        return statement.executeQuery();

    }

    public static int getTodaysSales() throws  Exception
    {

        Connection con =Dbinfo.startConnection();
        String query="SELECT sum(amount) as  'shuma' from Sales where DATE(data)=?";
        PreparedStatement statement=con.prepareStatement(query);
        statement.setDate(1,new Date(new java.util.Date().getTime()));
        ResultSet results= statement.executeQuery();
        if(results.next())
        {
            return results.getInt("shuma");
        }
        else
            throw new Exception("kuku");

    }



}
