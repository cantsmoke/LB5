/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5.GUI;
/**
 *
 * @author Арсений
 */
import javax.swing.*;
import java.awt.*;

public class LocationDialog extends JDialog {
    private int locations = -1;
    private boolean confirmed = false;

    public LocationDialog(JFrame parent) {
        super(parent, "Выберите количество локаций", true);
        setSize(350, 150);
        setLocationRelativeTo(parent);
        
        JLabel label = new JLabel("Введите количество локаций (от 1 до 10):");
        JTextField inputField = new JTextField();
        JButton confirmButton = new JButton("Подтвердить");
        confirmButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(inputField.getText().trim());
                if (value >= 1 && value <= 10) {
                    locations = value;
                    confirmed = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Введите число от 1 до 10.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Введите корректное число.");
            }
        });
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(label, BorderLayout.NORTH);
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(confirmButton, BorderLayout.SOUTH);
        add(panel);
    }

    public int getLocations() {
        return locations;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}