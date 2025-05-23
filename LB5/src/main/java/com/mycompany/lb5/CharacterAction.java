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
