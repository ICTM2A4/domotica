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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // wanneer moet de kachel aan
        jlHeatingQuestion = new JLabel("Wanneer moet de kachel aan?");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(jlHeatingQuestion, gbc);

        jlHeatingAnswerLeft = new JLabel("Beneden ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(jlHeatingAnswerLeft, gbc);

        jlHeatingInput = new JTextField("", 2);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(jlHeatingInput, gbc);

        jlHeatingAnswerRight = new JLabel(" graden Celsius.");
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(jlHeatingAnswerRight, gbc);

        // some dummy spacing
        jlDummy = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(jlDummy, gbc);

        jlDummy = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(jlDummy, gbc);

        // wanneer moet de verlichting aan
        jlLightingQuestion = new JLabel("Wanneer moet de verlichting aan?");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        add(jlLightingQuestion, gbc);

        jlLightingAnswerLeft = new JLabel("Beneden ");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(jlLightingAnswerLeft, gbc);

        jlLightingInput = new JTextField("", 2);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(jlLightingInput, gbc);

        jlLightingAnswerRight = new JLabel(" lichtsensor meting.");
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(jlLightingAnswerRight, gbc);

        // some dummy spacing
        jlDummy = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(jlDummy, gbc);

        // save
        jbSave = new JButton("Opslaan");
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(jbSave, gbc);
        jbSave.addActionListener(this);

        // cancel
        jbCancel = new JButton("Cancel");
        gbc.gridx = 2;
        gbc.gridy = 7;
        add(jbCancel, gbc);
        jbCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO add functions for the buttons
        settingsDialog.setVisible(false);
    }
}
