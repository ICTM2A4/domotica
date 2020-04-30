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
                setVisible(false);
            }
        }
        if(e.getSource() == jbCancel){
            setVisible(false);
        }
    }
}