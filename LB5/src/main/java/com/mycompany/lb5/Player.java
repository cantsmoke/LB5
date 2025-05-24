/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Мария
 */
public abstract class Player {
    protected int level;
    protected int health;
    protected int maxHealth;
    protected int maxDamage;
    protected int damage;
    protected int attack;
    protected int winAmount;
    
    ArrayList<ActionType> playerActionsHistory = new ArrayList<>();

    public Player(int level, int health, int damage) {
        this.level = level;
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.maxDamage = damage;
        this.winAmount = 0;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void levelUp() {
        this.level++;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    public void heal(Item item){
        double hu = item.getHealKF();
        this.health = (int) (health + maxHealth * hu);
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }
    
    public int getMaxDamage() {
        return this.maxDamage;
    }
    
    public void setLevel(){
        this.level++;
    }

    public abstract String getName();
    
    public abstract String getIconSource();
    
    public abstract int getReceivedPoints();
    
    public int attackEnemy(){
        playerActionsHistory.add(ActionType.ATTACK);
        return this.damage;
    }
    
    public void defendFromEnemy(){
        playerActionsHistory.add(ActionType.DEFEND);
    }
    
    public int getAttackCount() {
        int attackCount = 0;
        for (ActionType action : playerActionsHistory) {
            if (action == ActionType.ATTACK) {
                attackCount++;
            }
        }
        return attackCount;
    }

    public int getDefendCount() {
        int defendCount = 0;
        for (ActionType action : playerActionsHistory) {
            if (action == ActionType.DEFEND) {
                defendCount++;
            }
        }
        return defendCount;
    }
    
    private boolean stunned = false;

    public void setStunned(boolean val) {
        this.stunned = val;
    }

    public boolean isStunned() {
        return this.stunned;
    }
    
    public void resetStatus() {
        this.stunned = false;
    }
    
    
    
    protected int debuffTurnDuration = 0;
    
    public void setDebuff(Player enemy){
        int debuffDuration = enemy.getLevel();
        if (debuffDuration != 0){
            this.debuffTurnDuration = debuffDuration;
            this.setDamage((int) (this.getDamage() * 0.5));
            enemy.setDamage((int) (enemy.getDamage() * 1.25));
        }
    }
    
    public void resetDebuff(Player enemy){
        this.setDamage(this.getMaxDamage());
        enemy.setDamage(enemy.getMaxDamage());
        //логика сброса увеличенного урона врага и вовращения урона игроку
    }
    
    public int getDebuff(){
        return this.debuffTurnDuration;
    }
    
    public void reduceDebuffDuration(){
        if (debuffTurnDuration != 0){
            this.debuffTurnDuration--;
        }
    }

    
    
    public List<ActionType> getPlayerActionsHistory() {
        return this.playerActionsHistory;
    }
    
    public void addWin(){
        this.winAmount++;
    }
    
    public int getWinAmount(){
        return this.winAmount;
    }
    
    public abstract int returnExperienceForWin();

    public abstract void updateCharacteristicsBasedOnLevel(int playerLevel);
    
}