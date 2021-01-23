package sbd;

import java.sql.*;
import java.lang.*;

public class SQLModule {
    public static Class jdbc;
    private static Connection con;

    public static void startConnection(){
        System.out.println("Try connecting to database...");
        try {
            jdbc = Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    "scott", "tiger");
            System.out.println("Connection to database successful!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void endConnection(){
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void select(String value){
        Statement stmt = null;
        try {
            stmt = con.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet rs = null;
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





}
