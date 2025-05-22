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
    protected int damage;
    protected int attack;
    
    ArrayList<AttackType> playerActionsHistory = new ArrayList<>();

    public Player(int level, int health, int damage, int attack) {
        this.level = level;
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
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

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setNewHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    
    public int getMaxHealth(int maxHealth) {
        return this.maxHealth;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public void setLevel(){
        this.level++;
    }

    public abstract String getName();
    
    public int attackEnemy(){
        playerActionsHistory.add(AttackType.ATTACK);
        return this.damage;
    }
    
    public void defendFromEnemy(){
        playerActionsHistory.add(AttackType.DEFEND);
    }
    
    public int getAttackCount() {
        int attackCount = 0;
        for (AttackType action : playerActionsHistory) {
            if (action == AttackType.ATTACK) {
                attackCount++;
            }
        }
        return attackCount;
    }

    public int getDefendCount() {
        int defendCount = 0;
        for (AttackType action : playerActionsHistory) {
            if (action == AttackType.DEFEND) {
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

    public List<AttackType> getPlayerActionsHistory() {
        return this.playerActionsHistory;
    }

    public String getScore() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getCurrentExp() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getNextLevelExp() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}