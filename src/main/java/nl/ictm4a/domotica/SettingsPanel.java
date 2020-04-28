package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel implements ActionListener {
    private JLabel jlHeatingQuestion, jlHeatingAnswerLeft, jlHeatingAnswerRight, jlLightingQuestion, jlLightingAnswerLeft, jlLightingAnswerRight, jlDummy;
    private JTextField jlHeatingInput, jlLightingInput;
    private JButton jbSave, jbCancel;
    SettingsDialog settingsDialog;

    public SettingsPanel(SettingsDialog settingsDialog) {
        this.settingsDialog = settingsDialog;
        setLayout(new GridBagLayout());
        UIElement uiElement = new UIElement();
        uiElement.alterPanel(this);
        jlHeatingQuestion = uiElement.addLabel("Wanneer moet de kachel aan?", 3, 0, 0);
        jlHeatingAnswerLeft = uiElement.addLabel("Beneden", 1, 0, 1);
        jlHeatingInput = uiElement.addTextField(2,  1, 1);
        jlHeatingAnswerRight = uiElement.addLabel(" graden Celsius.", 2, 1);
        jlDummy = uiElement.addLabel(" ", 0, 2);//quick and dirty whitespace
        jlDummy = uiElement.addLabel(" ", 0, 3);//quick and dirty whitespace
        jlLightingQuestion = uiElement.addLabel("Wanneer moet de verlichting aan?", 3, 0, 4);// wanneer moet de verlichting aan
        jlLightingAnswerLeft = uiElement.addLabel("Beneden ", 3, 0, 5);// wanneer moet de verlichting aan
        jlLightingInput = uiElement.addTextField(2, 1, 5);
        jlLightingAnswerRight = uiElement.addLabel(" lichtsensor meting.", 2, 5);
        jlDummy = uiElement.addLabel(" ", 0, 3);//quick and dirty whitespace
        jbSave = uiElement.addButton("Opslaan", 0, 7);
        jbSave.addActionListener(this);
        jbCancel = uiElement.addButton("Cancel", 2, 7);
        jbCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO add functions for the buttons
        settingsDialog.setVisible(false);
    }
}
