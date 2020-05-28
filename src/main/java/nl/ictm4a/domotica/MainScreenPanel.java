package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenPanel extends JPanel implements ActionListener {
    public static JLabel jlTemperature, jlAirPressure, jlAirMoisture, jlHeating, jlLighting, jlDummy;
    private int jlTemperatureValue, jlAirPressureValue, jlAirMoistureValue;
    public static boolean jlHeatingStatus, jlLightingStatus;
    private JButton jbSettings;
    private MainScreenFrame mainScreenFrame;
    private User user;
    private MusicPlayerPanel musicPlayerPanel;
    private ArduinoListener arduinoListener;
    private RaspberryPiListener raspberryPiListener;

    /**
     * creates a frame and puts in all the panels
     * @param user user information
     */
    public MainScreenPanel(User user) {
        this.user = user;
        this.arduinoListener = new ArduinoListener(user, raspberryPiListener);
        setLayout(new GridBagLayout());
        UIElement uiElement = new UIElement();
        uiElement.alterPanel(this);
        jlTemperature = uiElement.addLabel("Temperatuur: " + jlTemperatureValue, 0, 0);//sensor values
        jlAirPressure = uiElement.addLabel("Luchtdruk: " + jlAirPressureValue, 0, 1);//sensor values
        jlAirMoisture = uiElement.addLabel("Luchtvochtigheid: " + jlAirMoistureValue, 0, 2);//sensor values
        jlDummy = uiElement.addLabel(" ", 0, 3);//quick and dirty whitespace
        jlDummy = uiElement.addLabel(" ", 0, 4);//quick and dirty whitespace
        jlHeating = uiElement.addLabel("Verwarming: " + getHeatingStatus(), 0, 5);// status actuator (heat)
        jlLighting = uiElement.addLabel("Verlichting: " + getLightingStatus(), 0, 6);// status actuator (lighting)
        jlDummy = uiElement.addLabel(" ", 0, 7);//quick and dirty whitespace
        jbSettings = uiElement.addButton("Instellingen", 0, 8);//settings button
        jbSettings.addActionListener(this); //actionlistener
        jlDummy = uiElement.addLabel(" ", 0, 9);//quick and dirty whitespace
    }

    /**
     *
     * @return aan or uit, on or off, is the lighting on?
     */
    public static String getLightingStatus() {
        return (jlLightingStatus) ? "aan" : "uit";
    }

    /**
     *
     * @return aan or uit, on or off, is the heating on?
     */
    public static String getHeatingStatus() {
        return (jlHeatingStatus) ? "aan" : "uit";
    }

    /**
     * actionperformed on buttons
     * @param e actionevent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbSettings) {
            UserSettingsDialog userSettingsDialog = new UserSettingsDialog(mainScreenFrame, user);
            userSettingsDialog.setVisible(true);
        }
    }
}
