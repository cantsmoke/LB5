/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;
/**
 *
 * @author Arseniy
 */
public class SmallHealthPotion implements Item{

    @Override
    public String getName() {
        return "Малое зелье лечения";
    }

    @Override
    public double getHealKF() {
        return 0.25;
    }
}