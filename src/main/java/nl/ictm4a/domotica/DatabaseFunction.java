package nl.ictm4a.domotica;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseFunction extends JFrame{
    private String hostName = "jdbc:mysql://localhost:3306/";
    private String dbName = "domotica";
    private String dateTimeCode = "?useLegacyDatetimeCode=false&serverTimezone=GMT";
    private String dbUserName = "root";
    private String dbPassword = "";
    private String MYSQL_URL = hostName + dbName + dateTimeCode;
    Connection con;
    Statement st;

    public ArrayList<String> selectRow(String column, String fromTable, String where, String is) {
        ArrayList<String> resultArrayList = new ArrayList<>();
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);
            PreparedStatement stmt = con.prepareStatement("SELECT " + column + " FROM " + fromTable + " WHERE " + where + " = ?");
            stmt.setString(1, is);
            ResultSet resultSet = stmt.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    resultArrayList.add(resultSet.getString(i));
                }
            }
            con.close();
        } catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Er is een probleem met de database. Probeer het later opnieuw");
        }
        return resultArrayList;
    }

    public boolean insertRow(String table, String column, String value){
        try {
            con = DriverManager.getConnection(MYSQL_URL,dbUserName,dbPassword);
            String query = "INSERT INTO " + table + " (" + column + ") VALUES (" + value + ")";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.executeUpdate();
            con.close();
        } catch(SQLIntegrityConstraintViolationException icve) {
            return false;
        }catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertLogging(String table, String column1, String column2, String column3, String column4, String column5, int value1, double value2, String value3, int value4, int value5) {
        try {
            con = DriverManager.getConnection(MYSQL_URL,dbUserName,dbPassword);
            String sqlInsert = "INSERT INTO "+table+" ("+column1+", "+column2+", "+column3+", "+column4+", "+column5+") VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sqlInsert);
            pstmt.setInt(1, value1);
            pstmt.setDouble(2, value2);
            pstmt.setString(3, value3);
            pstmt.setInt(4, value4);
            pstmt.setInt(5, value5);
            pstmt.executeUpdate();
            con.close();
        } catch(SQLIntegrityConstraintViolationException icve) {
            return false;
        }catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertLogging(String table, String column1, String column2, String column3, String column5, int value1, double value2, String value3, int value5) {
        try {
            con = DriverManager.getConnection(MYSQL_URL,dbUserName,dbPassword);
            String sqlInsert = "INSERT INTO "+table+" ("+column1+", "+column2+", "+column3+", "+column5+") VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sqlInsert);
            pstmt.setInt(1, value1);
            pstmt.setDouble(2, value2);
            pstmt.setString(3, value3);
            pstmt.setInt(4, value5);
            pstmt.executeUpdate();
            con.close();
        } catch(SQLIntegrityConstraintViolationException icve) {
            return false;
        }catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean updateUserSetting(String fromTable, String column1, String column2, int value1, int value2, int value3) {
        try {
            con = DriverManager.getConnection(MYSQL_URL,dbUserName,dbPassword);
            String sqlUpdate = "UPDATE "+fromTable+" SET "+column1+" = ?, "+column2+" = ? WHERE user_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlUpdate);
            pstmt.setInt(1, value1);
            pstmt.setInt(2, value2);
            pstmt.setInt(3, value3);
            pstmt.executeUpdate();
            con.close();
        } catch(SQLIntegrityConstraintViolationException icve) {
            return false;
        }catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public String getCurrentDateTime(){
        java.util.Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
        return format.format(date);
    }
}
