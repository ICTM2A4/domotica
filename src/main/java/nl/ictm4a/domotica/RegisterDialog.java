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
    private DatabaseFunction dbf = new DatabaseFunction();

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
        String password = new String(jpPassword.getPassword());

        if(e.getSource() == jbRegister){
            if(jtUsername.getText().length() < 1){
                JOptionPane.showMessageDialog(this, "Voer een gebruikersnaam in");
            }
            if(password.length() < 1){
                JOptionPane.showMessageDialog(this, "Voer een wachtwoord in");
            }
            if (jtUsername.getText().length() > 0 && password.length() > 0){
                if(dbf.select("select username from User where username = '" + jtUsername.getText() + "'").length()==0) {
                    JOptionPane.showMessageDialog(this, "U bent succesvol geregistreerd");
                    int userId = Integer.parseInt(dbf.select("Select max(user_id) from user"));
                    userId++;
                    dbf.insert("insert into User values (" + userId + ", '" + jtUsername.getText() + "', '" + password + "')");
                    setVisible(false);
                }
                else {
                        JOptionPane.showMessageDialog(this, "Er bestaat al een account met de ingevulde gebruikersnaam \n Probeer een andere gebruikersnaam");
                    }
                }
                }
        if(e.getSource() == jbCancel){
            setVisible(false);
        }
    }

}
