/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;
/**
 * Вспомогательны класс для отображения результатов.
 * Вспомогательный класс для хранения пары "имя игрока - очки".
 *
 * @author Arseniy
 */
class PlayerScoreEntry {
    String name;
    int score;

    PlayerScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }
}