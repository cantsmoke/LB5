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

public class Top10MessageDialog extends JDialog {

    private JButton btnFinish;

    public Top10MessageDialog(JFrame parent) {
        super(parent, "Результат", true); // модальное окно
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initializeComponents();
        addComponentsToDialog();
        setupActions();
    }

    private void initializeComponents() {
        // Сообщение
        JLabel messageLabel = new JLabel("<html>К сожалению, ваш результат<br>не попал в ТОП-10.</html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(messageLabel, BorderLayout.CENTER);

        // Кнопка "Закончить"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnFinish = new JButton("Закончить");
        buttonPanel.add(btnFinish);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addComponentsToDialog() {
        // Уже добавлено через add()
    }

    private void setupActions() {
        btnFinish.addActionListener(this::onFinishClicked);
    }

    public void onFinishClicked(ActionEvent e) {
        System.out.println("Кнопка 'Закончить' нажата.");
        dispose(); // Закрываем текущее окно

        // Возвращаемся в главное меню
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

}