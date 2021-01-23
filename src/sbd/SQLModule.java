package sbd;

import java.sql.*;
import java.lang.*;

public class SQLModule {
    public static Class jdbc;
    private static Connection con;
    private static ResultSet rs;
    private static Statement stmt;

    // Returns true when succeeds and false when not
    public static boolean startConnection(){
        System.out.println("Try connecting to database...");
        try {
            jdbc = Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    "scott", "tiger");
            System.out.println("Connection to database successful!");
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static void endConnection(){
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Simple test select
    public static void select(String value){
        stmt = null;
        try {
            stmt = con.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        rs = null;
        try {
            rs = stmt.executeQuery(value);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            while(true) {
                if (!rs.next()) break;
                String nazwisko = null;
                nazwisko = rs.getString("NAZWISKO");
                float placa = 0;
                placa = rs.getFloat(2);
                System.out.println(nazwisko + " " + placa);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get column names of table (because we don't get them when we are using selectAll()
    public ResultSet getColumnNames(String tableName){
        stmt = null;
        rs = null;
        String query = "Select COLUMN_NAME FROM ALL_TAB_COLUMNS WHERE TABLE_NAME=";
        query.concat(tableName);
        query.concat(";");
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    // Select whole table with conditions defined in three filter tables
    public ResultSet selectAll(String tableName, String[] filterColumn, String[] filterOperator, String[] filterValue){
        stmt = null;
        rs = null;
        String query = "Select * FROM ";
        query.concat(tableName);
        if(filterColumn.length == filterOperator.length &&  filterOperator.length ==  filterValue.length &&
        filterValue.length > 0){
            query.concat(" WHERE ");
            for(int i = 0; i < filterColumn.length; i++){
                query.concat(filterColumn[i]);
                query.concat(filterOperator[i]);
                query.concat(filterValue[i]);
                if(i < filterColumn.length - 1){
                    query.concat(" AND ");
                }
            }
        }
        else{
            System.out.println("Filter arguments not used");
        }
        query.concat(";");
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    // Select used when we need to create dropdown list for updating foreign keys in table
    // Just feed those values to dropdown list
    public ResultSet selectColumn(String tableName, String columnName){
        Statement stmt = null;
        ResultSet rs = null;
        String query = "Select ";
        query.concat(columnName);
        query.concat(" FROM ");
        query.concat(tableName);
        query.concat(";");
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    // Needs to be invoked after each statement
    public void close(){
        try{
            rs.close();
            stmt.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    // Mozliwe ze trzeba tu przekazywac typ kazdej kolumny jeszcze skoro values sa przekazywane stringami
    // ALbo switch dla każdego table name
    public void insertRow(String tableName, String[] values){

    }

    public void updateRow(String tableName, String primaryKey, String[] columnNames, String[] values){

    }

    public void deleteRow(String tableName, String primaryKey){

    }

    // SEPARATE FUNCTION FOR COMMIT?


}
