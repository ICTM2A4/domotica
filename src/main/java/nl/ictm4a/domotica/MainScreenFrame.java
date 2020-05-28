package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;

public class MainScreenFrame extends JFrame {
    RaspberryPiListener raspberryPiListener;

    /**
     * constructor, in here all the panels are added
     * @param user information from the user
     */
    public MainScreenFrame(User user) {
        this.raspberryPiListener = new RaspberryPiListener(user);

        setLayout(new FlowLayout(FlowLayout.LEFT));             // pulls all items to the left of the screen
        setTitle("Centrale PC-applicatie - " + user.getUserName());
        setSize(400,400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainScreenPanel mainScreenPanel = new MainScreenPanel(user);
        MusicPlayerPanel musicPlayerPanel = new MusicPlayerPanel(raspberryPiListener, this, user);
        add(mainScreenPanel);
        LoggingGraph loggingGraph = new LoggingGraph(user);
        LoggingGraphPanel loggingGraphPanel = new LoggingGraphPanel(250, 250, loggingGraph);
        add(loggingGraphPanel);
        add(musicPlayerPanel);


    }
}
