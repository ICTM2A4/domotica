package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDialog extends JDialog implements ActionListener {

    private JButton jbCancel, jbRegister;
    private JTextField jtUsername;
    private JPasswordField jpPassword;

    public RegisterDialog(JFrame parent) {
        super(parent, true);
        setTitle("Registreren centrale PC-applicatie");
        setSize(280,200);
        GridBagConstraints gbc = new GridBagConstraints();
        setResizable(false);
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(1, 1,2,1);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        jbCancel = new JButton("Annuleren");
        jbRegister = new JButton("Registreren");

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(jbRegister, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(jbCancel, gbc);
        jbCancel.addActionListener(this);
        jbRegister.addActionListener(this);
        setVisible(false);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == jbRegister){

            if(jtUsername.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Voer een gebruikersnaam in");
            }
            else if(jpPassword.getPassword().length == 0){
                JOptionPane.showMessageDialog(this, "Voer een wachtwoord in");
            }
            else if (!"".equals(jtUsername.getText())&& 0!= jpPassword.getPassword().length){
                JOptionPane.showMessageDialog(this, "U bent succesvol geregistreerd");
                System.out.println(jtUsername.getText());   //wordt uiteindelijk vervangen door een regel die ervoor zorgt dat de data naar de database gaat.
                System.out.println(jpPassword.getPassword());   //wordt uiteindelijk vervangen door een regel die ervoor zorgt dat de data naar de database gaat.
            }
        }
        if(e.getSource() == jbCancel){
            setVisible(false);
        }
    }
}
