package nl.ictm4a.domotica;

import java.sql.*;

public class DatabaseFunction {

    public String select(String query) {
        String MYSQL_URL = "jdbc:mysql://localhost:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=GMT";
        Connection con;
        Statement st;
        String result = "";

        try {
            con = DriverManager.getConnection(MYSQL_URL,"root","root");
            st = con.createStatement();
            ResultSet resultSet =st.executeQuery(query);

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    result = resultSet.getString(i);
                }
            }

        }
        catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
        }

        if(result.length() == 0) {
            result = "";
        }
            return result;

    }

    public void insert(String query){
        String MYSQL_URL = "jdbc:mysql://localhost:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=GMT";
        Connection con;
        Statement st;
        try {
            con = DriverManager.getConnection(MYSQL_URL,"root","root");
            st = con.createStatement();
            st.execute(query);
        }
        catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
        }
    }
}
