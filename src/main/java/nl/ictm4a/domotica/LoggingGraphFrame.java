package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;

public class LoggingGraphFrame extends JFrame {
    private int frameWidth = 240, frameHeight = 240;
    User user;

    public LoggingGraphFrame(User user) {
        this.user = user;
        setLayout(new FlowLayout());
        setSize(getFrameWidth(),getFrameHeight());
        setTitle("Logging");
        LoggingGraph loggingGraph = new LoggingGraph(user);
        LoggingGraphPanel loggingGraphPanel = new LoggingGraphPanel(getFrameWidth(), getFrameHeight(), loggingGraph);
        add(loggingGraphPanel);
        setVisible(true);
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

}
