/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;
/**
 *
 * @author Мария
 */
public class CharacterAction {
    
    public static ActionType adaptiveBehaviourChooser(int attackProbability, int defendProbability, Player human){
        int attackCount = human.getAttackCount();
        int defendCount = human.getDefendCount();
        int total = attackCount + defendCount;

        if (total < 15) {
            return Math.random() < attackProbability / 100.0 ? ActionType.ATTACK : ActionType.DEFEND;
        } else{
            double playerAggression = (double) attackCount / total;
            double playerDefense = (double) defendCount / total;

            int adjustedAttackProb = (int) (attackProbability * playerDefense + defendProbability * playerDefense * 0.5);
            int adjustedDefendProb = (int) (defendProbability * playerAggression + attackProbability * playerAggression * 0.5);

            int totalAdjusted = adjustedAttackProb + adjustedDefendProb;
            double normAttack = (double) adjustedAttackProb / totalAdjusted;

            return Math.random() < normAttack ? ActionType.ATTACK : ActionType.DEFEND;
        }
    }

    public static ActionType ChooseEnemyBehavior(Player human, Player enemy) {
        ActionType enemyAction = null;
        if (enemy instanceof Baraka) {
            enemyAction = adaptiveBehaviourChooser(30, 70, human);
        }
        if (enemy instanceof SubZero) {
            double debuffProbability = Math.random();
            if(debuffProbability < 0.2){
                enemyAction = ActionType.DEBUFF;
            } else {
                enemyAction = adaptiveBehaviourChooser(40, 60, human);
            } 
        }
        if (enemy instanceof LiuKang) {
            enemyAction = adaptiveBehaviourChooser(50, 50, human);
        }
        if (enemy instanceof SonyaBlade) {
            enemyAction = adaptiveBehaviourChooser(60, 40, human);
        }
        if (enemy instanceof ShaoKahn) {
            double healProbability = Math.random();
            if(healProbability < 0.3 && enemy.getMaxHealth() != enemy.getHealth()){
                enemyAction = ActionType.HEAL;
            } else {
                enemyAction = adaptiveBehaviourChooser(45, 55, human);
            }
        }
        return enemyAction;
    }

}
