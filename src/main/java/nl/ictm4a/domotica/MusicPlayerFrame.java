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

    public MusicPlayerFrame(){

        JPanel panel = new JPanel(new GridBagLayout());

        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;

        //Select playlist label

        gbc.gridwidth = 3;
        jlSelectPlaylist = new JLabel("Select Playlist:");

        gbc.gridx=0;
        gbc.gridy=0;

        panel.add(jlSelectPlaylist, gbc);

        //Select playlist dropdown

        jcbSelectPlaylist = new JComboBox(createDummyPlayLists().toArray());

        jcbSelectPlaylist.setBounds(60, 32, 200, 50);

        gbc.fill =  GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx=0;
        gbc.gridy=1;

        panel.add(jcbSelectPlaylist, gbc);

        //Create playlist button

        jbCreatePlaylist = new JButton("+");

        jbCreatePlaylist.addActionListener(this);

        gbc.gridwidth = 1;
        gbc.gridx=2;
        gbc.gridy=1;

        panel.add(jbCreatePlaylist, gbc);

        //Song name label

        jlSongName = new JLabel("Darude - Sandstorm");

        gbc.gridwidth = 3;
        gbc.gridx=0;
        gbc.gridy=2;

        panel.add(jlSongName, gbc);

        //Previous song button

        jbPreviousSong = new JButton("<<");

        gbc.gridwidth = 1;
        gbc.gridx=0;
        gbc.gridy=3;

        panel.add(jbPreviousSong, gbc);

        //Play buttun

        jbPlay = new JButton(">");

        gbc.gridwidth = 2;
        gbc.gridx=1;
        gbc.gridy=3;

        jbPlay.addActionListener(this);

        panel.add(jbPlay, gbc);

        //Play buttun

        jbPause = new JButton("||");

        gbc.gridwidth = 2;
        gbc.gridx=1;
        gbc.gridy=3;

        jbPause.addActionListener(this);

        panel.add(jbPause, gbc);

        jbPause.setVisible(false);

        //Next song button

        jbNextSong = new JButton(">>");

        gbc.gridwidth = 1;
        gbc.gridx=3;
        gbc.gridy=3;

        panel.add(jbNextSong, gbc);

        //remaining time label

        jlSongRemainder = new JLabel("1:23 remaining");

        gbc.gridwidth = 3;
        gbc.gridx=0;
        gbc.gridy=4;

        panel.add(jlSongRemainder, gbc);

        setTitle("MusicPlayer");
        setSize(800, 600);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public ArrayList<String> createDummyPlayLists(){

        ArrayList<String> myPlayLists = new ArrayList<>();

        myPlayLists.add("Chill");
        myPlayLists.add("Party");
        myPlayLists.add("Study");
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
