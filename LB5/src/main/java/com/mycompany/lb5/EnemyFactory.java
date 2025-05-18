/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Мария
 */
public class EnemyFactory {
    public static Player createEnemy(EnemyType type) {
        switch (type) {
            case TANK:
                return new Baraka(3, 100, 30, 1);
            case MAGICIAN:
                return new SubZero(3, 100, 30, 1);
            case FIGHTER:
                return new LiuKang(3, 100, 30, 1);
            case SOLDIER:
                return new SonyaBlade(3, 100, 30, 1);
            case BOSS:
                return new ShaoKahn(3, 100, 30, 1);
            default:
                throw new IllegalArgumentException("Такого типа соперника нет: " + type);
        }
    }
}