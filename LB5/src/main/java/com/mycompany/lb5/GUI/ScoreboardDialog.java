/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5.GUI;

/**
 *
 * @author Arseniy
 */
import com.mycompany.lb5.ExcelManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;


public class ScoreboardDialog extends JDialog {

    private JButton btnClose;
    private JTable table;

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
        List<String[]> scoreDataList = ExcelManager.loadTop10TableFromExcel();
        String[][] scoreData = new String[scoreDataList.size()][3];
        for (int i = 0; i < scoreDataList.size(); i++) {
            scoreData[i][0] = String.valueOf(i + 1); // Место
            scoreData[i][1] = scoreDataList.get(i)[0]; // Имя
            scoreData[i][2] = scoreDataList.get(i)[1]; // Очки
        }

        table = new JTable(new DefaultTableModel(scoreData, columnNames));
        table.setFillsViewportHeight(true);
        table.setEnabled(false); // запрещаем редактирование

        JScrollPane scrollPane = new JScrollPane(table);

        btnClose = new JButton("Закрыть");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnClose);

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

        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
    
}
