package nl.ictm4a.domotica;

public class Track {

    private int trackId;
    private String artistName;
    private String trackTitle;
    private double trackLength;
    private String url;

    public Track(int trackId, String artistName, String trackTitle, double trackLength, String url){
        this.artistName = artistName;
        this.trackId = trackId;
        this.trackTitle = trackTitle;
        this.trackLength = trackLength;
        this.url = url;
    }

    public int getTrackId(){
        return trackId;
    }

    public String getArtistName(){
        return artistName;
    }

    public String getTrackTitle(){
        return trackTitle;
    }

    public double getTrackLength(){
        return trackLength;
    }

    public String getUrl(){
        return url;
    }
}
