/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5.GUI;

/**
 *
 * @author ababa
 */
import com.mycompany.lb5.AttackType;
import com.mycompany.lb5.Game;
import com.mycompany.lb5.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BattleFrame2 extends JFrame {

    private JLabel lblPlayerHealth;
    private JLabel lblEnemyHealth;
    private JButton btnAttack;
    private JButton btnDefend;
    private JButton btnItems;
    private JProgressBar playerHpBar, enemyHpBar;
    private JLabel lblPlayerDamage, lblPlayerLevel, lblPlayerName;
    private JLabel lblEnemyDamage, lblEnemyLevel, lblEnemyName;
    private JLabel playerScoreLabel, playerExpLabel;
    private JTextArea logArea;
    private JLabel turnLabel, stunLabel;
    

    // ���������� ��� ������������
    private int playerHealth = 100;
    private int enemyHealth = 100;
    private final Player human;
    private final Player enemy;
    private final Game game;
    private final int currentLocation;
    private final int totalLocations;
    

    public BattleFrame2(Player human, Player enemy, Game game, int currentLocation, int totalLocations) {
        super("Битва");
        this.human = human;
        this.enemy = enemy;
        this.game = game;
        this.currentLocation = currentLocation;
        this.totalLocations = totalLocations;
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        addComponentsToFrame();
        setupActions();
    }
    
    public int getCurrentLocation(){
        return this.currentLocation;
    }
    
    public int getTotalLocations(){
        return this.totalLocations;
    }
    
    public Player getHuman(){
        return this.human;
    }
    
    public Game getGame(){
        return this.game;
    }

    private void initializeComponents() {
        // Top: Заголовок "FIGHT"
        JLabel fightLabel = new JLabel("FIGHT", SwingConstants.CENTER);
        fightLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(fightLabel, BorderLayout.NORTH);

        // Слева: Игрок
        JPanel playerPanel = createPlayerPanel(human, true);
        add(playerPanel, BorderLayout.WEST);

        // Справа: Враг
        JPanel enemyPanel = createPlayerPanel(enemy, false);
        add(enemyPanel, BorderLayout.EAST);

        // Центр: очки/опыт, лог, ход, статус
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        // Снизу: кнопки действий
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnAttack = new JButton("Атаковать");
        btnDefend = new JButton("Защититься");
        btnItems = new JButton("Предметы");
        buttonPanel.add(btnAttack);
        buttonPanel.add(btnDefend);
        buttonPanel.add(btnItems);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    

    private JPanel createPlayerPanel(Player player, boolean isHuman) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(150, 0));

        // Прогрессбар HP
        JProgressBar hpBar = new JProgressBar(0, player.getMaxHealth());
        hpBar.setValue(player.getHealth());
        hpBar.setStringPainted(true);
        if(isHuman) playerHpBar = hpBar;
        else enemyHpBar = hpBar;

        panel.add(new JLabel(isHuman ? "Игрок" : "Враг", SwingConstants.CENTER));
        panel.add(hpBar);
        panel.add(Box.createVerticalStrut(6));

        // Урон и Уровень
        JLabel lblDamage = new JLabel("Урон: " + player.getDamage());
        JLabel lblLevel = new JLabel("Уровень: " + player.getLevel());
        if(isHuman) {
            lblPlayerDamage = lblDamage;
            lblPlayerLevel = lblLevel;
        } else {
            lblEnemyDamage = lblDamage;
            lblEnemyLevel = lblLevel;
        }
        panel.add(lblDamage);
        panel.add(lblLevel);
        panel.add(Box.createVerticalStrut(10));

        // Картинка (иконку можешь пока не ставить)
        JLabel picLabel = new JLabel();
        // picLabel.setIcon( ... ); // Укажи, если будет картинка!
        panel.add(picLabel);

        // Имя
        JLabel lblName = new JLabel(player.getName() + (isHuman ? " (вы)" : " (враг)"), SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 13));
        if(isHuman) lblPlayerName = lblName; else lblEnemyName = lblName;
        panel.add(lblName);

        panel.add(Box.createVerticalGlue());
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Очки и опыт
        playerScoreLabel = new JLabel("Очки: " + 50);
        playerExpLabel = new JLabel("Опыт: " + 10 + "/" + 40);
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topRow.add(playerScoreLabel);
        topRow.add(Box.createHorizontalStrut(16));
        topRow.add(playerExpLabel);
        center.add(topRow);

        // Лог событий
        logArea = new JTextArea(7, 24);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        center.add(scrollPane);

        // Ход и оглушение
        turnLabel = new JLabel("Ход: игрока");
        stunLabel = new JLabel("Оглушен: нет");
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.add(turnLabel);
        statusPanel.add(Box.createHorizontalStrut(10));
        statusPanel.add(stunLabel);
        center.add(statusPanel);

        return center;
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
        playerHpBar.setValue(human.getHealth());
        enemyHpBar.setValue(enemy.getHealth());
        lblPlayerDamage.setText("Урон: " + human.getDamage());
        lblPlayerLevel.setText("Уровень: " + human.getLevel());
        lblEnemyDamage.setText("Урон: " + enemy.getDamage());
        lblEnemyLevel.setText("Уровень: " + enemy.getLevel());
    }

    public void onAttackClicked(ActionEvent e) {
        System.out.println("Button 'Attack' pressed");
        game.fight.performPlayerAction(human, enemy, AttackType.ATTACK);
        updateHealthLabels();
        checkWinCondition();
        checkLoseCondition();
        System.out.println("-------------------");
    }

    public void onDefendClicked(ActionEvent e) {
        System.out.println("Button 'Defend' pressed");
        game.fight.performPlayerAction(human, enemy, AttackType.DEFEND);
        updateHealthLabels();
        checkWinCondition();
        checkLoseCondition();
        /*if (checkIfPlayerStunned()){
            System.out.println("Player skips turn");
            game.fight.performPlayerAction(human, enemy, AttackType.ATTACK);//впоследствии метод в методе надо будет 
                                                                            //разбить, т.к. он выполняет много логики
                                                                            //тут будет обрабатываться метод, 
                                                                            //который соответствует состоянию оглушения игрока, который будет находиться в это классе
                                                                            //но метод обработки оглушений будет в Fight
        }*/ // этого не должно быть, из за условия в классе Fight
        System.out.println("-------------------");
    }

    public void onItemsClicked(ActionEvent e) {
        System.out.println("Button 'Items' pressed.");
        InventoryDialog inventoryDialog = new InventoryDialog(this);
        inventoryDialog.setVisible(true);
    }

    private void checkWinCondition() {
        if (enemy.getHealth() <= 0) {
            //WinDialog winDialog = new WinDialog(this);
            //winDialog.setVisible(true);
            setVisible(false);
        }
    }

    private void checkLoseCondition() {
        if (human.getHealth() <= 0) {
            JOptionPane.showMessageDialog(this, "Вы проиграли!");
            //логика начала боя заново с тем же врагом
            System.out.println("-------------------");
            System.out.println("Fight restarts");
            System.out.println("-------------------");
            resetBattle();
            //dispose(); // Закрыть окно боя
        }
    }
    
    private void resetBattle() {
        // Сбрасываем здоровье к максимальному
        human.setNewHealth(human.getMaxHealth());
        enemy.setNewHealth(enemy.getMaxHealth());

        // Очищаем статусы, если есть
        human.resetStatus(); // например, isStunned = false;
        enemy.resetStatus();

        updateHealthLabels();
    }
    
    private boolean checkIfPlayerStunned(){
        return human.isStunned();
    }

}