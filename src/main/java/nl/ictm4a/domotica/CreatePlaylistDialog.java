package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreatePlaylistDialog extends JDialog implements ActionListener {
    JButton jbSavePlaylist, jbCancelPlaylist;
    JTextField jtfPlaylistName;
    JLabel jlPlaylistName, jlAddSongs;
    Box bAddSongs;
    ArrayList<Track> allTracks;
    DatabaseFunction dbs;
    User user;
    ArrayList<JCheckBox> jCheckBoxes;

    public CreatePlaylistDialog(JFrame frame, User user){
        super(frame, true);
        setTitle("Create Playlist");
        this.dbs = new DatabaseFunction();
        this.user = user;
        allTracks = dbs.selectAllTracks();
        setSize(400, 300);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        UIElement uiElement = new UIElement();
        JPanel modalPanel = uiElement.panel;
        add(modalPanel);
        jlPlaylistName = uiElement.addLabel("Naam afspeellijst:", 0, 0);
        jtfPlaylistName = uiElement.addTextField("", 10, 1, 0);
        jlAddSongs = uiElement.addLabel("Nummers toevoegen", 0, 1);
        bAddSongs = uiElement.addVerticalCheckboxContainer(getAllTracks(), 0, 2);
        jbSavePlaylist = uiElement.addButton("opslaan", 0, 7);
        jbSavePlaylist.addActionListener(this);
        jbCancelPlaylist = uiElement.addButton("annuleren", 1, 7);
        jbCancelPlaylist.addActionListener(this);
        setVisible(true);
    }

   private ArrayList<JCheckBox> getAllTracks(){
        jCheckBoxes = new ArrayList<>();

        for(Track track: allTracks){
            JCheckBox jCheckBox = new JCheckBox(track.getTrackId() + ": "+ track.getArtistName() + " - " +track.getTrackTitle());
            jCheckBoxes.add(jCheckBox);
        }

        return jCheckBoxes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(jbCancelPlaylist)){
            this.setVisible(false);
        }
        if(e.getSource().equals(jbSavePlaylist)){
            int trackIds[] = new int[allTracks.size()];
            int count = 0;

            for(Track track: allTracks){
                //insert tracklist and receive tracklist_id
                if(!jtfPlaylistName.getText().equals(""));

                    int tick = 0;
                    for(JCheckBox jCheckBox : jCheckBoxes){
                        if(jCheckBox.isSelected()){
                            trackIds[tick] = Character.getNumericValue(jCheckBox.getText().charAt(0));
                            count++;
                        }
                        tick++;
                    }
                }

            if(count != 0){

                int tracklistId = dbs.insertPlaylist(jtfPlaylistName.getText(), user.getUserID());


                //for each checkbox checked..
                for(int i = 0; i < trackIds.length; i++){
                    //insert tracklist_tracks
                    if(trackIds[i] != 0){
                        dbs.insertTracklistTrack(trackIds[i], tracklistId);
                    }
                }
            }
        }
        this.setVisible(false);
        }

    }

