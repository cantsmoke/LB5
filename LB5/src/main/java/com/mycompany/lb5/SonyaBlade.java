/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

/**
 *
 * @author Мария
 */
public class SonyaBlade extends Player{
    
    public SonyaBlade (int level, int health, int  damage){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return "Sonya Blade";
    }
    
    @Override
    public String getIconSource(){
        return "C:\\Users\\Arseniy\\Documents\\GitHub\\LB5\\Соня_(MK11).png";
    }

    @Override
    public int getReceivedPoints() {
        return 120 + level*10;
    }
    
    @Override
    public int returnExperienceForWin() {
        return 20;
    }
    
    @Override
    public void updateCharacteristicsBasedOnLevel(int playerLevel) {
        this.level = playerLevel;
        this.health = 80 + (playerLevel) * 15;
        this.damage = 16 + (playerLevel) * 15;
    }
    
}
