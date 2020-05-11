package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;

public class UserSettingsDialog extends JDialog {

    public UserSettingsDialog(JFrame parent, User user) {
        super(parent, true);
        setLayout(new FlowLayout(FlowLayout.LEFT));             // pulls all items to the left of the screen
        setTitle("Instellingen");
        setSize(300,200);
        setResizable(false);

        UserSettingsPanel settingsPanel = new UserSettingsPanel(this, user);
        add(settingsPanel);
    }

}
