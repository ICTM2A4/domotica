package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MusicPlayerPanel extends JPanel implements ActionListener {
    JButton jbCreatePlaylist, jbPreviousSong, jbNextSong, jbPlay, jbPause;
    JComboBox jcbSelectPlaylist;
    JLabel jlSelectPlaylist, jlSongName, jlSongRemainder;
    CreatePlaylistDialog createPlaylistDialog;
    UIElement uiElement;
    RaspberryPiListener raspberryPiListener;
    MainScreenFrame mainScreenFrame;

    public MusicPlayerPanel(RaspberryPiListener raspberryPiListener, MainScreenFrame mainScreenFrame){
        this.raspberryPiListener = raspberryPiListener;
        this.mainScreenFrame = mainScreenFrame;
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
        jbPreviousSong.addActionListener(this);//triggers actionPerformed for previous song button below
        jbPlay = uiElement.addButton(">",2, 1, 3);//Play button
        jbPlay.addActionListener(this);//triggers actionPerformed for play button below
        jbPause = uiElement.addButton("||",2, 1, 3);//Pause button
        jbPause.addActionListener(this);//triggers actionPerformed for pause button below
        jbPause.setVisible(false);//pause button starts hidden to simulate toggle
        jbNextSong = uiElement.addButton(">>", 3, 3);//Next Song button
        jbNextSong.addActionListener(this);//triggers actionPerformed for next song button below
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
            createPlaylistDialog = new CreatePlaylistDialog(this.mainScreenFrame);
        }
        if(e.getSource().equals(jbPlay)){
            jbPlay.setVisible(false);
            //sends a message to the raspberry pi: check RaspberyPiListener for more info
            //both presses send the message "pause" triggering a toggle on the raspberry pi
            raspberryPiListener.sendMessage("pause");
            jbPause.setVisible(true);
        }
        if(e.getSource().equals(jbPreviousSong)){
            //sends a message to the raspberry pi: check RaspberyPiListener for more info
            raspberryPiListener.sendMessage("previous");
        }
        if(e.getSource().equals(jbNextSong)){
            //sends a message to the raspberry pi: check RaspberyPiListener for more info
            raspberryPiListener.sendMessage("next");
        }
        if(e.getSource().equals(jbPause)){
            jbPlay.setVisible(true);
            //sends a message to the raspberry pi: check RaspberyPiListener for more info
            raspberryPiListener.sendMessage("pause");
            jbPause.setVisible(false);
        }
        if(e.getSource().equals(jbPause)){
            jbPlay.setVisible(true);
            //sends a message to the raspberry pi: check RaspberyPiListener for more info
            raspberryPiListener.sendMessage("pause");
            jbPause.setVisible(false);
        }

    }
}