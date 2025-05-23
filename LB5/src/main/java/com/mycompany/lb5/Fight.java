/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

//ADD IMAGE!!!
import static com.mycompany.lb5.ActionType.HEAL;
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


    public String performPlayerAction(Player human, Player enemy, ActionType action) {
        String battleLog = null;
        switch (action) {
            case ATTACK -> battleLog = processPlayersAttack(human, enemy);
            case DEFEND -> battleLog = processPlayersDefend(human, enemy);
        }
        return battleLog;
    }

    private String processPlayersAttack(Player human, Player enemy) {
        StringBuilder log = new StringBuilder();
        
        if (isPlayersTurn && human.isStunned()) {
            log.append("Игрок пропускает ход из-за оглушения!\n");
            ActionType enemyBehaviour = ActionType.ATTACK;
            log.append("Противник выбрал: ").append(enemyBehaviour).append("\n");
            human.setHealth(human.getHealth() - enemy.attackEnemy());
            human.setStunned(false);
            isPlayersTurn = FALSE;
            log.append("-----------------\n");
            return log.toString();
        } else if (!isPlayersTurn && enemy.isStunned()) {
            log.append("Противник пропускает ход из-за оглушения!\n");
            enemy.setHealth(enemy.getHealth() - (int) (human.attackEnemy()));
            enemy.setStunned(false);
            isPlayersTurn = true;
            log.append("-----------------\n");
            return log.toString();
        } else {
            ActionType enemyBehaviour = CharacterAction.ChooseEnemyBehavior(human, enemy);
            log.append("Противник выбрал: ").append(enemyBehaviour).append("\n");

            switch (enemyBehaviour) {
                case DEFEND:
                    if (isPlayersTurn){//этот иф верный
                        human.setHealth(human.getHealth() - (int) (enemy.attackEnemy() * 0.5));
                        enemy.defendFromEnemy();
                        log.append("Противник контратаковал!\n");
                        isPlayersTurn = FALSE;
                    } else {
                        human.attackEnemy();
                        enemy.defendFromEnemy();
                        log.append("Игрок атаковал, но противник заблокировал удар!\n");
                        isPlayersTurn = TRUE;
                    }
                    break;

                case ATTACK:
                    if (isPlayersTurn){
                        enemy.setHealth(enemy.getHealth() - human.attackEnemy());
                        log.append("Игрок атаковал, противник получил урон!\n");
                        isPlayersTurn = FALSE;
                    } else {
                        human.setHealth(human.getHealth() - enemy.attackEnemy());
                        log.append("Противник атаковал, игрок получил урон!\n");
                        isPlayersTurn = TRUE;
                    }
                    break;
                    
                case HEAL:
                    if (isPlayersTurn){
                        enemy.setHealth(enemy.getHealth() - human.attackEnemy()*2);
                        isPlayersTurn = FALSE;
                    } else {
                        enemy.setHealth(enemy.getHealth() - human.attackEnemy()*2);
                        isPlayersTurn = TRUE;
                    }
                    log.append("Противник получил двойной урон!\n");
                    break;
            }
        }
        log.append("-----------------\n");
        return log.toString();
    }

    private String processPlayersDefend(Player human, Player enemy) {
        StringBuilder log = new StringBuilder();
        
        if (isPlayersTurn && human.isStunned()) {
            log.append("Игрок пропускает ход из-за оглушения!\n");
            ActionType enemyBehaviour = ActionType.ATTACK;
            log.append("Противник выбрал: ").append(enemyBehaviour).append("\n");
            human.setHealth(human.getHealth() - enemy.attackEnemy());
            human.setStunned(false);
            isPlayersTurn = FALSE;
            log.append("-----------------\n");
            return log.toString();
        } else if (!isPlayersTurn && enemy.isStunned()) {
            log.append("Противник пропускает ход из-за оглушения!\n");
            enemy.setStunned(false);
            isPlayersTurn = true;
            log.append("-----------------\n");
            return log.toString();
        } else {
            ActionType enemyBehaviour = CharacterAction.ChooseEnemyBehavior(human, enemy);
            log.append("Противник выбрал: ").append(enemyBehaviour).append("\n");
            switch (enemyBehaviour) {
                case ATTACK:
                    if (isPlayersTurn){
                        if(enemy instanceof ShaoKahn){
                            double breakingBlockP = Math.random();
                            if(breakingBlockP < 0.15){
                                human.setHealth(human.getHealth() - (int) (human.attackEnemy() * 0.5));
                                enemy.attackEnemy();
                                human.defendFromEnemy();
                                log.append("Shao Kahn прорвал блок и нанес урон!\n");
                            } else {
                                enemy.attackEnemy();
                                human.defendFromEnemy();
                                log.append("Противник атаковал, но игрок заблокировал удар!\n");
                            }
                        } else {
                            enemy.attackEnemy();
                            human.defendFromEnemy();
                             log.append("Противник атаковал, но игрок заблокировал удар!\n");
                        }
                        isPlayersTurn = FALSE;
                    } else {//этот иф верный
                        enemy.setHealth(enemy.getHealth() - (int) (human.attackEnemy() * 0.5));
                        log.append("Игрок контратаковал!\n");
                        human.defendFromEnemy();
                        isPlayersTurn = TRUE;
                    }
                    break;

                case DEFEND:
                    if (isPlayersTurn){
                        if (Math.random() < 0.5) {//этот иф правильный
                            enemy.setStunned(true); // добавь поле и обработку stun-статуса
                            log.append("Противник оглушён!\n");
                        }
                        isPlayersTurn = FALSE;
                    } else {
                        if (Math.random() < 0.5) {//этот иф правильный
                            human.setStunned(true); // добавь поле и обработку stun-статуса
                            log.append("Игрок оглушён!\n");
                        }
                        isPlayersTurn = TRUE;
                    }
                    break;
                    
                case HEAL:
                    if (isPlayersTurn){
                        enemy.setHealth((int) (enemy.getHealth() + (enemy.getMaxHealth() - enemy.getHealth()) * 0.5));
                        isPlayersTurn = FALSE;
                    } else {
                        enemy.setHealth((int) (enemy.getHealth() + (enemy.getMaxHealth() - enemy.getHealth()) * 0.5));
                        isPlayersTurn = TRUE;
                    }
                    log.append("Противник восстановил здоровье!\n");
                    break;
            }
        }
        log.append("-----------------\n");
        return log.toString();
    }
    
}