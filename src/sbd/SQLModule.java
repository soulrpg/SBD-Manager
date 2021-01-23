package sbd;

import java.sql.*;
import java.lang.*;

public class SQLModule {
    public static Class jdbc;
    private static Connection con;
    private static ResultSet rs;
    private static Statement stmt;

    // Returns true when succeeds and false when not
    public static boolean startConnection(String user, String password){
        System.out.println("Try connecting to database...");
        try {
            jdbc = Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    user, password);
            System.out.println("Connection to database successful!");
            con.setAutoCommit(false);
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
    public static ResultSet getColumnNames(String tableName){
        stmt = null;
        rs = null;
        String query = "select COLUMN_NAME from user_tab_columns where table_name=";
        query = query.concat(tableName);
        //query = query.concat(";");
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
    public static ResultSet selectAll(String tableName, String[] filterColumn, String[] filterOperator, String[] filterValue){
        stmt = null;
        rs = null;
        String query = "Select * FROM ";
        query = query.concat(tableName);
        if(filterColumn.length == filterOperator.length &&  filterOperator.length ==  filterValue.length &&
        filterValue.length > 0){
            query = query.concat(" WHERE ");
            for(int i = 0; i < filterColumn.length; i++){
                query = query.concat(filterColumn[i] + " ");
                query = query.concat(filterOperator[i]);
                query = query.concat(filterValue[i]);
                if(i < filterColumn.length - 1){
                    query = query.concat(" AND ");
                }
            }
        }
        else{
            System.out.println("Filter arguments not used");
        }
        //query.concat(";");
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
    public static ResultSet selectColumn(String tableName, String columnName){
        stmt = null;
        rs = null;
        String query = "Select ";
        query = query.concat(columnName);
        query = query.concat(" FROM ");
        query = query.concat(tableName);
        query = query.concat(";");
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
    public static void close(){
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
    public static int insertRow(String tableName, String[] values, String[] types){
        try {
            stmt = null;
            stmt = con.createStatement();
            String query = "INSERT INTO ";
            query = query.concat(tableName);
            query = query.concat(" VALUES(");
            for(int i = 0; i < values.length; i++){
                switch(types[i]){
                    case "VARCHAR":
                        query = query.concat("\'" + values[i] +"\'");
                        break;
                    default:
                        query = query.concat(values[i]);
                }
                if(i < values.length - 1)
                    query = query.concat(",");
            }
            query = query.concat(")");
            int changes = stmt.executeUpdate(query);
            stmt.close();
            return changes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int updateRow(String tableName, String[] pK, String[] columns, String[] values, String[] oldValues, String[] types){
        try{
            stmt = null;
            stmt = con.createStatement();
            String query = "UPDATE ";
            query = query.concat(tableName);
            query = query.concat(" SET ");
            for(int i = 0; i < columns.length; i++){
                if(oldValues[i].equals(values[i]))
                    continue;
                query = query.concat(columns[i] + "=");
                switch(types[i]){
                    case "VARCHAR":
                        query = query.concat("\'" + values[i] +"\'");
                        break;
                    default:
                        query = query.concat(values[i]);
                }
                if(i < columns.length - 1){
                    query = query.concat(", ");
                }
            }
            if(query.endsWith(", ")){
                query = query.substring(0, query.length() - 2);
            }
            query = query.concat(" WHERE ");
            for(int i = 0; i < pK.length; i++){
                for(int j = 0; j < columns.length; j++){
                    if(pK[i].equals(columns[j])){
                        query = query.concat(pK[i] + "=");
                        switch(types[j]){
                            case "VARCHAR":
                                query = query.concat("\'" + oldValues[j] +"\'");
                                break;
                            default:
                                query = query.concat(oldValues[j]);
                        }
                        break;
                    }
                }
                if(i < pK.length - 1){
                    query = query.concat(" AND ");
                }
            }

            int changes = stmt.executeUpdate(query);
            stmt.close();
            return changes;
        }
        catch(SQLException e){
            e.printStackTrace();
            return -1;
        }

    }

    public static void deleteRow(String tableName, String primaryKeyColumn, String primaryKey){
       // DELETE FROM tableName WHERE primaryKeyColumn = primaryKey;

    }

    // SEPARATE FUNCTION FOR COMMIT?
    public static void commit(){
        try {
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(){
        try {
            con.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
