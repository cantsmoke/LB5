/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;
/**
 *
 * @author Арсений
 */
public class Baraka extends Player{
    
    public Baraka(int level, int health, int  damage){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return "Baraka";
    }
    
    public String getIconSource(){
        return "C:\\Users\\Arseniy\\Documents\\GitHub\\LB5\\Baraka_in_MK1.png";
    }

    @Override
    public int getReceivedPoints() {
        return 110 + level*30;
    }
    
    @Override
    public int returnExperienceForWin() {
        return 20;
    }
    
    @Override
    public void updateCharacteristicsBasedOnLevel(int playerLevel) {
        this.level = playerLevel;
        int updatedHealth = 100 + (playerLevel) * 15;
        int updatedDamage = 12 + (playerLevel) * 5;
        this.health = updatedHealth;
        this.damage = updatedDamage;
        this.maxHealth = updatedHealth;
        this.maxDamage = updatedDamage;
    }
}