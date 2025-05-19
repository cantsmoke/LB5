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
                return new Baraka(1, 100, 12, 1);
            case MAGICIAN:
                return new SubZero(1, 60, 16, 1);
            case FIGHTER:
                return new LiuKang(1, 70, 20, 1);
            case SOLDIER:
                return new SonyaBlade(1, 80, 16, 1);
            case BOSS:
                return new ShaoKahn(3, 145, 44, 1);
            default:
                throw new IllegalArgumentException("Такого типа соперника нет: " + type);
        }
    }
}