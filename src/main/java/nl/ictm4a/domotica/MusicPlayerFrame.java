package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MusicPlayerFrame extends JFrame implements ActionListener {
    JButton jbCreatePlaylist, jbPreviousSong, jbNextSong, jbPlay, jbPause;
    JComboBox jcbSelectPlaylist;
    JLabel jlSelectPlaylist, jlSongName, jlSongRemainder;
    CreatePlaylistDialog createPlaylistDialog;
    UIElement uiElement;

    public MusicPlayerFrame(){
        setTitle("Muziekspeler");
        setSize(250, 150);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        UIElement uiElement = new UIElement();
        JPanel panel = uiElement.panel;
        add(panel);
        GridBagConstraints gbc = uiElement.setupGbc();
        jlSelectPlaylist = uiElement.addLabel("Afspeellijst selecteren:", 3, 0, 0); //Select playlist label
        jcbSelectPlaylist = uiElement.addComboBox( createDummyPlayLists().toArray(), 0, 1);//Select playlist dropdown
        jbCreatePlaylist = uiElement.addButton("+", 1, 1); //Create playlist button
        jbCreatePlaylist.addActionListener(this);//triggers actionPerformed for pause button
        jlSongName = uiElement.addLabel("Darude - Sandstorm", 3, 0,2);//Song name label
        jbPreviousSong = uiElement.addButton("<<", 0, 3);//Previous Song button
        jbPlay = uiElement.addButton(">",2, 1, 3);//Play button
        jbPlay.addActionListener(this);//triggers actionPerformed for play button below
        jbPause = uiElement.addButton("||",2, 1, 3);//Pause button
        jbPause.addActionListener(this);//triggers actionPerformed for pause button below
        jbPause.setVisible(false);//pause button starts hidden to simulate toggle
        jbNextSong = uiElement.addButton(">>", 3, 3);//Next Song button
        jlSongRemainder = uiElement.addLabel("1:23 resterend", 3, 0, 4);//remaining time label
    }

    public ArrayList<String> createDummyPlayLists(){
        ArrayList<String> myPlayLists = new ArrayList<>();
        myPlayLists.add("Chill");
        myPlayLists.add("Study");
        myPlayLists.add("Party");
        myPlayLists.add("Rock");
        return myPlayLists;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(jbCreatePlaylist)) {
            createPlaylistDialog = new CreatePlaylistDialog(this);
        }
        if(e.getSource().equals(jbPlay)){
            jbPlay.setVisible(false);
            jbPause.setVisible(true);
        }
        if(e.getSource().equals(jbPause)){
            jbPlay.setVisible(true);
            jbPause.setVisible(false);
        }
    }
}