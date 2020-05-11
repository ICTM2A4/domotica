package nl.ictm4a.domotica;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseFunction extends JFrame{
    private String hostName = "jdbc:mysql://localhost:3306/";
    private String dbName = "domotica";
    private String dateTimeCode = "?useLegacyDatetimeCode=false&serverTimezone=GMT";
    private String dbUserName = "root";
    private String dbPassword = "";
    private String MYSQL_URL = hostName + dbName + dateTimeCode;
    Connection con;
    Statement st;

    public ArrayList<String> select(String column, String fromTable, String where, String is) {
        ArrayList<String> resultArray = new ArrayList<>();
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
                    resultArray.add(resultSet.getString(i));
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

    public boolean insertRow(String query){
        try {
            con = DriverManager.getConnection(MYSQL_URL,dbUserName,dbPassword);
            st = con.createStatement();
            st.execute(query);
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

    public boolean insertRow(String table, String column, String value){
        try {
            con = DriverManager.getConnection(MYSQL_URL,dbUserName,dbPassword);
            String query = "INSERT INTO " + table + " (" + column + ") VALUES (" + value + ")";
            PreparedStatement st = con.prepareStatement(query);
            System.out.println(st);
            st.executeUpdate();
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
}
