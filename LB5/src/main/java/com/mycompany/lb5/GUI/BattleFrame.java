/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5.GUI;

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
/**
 * Класс {@code BattleFrame} представляет собой окно пошагового боя между игроком (объектом {@link Human})
 * и списком врагов ({@link Player}). Отображает здоровье, урон, уровень и состояния игроков, 
 * а также предоставляет кнопки для действий игрока: атака, защита, дебафф и использование предметов.
 * 
 * <p>Класс также обрабатывает переход к следующему врагу после победы, получение опыта, предметов,
 * и обработку проигрыша с возможностью воскрешения.</p>
 *
 * <p>Ключевые функции:</p>
 * <ul>
 *     <li>Обработка логики боя (атака, защита, дебафф)</li>
 *     <li>Обновление интерфейса в реальном времени</li>
 *     <li>Отображение опыта, состояния, побед, уровней и получаемых предметов</li>
 *     <li>Переход между врагами в рамках одной локации</li>
 *     <li>Уровневый рост игрока</li>
 * </ul>
 * 
 * @author 
 */
public class BattleFrame extends JFrame {

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
    
    public BattleFrame(Human human, List<Player> enemyList, Game game, int currentLocation, int totalLocations) {
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
    
    /**
    * Инициализирует и размещает основные визуальные компоненты окна боя:
    * заголовок, панели игрока и врага, центральную панель с логом и индикаторами,
    * а также нижнюю панель с кнопками действий.
    */
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
    
    /**
    * Создает панель с информацией об игроке или враге.
    *
    * @param player Объект игрока или врага.
    * @param isHuman true, если панель создается для игрока; false — для врага.
    * @return JPanel с визуальным представлением игрока/врага.
    */
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
            icon = new ImageIcon(getClass().getResource(human.getIconSource()));
            playerIconLabel = new JLabel("Текст", icon, JLabel.CENTER);
            panel.add(playerIconLabel);
        } else {
            icon = new ImageIcon(getClass().getResource(enemy.getIconSource()));
            enemyIconLabel = new JLabel("Текст", icon, JLabel.CENTER);
            panel.add(enemyIconLabel);
        }
        panel.add(Box.createVerticalGlue());
        return panel;
    }
    
    /**
    * Создает центральную панель, содержащую лог событий, индикаторы очков, опыта
    * и текущего состояния боя (чей ход, оглушение).
    *
    * @return JPanel, содержащая лог боя и индикаторы состояния.
    */
    private JPanel createCenterPanel() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        playerScoreLabel = new JLabel("Очки: " + human.getPoints());
        playerExpLabel = new JLabel("Опыт: " + human.getExperience() + "/" + human.getRequiredExperience());
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
    
    /**
    * Привязывает обработчики действий к кнопкам: атака, защита, ослабление, предметы.
    */
    private void setupActions() {
        btnAttack.addActionListener(this::onAttackClicked);
        btnDefend.addActionListener(this::onDefendClicked);
        btnDebuff.addActionListener(this::onDebuffClicked);
        btnItems.addActionListener(this::onItemsClicked);
    }
    
    /**
    * Обновляет все визуальные элементы, отражающие текущее состояние игроков:
    * здоровье, уровень, урон, статус оглушения и доступность кнопки "Ослабить".
    * Также изменяет цвет полосы здоровья в зависимости от текущего состояния.
    */
    void updateLabels() {
        playerHpBar.setValue(human.getHealth());
        enemyHpBar.setValue(enemy.getHealth());
        if(human.getHealth() < human.getMaxHealth() * 0.25){
            playerHpBar.setForeground(Color.RED);
        } else {
            playerHpBar.setForeground(Color.GREEN);
        }
        if(enemy.getHealth() < enemy.getMaxHealth() * 0.25){  
            enemyHpBar.setForeground(Color.RED);
        } else {
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
    
    /**
    * Обработчик нажатия кнопки "Атаковать".
    * Выполняет атаку игрока, обновляет интерфейс, обрабатывает эффекты дебаффов,
    * проверяет условия победы и поражения, а также переключает ход.
    *
    * @param e Событие нажатия кнопки.
    */
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
   
    /**
    * Обработчик нажатия кнопки "Защититься".
    * Выполняет действие защиты, обновляет интерфейс, учитывает пропуск снижения дебаффа
    * при определённых условиях, проверяет условия победы и поражения.
    *
    * @param e Событие нажатия кнопки.
    */
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
    
    /**
    * Обработчик нажатия кнопки "Ослабить".
    * Выполняет дебафф противника, обновляет интерфейс и проверяет условия окончания боя.
    *
    * @param e Событие нажатия кнопки.
    */
    private void onDebuffClicked(ActionEvent e) {
        updateLabels();
        
        String battleLog = game.fight.performPlayerAction(human, enemy, ActionType.DEBUFF);
        logArea.append(battleLog);

        updateLabels();
        checkWinCondition();
        checkLoseCondition();
        
        turnLabel.setText("Ход игрока: " + game.fight.getIsPlayerTurn());
    }
    
    /**
    * Обработчик нажатия кнопки "Предметы".
    * Открывает диалоговое окно инвентаря и обновляет интерфейс после возможного применения предметов.
    *
    * @param e Событие нажатия кнопки.
    */
    public void onItemsClicked(ActionEvent e) {
        InventoryDialog inventoryDialog = new InventoryDialog(this);
        inventoryDialog.setVisible(true);
        updateLabels();
    }
    
    /**
    * Проверяет условие победы игрока: если здоровье врага <= 0,
    * начисляет очки, опыт, дропает предметы, запускает следующего врага или завершает игру.
    */
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
            playerExpLabel.setText("Опыт: " + human.getExperience() + "/" + human.getRequiredExperience());
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
    
    /**
    * Обрабатывает выпадение предметов после победы.
    * Шанс выпадения зависит от типа врага. Возможные предметы:
    * малое зелье лечения, большое зелье лечения и крест возрождения.
    */
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

    /**
    * Проверяет, достиг ли игрок нового уровня.
    * Если достиг — повышает уровень, предлагает выбор улучшения и обновляет характеристики врагов.
    */
    public void checkLevelUpdate(){
        if (human.getExperience() >= human.getRequiredExperience()){
            human.levelUp();
            showLevelUpDialog();
            human.setRequiredExperiance();
            for(Player enemy: enemyList){
                enemy.updateCharacteristicsBasedOnLevel(human.getLevel());
            }
        }
    }
    
    /**
    * Отображает диалог повышения уровня, предлагая игроку выбор между увеличением урона и здоровья.
    * Применяет выбранное улучшение и обновляет интерфейс.
    */
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

    /**
    * Проверяет условие поражения игрока.
    * Если здоровье игрока <= 0, пытается использовать крест воскрешения.
    * Если не удалось воскреснуть — сбрасывает битву.
    */
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
    
    /**
    * Пытается воскресить игрока, если в инвентаре есть крест воскрешения.
    * Восстанавливает 5% от максимального здоровья. Показывает сообщение о воскрешении.
    *
    * @return true, если воскрешение произошло, иначе false.
    */
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
    
    /**
    * Сбрасывает состояние боя: восстанавливает здоровье, урон, статус,
    * иконки, полоски здоровья, и очищает лог. Применяется при начале новой битвы
    * или после воскрешения/поражения.
    */
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