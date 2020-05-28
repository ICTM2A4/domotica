package nl.ictm4a.domotica;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MusicPlayerPanel extends JPanel implements ActionListener {
    JButton jbCreatePlaylist, jbPreviousSong, jbNextSong, jbPlay, jbPause;
    JComboBox jcbSelectPlaylist;
    JLabel jlSelectPlaylist, jlSongName, jlSongRemainder;
    CreatePlaylistDialog createPlaylistDialog;
    UIElement uiElement;
    RaspberryPiListener raspberryPiListener;
    MainScreenFrame mainScreenFrame;
    User user;
    double songLength;
    Tracklist tracklist;
    Track track;
    DatabaseFunction databaseFunction;
    int currentIndex = 0;
    Timer timer;
    long interval = 1000;
    boolean started = false;
    boolean waiting = false;
    Thread thread;


    public MusicPlayerPanel(RaspberryPiListener raspberryPiListener, MainScreenFrame mainScreenFrame, User user){
        this.user = user;
        this.raspberryPiListener = raspberryPiListener;
        this.databaseFunction = new DatabaseFunction();
        this.mainScreenFrame = mainScreenFrame;
        this.tracklist = user.getUserTracklists().get(0);
        this.track = tracklist.getTrackListTracks().get(0);
        this.songLength = this.track.getTrackLength();
        setSize(250, 150);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        UIElement uiElement = new UIElement();
        JPanel panel = uiElement.panel;
        add(panel);
        GridBagConstraints gbc = uiElement.setupGbc();
        jlSelectPlaylist = uiElement.addLabel("Afspeellijst selecteren:", 3, 0, 0); //Select playlist label
        jcbSelectPlaylist = uiElement.addComboBox( createDummyPlayLists().toArray(), 0, 1);//Select playlist dropdown
        jcbSelectPlaylist.addActionListener(this);
        jbCreatePlaylist = uiElement.addButton("+", 1, 1); //Create playlist button
        jbCreatePlaylist.addActionListener(this);//triggers actionPerformed for pause button
        jlSongName = uiElement.addLabel(track.getArtistName() + " - " + track.getTrackTitle(), 3, 0,2);//Song name label
        jbPreviousSong = uiElement.addButton("<<", 0, 3);//Previous Song button
        jbPreviousSong.addActionListener(this);//triggers actionPerformed for previous song button below
        jbPlay = uiElement.addButton(">",2, 1, 3);//Play button
        jbPlay.addActionListener(this);//triggers actionPerformed for play button below
        jbPause = uiElement.addButton("||",2, 1, 3);//Pause button
        jbPause.addActionListener(this);//triggers actionPerformed for pause button below
        jbPause.setVisible(false);//pause button starts hidden to simulate toggle
        jbNextSong = uiElement.addButton(">>", 3, 3);//Next Song button
        jbNextSong.addActionListener(this);//triggers actionPerformed for next song button below
        jlSongRemainder = uiElement.addLabel(track.getTrackLength() + " resterend", 3, 0, 4);//remaining time label
        timer = new Timer(1000, setSongTimer);

    }

    public ArrayList<String> createDummyPlayLists(){
       ArrayList<String> result = new ArrayList<>();

       for(Tracklist tracklist : user.getUserTracklists()){
           result.add(tracklist.getName());
       }

       return result;
    }

    ActionListener setSongTimer = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            if (songLength % 1 == 0) {
                songLength -= 0.41;
            } else {
                songLength -= 0.01;
            }


            DecimalFormat value = new DecimalFormat("#.##");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            songLength = Double.parseDouble(value.format(songLength));

                if (Double.toString(songLength).length() < 4) {
                    jlSongRemainder.setText((songLength + "0 resterend").replaceAll("\\.", "\\:"));
                } else if (Double.toString(songLength).length() < 2) {
                    jlSongRemainder.setText((songLength + "00 resterend").replaceAll("\\.", "\\:"));
                } else {
                    jlSongRemainder.setText((Double.toString(songLength) + " resterend").replaceAll("\\.", "\\:"));
                }





            }
    };

    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(jbCreatePlaylist)) {
            createPlaylistDialog = new CreatePlaylistDialog(this.mainScreenFrame, user);
        }
        if(e.getSource().equals(jbPlay)){
            jbPlay.setVisible(false);
            jbPause.setVisible(true);
            //sends a message to the raspberry pi: check RaspberyPiListener for more info
            //both presses send the message "pause" triggering a toggle on the raspberry pi
            raspberryPiListener.sendMessage("pause," + track.getUrl());
            timer.start();
            databaseFunction.insertLogging("logging","sensor_id","value","datetime","status", "user_id",6, 0, databaseFunction.getCurrentDateTime(),1, user.getUserID());
        }
        if(e.getSource().equals(jbPreviousSong)){

            //sends a message to the raspberry pi: check RaspberyPiListener for more info


            if(this.currentIndex<this.tracklist.getTrackListTracks().size() && this.currentIndex>0){
            this.currentIndex--;
            this.track = this.tracklist.getTrackListTracks().get(this.currentIndex);
            raspberryPiListener.sendMessage("previous," + track.getUrl());
            this.songLength = track.getTrackLength();
            jlSongName.setText(track.getArtistName() + " - " + track.getTrackTitle());
                databaseFunction.insertLogging("logging","sensor_id","value","datetime","status", "user_id",5, 0, databaseFunction.getCurrentDateTime(),0, user.getUserID());
            }
        }
        if(e.getSource().equals(jbNextSong)){
            if(this.currentIndex<this.tracklist.getTrackListTracks().size()-1 && this.currentIndex>=0){
            this.currentIndex++;
            this.track = this.tracklist.getTrackListTracks().get(this.currentIndex);
            raspberryPiListener.sendMessage("next," + track.getUrl());
            this.songLength = track.getTrackLength();
            jlSongName.setText(track.getArtistName() + " - " + track.getTrackTitle());
            databaseFunction.insertLogging("logging","sensor_id","value","datetime","status", "user_id",5, 0, databaseFunction.getCurrentDateTime(),1, user.getUserID());
            }
        }
        if(e.getSource().equals(jbPause)){
            jbPlay.setVisible(true);
            //sends a message to the raspberry pi: check RaspberyPiListener for more info
            raspberryPiListener.sendMessage("pause," + track.getUrl());
            jbPause.setVisible(false);
            timer.stop();
            databaseFunction.insertLogging("logging","sensor_id","value","datetime","status", "user_id",6, 0, databaseFunction.getCurrentDateTime(),1, user.getUserID());
        }
        if(e.getSource().equals(jcbSelectPlaylist)){
            for(Tracklist tracklist : user.getUserTracklists()){
                if(tracklist.getName().equals(jcbSelectPlaylist.getSelectedItem())){
                    this.tracklist = tracklist;
                    this.track = this.tracklist.getTrackListTracks().get(0);
                    this.songLength = track.getTrackLength();
                    jlSongName.setText(track.getArtistName() + " - " + track.getTrackTitle());
                    this.currentIndex = 0;
                }
            }
        }

    }
}