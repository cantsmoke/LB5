/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5.GUI;

/**
 *
 * @author Arseniy
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ItemErrorDialog extends JDialog {

    private JButton btnOk;

    public ItemErrorDialog(JFrame parent) {
        super(parent, "Ошибка использования", true); // модальное окно
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initializeComponents();
        addComponentsToDialog();
        setupActions();
    }

    private void initializeComponents() {
        // Сообщение об ошибке
        JLabel messageLabel = new JLabel("<html>Невозможно использовать этот<br>предмет в текущей ситуации.</html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(messageLabel, BorderLayout.CENTER);

        // Кнопка OK
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnOk = new JButton("OK");
        buttonPanel.add(btnOk);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addComponentsToDialog() {
        // Уже добавлено через add()
    }

    private void setupActions() {
        btnOk.addActionListener(this::onOkClicked);
    }

    public void onOkClicked(ActionEvent e) {
        System.out.println("Кнопка 'OK' нажата.");
        dispose(); // Закрываем диалог
    }


}