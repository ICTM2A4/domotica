package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UIElement {
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel panel = new JPanel(new GridBagLayout());

    GridBagConstraints setupGbc(){
        gbc.anchor = GridBagConstraints.NORTHWEST;
        return gbc;
    }

    void alterPanel(JPanel panel){
        this.panel = panel;
    }

    void alterGbc(int width, int x, int y){
        gbc.gridwidth = width;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }

    JButton addButton(String buttonText, int width, int x, int y){
        JButton button = new JButton(buttonText);
        alterGbc(width, x, y);
        panel.add(button, gbc);
        return button;
    }

    JButton addButton(String buttonText, int x, int y){
        return addButton(buttonText, 1, x, y);
    }

    JLabel addLabel(String labelText, int width, int x, int y){
        JLabel label = new JLabel(labelText);
        alterGbc(width, x, y);
        panel.add(label, gbc);
        return label;
    }

    JLabel addLabel(String labelText, int x, int y){
        return addLabel(labelText, 1, x, y);
    }

    JTextField addTextField(String textFieldText, int rows, int width, int x, int y){
        JTextField textField = new JTextField(textFieldText, rows);
        alterGbc(width, x, y);
        panel.add(textField, gbc);
        return textField;
    }

    JTextField addTextField(String textFieldText, int rows, int x, int y){ return addTextField(textFieldText, rows, 1, x, y); }

    JTextField addTextField(int rows, int x, int y){
        return addTextField("", rows, 1, x, y);
    }

    JTextField addTextField(int x, int y){
        return addTextField("", 10, 1, x, y);
    }

    JPasswordField addPasswordField(String passwordFieldText, int rows, int x, int y){
        JPasswordField jPasswordField = new JPasswordField(passwordTextField, rows);
        alterGbc(1, x, y);
        panel.add(jPasswordField, gbc);
        return jPasswordField;
    }

    JPasswordField addPasswordField(int x, int y){
        return addPasswordField("", 1, x, y);
    }

    JPasswordField addPasswordField(String passwordTextField, int x, int y){
        return addPasswordField(passwordTextField, 1, x, y);
    }


    JComboBox addComboBox(Object[] options, int width, int x, int y){
        JComboBox jComboBox = new JComboBox(options);
        alterGbc(width, x, y);
        panel.add(jComboBox, gbc);
        return jComboBox;
    }
    JComboBox addComboBox (Object[] options, int x, int y){
        return addComboBox(options,1, x, y);
    }

    Box addVerticalCheckboxContainer(ArrayList<JCheckBox> boxContents, int x, int y){
        Box box = Box.createVerticalBox();
        for(JCheckBox jCheckBox: boxContents){
            box.add(jCheckBox);
        }
        alterGbc(1, x, y);
        panel.add(box, gbc);
        return box;
    }
}
