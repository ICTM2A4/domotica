package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserSettingsPanel extends JPanel implements ActionListener {
    private JLabel jlHeatingQuestion, jlHeatingAnswerLeft, jlHeatingAnswerRight, jlLightingQuestion, jlLightingAnswerLeft, jlLightingAnswerRight, jlDummy, jlError;
    private JTextField jtfHeatingInput, jtfLightingInput;
    private JButton jbSave, jbCancel;
    UserSettingsDialog userSettingsDialog;
    User user;

    public UserSettingsPanel(UserSettingsDialog userSettingsDialog, User user) {
        this.userSettingsDialog = userSettingsDialog;
        this.user = user;
        setLayout(new GridBagLayout());
        UIElement uiElement = new UIElement();
        uiElement.alterPanel(this);
        jlHeatingQuestion = uiElement.addLabel("Wanneer moet de kachel aan?", 3, 0, 0);
        jlHeatingAnswerLeft = uiElement.addLabel("Beneden", 1, 0, 1);
        jtfHeatingInput = uiElement.addTextField(String.valueOf(user.getHeatingInputText()), 4,  1, 1);
        jlHeatingAnswerRight = uiElement.addLabel(" graden Celsius.", 2, 1);
        jlDummy = uiElement.addLabel(" ", 0, 2);//quick and dirty whitespace
        jlDummy = uiElement.addLabel(" ", 0, 3);//quick and dirty whitespace
        jlLightingQuestion = uiElement.addLabel("Wanneer moet de verlichting aan?", 3, 0, 3);// wanneer moet de verlichting aan
        jlLightingAnswerLeft = uiElement.addLabel("Beneden ", 3, 0, 4);// wanneer moet de verlichting aan
        jtfLightingInput = uiElement.addTextField(String.valueOf(user.getLightingInputText()), 4, 1, 4);
        jlLightingAnswerRight = uiElement.addLabel(" lichtsensor meting.", 2, 4);
        jlDummy = uiElement.addLabel(" ", 0, 5);//quick and dirty whitespace
        jbSave = uiElement.addButton("Opslaan", 0, 6);
        jbSave.addActionListener(this);
        jbCancel = uiElement.addButton("Cancel", 2, 6);
        jbCancel.addActionListener(this);
        jlError = uiElement.addLabel("", 3, 0, 7);
        jlError.setForeground(Color.RED);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO add functions for the buttons
        if(e.getSource() == jbSave) {
            // is the input changed//filled in and only integers?
            if(jtfHeatingInput.getText().length() > 0 && jtfLightingInput.getText().length() > 0) { // filled?
                try { // are they integers?
                    int heatingInput = Integer.parseInt(jtfHeatingInput.getText());
                    int lightingInput = Integer.parseInt(jtfLightingInput.getText());
                    if(heatingInput != user.getHeatingInputText() || lightingInput != user.getLightingInputText()) { // can only save if something actually changed
                        user.setUserSetting(heatingInput, lightingInput); // update
                    }
                    userSettingsDialog.setVisible(false); // even tho nothing changed, the dialog will still close
                } catch(NumberFormatException nfe) {
                    jlError.setText("Er mogen alleen getallen gebruikt worden!");
                }
            } else {
                jlError.setText("Er moet wel wat ingevuld zijn!");
            }
        }
        if(e.getSource() == jbCancel) {
            userSettingsDialog.setVisible(false);
        }
    }
}
