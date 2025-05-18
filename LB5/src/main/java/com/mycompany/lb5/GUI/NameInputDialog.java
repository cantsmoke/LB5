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

public class NameInputDialog extends JDialog {

    private JTextField txtName;
    private JButton btnFinish;
    private JFrame parentFrame;

    public NameInputDialog(JFrame parent) {
        super(parent, "Введите ваше имя", true); // модальное окно
        this.parentFrame = parent; // Сохраняем родительское окно
        setSize(400, 150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initializeComponents();
        addComponentsToDialog();
        setupActions();
    }

    private void initializeComponents() {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel label = new JLabel("Имя:");
        txtName = new JTextField(20);
        inputPanel.add(label);
        inputPanel.add(txtName);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnFinish = new JButton("Завершить игру");
        buttonPanel.add(btnFinish);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addComponentsToDialog() {
        // Уже добавлено через add()
    }

    private void setupActions() {
        btnFinish.addActionListener(this::onFinishGameClicked);
    }

    public void onFinishGameClicked(ActionEvent e) {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, введите ваше имя.", "Ошибка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("Игрок ввёл имя: " + name);

        boolean isTop10 = Math.random() > 0.5;

        if (isTop10) {
            ScoreboardDialog scoreboardDialog = new ScoreboardDialog(parentFrame); // Используем parentFrame
            scoreboardDialog.setVisible(true);
        } else {
            Top10MessageDialog messageDialog = new Top10MessageDialog(parentFrame); // Используем parentFrame
            messageDialog.setVisible(true);
        }
    }


}