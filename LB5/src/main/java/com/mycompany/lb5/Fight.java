/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

//ADD IMAGE!!!
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

/**
 *
* @author Мария
 */
public class Fight {

    ChangeTexts change = new ChangeTexts();
    int kind_attack[] = {0};
    int experiences[] = {40, 90, 180, 260, 410};
    EnemyFactory fabric = new EnemyFactory();
    Boolean isPlayersTurn = TRUE;
    //Boolean isEnemyTurn = FALSE;
    int k = -1;
    int stun = 0;
    double v = 0.0;
    int i;

    //этот метод нужно удалить и перенести логику пошагового боя в методы с процесс эктион
    /*public void Move(Player human, Player enemy, JLabel labelHuman, JLabel labelEnemy) {
        if (!isPlayersTurn) return; // Прерывание, если сейчас не ход игрока

        if (enemy.isStunned()) {
            // Враг пропускает ход
            enemy.setStunned(false);
            labelEnemy.setText(enemy.getName() + " оглушён и пропустил ход.");
            isPlayersTurn = true; // Переход хода к игроку
            return;
        }

        AttackType enemyAction = CharacterAction.ChooseEnemyBehavior(human, enemy);
        AttackType playerAction = getLastPlayerAction(human); // метод, сохраняющий последнее действие игрока

        switch (playerAction) {
            case ATTACK:
                if (enemyAction == AttackType.DEFEND) {
                    // Контрудар
                    human.setHealth(human.getHealth() - (int)(enemy.getDamage() * 0.5));
                    labelEnemy.setText(enemy.getName() + " контратаковал.");
                } else {
                    // Обычная атака
                    enemy.setHealth(enemy.getHealth() - human.getDamage());
                    labelEnemy.setText("Вы нанесли урон " + enemy.getName());
                }
                break;

            case DEFEND:
                if (enemyAction == AttackType.ATTACK) {
                    // Враг атакует, но ты защищаешься — урона нет
                    labelEnemy.setText("Вы блокировали атаку " + enemy.getName());
                } else {
                    // Оба защищаются — возможен стан
                    if (Math.random() < 0.5) {
                        enemy.setStunned(true);
                        labelEnemy.setText(enemy.getName() + " оглушён из-за двойной защиты!");
                    } else {
                        labelEnemy.setText("Оба оборонялись. Ничего не произошло.");
                    }
                }
                break;
        }

        // Обновление текста состояния игрока
        labelHuman.setText(human.getName() + ": " + human.getHealth() + "/" + human.getMaxHealth());
        isPlayersTurn = true; // Теперь снова ход игрока
    }*/
    
    private AttackType getLastPlayerAction(Player human) {
        List<AttackType> history = human.getPlayerActionsHistory();
        if (history.isEmpty()) return AttackType.DEFEND; // По умолчанию
        return history.get(history.size() - 1);
    }


    public void performPlayerAction(Player human, Player enemy, AttackType action) {
        System.out.println(isPlayersTurn);
        switch (action) {
            case ATTACK -> processPlayersAttack(human, enemy);
            case DEFEND -> processPlayersDefend(human, enemy);
            default -> throw new IllegalArgumentException("Такого типа соперника нет: " + action);
        }
    }
    
    
    //Если игрок в стане - то тогда враг либо просто наносит ему урон, либо враг не делает ничего в случае защиты
    //Если враг в стане - то тогда игрок либо наносит ему урон, либо ничего ему не делает при варианте защиты
    //в этих двух методах надо вынести первые два if'а в отдельный метод, делающий проверку на оглушение
    
    private void processPlayersAttack(Player human, Player enemy) {
        if (isPlayersTurn && human.isStunned()) { //если ход игрока и игрок в стане - то враг либо атакует либо защищается
            System.out.println(human.getName() + " is skipping turn!");
            AttackType enemyBehaviour = CharacterAction.ChooseEnemyBehavior(human, enemy);
            switch (enemyBehaviour) {
                case DEFEND -> enemy.defendFromEnemy();
                case ATTACK -> human.setHealth(human.getHealth() - enemy.attackEnemy());
            }
            human.setStunned(false);
            isPlayersTurn = FALSE;
            return;
        } else if (!isPlayersTurn && enemy.isStunned()) { //если ход врага и он пропускате ход из за стана - то игрок 
                                                            //только атакует его тк метод обрабатывает именно атаку игрока
            System.out.println(enemy.getName() + " is skipping turn!");
            AttackType humanBehaviour = AttackType.ATTACK;//оно задается но не используется в методе
            enemy.setHealth(enemy.getHealth() - (int) (human.attackEnemy() * 0.5));
            enemy.setStunned(false);
            isPlayersTurn = true;
            return;
        } else { //никто не в стане и все делают последовательно свои шаги
            AttackType humanBehaviour = AttackType.ATTACK;//оно задается но не используется в методе
            AttackType enemyBehaviour = CharacterAction.ChooseEnemyBehavior(human, enemy);
            //enemy.setHealth(enemy.getHealth() - human.attackEnemy());
            System.out.println(enemyBehaviour);
            switch (enemyBehaviour) {

                case DEFEND:
                    // Контрудар — 50% урона врага
                    //добавить логику хода игрока или врага
                    if (isPlayersTurn){
                        human.setHealth(human.getHealth() - (int) (enemy.attackEnemy() * 0.5));
                        enemy.defendFromEnemy();
                        //human.attackEnemy();
                        isPlayersTurn = FALSE;
                    } else {
                        enemy.setHealth(enemy.getHealth() - (int) (human.attackEnemy() * 0.5));
                        human.defendFromEnemy();
                        //enemy.attackEnemy();
                        isPlayersTurn = TRUE;
                    }
                    break;

                case ATTACK:
                    // Обычная атака — урон от игрока
                    //добавить логику хода игрока или врага
                    if (isPlayersTurn){
                        enemy.setHealth(enemy.getHealth() - human.attackEnemy());
                        isPlayersTurn = FALSE;
                    } else {
                        human.setHealth(human.getHealth() - enemy.attackEnemy());
                        isPlayersTurn = TRUE;
                    }
                    //enemy.setHealth(enemy.getHealth() - human.attackEnemy());
                    break;
            }
        }
        //isPlayersTurn = FALSE;
    }

    private void processPlayersDefend(Player human, Player enemy) {
        if (isPlayersTurn && human.isStunned()) { //если ход игрока и игрок в стане - то враг либо атакует либо защищается
            System.out.println(human.getName() + " is skipping turn!");
            AttackType enemyBehaviour = CharacterAction.ChooseEnemyBehavior(human, enemy);
            switch (enemyBehaviour) {
                case DEFEND -> enemy.defendFromEnemy();
                case ATTACK -> human.setHealth(human.getHealth() - enemy.attackEnemy());
            }
            human.setStunned(false);
            isPlayersTurn = FALSE;
            return;
        } else if (!isPlayersTurn && enemy.isStunned()) { //если ход врага и он пропускате ход из за стана - то игрок 
                                                            //только атакует его тк метод обрабатывает именно атаку игрока
            System.out.println(enemy.getName() + " is skipping turn!");
            AttackType humanBehaviour = AttackType.ATTACK;//оно задается но не используется в методе
            enemy.setHealth(enemy.getHealth() - (int) (human.attackEnemy() * 0.5));
            enemy.setStunned(false);
            isPlayersTurn = true;
            return;
        } else {
            AttackType humanBehaviour = AttackType.DEFEND;
            AttackType enemyBehaviour = CharacterAction.ChooseEnemyBehavior(human, enemy);
            System.out.println(enemyBehaviour);
            switch (enemyBehaviour) {
                case ATTACK:
                    // Игрок защищается — ничего не происходит
                    //добавить логику хода игрока или врага
                    if (isPlayersTurn){
                        human.setHealth(human.getHealth() - (int) (enemy.attackEnemy() * 0.5));
                        enemy.defendFromEnemy();
                        //human.attackEnemy();
                        isPlayersTurn = FALSE;
                    } else {
                        enemy.setHealth(enemy.getHealth() - (int) (human.attackEnemy() * 0.5));
                        human.defendFromEnemy();
                        //enemy.attackEnemy();
                        isPlayersTurn = TRUE;
                    }
                    break;

                case DEFEND:
                    // Оба защищаются — шанс оглушения врага
                    //добавить логику хода игрока или врага
                    if (isPlayersTurn){
                        if (Math.random() < 0.5) {
                            enemy.setStunned(true); // добавь поле и обработку stun-статуса
                            System.out.println(enemy.getName() + " is stunned!");
                        }
                        isPlayersTurn = FALSE;
                    } else {
                        if (Math.random() < 0.5) {
                            human.setStunned(true); // добавь поле и обработку stun-статуса
                            System.out.println(human.getName() + " is stunned!");
                        }
                        isPlayersTurn = TRUE;
                    }
                    break;
            }
        }
        //isPlayersTurn = FALSE;
    }

    public void EndRound(Player human, Player enemy, JDialog dialog, JLabel label,
            CharacterAction action, Items[] items) {

        dialog.setVisible(true);
        dialog.setBounds(300, 150, 700, 600);
        if (human.getHealth() > 0) {
            label.setText("You win");
            ((Human) human).setWin();

            if (enemy instanceof ShaoKahn) {
                action.AddItems(38, 23, 8, items);
                action.AddPointsBoss(((Human) human), action.getEnemyes());
            } else {
                action.AddItems(25, 15, 5, items);
                action.AddPoints(((Human) human), action.getEnemyes());
            }
        } else {
            label.setText(enemy.getName() + " win");
        }

        isPlayersTurn = TRUE;
        k = -1;
        kind_attack = ResetAttack();

    }

    public void EndFinalRound(Human human, CharacterAction action,
            ArrayList<Result> results, JDialog dialog1, JDialog dialog2, JFrame frame,
            JLabel label1, JLabel label2) {
        String text = "Победа не на вашей стороне";
        if (human.getHealth() > 0) {
            human.setWin();
            action.AddPoints(human, action.getEnemyes());
            text = "Победа на вашей стороне";
        }
        boolean top = false;
        if (results == null) {
            top = true;
        } else {
            int i = 0;
            for (int j = 0; j < results.size(); j++) {
                if (human.getPoints() < results.get(j).getPoints()) {
                    i++;
                }
            }
            if (i < 10) {
                top = true;
            }
        }
        if (top) {
            dialog1.setVisible(true);
            dialog1.setBounds(150, 150, 600, 500);
            label1.setText(text);
        } else {
            dialog2.setVisible(true);
            dialog2.setBounds(150, 150, 470, 360);
            label2.setText(text);
        }
        frame.dispose();
    }

    public int[] ResetAttack() {
        int a[] = {0};
        return a;
    }

    public Player NewRound(Player human, JLabel label, JProgressBar pr1,
            JProgressBar pr2, JLabel label2, JLabel text, JLabel label3, CharacterAction action) {

        Player enemy1 = null;
        if (((Human) human).getWin() == 6 | ((Human) human).getWin() == 11) {
            enemy1 = action.ChooseBoss(label, label2, text, label3, human.getLevel());
        } else {
            enemy1 = action.ChooseEnemy(/*label, label2, text, label3*/);
        }
        pr1.setMaximum(human.getMaxHealth());
        pr2.setMaximum(enemy1.getMaxHealth());
        human.setNewHealth(human.getMaxHealth());
        enemy1.setNewHealth(enemy1.getMaxHealth());
        action.HP(human, pr1);
        action.HP(enemy1, pr2);
        return enemy1;
    }

    

}
