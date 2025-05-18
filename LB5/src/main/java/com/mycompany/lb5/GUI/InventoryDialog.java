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

public class InventoryDialog extends JDialog {

    private JList<String> itemsList;
    private JButton btnUseItem;
    private JFrame parentFrame; 

    // Пример списка предметов
    private String[] inventoryItems = {
            "Зелье здоровья",
            "Щит защиты",
            "Меч силы",
            "Лук меткости"
    };

    public InventoryDialog(JFrame parent) {
        super(parent, "Мешок предметов", true); // модальное окно
        this.parentFrame = parent; // Сохраняем родительское окно
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initializeComponents();
        addComponentsToDialog();
        setupActions();
    }

    private void initializeComponents() {
        // Список предметов
        itemsList = new JList<>(inventoryItems);
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsList.setLayoutOrientation(JList.VERTICAL);
        itemsList.setVisibleRowCount(-1); // чтобы не было строк по умолчанию

        JScrollPane listScroller = new JScrollPane(itemsList);

        // Кнопка использования предмета
        btnUseItem = new JButton("Использовать");

        // Панель с кнопкой
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnUseItem);

        // Добавляем компоненты
        add(listScroller, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addComponentsToDialog() {
        // Уже добавлено через add()
    }

    private void setupActions() {
        btnUseItem.addActionListener(this::onUseItemClicked);
    }

    public void onUseItemClicked(ActionEvent e) {
        String selectedItem = itemsList.getSelectedValue();

        if (selectedItem == null) {
            JOptionPane.showMessageDialog(this, "Выберите предмет для использования.", "Ошибка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("Используется предмет: " + selectedItem);

        boolean canUseItem = Math.random() > 0.5;

        if (canUseItem) {
            JOptionPane.showMessageDialog(this, "Вы использовали предмет: " + selectedItem);
        } else {
            ItemErrorDialog errorDialog = new ItemErrorDialog(parentFrame); // Используем parentFrame
            errorDialog.setVisible(true);
        }
    }

}