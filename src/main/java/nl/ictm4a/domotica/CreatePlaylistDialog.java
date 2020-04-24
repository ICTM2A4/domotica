package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePlaylistDialog extends JDialog implements ActionListener {

    JButton jbSavePlaylist, jbCancelPlaylist;
    JTextField jtfPlaylistName;
    JLabel jlPlaylistName, jlAddSongs;
    JCheckBoxMenuItem jcbmiSong1, jcbmiSong2, jcbmiSong3, jcbmiSong4, jcbmiSong5;

    public CreatePlaylistDialog(JFrame frame){
        super(frame, true);

        JPanel modalPanel = new JPanel(new GridBagLayout());

        add(modalPanel);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;

        gbc.gridx = 0;
        gbc.gridy = 0;

        jlPlaylistName = new JLabel("Naam afspeellijst:");

        modalPanel.add(jlPlaylistName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;

        jtfPlaylistName = new JTextField(10);

        modalPanel.add(jtfPlaylistName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        jlAddSongs = new JLabel("Nummers toevoegen");

        modalPanel.add(jlAddSongs, gbc);

        jcbmiSong1 = new JCheckBoxMenuItem("Nummer 1");
        jcbmiSong2 = new JCheckBoxMenuItem("Nummer 2");
        jcbmiSong3 = new JCheckBoxMenuItem("Nummer 3");
        jcbmiSong4 = new JCheckBoxMenuItem("Nummer 4");
        jcbmiSong5 = new JCheckBoxMenuItem("Nummer 5");

        gbc.gridx = 0;
        gbc.gridy = 2;
        modalPanel.add(jcbmiSong1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        modalPanel.add(jcbmiSong2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        modalPanel.add(jcbmiSong3, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        modalPanel.add(jcbmiSong4, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        modalPanel.add(jcbmiSong5, gbc);


        jbSavePlaylist = new JButton("save");
        gbc.gridx = 0;
        gbc.gridy = 7;
        jbSavePlaylist.addActionListener(this);
        modalPanel.add(jbSavePlaylist, gbc);

        jbCancelPlaylist = new JButton("cancel");
        gbc.gridx = 1;
        gbc.gridy = 7;
        jbCancelPlaylist.addActionListener(this);
        modalPanel.add(jbCancelPlaylist, gbc);

        setTitle("Create Playlist");
        setSize(360, 300);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.setVisible(false);

    }
}
