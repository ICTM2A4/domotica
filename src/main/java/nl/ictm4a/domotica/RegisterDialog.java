package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegisterDialog extends JDialog implements ActionListener {
    private JButton jbCancel, jbRegister;
    private JTextField jtUsername;
    private JPasswordField jpPassword;
    private JLabel jlUsername, jlPassword;
    private JTextArea jtfError;
    DatabaseFunction databaseFunction = new DatabaseFunction();


    public RegisterDialog(JFrame parent) {
        super(parent, true);
        setTitle("Registreren centrale PC-applicatie");
        setSize(280,200);
        UIElement uiElement = new UIElement();
        setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel = uiElement.panel;
        add(panel);
        jlUsername = uiElement.addLabel("Gebruikersnaam", 0, 0);
        jtUsername = uiElement.addTextField(10, 1, 0);
        jlPassword = uiElement.addLabel("Wachtwoord", 0, 1);
        jpPassword = uiElement.addPasswordField("", 10, 1, 1);
        jbRegister = uiElement.addButton("Registreren", 0,4);
        jbRegister.addActionListener(this);
        jbCancel = uiElement.addButton("Annuleren", 1,4);
        jtfError = new JTextArea("", 2, 1);
        jtfError.setOpaque(false);
        jtfError.setForeground(Color.RED);
        add(jtfError);
        jbCancel.addActionListener(this);
        setVisible(false);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == jbRegister){
            if(jtUsername.getText().equals("")){
                jtfError.setText("Voer een gebruikersnaam in");
            } else if(jpPassword.getPassword().length == 0){
                jtfError.setText("Voer een wachtwoord in");
            } else if (!"".equals(jtUsername.getText())&& 0!= jpPassword.getPassword().length){
                int lastInsertedID = databaseFunction.insertNewUser(jtUsername.getText(), String.valueOf(jpPassword.getPassword()));
                if(lastInsertedID > 0) {
                    // also have to register user settings, just use the standard input
                    lastInsertedID = databaseFunction.insertNewUserSetting(lastInsertedID);
                    if(lastInsertedID > 0) {
                        jtfError.setText("U bent succesvol geregistreerd");
                        setVisible(false);
                    }
                } else {
                    jtfError.setText("Gebruikersnaam is al in gebruik");
                }
            }
        }
        if(e.getSource() == jbCancel){
            setVisible(false);
        }
    }
}
