/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Мария
 */
public class CharacterAction {

    private final int experience_for_next_level[] = {40, 90, 180, 260, 410, 1000};

    private Player enemyes[] = new Player[5];

    private Player enemyy = null;

    public void setEnemies() {
        enemyes[0] = EnemyFactory.createEnemy(EnemyType.TANK);      
        enemyes[1] = EnemyFactory.createEnemy(EnemyType.MAGICIAN);      
        enemyes[2] = EnemyFactory.createEnemy(EnemyType.FIGHTER);      
        enemyes[3] = EnemyFactory.createEnemy(EnemyType.SOLDIER);   
        //enemyes[4] = EnemyFactory.createEnemy(EnemyType.BOSS);
    }

    public Player[] getEnemyes() {
        return this.enemyes;
    }

    public Player ChooseEnemy() {
        int randomEnemyIndex = (int) (Math.random() * 4);
        switch (randomEnemyIndex) {
            case 0:
                enemyy = enemyes[0];
                break;
            case 1:
                enemyy = enemyes[1];
                break;
            case 2:
                enemyy = enemyes[2];
                break;
            case 3:
                enemyy = enemyes[3];
                break;
        }
        return enemyy;
    }
    
    public Player makeBoss(){
        return EnemyFactory.createEnemy(EnemyType.BOSS);
    }
    
    public static AttackType adaptiveBehaviourChooser(int attackProbability, int defendProbability, Player human){
        int attackCount = human.getAttackCount();
        int defendCount = human.getDefendCount();
        int total = attackCount + defendCount;

        // Если данных ещё недостаточно, выбираем на основе базовых вероятностей
        if (total < 15) {
            return Math.random() < attackProbability / 100.0 ? AttackType.ATTACK : AttackType.DEFEND;
        } else{
            // Вычисляем склонность игрока
            double playerAggression = (double) attackCount / total;
            double playerDefense = (double) defendCount / total;

            // Адаптируем вероятности врага
            // Если игрок атакует часто, враг чаще будет защищаться и наоборот
            int adjustedAttackProb = (int) (attackProbability * playerDefense + defendProbability * playerDefense * 0.5);
            int adjustedDefendProb = (int) (defendProbability * playerAggression + attackProbability * playerAggression * 0.5);

            // Нормализация до 100%
            int totalAdjusted = adjustedAttackProb + adjustedDefendProb;
            double normAttack = (double) adjustedAttackProb / totalAdjusted;

            return Math.random() < normAttack ? AttackType.ATTACK : AttackType.DEFEND;
        }
    }

    public static AttackType ChooseEnemyBehavior(Player human, Player enemy) {
        AttackType enemyAction = null;
        if (enemy instanceof Baraka) {
            enemyAction = adaptiveBehaviourChooser(30, 70, human);
        }
        if (enemy instanceof SubZero) {
            enemyAction = adaptiveBehaviourChooser(40, 60, human);
        }
        if (enemy instanceof LiuKang) {
            enemyAction = adaptiveBehaviourChooser(50, 50, human);
        }
        if (enemy instanceof SonyaBlade) {
            enemyAction = adaptiveBehaviourChooser(60, 40, human);
        }
        if (enemy instanceof ShaoKahn) {
            enemyAction = adaptiveBehaviourChooser(45, 55, human);
        }
        return enemyAction;
    }

}
