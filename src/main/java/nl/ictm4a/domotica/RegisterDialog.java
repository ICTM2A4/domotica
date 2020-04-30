package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class RegisterDialog extends JDialog implements ActionListener {

    private JButton jbCancel, jbRegister;
    private JTextField jtUsername;
    private JPasswordField jpPassword;
    private JLabel jlUsername, jlPassword;
    private DatabaseFunction dbf = new DatabaseFunction();
    private HashFunction hsf = new HashFunction();

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
        jpPassword = uiElement.addPasswordField(10, 1, 1);
        jbRegister = uiElement.addButton("Registreren", 0,4);
        jbRegister.addActionListener(this);
        jbCancel = uiElement.addButton("Annuleren", 1,4);
        jbCancel.addActionListener(this);
        setVisible(false);
    }

    public void actionPerformed(ActionEvent e){


        if(e.getSource() == jbRegister){
            String password = new String(jpPassword.getPassword());
            if(jtUsername.getText().length() < 1){
                JOptionPane.showMessageDialog(this, "Voer een gebruikersnaam in");               // return a message to tell the owner that they have not yet typed in their username
            }
            if(password.length() < 1){
                JOptionPane.showMessageDialog(this, "Voer een wachtwoord in");                  // return a message to tell the owner that they have not yet typed in their password
            }
            if (jtUsername.getText().length() > 0 && password.length() > 0){                                            //true when both the username and the password are filled with at least 1 character
                    if(dbf.select("select username from User where username = '" + jtUsername.getText() + "'").length()==0) {           // This statement makes sure that every username is unique.
                        String hPassword = null;
                        try {
                            hPassword = hsf.stringToHex(password);                                                      //turns the password into hash (uses 'HashFunction')
                        } catch (NoSuchAlgorithmException ex) {
                            ex.printStackTrace();
                        }
                        dbf.insert("insert into User (username, password) values ('" + jtUsername.getText() + "', '" + hPassword + "')");       //inserts the new user data into the database

                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Er bestaat al een account met de ingevulde gebruikersnaam. \n Probeer een andere gebruikersnaam");             //this message is shown if the chosen username is already taken
                    }
                    JOptionPane.showMessageDialog(this, "U bent succesvol geregistreerd");
                    setVisible(false);                                                                                                                                                              //if the user is successfully registered, the register dialog closes and returns to the login page
                }

                }
        if(e.getSource() == jbCancel){
            setVisible(false);
        }
    }

}
