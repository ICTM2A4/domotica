package nl.ictm4a.domotica;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener {

    private JButton jbLogin, jbCancel, jbRegister;
    private JTextField jtUsername;
    private JPasswordField jpPassword;
    private JLabel jlUsername, jlPassword;
    LoginFrame loginFrame;

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
        if(e.getSource() == jbLogin){
            if(jtUsername.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Voer een gebruikersnaam in");
            }
            else if(jpPassword.getPassword().length == 0){
                JOptionPane.showMessageDialog(this, "Voer een wachtwoord in");
            }

            else if (!"".equals(jtUsername.getText())&& 0!=jpPassword.getPassword().length){
                JOptionPane.showMessageDialog(this, "U bent succesvol ingelogd");
                setVisible(false);
                //System.out.println(jtUsername.getText());   //wordt uiteindelijk vervangen door een regel die ervoor zorgt dat de data naar de database gaat.
                //System.out.println(jpPassword.getPassword());   //wordt uiteindelijk vervangen door een regel die ervoor zorgt dat de data naar de database gaat.

                // succes login, change screen to main screen
                MainScreenFrame mainScreenFrame = new MainScreenFrame();
                mainScreenFrame.setVisible(true);
            }

           else{                                 //wordt uiteindelijk vervangen voor een voorwaarde die checkt of de gegevens kloppen.
                JOptionPane.showMessageDialog(this, "De combinatie van gebruikersnaam en wachtwoord komt niet overeen");
            }
        }
        if(e.getSource() == jbCancel){
            dispose();                                      //wordt uiteindelijk vervangen door een regel die ervoor zorgt dat de gebruiker terug gaat naar het hoofdmenu.
        }
        if(e.getSource() == jbRegister){
            RegisterDialog rg = new RegisterDialog(loginFrame);
            rg.setVisible(true);
        }

    }
}
