package nl.ictm4a.domotica;

import java.util.ArrayList;
import java.util.Collections;

public class LoggingGraph {
    private DatabaseFunction databaseFunction = new DatabaseFunction();
    private ArrayList<Double> tempValueArrayList = new ArrayList<>();
    User user;

    public LoggingGraph(User user) {
        this.user = user;
    }

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

    public int getUserTempChoice() {
        return databaseFunction.selectUserChoice("heatValue", user.getUserID());
    }
}
