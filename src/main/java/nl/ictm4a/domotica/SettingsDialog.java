package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends JDialog {

    public SettingsFrame(JFrame parent) {
        super(parent, true);
        setLayout(new FlowLayout(FlowLayout.LEFT));             // pulls all items to the left of the screen
        setTitle("Instellingen");
        setSize(300,200);
        setResizable(false);
        //setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);

        SettingsPanel settingsPanel = new SettingsPanel();
        add(settingsPanel);
    }
}
