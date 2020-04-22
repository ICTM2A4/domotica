package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenPanel extends JPanel implements ActionListener {
    private JLabel jlTemperature, jlAirPressure, jlAirMoisture, jlHeating, jlLighting, jlDummy;
    private int jlTemperatureValue, jlAirPressureValue, jlAirMoistureValue;
    private boolean jlHeatingStatus, jlLightingStatus;
    private JButton jbSettings, jbMusicPlayer;
    MainScreenFrame mainScreenFrame;

    public MainScreenPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // measured values
        jlTemperature = new JLabel("Temperatuur: " + jlTemperatureValue);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(jlTemperature, gbc);

        jlAirPressure = new JLabel("Luchdruk: " + jlAirPressureValue);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(jlAirPressure, gbc);

        jlAirMoisture = new JLabel("Luchtvochtigheid: " + jlAirMoistureValue);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(jlAirMoisture, gbc);

        // some dummy spacing
        jlDummy = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(jlDummy, gbc);

        jlDummy = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(jlDummy, gbc);

        // status actuators (heat and lighting)
        jlHeating = new JLabel("Verwarming: " + jlHeatingStatus);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(jlHeating, gbc);

        jlLighting = new JLabel("Verlichting: " + jlLightingStatus);
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(jlLighting, gbc);

        // some dummy spacing
        jlDummy = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(jlDummy, gbc);

        // add buttons
        jbSettings = new JButton("Instellingen");
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(jbSettings, gbc);
        jbSettings.addActionListener(this);

        // some dummy spacing
        jlDummy = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(jlDummy, gbc);

        jbMusicPlayer = new JButton("Muziekspeler");
        gbc.gridx = 0;
        gbc.gridy = 10;
        add(jbMusicPlayer, gbc);
        jbMusicPlayer.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbSettings) {
            SettingsDialog settingsDialog = new SettingsDialog(mainScreenFrame);
            settingsDialog.setVisible(true);
        }
    }
}
