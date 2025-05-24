/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Арсения
 */
public class Game {
    
    private static Player enemies[] = new Player[4];
    public Fight fight = new Fight();
    
    public List<Player> generateEnemiesForLocation(int playerLevel) {
        setEnemies();
        for(Player enemy: enemies){
            enemy.updateCharacteristicsBasedOnLevel(playerLevel);
        }
        List<Player> enemies = new ArrayList<>();
        int count = playerLevel + 3 +  new Random().nextInt(2);       
        for (int i = 0; i < count - 1; i++) {
            enemies.add(NewEnemy());
        }
        Player boss = NewBoss();
        enemies.add(boss);
        return enemies;
    }

    public Player NewBoss(){
        Player boss = makeBoss();
        return boss;
    }

    public Player NewEnemy() {
        Player enemy = ChooseEnemy();
        return enemy;
    }
    
    public static void setEnemies() {
        enemies[0] = EnemyFactory.createEnemy(EnemyType.TANK);      
        enemies[1] = EnemyFactory.createEnemy(EnemyType.MAGICIAN);      
        enemies[2] = EnemyFactory.createEnemy(EnemyType.FIGHTER);      
        enemies[3] = EnemyFactory.createEnemy(EnemyType.SOLDIER);   
    }

    public Player ChooseEnemy() {
        int randomEnemyIndex = (int) (Math.random() * 4);
        Player enemy = null;
        switch (randomEnemyIndex) {
            case 0:
                enemy = enemies[0];
                break;
            case 1:
                enemy = enemies[1];
                break;
            case 2:
                enemy = enemies[2];
                break;
            case 3:
                enemy = enemies[3];
                break;
        }
        return enemy;
    }
    
    public Player makeBoss(){
        return EnemyFactory.createEnemy(EnemyType.BOSS);
    }
    
    public Human NewHuman(){
        Human human = new Human (0, 100, 50);
        return human;
    }  
}
