package nl.ictm4a.domotica;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener {

    private JButton jbLogin, jbCancel, jbRegister;
    private JTextField jtUsername;
    private JPasswordField jpPassword;
    private JLabel jlUsername, jlPassword;
    private LoginFrame loginFrame;
    private DatabaseFunction dbf = new DatabaseFunction();

    public LoginFrame() {
        setTitle("Inloggen centrale PC-applicatie");
        setSize(250,250);
        setResizable(false);
        UIElement uiElement = new UIElement();
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel panel = uiElement.panel;
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jlUsername = uiElement.addLabel("Gebruikersnaam", 0, 0);
        jtUsername = uiElement.addTextField(10, 1, 0);
        jlPassword = uiElement.addLabel("Wachtwoord", 0, 1);
        jpPassword = uiElement.addPasswordField(10, 1, 1);
        jbLogin = uiElement.addButton("Inloggen", 1, 1, 2);
        jbLogin.addActionListener(this);
        jbRegister = uiElement.addButton("Registreren", 0,4);
        jbRegister.addActionListener(this);
        jbCancel = uiElement.addButton("Annuleren", 1,4);
        jbCancel.addActionListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        String password = new String(jpPassword.getPassword());
        dbf.select("select * from user");
        if(e.getSource() == jbLogin){
            if(jtUsername.getText().length() < 1){
                JOptionPane.showMessageDialog(this, "Voer een gebruikersnaam in");
            }
            if(password.length() < 1){
                JOptionPane.showMessageDialog(this, "Voer een wachtwoord in");
            }
            if (jtUsername.getText().length() > 0 && password.length() > 0){


                if(dbf.select("Select password from User where username = '" + jtUsername.getText() + "'").equals(password)) {
                    JOptionPane.showMessageDialog(this, "U bent succesvol ingelogd");
                    System.out.println(jtUsername.getText());   //wordt uiteindelijk vervangen door een regel die ervoor zorgt dat de data naar de database gaat.
                    System.out.println(jpPassword.getPassword());   //wordt uiteindelijk vervangen door een regel die ervoor zorgt dat de data naar de database gaat.
                    setVisible(false);

                    // succes login, change screen to main screen
                    MainScreenFrame mainScreenFrame = new MainScreenFrame();
                    mainScreenFrame.setVisible(true);
                }
                else {                                 //wordt uiteindelijk vervangen voor een voorwaarde die checkt of de gegevens kloppen.
                    JOptionPane.showMessageDialog(this, "De combinatie van gebruikersnaam en wachtwoord komt niet overeen");
                }
            }
        }
        if(e.getSource() == jbCancel){
            dispose();
        }
        if(e.getSource() == jbRegister){
            RegisterDialog rg = new RegisterDialog(loginFrame);
            rg.setVisible(true);
        }

    }

}
