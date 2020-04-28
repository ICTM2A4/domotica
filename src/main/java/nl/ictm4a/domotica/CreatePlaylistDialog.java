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

    public CreatePlaylistDialog(JFrame frame){
        super(frame, true);
        setTitle("Create Playlist");
        setSize(360, 300);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        UIElement uiElement = new UIElement();
        JPanel modalPanel = uiElement.panel;
        add(modalPanel);
        jlPlaylistName = uiElement.addLabel("Naam afspeellijst:", 0, 0);
        jtfPlaylistName = uiElement.addTextField("", 10, 1, 0);
        jlAddSongs = uiElement.addLabel("Nummers toevoegen", 0, 1);
        bAddSongs = uiElement.addVerticalCheckboxContainer(createDummySongs(5), 0, 2);
        jbSavePlaylist = uiElement.addButton("opslaan", 0, 7);
        jbSavePlaylist.addActionListener(this);
        jbCancelPlaylist = uiElement.addButton("annuleren", 1, 7);
        jbCancelPlaylist.addActionListener(this);
        setVisible(true);
    }

    ArrayList<JCheckBox> createDummySongs(int ammount){
        ArrayList<JCheckBox> jCheckBoxes = new ArrayList<>();
        for(int i = 0; i <= ammount; i++){
            jCheckBoxes.add(new JCheckBox("Nummer " + i));
        }
        return jCheckBoxes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
}
