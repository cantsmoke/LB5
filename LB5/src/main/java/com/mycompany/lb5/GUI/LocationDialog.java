/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5.GUI;

import javax.swing.*;
import java.awt.*;
/**
 * Диалоговое окно для выбора количества локаций.
 * Пользователь вводит число от 1 до 10.
 * Если введено корректное число — диалог закрывается и результат можно получить через геттеры.
 * 
 * Наследуется от JDialog, является модальным, блокирует родительское окно.
 * 
 * @author Арсений
 */
public class LocationDialog extends JDialog {
    private int locations = -1;
    private boolean confirmed = false;
    /**
     * Конструктор диалога.
     * @param parent родительское окно (обычно JFrame)
     */
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