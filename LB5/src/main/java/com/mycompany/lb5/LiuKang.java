/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;
/**
 *
 * @author Арсений
 */
public class LiuKang extends Player{
    
    public LiuKang(int level, int health, int  damage){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return "Liu Kang";
    }
    
    @Override
    public String getIconSource(){
        return "C:\\Users\\Arseniy\\Documents\\GitHub\\LB5\\Liukangmk11.png";
    }

    @Override
    public int getReceivedPoints() {
        return 120 + level*20;
    }
    
    @Override
    public int returnExperienceForWin() {
        return 20;
    }
    
    @Override
    public void updateCharacteristicsBasedOnLevel(int playerLevel) {
        this.level = playerLevel;
        int updatedHealth = 70 + (playerLevel) * 15;
        int updatedDamage = 20 + (playerLevel) * 5;
        this.health = updatedHealth;
        this.damage = updatedDamage;
        this.maxHealth = updatedHealth;
        this.maxDamage = updatedDamage;
    }
}