/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

//ADD IMAGE!!!
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.List;
/**
 *
* @author Мария
 */
public class Fight {

    Boolean isPlayersTurn = TRUE;

    
    private ActionType getLastPlayerAction(Player human) {
        List<ActionType> history = human.getPlayerActionsHistory();
        if (history.isEmpty()) return ActionType.DEFEND; // По умолчанию
        return history.get(history.size() - 1);
    }
    
    public boolean getIsPlayerTurn(){
        return this.isPlayersTurn;
    }


    public void performPlayerAction(Player human, Player enemy, ActionType action) {
        System.out.println("player is first to perform action: " + isPlayersTurn);
        switch (action) {
            case ATTACK -> processPlayersAttack(human, enemy);
            case DEFEND -> processPlayersDefend(human, enemy);
            default -> throw new IllegalArgumentException("Такого типа соперника нет: " + action);
        }
    }

    private void processPlayersAttack(Player human, Player enemy) {
        if (isPlayersTurn && human.isStunned()) {
            System.out.println("player is skipping turn!");
            ActionType enemyBehaviour = ActionType.ATTACK;
            System.out.println("enemy chose: " + enemyBehaviour);
            human.setHealth(human.getHealth() - enemy.attackEnemy());
            human.setStunned(false);
            isPlayersTurn = FALSE;
            return;
        } else if (!isPlayersTurn && enemy.isStunned()) {
            System.out.println("enemy is skipping turn!");
            enemy.setHealth(enemy.getHealth() - (int) (human.attackEnemy()));
            enemy.setStunned(false);
            isPlayersTurn = true;
            return;
        } else {
            ActionType enemyBehaviour = CharacterAction.ChooseEnemyBehavior(human, enemy);
            System.out.println("enemy chose: " + enemyBehaviour);

            switch (enemyBehaviour) {
                case DEFEND:
                    if (isPlayersTurn){//этот иф верный
                        human.setHealth(human.getHealth() - (int) (enemy.attackEnemy() * 0.5));
                        enemy.defendFromEnemy();
                        System.out.println("Enemy counter attacked");
                        isPlayersTurn = FALSE;
                    } else {
                        human.attackEnemy();
                        enemy.defendFromEnemy();
                        System.out.println("Player attacked, but enemy blocked");
                        isPlayersTurn = TRUE;
                    }
                    break;

                case ATTACK:
                    if (isPlayersTurn){
                        enemy.setHealth(enemy.getHealth() - human.attackEnemy());
                        isPlayersTurn = FALSE;
                    } else {
                        human.setHealth(human.getHealth() - enemy.attackEnemy());
                        isPlayersTurn = TRUE;
                    }
                    break;
            }
        }
    }

    private void processPlayersDefend(Player human, Player enemy) {
        if (isPlayersTurn && human.isStunned()) {
            System.out.println("player is skipping turn!");
            ActionType enemyBehaviour = ActionType.ATTACK;
            System.out.println("enemy chose: " + enemyBehaviour);
            human.setHealth(human.getHealth() - enemy.attackEnemy());
            human.setStunned(false);
            isPlayersTurn = FALSE;
            return;
        } else if (!isPlayersTurn && enemy.isStunned()) {
            System.out.println("enemy is skipping turn!");
            enemy.setStunned(false);
            isPlayersTurn = true;
            return;
        } else {
            ActionType enemyBehaviour = CharacterAction.ChooseEnemyBehavior(human, enemy);
            System.out.println("enemy chose: " + enemyBehaviour);
            switch (enemyBehaviour) {
                case ATTACK:
                    if (isPlayersTurn){
                        if(enemy instanceof ShaoKahn){
                            double breakingBlockP = Math.random();
                            if(breakingBlockP < 0.15){
                                human.setHealth(human.getHealth() - (int) (human.attackEnemy() * 0.5));
                                enemy.attackEnemy();
                                human.defendFromEnemy();
                                System.out.println("Shao Kahn broke the block!");
                            } else {
                                enemy.attackEnemy();
                                human.defendFromEnemy();
                                System.out.println("Enemy attacked, but player blocked");
                            }
                        } else {
                            enemy.attackEnemy();
                            human.defendFromEnemy();
                            System.out.println("Enemy attacked, but player blocked");
                        }
                        isPlayersTurn = FALSE;
                    } else {//этот иф верный
                        enemy.setHealth(enemy.getHealth() - (int) (human.attackEnemy() * 0.5));
                        System.out.println("Player counter attacked");
                        human.defendFromEnemy();
                        isPlayersTurn = TRUE;
                    }
                    break;

                case DEFEND:
                    if (isPlayersTurn){
                        if (Math.random() < 0.5) {//этот иф правильный
                            enemy.setStunned(true); // добавь поле и обработку stun-статуса
                            System.out.println("enemy is stunned!");
                        }
                        isPlayersTurn = FALSE;
                    } else {
                        if (Math.random() < 0.5) {//этот иф правильный
                            human.setStunned(true); // добавь поле и обработку stun-статуса
                            System.out.println("player is stunned!");
                        }
                        isPlayersTurn = TRUE;
                    }
                    break;
            }
        }
    }
    
}