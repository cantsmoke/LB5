/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

/**
 *
 * @author Мария
 */
public class ShaoKahn extends Player{
    
    public ShaoKahn(int level, int health, int  damage, int attack){
        super (level, health, damage, attack);
    }
    
    @Override
    public String getName(){
        return "Shao Kahn";
    }
    
    public String getIconSource(){
        return "C:\\Users\\Arseniy\\Documents\\GitHub\\LB5\\Шао_Кан_(MK11).png";
    }

    @Override
    public int getReceivedPoints() {
        return 250 + level*20;
    }

    @Override
    public void addPoints(int p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getPoints() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
