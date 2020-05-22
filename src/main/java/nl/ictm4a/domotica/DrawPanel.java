package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {
    int panelWidth, panelHeight;

    public DrawPanel() {
        this.panelWidth = 700;
        this.panelHeight = 700;
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setVisible(true);
    }

    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        int x = this.getWidth() / 2;
        int y = this.getHeight() / 2;
        g.rotate(Math.toRadians(180.0), x, y);
        super.paintComponent(g);
        // x coordinates of vertices
        int xPolygon[] = { 10, 30, 40, 50, 110, 0, 140 };
        // y coordinates of vertices
        int yPolygon[] = { 140, 110, 50, 0, 40, 30, 10 };
        // number of vertices
        int numberofpoints = 7;
        // set the color of line drawn to blue
        g.setColor(Color.blue);
        // draw the polygon using drawPolygon function
        g.drawPolygon(xPolygon,yPolygon,numberofpoints);
        g.setColor(new Color(0, 10, 255,25));
        g.fillPolygon(xPolygon,yPolygon,numberofpoints);
    }

}
