package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;

public class MainScreenFrame extends JFrame {
    RaspberryPiListener raspberryPiListener;

    public MainScreenFrame(User user) {
        this.raspberryPiListener = new RaspberryPiListener(user);

        setLayout(new FlowLayout(FlowLayout.LEFT));             // pulls all items to the left of the screen
        setTitle("Centrale PC-applicatie - " + user.getUserName());
        setSize(420,250);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainScreenPanel mainScreenPanel = new MainScreenPanel(user);
        MusicPlayerPanel musicPlayerPanel = new MusicPlayerPanel(raspberryPiListener, this);
        add(mainScreenPanel);
        add(musicPlayerPanel);
    }
}
