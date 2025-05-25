/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

import static com.mycompany.lb5.ActionType.ATTACK;
import static com.mycompany.lb5.ActionType.DEBUFF;
import static com.mycompany.lb5.ActionType.DEFEND;
import static com.mycompany.lb5.ActionType.HEAL;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
/**
 *
* @author Арсений
 */
public class Fight {

    Boolean isPlayersTurn = TRUE; 
    
    public boolean getIsPlayerTurn(){
        return this.isPlayersTurn;
    }

    public String performPlayerAction(Player human, Player enemy, ActionType action) {
        String battleLog = "";
        if (isPlayersTurn && human.isStunned()){
            battleLog = processPlayersStun(human, enemy);
        } else {
            switch (action) {
                case ATTACK -> battleLog = processPlayersAttack(human, enemy);
                case DEFEND -> battleLog = processPlayersDefend(human, enemy);
                case DEBUFF -> battleLog = processPlayersDebuff(human, enemy);
            }
        }
        return battleLog;
    }
    
    private String processPlayersStun(Player human, Player enemy){
        StringBuilder log = new StringBuilder();
        log.append("Игрок пропускает ход из-за оглушения!\n");
        ActionType enemyBehaviour = ActionType.ATTACK;
        log.append("Противник выбрал: ").append(enemyBehaviour).append("\n");
        human.setHealth(human.getHealth() - enemy.attackEnemy());
        human.setStunned(false);
        isPlayersTurn = FALSE;
        log.append("-----------------\n");
        return log.toString();
    }
    
    private String processPlayersAttack(Player human, Player enemy) {
        StringBuilder log = new StringBuilder();    
        if (!isPlayersTurn && enemy.isStunned()) {
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
                    if (isPlayersTurn){
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
                
                case DEBUFF:
                    if (isPlayersTurn){
                        enemy.setHealth((int) (enemy.getHealth() - human.attackEnemy() * 1.15));
                        log.append("Враг попытался ослабить игрока, но игрок нанес увеличенный урон!\n");
                        isPlayersTurn = FALSE;
                    } else {
                        enemy.setHealth((int) (enemy.getHealth() - human.attackEnemy() * 1.15));
                        log.append("Враг попытался ослабить игрока, но игрок нанес увеличенный урон!\n");
                        isPlayersTurn = TRUE;
                    }             
                    break;
            }
        }
        log.append("-----------------\n");
        return log.toString();
    }

    private String processPlayersDefend(Player human, Player enemy) {
        StringBuilder log = new StringBuilder();
        if (!isPlayersTurn && enemy.isStunned()) {
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
                    } else {
                        enemy.setHealth(enemy.getHealth() - (int) (human.attackEnemy() * 0.5));
                        log.append("Игрок контратаковал!\n");
                        human.defendFromEnemy();
                        isPlayersTurn = TRUE;
                    }
                    break;

                case DEFEND:
                    if (isPlayersTurn){
                        if (Math.random() < 0.5) {
                            enemy.setStunned(true);
                            log.append("Противник оглушён!\n");
                        }
                        isPlayersTurn = FALSE;
                    } else {
                        if (Math.random() < 0.5) {
                            human.setStunned(true);
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
                    
                case DEBUFF:
                    double enemyDebuffProbability = Math.random();
                    if (isPlayersTurn){
                        if(enemyDebuffProbability < 0.75){
                            human.setDebuff(enemy);
                            log.append("Игрок ослаблен на ").append(enemy.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append("Игрок не был ослаблен!\n");
                        }
                        isPlayersTurn = FALSE;
                    } else {
                        if(enemyDebuffProbability < 0.75){
                            human.setDebuff(enemy);
                            log.append("Игрок ослаблен на ").append(enemy.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append("Игрок не был ослаблен!\n");
                        }
                        isPlayersTurn = TRUE;
                    }
                    break;
            }
        }
        log.append("-----------------\n");
        return log.toString();
    }

    private String processPlayersDebuff(Player human, Player enemy) {
        StringBuilder log = new StringBuilder();
        if (!isPlayersTurn && enemy.isStunned()) {
            log.append("Противник пропускает ход из-за оглушения!\n");
            enemy.setDebuff(human);
            log.append("Враг ослаблен на ").append(human.getLevel()).append(" ходов!").append("\n");
            enemy.setStunned(false);
            isPlayersTurn = true;
            log.append("-----------------\n");
            return log.toString();
        } else {
            ActionType enemyBehaviour = CharacterAction.ChooseEnemyBehavior(human, enemy);
            log.append("Противник выбрал: ").append(enemyBehaviour).append("\n");
            double debuffProbability = Math.random();
            switch (enemyBehaviour) {
                case DEFEND:
                    if (isPlayersTurn){
                        if(debuffProbability < 0.75){
                            enemy.setDebuff(human);
                            log.append("Враг ослаблен на ").append(human.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append("Враг не был ослаблен!\n");
                        }
                        isPlayersTurn = FALSE;
                    } else {
                        if(debuffProbability < 0.75){
                            enemy.setDebuff(human);
                            log.append("Враг ослаблен на ").append(human.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append("Враг не был ослаблен!\n");
                        }
                        isPlayersTurn = TRUE;
                    }
                    break;

                case ATTACK:
                    if (isPlayersTurn){
                        human.setHealth((int) (human.getHealth() - enemy.attackEnemy() * 1.15));
                        log.append("Игрок попытался ослабить врага, но враг нанес увеличенный урон!\n");
                        isPlayersTurn = FALSE;
                    } else {
                        human.setHealth((int) (human.getHealth() - enemy.attackEnemy() * 1.15));
                        log.append("Игрок попытался ослабить врага, но враг нанес увеличенный урон!\n");
                        isPlayersTurn = TRUE;
                    }
                    break;
                    
                case HEAL:
                    if (isPlayersTurn){
                        if(debuffProbability < 0.75){
                            enemy.setDebuff(human);
                            enemy.setHealth((int) (enemy.getHealth() + (enemy.getMaxHealth() - enemy.getHealth()) * 0.5));
                            log.append("Враг восстановил здоровье и был ослаблен на ").append(human.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append("Враг не был ослаблен и восстановил здоровье!\n");
                        }
                        isPlayersTurn = FALSE;
                    } else {
                        if(debuffProbability < 0.75){
                            enemy.setDebuff(human);
                            enemy.setHealth((int) (enemy.getHealth() + (enemy.getMaxHealth() - enemy.getHealth()) * 0.5));
                            log.append("Враг восстановил здоровье и был ослаблен на ").append(human.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append("Враг не был ослаблен и восстановил здоровье!\n");
                        }
                        isPlayersTurn = TRUE;
                    }
                    break;
                    
                case DEBUFF:
                    if (isPlayersTurn){
                        log.append("Игрок и враг попытались ослабить друг друга => никто не был ослаблен!\n");
                        isPlayersTurn = FALSE;
                    } else {
                        log.append("Игрок и враг попытались ослабить друг друга => никто не был ослаблен!\n");
                        isPlayersTurn = TRUE;
                    }
                    break;
            }
        }
        log.append("-----------------\n");
        return log.toString();
    }
}