package nl.ictm4a.domotica;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class LoginFrame extends JFrame implements ActionListener {

    private JButton jbLogin, jbCancel, jbRegister;
    private JTextField jtUsername;
    private JPasswordField jpPassword;
    private JLabel jlUsername, jlPassword;
    private LoginFrame loginFrame;
    private DatabaseFunction dbf = new DatabaseFunction();
    private HashFunction hsf = new HashFunction();

    // TODO: TEMPORARY ALREADY FILLED IN TEXT
    private String userName = "Frans";
    private String userPassword = "test";

    public LoginFrame() {
        setTitle("Inloggen centrale PC-applicatie");
        setSize(250, 250);
        setResizable(false);
        UIElement uiElement = new UIElement();
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //adds al UI elements
        JPanel panel = uiElement.panel;
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jlUsername = uiElement.addLabel("Gebruikersnaam", 0, 0);
        jtUsername = uiElement.addTextField(userName, 10, 1, 0); // TEMPORARY FILLED IN TEXT
        jlPassword = uiElement.addLabel("Wachtwoord", 0, 1); // TEMPORARY FILLED IN TEXT
        jpPassword = uiElement.addPasswordField(userPassword, 10, 1, 1);
        jbLogin = uiElement.addButton("Inloggen", 1, 1, 2);
        jbLogin.addActionListener(this);
        jbRegister = uiElement.addButton("Registreren", 0, 4);
        jbRegister.addActionListener(this);
        jbCancel = uiElement.addButton("Annuleren", 1, 4);
        jbCancel.addActionListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbLogin) {
            String password = new String(jpPassword.getPassword());             //turns getPassword into a String
            if (jtUsername.getText().length() < 1) {
                JOptionPane.showMessageDialog(this, "Voer een gebruikersnaam in");      // return a message to tell the owner that they have not yet typed in their username
            }
            if (password.length() < 1) {
                JOptionPane.showMessageDialog(this, "Voer een wachtwoord in");      // return a message to tell the owner that they have not yet typed in their password
            }
            if (jtUsername.getText().length() > 0 && password.length() > 0) {       //true when both the username and the password are filled with at least 1 character
                String hPassword = null;
                try {
                    hPassword = hsf.stringToHex(password);    //turns the password into hash (uses 'HashFunction')
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
                    if (dbf.selectRow("Select password from User where username = '" + jtUsername.getText() + "'").get(0).equals(hPassword)) {        //checks if the used username and password are recognized in the database
                        setVisible(false);

                        ArrayList<String> resultArray = dbf.selectRow("SELECT user_id, username FROM User WHERE username = '" + jtUsername.getText() + "'");
                        int userID = Integer.parseInt(resultArray.get(0)); // make integer from first in arraylist (which is the userid from the select query)
                        String userName = String.valueOf(resultArray.get(1)); // make string from the username
                        User user = new User(userID, userName);
                        //JOptionPane.showMessageDialog(this, "U bent succesvol ingelogd");
                    } else {
                        JOptionPane.showMessageDialog(this, "De combinatie van gebruikersnaam en wachtwoord komt niet overeen");        // a message is shown if the user enters a username and password that do not match data from the database
                    }

            }
        }
            if (e.getSource() == jbCancel) {
                dispose();                                                                                                                        //closes the entire application
            }
            if (e.getSource() == jbRegister) {
                RegisterDialog rg = new RegisterDialog(loginFrame);                                                                               //the register dialog is opened
                rg.setVisible(true);
            }

        }

    }

