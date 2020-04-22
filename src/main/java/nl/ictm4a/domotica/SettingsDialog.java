package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {

    public SettingsDialog(JFrame parent) {
        super(parent, true);
        setLayout(new FlowLayout(FlowLayout.LEFT));             // pulls all items to the left of the screen
        setTitle("Instellingen");
        setSize(300,200);
        setResizable(false);

        SettingsPanel settingsPanel = new SettingsPanel(this);
        add(settingsPanel);
    }

}
