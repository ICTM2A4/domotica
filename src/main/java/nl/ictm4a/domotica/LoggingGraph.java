package nl.ictm4a.domotica;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Information for the logging graph (temperature at this moment)
 */
public class LoggingGraph {
    private DatabaseFunction databaseFunction = new DatabaseFunction();
    private ArrayList<Double> tempValueArrayList = new ArrayList<>();
    private User user;

    /**
     * constructor
     * @param user Logging belongs to this user
     */
    public LoggingGraph(User user) {
        this.user = user;
    }

    /**
     * Puts the last 10 (if available) logging for temperature from the database, used to paint the graph
     * @return tempValueArrayList an arrayList with the last 10 (if available) logging for temperature
     */
    public ArrayList<Double> getTempValueArrayList() {
        this.tempValueArrayList = databaseFunction.selectRowLogging(2, user.getUserID());
        // if the arraylist doesnt have 10, just add some with an input of 0...
        if(tempValueArrayList.size() < 11) {
            int below = 10 - tempValueArrayList.size();
            for(int i = 0; i < below; i++) {
                tempValueArrayList.add(0.0);
            }
        }
        Collections.reverse(tempValueArrayList); // reverse so that the graph shows properly
        return tempValueArrayList;
    }

    /**
     * Gets the user's choice of when the heating should be turned on
     * @return heatValue from database which the user has set as a setting
     */
    public int getUserTempChoice() {
        return databaseFunction.selectUserChoice("heatValue", user.getUserID());
    }
}
