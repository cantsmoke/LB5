/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

/**
 * Перечисление {@code ActionType} представляет собой возможные типы действий,
 * которые может выполнить персонаж в бою.
 * <ul>
 *     <li>{@link #ATTACK} — атака
 *     <li>{@link #DEFEND} — защита</li>
 *     <li>{@link #HEAL} — восстановление очков здоровья.</li>
 *     <li>{@link #DEBUFF} — ослабление</li>
 * </ul>
 * 
 * @author Arseniy
 */
public enum ActionType {
    ATTACK,
    DEFEND,
    HEAL,
    DEBUFF
}