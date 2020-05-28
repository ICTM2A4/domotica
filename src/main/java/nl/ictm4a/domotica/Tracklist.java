package nl.ictm4a.domotica;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class Tracklist {

    private int tracklistId;
    private String name;
    private int userId;
    private ArrayList<Track> trackListTracks;
    DatabaseFunction databaseFunction;

    public Tracklist(int tracklistId, String name, int userId){
        this.tracklistId = tracklistId;
        this.name = name;
        this.userId = userId;
        databaseFunction = new DatabaseFunction();
        this.trackListTracks = databaseFunction.selectTracks(this.tracklistId);
    }

    public int getTracklistId(){
        return tracklistId;
    }

    public String getName(){
        return name;
    }

    public int getUserId(){
        return userId;
    }

    public ArrayList<Track> getTrackListTracks(){
        return trackListTracks;
    }

}
