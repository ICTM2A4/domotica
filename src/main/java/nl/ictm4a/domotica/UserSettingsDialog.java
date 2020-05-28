package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;

/**
 * Is used for the user to set their settings for heating and lighting preferences
 * This is a JDialog and has to be closed before the user can use any other function of the application
 */
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
