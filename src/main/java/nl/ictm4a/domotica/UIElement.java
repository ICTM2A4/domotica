package nl.ictm4a.domotica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * this class is used to create elements like buttons or labels and add them to the desired panel while also placing them within a GridBagLayout
 * every add...() does the same thing except in a couple of cases where more data is needed, like the checkbox container
 */

public class UIElement {
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel panel = new JPanel(new GridBagLayout());

    /**
     * sets the layout to allways start on the top left side of the panel
     * @return a layout with the correct starting position
     */
    GridBagConstraints setupGbc(){
        gbc.anchor = GridBagConstraints.NORTHWEST;
        return gbc;
    }

    /**
     * optional setting in case a panel has already been created (for an example see MainScreenFrame)
     * @param panel sets the desired Panel instead of a new one
     */
    void alterPanel(JPanel panel){
        this.panel = panel;
    }

    /**
     * Changes the layout of the element that's about to be added: HORIZONTAL fills the grid so all elements are equal in width
     * @param width width of the element, isn't allways needed or functional when used in combination with elements like textfields
     * @param x coordinate
     * @param y coordinate
     */
    void alterGbc(int width, int x, int y){
        gbc.gridwidth = width;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }

    /**
     * Creates a new button and sets it up inside the grid and adds it to the desired panel
     * @param buttonText self explanatory
     * @param width width inside grid see alterGbc()
     * @param x coordinate see alterGbc()
     * @param y coordinate see alterGbc()
     * @return the button itself so the user can add actionListeners etc
     */
    JButton addButton(String buttonText, int width, int x, int y){
        JButton button = new JButton(buttonText);
        alterGbc(width, x, y);
        panel.add(button, gbc);
        return button;
    }

    /**
     * overloading upper addButton
     */
    JButton addButton(String buttonText, int x, int y){
        return addButton(buttonText, 1, x, y);
    }

    /**
     * Creates a new label and sets it up inside the grid and adds it to the desired panel
     * @param labelText self explanatory
     * @param width width inside grid see alterGbc()
     * @param x coordinate see alterGbc()
     * @param y coordinate see alterGbc()
     * @return the label itself so the user can add actionListeners etc
     */
    JLabel addLabel(String labelText, int width, int x, int y){
        JLabel label = new JLabel(labelText);
        alterGbc(width, x, y);
        panel.add(label, gbc);
        return label;
    }

    /**
     * overloading upper addLabel
     */
    JLabel addLabel(String labelText, int x, int y){
        return addLabel(labelText, 1, x, y);
    }

    /**
     * Creates a new textfield and sets it up inside the grid and adds it to the desired panel
     * @param textFieldText self explanatory
     * @param width width inside grid see alterGbc()
     * @param x coordinate see alterGbc()
     * @param y coordinate see alterGbc()
     * @return the textfield itself so the user can add actionListeners etc
     */
    JTextField addTextField(String textFieldText, int rows, int width, int x, int y){
        JTextField textField = new JTextField(textFieldText, rows);
        alterGbc(width, x, y);
        panel.add(textField, gbc);
        return textField;
    }

    /**
     * overloading upper addTextField
     */
    JTextField addTextField(String textFieldText, int rows, int x, int y){ return addTextField(textFieldText, rows, 1, x, y); }

    /**
     * overloading upper addTextField
     */
    JTextField addTextField(int rows, int x, int y){ return addTextField("", rows, 1, x, y); }

    JPasswordField addPasswordField(String passwordFieldText, int rows, int x, int y){
        JPasswordField jPasswordField = new JPasswordField(passwordFieldText, rows);
        alterGbc(1, x, y);
        panel.add(jPasswordField, gbc);
        return jPasswordField;
    }

    JPasswordField addPasswordField(int x, int y){
        return addPasswordField("", 1, x, y);
    }

    JPasswordField addPasswordField(String passwordFieldText, int x, int y){
        return addPasswordField(passwordFieldText, 1, x, y);
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
