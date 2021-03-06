package nl.ictm4a.domotica;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;

public class LoginFrame extends JFrame implements ActionListener {

    private JButton jbLogin, jbCancel, jbRegister;
    private JTextField jtUsername;
    private JPasswordField jpPassword;
    private JLabel jlUsername, jlPassword;
    private JTextArea jtfError;
    private LoginFrame loginFrame;
    private DatabaseFunction databaseFunction = new DatabaseFunction();
    private HashFunction hashFunction = new HashFunction();

    // TODO: TEMPORARY ALREADY FILLED IN TEXT
    private String userName = "";
    private String userPassword = "";

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
        jtfError = new JTextArea("", 2, 1);
        jtfError.setOpaque(false);
        jtfError.setForeground(Color.RED);
        add(jtfError);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbLogin) {
            String password = new String(jpPassword.getPassword());         //turns getPassword into a String variable
            if (jtUsername.getText().length() < 1) {
                jtfError.setText("Voer een gebruikersnaam in");    // return a message to tell the owner that they have not yet typed in their username
            }
            if (password.length() < 1) {
                jtfError.setText("Voer een wachtwoord in");      // return a message to tell the owner that they have not yet typed in their password
            }
            if (jtUsername.getText().length() > 0 && password.length() > 0) {       //true when both the username and the password are filled with at least 1 character
                if (!(jtUsername.getText().contains(" "))) {
                    String hPassword = null;
                    try {
                        hPassword = hashFunction.stringToHex(password);    //turns the password into hash (uses 'HashFunction')
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }
                    String userpassword = databaseFunction.selectUserPassword(jtUsername.getText());
                    if (userpassword.equals(hPassword)) {//checks if the used username and password are recognized in the database
                        setVisible(false);
                        User user = new User(jtUsername.getText(), databaseFunction.selectUserID(jtUsername.getText(), userpassword));
                    } else {
                        jtfError.setText("De combinatie van gebruikersnaam \n en wachtwoord komt niet overeen");        // a message is shown if the user enters a username and password that do not match data from the database
                    }
                }
                else {
                    jtfError.setText("De gebruikersnaam mag \n geen spaties bevatten");
                }
            }
        }
        if (e.getSource() == jbCancel) {
            dispose();                                                                                                                        //closes the entire application
        }
        if (e.getSource() == jbRegister) {
            jtfError.setText("");
            RegisterDialog rg = new RegisterDialog(loginFrame);                                                                               //the register dialog is opened
            rg.setVisible(true);
        }

    }

}

