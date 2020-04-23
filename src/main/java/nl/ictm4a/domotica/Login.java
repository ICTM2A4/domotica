package nl.ictm4a.domotica;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {

    private JButton jbLogin, jbCancel, jbRegister;
    private JTextField jtUsername;
    private JPasswordField jpPassword;
    Login login;

    public Login() {
        setTitle("Inloggen centrale PC-applicatie");
        setSize(250,250);
        GridBagConstraints gbc = new GridBagConstraints();
        setResizable(false);
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(1, 1,2,1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel jlUsername = new JLabel("Gebruikersnaam");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(jlUsername, gbc);
        jtUsername = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(jtUsername, gbc);
        JLabel jlPassword = new JLabel("Wachtwoord");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(jlPassword, gbc);
        jpPassword = new JPasswordField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(jpPassword, gbc);
        jbLogin = new JButton("Inloggen");
        jbCancel = new JButton("Annuleren");
        jbRegister = new JButton("Registreren");

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(jbLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(jbRegister, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(jbCancel, gbc);
        jbLogin.addActionListener(this);
        jbCancel.addActionListener(this);
        jbRegister.addActionListener(this);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == jbLogin){
            if(jtUsername.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Voer een gebruikersnaam in");
            }
            if(jpPassword.getPassword().equals("")){
                JOptionPane.showMessageDialog(this, "Voer een wachtwoord in");
            }

            if (!"".equals(jtUsername.getText())&& !"".equals(jpPassword.getPassword())){
                JOptionPane.showMessageDialog(this, "U bent succesvol ingelogd");
                setVisible(false);
                //System.out.println(jtUsername.getText());   //wordt uiteindelijk vervangen door een regel die ervoor zorgt dat de data naar de database gaat.
                //System.out.println(jpPassword.getPassword());   //wordt uiteindelijk vervangen door een regel die ervoor zorgt dat de data naar de database gaat.

                // succes login, change screen to main screen
                MainScreenFrame mainScreenFrame = new MainScreenFrame();
                mainScreenFrame.setVisible(true);
            }

            else if(0 == 0){                                 //wordt uiteindelijk vervangen voor een voorwaarde die checkt of de gegevens kloppen.
                JOptionPane.showMessageDialog(this, "De combinatie van gebruikersnaam en wachtwoord komt niet overeen");
            }
        }
        if(e.getSource() == jbCancel){
            dispose();                                      //wordt uiteindelijk vervangen door een regel die ervoor zorgt dat de gebruiker terug gaat naar het hoofdmenu.
        }
        if(e.getSource() == jbRegister){
            Register rg = new Register(login);
            rg.setVisible(true);
        }

    }
}
