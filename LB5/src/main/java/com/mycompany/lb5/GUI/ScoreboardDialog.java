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

public class ScoreboardDialog extends JDialog {

    private JButton btnClose;

    // Пример данных для таблицы (в реальном приложении можно загружать из файла или БД)
    private String[][] scoreData = {
            {"1", "Алекс", "150"},
            {"2", "Дмитрий", "140"},
            {"3", "Екатерина", "135"},
            {"4", "Максим", "130"},
            {"5", "Ольга", "125"},
            {"6", "Иван", "120"},
            {"7", "Наталья", "115"},
            {"8", "Сергей", "110"},
            {"9", "Анна", "105"},
            {"10", "Виктор", "100"}
    };

    private String[] columnNames = {"Место", "Имя", "Результат"};

    public ScoreboardDialog(JFrame parent) {
        super(parent, "Таблица рекордов", true); // модальное окно
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initializeComponents();
        addComponentsToDialog();
        setupActions();
    }

    private void initializeComponents() {
        // Создаем таблицу
        JTable table = new JTable(scoreData, columnNames);
        table.setFillsViewportHeight(true);
        table.setEnabled(false); // запрещаем редактирование

        // Оборачиваем в JScrollPane для прокрутки
        JScrollPane scrollPane = new JScrollPane(table);

        // Кнопка закрытия
        btnClose = new JButton("Закрыть");

        // Панель с кнопкой
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnClose);

        // Добавляем компоненты
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addComponentsToDialog() {
        // Уже добавлено через add()
    }

    private void setupActions() {
        btnClose.addActionListener(this::onCloseClicked);
    }

    public void onCloseClicked(ActionEvent e) {
        System.out.println("Кнопка 'Закрыть' нажата.");
        dispose(); // Закрываем диалог

        // Возвращаемся в главное меню
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }


}