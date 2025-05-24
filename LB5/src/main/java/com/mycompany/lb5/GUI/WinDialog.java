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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Collections;

public class WinDialog extends JDialog {

    private JButton btnNext;
    private BattleFrame2 parentFrame;
    
    public WinDialog(BattleFrame2 parentFrame) {
        super(parentFrame, "Победа!", true);
        this.parentFrame = parentFrame;
        setSize(500, 200);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        setupActions();
    }

    private void initializeComponents() {
        JLabel messageLabel = new JLabel("Вы одержали победу в локации №" + parentFrame.getCurrentLocation() + " из " + parentFrame.getTotalLocations() + " !", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnNext = new JButton("Дальше");
        buttonPanel.add(btnNext);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupActions() {
        btnNext.addActionListener(this::onNextClicked);
    }
    
    public void onNextClicked(ActionEvent e) {
        System.out.println("Кнопка 'Далее' нажата.");
        dispose();
        if (parentFrame.getCurrentLocation() < parentFrame.getTotalLocations()) {
            BattleFrame2 nextBattle = new BattleFrame2(
                    parentFrame.getHuman(), 
                    parentFrame.getGame().generateEnemiesForLocation(parentFrame.getHuman().getLevel()), 
                    parentFrame.getGame(), 
                    parentFrame.getCurrentLocation() + 1, 
                    parentFrame.getTotalLocations());
            nextBattle.setVisible(true);
        } else {
            int playerScore = parentFrame.getHuman().getPoints();
            int position = getTop10Position(playerScore);

            if (position == -1) {
                JOptionPane.showMessageDialog(null, 
                    "Вы прошли все локации!\nК сожалению, вы не попали в топ-10.\nВаш результат: " + playerScore + " очков.",
                    "Конец игры", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JTextField nameField = new JTextField();
                Object[] message = {
                    "Поздравляем!\nВы попали в топ-10 на позицию #" + position + "!",
                    "Ваши очки: " + playerScore,
                    "Введите ваше имя:", nameField
                };
                int option = JOptionPane.showConfirmDialog(null, message, "Топ-10", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String playerName = nameField.getText().trim();
                    if (!playerName.isEmpty()) {
                        ExcelManager.writeToExcel(playerName, playerScore); // вызов без реализации
                        JOptionPane.showMessageDialog(null, "Результат сохранён! Спасибо за игру!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Имя не введено. Результат не сохранён.");
                    }
                }
            }
            MainFrame mainMenu = new MainFrame();
            mainMenu.setVisible(true);
        }
    }
    
    private int getTop10Position(int playerScore) {
        List<Integer> topScores = ExcelManager.loadTop10ScoresFromExcel();
        int position = 0;
        if (playerScore > getMin(topScores)){
            for (int i = 0; i < topScores.size(); i++) {
                if (playerScore > topScores.get(i)) {
                    position = i + 1;
                    break;
                }
            }
            if (position == -1) {
                position = topScores.size() + 1;
            }
        } else {
            position = -2;
        }
        return position + 1;
    }
    
    public int getMin(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список пуст или null");
        }
        return Collections.min(list);
    }
}