/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5.GUI;

/**
 *
 * @author Arseniy
 */
import com.mycompany.lb5.AttackType;
import com.mycompany.lb5.Game;
import com.mycompany.lb5.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BattleFrame extends JFrame {

    private JLabel lblPlayerHealth;
    private JLabel lblEnemyHealth;
    private JButton btnAttack;
    private JButton btnDefend;
    private JButton btnItems;

    // ���������� ��� ������������
    private int playerHealth = 100;
    private int enemyHealth = 100;
    private final Player human;
    private final Player enemy;
    private final Game game;

    public BattleFrame(Player human, Player enemy, Game game) {
        super("Битва");
        this.human = human;
        this.enemy = enemy;
        this.game = game;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        addComponentsToFrame();
        setupActions();
    }

    private void initializeComponents() {
        // Информация о здоровье
        JPanel healthPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        lblPlayerHealth = new JLabel("Здоровье " + human.getName() + " (вы): " + human.getHealth(), SwingConstants.CENTER);
        lblEnemyHealth = new JLabel("Здоровье " + enemy.getName() + " (враг): " + enemy.getHealth(), SwingConstants.CENTER);
        lblPlayerHealth.setFont(new Font("Arial", Font.BOLD, 18));
        lblEnemyHealth.setFont(new Font("Arial", Font.BOLD, 18));
        healthPanel.add(lblPlayerHealth);
        healthPanel.add(lblEnemyHealth);

        // Кнопки действий
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnAttack = new JButton("Атаковать");
        btnDefend = new JButton("Защититься");
        btnItems = new JButton("Предметы");
        buttonPanel.add(btnAttack);
        buttonPanel.add(btnDefend);
        buttonPanel.add(btnItems);

        add(healthPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addComponentsToFrame() {
        // Уже добавлено через add() в initializeComponents()
    }

    private void setupActions() {
        btnAttack.addActionListener(this::onAttackClicked);
        btnDefend.addActionListener(this::onDefendClicked);
        btnItems.addActionListener(this::onItemsClicked);
    }

    private void updateHealthLabels() {
        lblPlayerHealth.setText("Здоровье " + human.getName() + " (вы): " + human.getHealth());
        lblEnemyHealth.setText("Здоровье " + enemy.getName() + " (враг): " + enemy.getHealth());
    }

    public void onAttackClicked(ActionEvent e) {
        System.out.println("Button 'Attack' pressed");
        game.fight.performPlayerAction(human, enemy, AttackType.ATTACK);
        updateHealthLabels();
        checkWinCondition();
        checkLoseCondition();
    }

    public void onDefendClicked(ActionEvent e) {
        System.out.println("Button 'Defend' pressed");
        game.fight.performPlayerAction(human, enemy, AttackType.DEFEND);
        updateHealthLabels();
        checkWinCondition();
        checkLoseCondition();
    }

    public void onItemsClicked(ActionEvent e) {
        System.out.println("Button 'Items' pressed.");
        InventoryDialog inventoryDialog = new InventoryDialog(this);
        inventoryDialog.setVisible(true);
    }

    private void checkWinCondition() {
        if (enemy.getHealth() <= 0) {
            WinDialog winDialog = new WinDialog(this);
            winDialog.setVisible(true);
            setVisible(false);
        }
    }

    private void checkLoseCondition() {
        if (human.getHealth() <= 0) {
            JOptionPane.showMessageDialog(this, "Вы проиграли!");
            //логика начала боя заново
            dispose(); // Закрыть окно боя
        }
    }

}