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

    public ArrayList select(String query) {
        String MYSQL_URL = hostName + dbName + dateTimeCode;
        Connection con;
        Statement st;
        String result = "";
        ArrayList<String> resultArray = new ArrayList<>();

        try {
            con = DriverManager.getConnection(MYSQL_URL,dbUserName,dbPassword);
            st = con.createStatement();
            ResultSet resultSet =st.executeQuery(query);

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    resultArray.add(resultSet.getString(i));
                    //result = resultSet.getString(i);
                }
            }
            con.close();
        } catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "De verbinding met de database is verbroken. Probeer het later opnieuw");
        }
        return resultArray;
        //return (result.length() == 0) ? "" : result;
    }

    public boolean insert(String query){
        String MYSQL_URL = hostName + dbName + dateTimeCode;
        Connection con;
        Statement st;
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
}