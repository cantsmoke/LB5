/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5.GUI;

/**
 *
 * @author Arseniy
 */
import com.mycompany.lb5.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WinDialog extends JDialog {

    private JButton btnNext;
    private JFrame parentFrame;
    private MainFrame mainFGrame;

    public WinDialog(JFrame parent, MainFrame mainFrame) {
        super(parent, "Победа!", true); // модальное окно
        this.parentFrame = parent;
        this.mainFGrame = mainFrame;// Сохраняем родительское окно
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initializeComponents();
        addComponentsToDialog();
        setupActions();
    }

    private void initializeComponents() {
        JLabel messageLabel = new JLabel("Вы одержали победу!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnNext = new JButton("Дальше");
        buttonPanel.add(btnNext);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addComponentsToDialog() {
        // Уже добавлено через add()
    }

    private void setupActions() {
        btnNext.addActionListener(this::onNextClicked);
    }

    /*public void onNextClicked(ActionEvent e) {
        System.out.println("Кнопка 'Далее' нажата.");
        dispose(); // Закрываем текущий диалог
        Player human = MainFrame.game.NewHuman();
        Player enemy = MainFrame.game.NewEnemy();
        // Открываем новый BattleFrame
        BattleFrame battleFrame = new BattleFrame(human, enemy, MainFrame.game);
        battleFrame.setVisible(true);
    }*/
    
    public void onNextClicked(ActionEvent e) {
        System.out.println("Кнопка 'Далее' нажата.");
        dispose(); // закрыть текущий диалог

        if (currentLocation < totalLocations) {
            // Перейти к следующей локации
            BattleFrame nextBattle = new BattleFrame(human, game.NewEnemy(), game, currentLocation + 1, totalLocations);
            nextBattle.setVisible(true);
        } else {
            // Все локации пройдены
            JOptionPane.showMessageDialog(null, "Вы прошли все локации!");
            MainFrame mainMenu = new MainFrame();
            mainMenu.setVisible(true);
        }
    }
    
}