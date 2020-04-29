package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;

public class MainScreenFrame extends JFrame {
    public MainScreenFrame() {
        setLayout(new FlowLayout(FlowLayout.LEFT));             // pulls all items to the left of the screen
        setTitle("Centrale PC-applicatie - <gebruikersnaam>");
        setSize(420,250);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainScreenPanel mainScreenPanel = new MainScreenPanel();
        add(mainScreenPanel);
    }

/*    public static void main(String[] args) {
        MainScreenFrame mainScreenFrame = new MainScreenFrame();
        mainScreenFrame.setVisible(true);
    }*/

}
