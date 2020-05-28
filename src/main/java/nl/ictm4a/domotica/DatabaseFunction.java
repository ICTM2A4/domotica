package nl.ictm4a.domotica;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseFunction extends JFrame{
    private String hostName = "jdbc:mysql://localhost:3306/", dbName = "domotica", dateTimeCode = "?useLegacyDatetimeCode=false&serverTimezone=GMT", dbUserName = "root", dbPassword = "", MYSQL_URL = hostName + dbName + dateTimeCode;
    private HashFunction hashFunction = new HashFunction();
    private Connection con;
    private ResultSet rs = null;
    private PreparedStatement ps = null;
    private ResultSetMetaData rsmd = null;




    public void insertLogging(String table, String column1, String column2, String column3, String column4, String column5, int value1, double value2, String value3, int value4, int value5) {
        try {
            con = DriverManager.getConnection(MYSQL_URL,dbUserName,dbPassword);
            ps = con.prepareStatement("INSERT INTO "+table+" ("+column1+", "+column2+", "+column3+", "+column4+", "+column5+") VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setInt(index++, value1);
            ps.setDouble(index++, value2);
            ps.setString(index++, value3);
            ps.setInt(index++, value4);
            ps.setInt(index++, value5);
            ps.executeUpdate();
            con.close();
        } catch(SQLException ignored) {  }
    }

    public void insertLogging(String table, String column1, String column2, String column3, String column4, int value1, double value2, String value3, int value4) {
        try {
            con = DriverManager.getConnection(MYSQL_URL,dbUserName,dbPassword);
            ps = con.prepareStatement("INSERT INTO "+table+" ("+column1+", "+column2+", "+column3+", "+column4+") VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setInt(index++, value1);
            ps.setDouble(index++, value2);
            ps.setString(index++, value3);
            ps.setInt(index++, value4);
            ps.executeUpdate();
            con.close();
        } catch(SQLException ignored) { }
    }


    public void updateUserSetting(int ldrValue, int heatValue, int userID) {
        try {
            con = DriverManager.getConnection(MYSQL_URL,dbUserName,dbPassword);
            ps = con.prepareStatement("UPDATE usersetting SET ldrValue = ?, heatValue = ? WHERE user_id = ?", PreparedStatement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setInt(index++, ldrValue);
            ps.setInt(index++, heatValue);
            ps.setInt(index++, userID);
            ps.executeUpdate();
            con.close();
        } catch(SQLException ignored) { }
    }

    public String getCurrentDateTime(){
        java.util.Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
        return format.format(date);
    }


    public int selectUserChoice(String columnName, int userID) {
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);
            ps = con.prepareStatement("SELECT "+columnName+" FROM usersetting WHERE user_id = ?");
            int index = 1;
            ps.setInt(index++, userID);
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            if(rsmd.getColumnCount() > 0) {
                if (rs.next()) {
                    return rs.getInt(1); // only need to return 1
                }
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insertNewUser(String insertUserName, String insertUserPassword) {
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);
            ps = con.prepareStatement("INSERT INTO user (username, password) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setString(index++, insertUserName);
            ps.setString(index++, hashFunction.stringToHex(insertUserPassword));
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1); // returns the last inserted id
            }
            rs.close();
            con.close();
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insertNewUserSetting(int userID) {
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);
            ps = con.prepareStatement("INSERT INTO usersetting (user_id) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setInt(index++, userID);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1); // returns the last inserted id
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insertPlaylist(String title, int userId){
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);
            ps = con.prepareStatement("INSERT INTO tracklist (title, user_id) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setString(index++, title);
            ps.setInt(index++, userId);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1); // returns the last inserted id
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertTracklistTrack(int trackId, int tracklistId) {
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);
            ps = con.prepareStatement("INSERT INTO track_tracklist (track_id, tracklist_id) VALUES (?, ?)");
            int index = 1;
            ps.setInt(index++, trackId);
            ps.setInt(index, tracklistId);
            ps.executeUpdate();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String selectUserPassword(String userName) {
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);
            ps = con.prepareStatement("SELECT password FROM user WHERE username = ?");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            if(rsmd.getColumnCount() > 0) {
                if(rs.next()) {
                    return rs.getString(1); // only need to return 1
                }
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int selectUserID(String username, String password) {
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);
            ps = con.prepareStatement("SELECT user_id FROM user WHERE username = ? AND password = ?");
            int index = 1;
            ps.setString(index++, username);
            ps.setString(index++, password);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            if(rsmd.getColumnCount() > 0) {
                if(rs.next()) {
                    return rs.getInt(1); // only need to return 1
                }
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Double> selectRowLogging(int sensorID, int userID) {
        ArrayList<Double> resultArrayList = new ArrayList<>();
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);
            ps = con.prepareStatement("SELECT value FROM logging WHERE sensor_id = ? AND user_id = ? ORDER BY logging_id DESC LIMIT 10");
            int index = 1;
            ps.setInt(index++, sensorID);
            ps.setInt(index++, userID);
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            while (rs.next()) {
                resultArrayList.add(rs.getDouble("value"));
            }
            rs.close();
            con.close();
        } catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Er is een probleem met de database. Probeer het later opnieuw"); // TODO: jlabel error reporting instead of dialog
        }
        return resultArrayList;
    }

    public ArrayList<Tracklist> selectPlaylists(int userID){
        ArrayList<Tracklist> resultArrayList = new ArrayList<>();
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);

            ps = con.prepareStatement("SELECT * FROM tracklist WHERE user_id = ?");
            int index = 1;
            ps.setInt(index++, userID);
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            while (rs.next()) {
                resultArrayList.add(new Tracklist(rs.getInt("tracklist_id"), rs.getString("title"), rs.getInt("user_id")));
            }
            rs.close();
            con.close();
        } catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Er is een probleem met de database. Probeer het later opnieuw"); // TODO: jlabel error reporting instead of dialog
        }
        return resultArrayList;

    }

    public ArrayList<Track> selectAllTracks(){
        ArrayList<Track> resultArrayList = new ArrayList<>();
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);

            ps = con.prepareStatement("SELECT * FROM track");
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            while (rs.next()) {
                resultArrayList.add(new Track(rs.getInt("track_id"), rs.getString("artist_name"), rs.getString("title"), rs.getDouble("length"), rs.getString("url")));
            }
            rs.close();
            con.close();
        } catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
        }

        return resultArrayList;

    }


    public ArrayList<Track> selectTracks(int tracklistId){
        ArrayList<Track> resultArrayList = new ArrayList<>();
        try {
            con = DriverManager.getConnection(MYSQL_URL, dbUserName, dbPassword);

            ps = con.prepareStatement("SELECT * FROM track WHERE track_id IN(SELECT track_id FROM track_tracklist WHERE tracklist_id = ?)");
            int index = 1;
            ps.setInt(index++, tracklistId);
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            while (rs.next()) {
                resultArrayList.add(new Track(rs.getInt("track_id"), rs.getString("artist_name"), rs.getString("title"), rs.getDouble("length"), rs.getString("url")));
            }
            rs.close();
            con.close();
        } catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
        }

            return resultArrayList;

    }

}

