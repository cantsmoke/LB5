/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5.GUI;
/**
 *
 * @author Арсений
 */
import com.mycompany.lb5.ActionType;
import com.mycompany.lb5.BigHealthPotion;
import com.mycompany.lb5.Game;
import com.mycompany.lb5.Player;
import com.mycompany.lb5.ShaoKahn;
import com.mycompany.lb5.Human;
import com.mycompany.lb5.RessurectionCross;
import com.mycompany.lb5.SmallHealthPotion;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;

public class BattleFrame2 extends JFrame {

    private JButton btnAttack;
    private JButton btnDefend;
    private JButton btnItems;
    private JButton btnDebuff;
    private JProgressBar playerHpBar, enemyHpBar;
    private JLabel lblPlayerDamage, lblPlayerLevel;
    private JLabel lblEnemyDamage, lblEnemyLevel;
    private JLabel playerScoreLabel, playerExpLabel;
    private JTextArea logArea;
    private JLabel turnLabel, playerStunLabel, enemyStunLabel;
    private JLabel playerIconLabel, enemyIconLabel, humanNameLabel, enemyNameLabel;
    
    private final Human human;
    private Player enemy;
    private final Game game;
    private final int currentLocation;
    private final int totalLocations;
    private final List<Player> enemyList;
    private int currentEnemyIndex = 0;
    
    public BattleFrame2(Human human, List<Player> enemyList, Game game, int currentLocation, int totalLocations) {
        super("Битва");
        this.human = human;
        human.setHealth(human.getMaxHealth());
        this.enemyList = enemyList;
        this.enemy = enemyList.get(0);
        this.game = game;
        this.currentLocation = currentLocation;
        this.totalLocations = totalLocations;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        setupActions();
    }
    
    public int getCurrentLocation(){
        return this.currentLocation;
    }
    
    public int getTotalLocations(){
        return this.totalLocations;
    }
    
    public Human getHuman(){
        return this.human;
    }
    
    public Game getGame(){
        return this.game;
    }

    private void initializeComponents() {
        JLabel fightLabel = new JLabel("FIGHT; " + "Location №" + currentLocation, SwingConstants.CENTER);
        fightLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(fightLabel, BorderLayout.NORTH);

        JPanel playerPanel = createPlayerPanel(human, true);
        add(playerPanel, BorderLayout.WEST);

        JPanel enemyPanel = createPlayerPanel(enemy, false);
        add(enemyPanel, BorderLayout.EAST);

        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnAttack = new JButton("Атаковать");
        btnDefend = new JButton("Защититься");
        btnDebuff = new JButton("Ослабить");
        btnItems = new JButton("Предметы");
        buttonPanel.add(btnAttack);
        buttonPanel.add(btnDefend);
        buttonPanel.add(btnDebuff);
        buttonPanel.add(btnItems);
        btnDebuff.setEnabled(enemy.getDebuff() == 0 && human.getLevel() != 0);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createPlayerPanel(Player player, boolean isHuman) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(150, 0));

        JProgressBar hpBar = new JProgressBar(0, player.getMaxHealth());
        hpBar.setValue(player.getHealth());
        hpBar.setStringPainted(true);
        
        if(isHuman) playerHpBar = hpBar;
        else enemyHpBar = hpBar;
        hpBar.setForeground(Color.GREEN);
        
        if(isHuman) {
            humanNameLabel = new JLabel("Игрок " + player.getName(), SwingConstants.CENTER);
            panel.add(humanNameLabel);
        } else {
            enemyNameLabel = new JLabel("Враг " + player.getName(), SwingConstants.CENTER);
            panel.add(enemyNameLabel);
        }
        panel.add(hpBar);
        panel.add(Box.createVerticalStrut(6));

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
        ImageIcon icon = null;
        if(isHuman) {
            icon = new ImageIcon("C:\\Users\\Arseniy\\Documents\\GitHub\\LB5\\Kitana_in_MK1.png");
            playerIconLabel = new JLabel("Текст", icon, JLabel.CENTER);
            panel.add(playerIconLabel);
        } else {
            icon = new ImageIcon(player.getIconSource());
            enemyIconLabel = new JLabel("Текст", icon, JLabel.CENTER);
            panel.add(enemyIconLabel);
        }
        panel.add(Box.createVerticalGlue());
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        playerScoreLabel = new JLabel("Очки: " + human.getPoints());
        playerExpLabel = new JLabel("Опыт: " + human.getExperience() + "/" + human.getRequiredExperiance());
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topRow.add(playerScoreLabel);
        topRow.add(Box.createHorizontalStrut(16));
        topRow.add(playerExpLabel);
        center.add(topRow);

        logArea = new JTextArea(7, 24);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        center.add(scrollPane);

        turnLabel = new JLabel("Ход игрока: " + game.fight.getIsPlayerTurn());
        playerStunLabel = new JLabel("Игрок оглушен: " + human.isStunned());
        enemyStunLabel = new JLabel("Враг оглушен: "+ enemy.isStunned());
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.add(turnLabel);
        statusPanel.add(Box.createHorizontalStrut(10));
        statusPanel.add(playerStunLabel);
        statusPanel.add(enemyStunLabel);
        center.add(statusPanel);

        return center;
    }

    private void setupActions() {
        btnAttack.addActionListener(this::onAttackClicked);
        btnDefend.addActionListener(this::onDefendClicked);
        btnDebuff.addActionListener(this::onDebuffClicked);
        btnItems.addActionListener(this::onItemsClicked);
    }

    void updateLabels() {
        playerHpBar.setValue(human.getHealth());
        enemyHpBar.setValue(enemy.getHealth());
        if(human.getHealth() < human.getMaxHealth() * 0.25)
            playerHpBar.setForeground(Color.RED);
        else if(enemy.getHealth() < enemy.getMaxHealth() * 0.25){  
            enemyHpBar.setForeground(Color.RED);
        } else {
            playerHpBar.setForeground(Color.GREEN);
            enemyHpBar.setForeground(Color.GREEN);
        }
        if(enemy.getDebuff() != 0){
            enemyHpBar.setForeground(Color.BLUE);
        }
        if(human.getDebuff() != 0){
            playerHpBar.setForeground(Color.BLUE);
        }
        lblPlayerDamage.setText("Урон: " + human.getDamage());
        lblPlayerLevel.setText("Уровень: " + human.getLevel());
        lblEnemyDamage.setText("Урон: " + enemy.getDamage());
        lblEnemyLevel.setText("Уровень: " + enemy.getLevel());
        
        playerStunLabel.setText("Игрок оглушен: " + human.isStunned());
        enemyStunLabel.setText("Враг оглушен: "+ enemy.isStunned());
        
        btnDebuff.setEnabled(
            human.getDebuff() == 0 &&
            enemy.getDebuff() == 0 &&
            human.getLevel() != 0
        );
        
    }

    public void onAttackClicked(ActionEvent e) {
        updateLabels();
        String battleLog = game.fight.performPlayerAction(human, enemy, ActionType.ATTACK);
        logArea.append(battleLog);
        updateLabels();
        checkWinCondition();
        checkLoseCondition();

        enemy.reduceDebuffDuration();
        if(enemy.getDebuff() == 0 && human.getDebuff() == 0 ){
            enemy.resetDebuff(human);
        }

        human.reduceDebuffDuration();
        if(human.getDebuff() == 0 && enemy.getDebuff() == 0 ){
            human.resetDebuff(enemy);
        }
        
        updateLabels();
        turnLabel.setText("Ход игрока: " + game.fight.getIsPlayerTurn());
    }
   

    public void onDefendClicked(ActionEvent e) {
        updateLabels();    
        String battleLog = game.fight.performPlayerAction(human, enemy, ActionType.DEFEND);
        logArea.append(battleLog);
        updateLabels();
        checkWinCondition();
        checkLoseCondition();

        enemy.reduceDebuffDuration();
        if(enemy.getDebuff() == 0 && human.getDebuff() == 0 ){
            enemy.resetDebuff(human);
        }

        if (human.isSkipNextDebuffTurn()) {
            human.setSkipNextDebuffTurn(false);
        } else {
            human.reduceDebuffDuration();
        }
        if(human.getDebuff() == 0 && enemy.getDebuff() == 0 ){
            human.resetDebuff(enemy);
        }
        
        updateLabels();   
        playerStunLabel.setText("Игрок оглушен: " + human.isStunned());
        enemyStunLabel.setText("Враг оглушен: "+ enemy.isStunned());
        turnLabel.setText("Ход игрока: " + game.fight.getIsPlayerTurn());
    }
    
    private void onDebuffClicked(ActionEvent e) {
        updateLabels();
        
        String battleLog = game.fight.performPlayerAction(human, enemy, ActionType.DEBUFF);
        logArea.append(battleLog);

        updateLabels();
        checkWinCondition();
        checkLoseCondition();
        
        turnLabel.setText("Ход игрока: " + game.fight.getIsPlayerTurn());
    }
    
    public void onItemsClicked(ActionEvent e) {
        InventoryDialog inventoryDialog = new InventoryDialog(this);
        inventoryDialog.setVisible(true);
        updateLabels();
    }
    
    private void checkWinCondition() {
        if (enemy.getHealth() <= 0) {
            human.addWin();
            human.addPoints(enemy.getReceivedPoints());
            currentEnemyIndex++;
            int defeatedNumber = currentEnemyIndex;
            int totalEnemies = enemyList.size();
            JOptionPane.showMessageDialog(this, 
                "Побежден враг " + defeatedNumber + " из " + totalEnemies + "!",
                "Победа!", 
                JOptionPane.INFORMATION_MESSAGE);
            
            human.gainExperience(enemy.returnExperienceForWin());
            checkLevelUpdate();
            playerExpLabel.setText("Опыт: " + human.getExperience() + "/" + human.getRequiredExperiance());
            processItemDrop();
            logArea.setText("");
            if (currentEnemyIndex < enemyList.size()) {
                enemy = enemyList.get(currentEnemyIndex);
                resetBattle();
                logArea.append("\nСледующий враг: " + enemy.getName() + "\n");
                logArea.append("-----------------\n");
            } else {
                WinDialog winDialog = new WinDialog(this);
                winDialog.setVisible(true);
                setVisible(false);
            }
        }
    }
    
    private void processItemDrop() {
        double smallPotionDropP = Math.random();
        double bigPotionDropP = Math.random();
        double ressurectionCrossDropP = Math.random(); 
        double probabilityMultiplier = 1;
        if (enemy instanceof ShaoKahn){
            probabilityMultiplier = 1.5;
        }
        if (smallPotionDropP < 0.25 * probabilityMultiplier) {
            human.getInventory().addSmallHealthPotion(new SmallHealthPotion());
            JOptionPane.showMessageDialog(this, "Вам выпало: малое зелье лечения!");
        } else if (bigPotionDropP < 0.15 * probabilityMultiplier) {
            human.getInventory().addBigHealthPotion(new BigHealthPotion());
            JOptionPane.showMessageDialog(this, "Вам выпало: большое зелье лечения!");
        } else if (ressurectionCrossDropP < 0.05 * probabilityMultiplier) {
            human.getInventory().addRessurectionCross(new RessurectionCross());
            JOptionPane.showMessageDialog(this, "Вам выпал: крест возрождения!");
        }
    }

    public void checkLevelUpdate(){
        if (human.getExperience() >= human.getRequiredExperiance()){
            human.levelUp();
            showLevelUpDialog();
            human.setRequiredExperiance();
            for(Player enemy: enemyList){
                enemy.updateCharacteristicsBasedOnLevel(human.getLevel());
            }
        }
    }
    
    private void showLevelUpDialog() {
        Object[] options = {"Увеличить урон (+20%)", "Увеличить здоровье (+25%)"};
        int choice = JOptionPane.showOptionDialog(null,
                "Вы достигли уровня " + human.getLevel() + "! Выберите улучшение:",
                "Повышение уровня",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            human.setMaxDamage((int) (human.getMaxDamage() + human.getMaxDamage()*0.2));
        } else if (choice == 1) {
            human.setMaxHealth((int) (human.getMaxHealth() + human.getMaxHealth()*0.25));
        }
        updateLabels();
    }

    private void checkLoseCondition() {
        if (human.getHealth() <= 0) {
            boolean resurrected = tryRessurection();
            if (resurrected){
                updateLabels();
            } else {
                JOptionPane.showMessageDialog(this, "Вы проиграли!");
                resetBattle();
            }
        }
    }
    
    private boolean tryRessurection() {
        if (human.getInventory().getRessurectionCrossCount() > 0) {
            RessurectionCross ressurectionCross = human.getInventory().getRessurectionCross();
            int restored = (int) Math.ceil(human.getMaxHealth() * ressurectionCross.getHealKF());
            if (restored < 1) restored = 1;
            human.setHealth(restored);
            JOptionPane.showMessageDialog(this, 
                "Крест возрождения спасает вас! Ваше здоровье восстановлено на 5% (" + restored + " HP).",
                "Воскрешение", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    private void resetBattle() {
        human.setHealth(human.getMaxHealth());
        enemy.setHealth(enemy.getMaxHealth());

        human.resetStatus();
        enemy.resetStatus();

        human.resetDebuff(enemy);
        enemy.resetDebuff(human);
        
        human.setDamage(human.getMaxDamage());
        enemy.setDamage(enemy.getMaxDamage());
        
        playerIconLabel.setIcon(new ImageIcon(human.getIconSource()));
        enemyIconLabel.setIcon(new ImageIcon(enemy.getIconSource()));
        
        playerScoreLabel.setText("Очки: " + human.getPoints());
        
        enemyNameLabel.setText("Враг " + enemy.getName());
        
        playerHpBar.setMaximum(human.getMaxHealth());
        enemyHpBar.setMaximum(enemy.getMaxHealth());

        updateLabels();
        logArea.setText("");
        
        playerStunLabel.setText("Игрок оглушен: " + human.isStunned());
        enemyStunLabel.setText("Враг оглушен: "+ enemy.isStunned());
        
        playerHpBar.setForeground(Color.GREEN);
        enemyHpBar.setForeground(Color.GREEN);
    }
}