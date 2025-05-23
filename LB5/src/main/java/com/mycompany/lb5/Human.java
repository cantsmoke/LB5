/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

import java.util.ArrayList;

/**
 *
 * @author Мария
 */
public class Human extends Player{
    

    private int points;
    private int experience;
    private int win;
    private int nextexperience;
    
    public Human(int level, int health, int  damage, int attack){
        super (level, health, damage, attack);
        this.points=0;
        this.experience=0;
        this.nextexperience=40;
        this.win=0;
    }
    

    public int getPoints(){
        return this.points;
    }
    public int getExperience(){
        return this.experience;
    }
    public int getRequiredExperience(){
        return this.nextexperience;
    }
    public int getWin(){
        return this.win;
    }

    public void addPoints(int p){
        this.points+=p;
    }
    public void gainExperience(int e){
        this.experience+=e;
    }
    public void setNextExperience(int e){
        this.nextexperience=e;
    }
    public void addWin(){
        this.winAmount++;
    }
    
    @Override
    public String getName(){
        return "Kitana";
    }
    
    public String getIconSource(){
        return "C:\\Users\\Arseniy\\Documents\\GitHub\\LB5\\Kitana_in_MK1.png";
    }

    @Override
    public int getReceivedPoints() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getRequiredExperiance() {
        return this.nextexperience;
    }

    public void setRequiredExperiance() {
        this.nextexperience = nextexperience + 40;
    }

    @Override
    public int returnExperienceForWin() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
