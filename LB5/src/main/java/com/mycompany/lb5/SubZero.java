/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

/**
 *
 * @author Мария
 */
public class SubZero extends Player{
    
    public SubZero(int level, int health, int damage){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return "Sub-Zero";
    }
    
    public String getIconSource(){
        return "C:\\Users\\Arseniy\\Documents\\GitHub\\LB5\\Sub-Zero_in_MK1.png";
    }

    @Override
    public int getReceivedPoints() {
        return 100 + level*50;
    }

    @Override
    public int returnExperienceForWin() {
        return 20;
    }

    @Override
    public void updateCharacteristicsBasedOnLevel(int playerLevel) {
        this.level = playerLevel;
        this.health = 60 + (playerLevel) * 15;
        this.damage = 16 + (playerLevel) * 10;
    }
    
}
