package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;

public class LoggingGraphPanel extends JPanel {
    LoggingGraph loggingGraph;
    int panelWidth, panelHeight;

    public LoggingGraphPanel(int frameWidth, int frameHeight, LoggingGraph loggingGraph) {
        this.panelWidth = frameWidth-20;
        this.panelHeight = frameHeight-50;
        this.loggingGraph = loggingGraph;
        setPreferredSize(new Dimension(panelWidth,panelHeight));
        setVisible(true);

        // make a thread because it has to paint the logging
        Thread paintTemperatureLogging = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(1000); // pause before repaint
                    repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        paintTemperatureLogging.start();
    }

    public void paintComponent(Graphics graphics) {
        // rotate the panel with a 180 degrees so its upside down, this way it makes it easier to paint the line graph
        Graphics2D g = (Graphics2D) graphics;
        int x = this.getWidth() / 2;
        int y = this.getHeight() / 2;
        g.rotate(Math.toRadians(180.0), x, y);

        super.paintComponent(g);
        paintTemperatureLogging(g, x, y);
    }

    public void paintTemperatureLogging(Graphics2D g, int x, int y) {
        //setBackground(Color.RED);
        //50
        //40
        //30
        //20
        //10
        //0
        //-10
        //-20
        //-30
        //---- 10 ---- 9 ---- 8 ---- 7 ---- 6 ---- 5 ---- 4 ---- 3 ---- 2 ---- 1 //

        // draws the graph
        g.setColor(Color.LIGHT_GRAY);
        int multiplySize = 2, totaleWidth = 10*multiplySize, totaleHeight = 10*multiplySize;
        int xDraw = 1, yDraw, width = totaleWidth, height = totaleHeight;
        for(int i = 0; i < 10; i++) {
            g.drawLine(xDraw,0,xDraw,8 * height);
            xDraw += width;
        }
        xDraw = 1; yDraw = 1; width = totaleWidth; height = totaleHeight;
        for(int i = 0; i < 9; i++) {
            g.drawLine(xDraw,yDraw,9 * width,yDraw);
            yDraw += height;
        }
        width = totaleWidth;

        // draws the values on the graph
        g.setColor(Color.BLACK);
        double firstValue = loggingGraph.getTempValueArrayList().get(0);
        int widthNmbr = 9;
        for(int i = 1; i < loggingGraph.getTempValueArrayList().size(); i++) {
            double secondValue = loggingGraph.getTempValueArrayList().get(i);
            g.drawLine(widthNmbr * width,(int)(firstValue + 30)*multiplySize +1,(widthNmbr-1) * width,(int)(secondValue + 30)*multiplySize +1);
            firstValue = secondValue;
            widthNmbr--;
        }

        // users choice when heating turns on
        int userChoice = loggingGraph.getUserTempChoice();
        g.setColor(Color.BLUE);
        g.drawLine(9 * width,(userChoice + 30)*multiplySize +1,1,(userChoice + 30)*multiplySize +1);

        // displays text next to the graph
        g.setColor(Color.BLACK);
        g.rotate(Math.toRadians(180.0), x, y); // woops got to rotate it back to normal to display the degree as text
        int displayTemp = -30;
        int displayExtra = 0;
        for(int i = 1; i < 10; i++) {
            g.drawString(String.valueOf(displayTemp), width, panelHeight - displayExtra);
            displayExtra = (height*i)-3;
            displayTemp += 10;
        }
    }
}
