/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Мария
 */
public class Game {
    
    private static Player enemies[] = new Player[4];
    CharacterAction action = new CharacterAction();
    public Fight fight = new Fight();
    private ArrayList<Result> results = new ArrayList<>();
    
    public List<Player> generateEnemiesForLocation(int playerLevel) {
        
        setEnemies();
        
        //тут в зависимости от уровня игрока меняются хар-ки врагов
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
        Human human = new Human (0,80,100); //пока вместо 16 поставил урон 100 чтобы тестить
        return human;
    }

    public void EndGameTop(Human human, JTextField text, JTable table) throws IOException {
        results.add(new Result(text.getText(), human.getPoints()));
        results.sort(Comparator.comparing(Result::getPoints).reversed());
        WriteToTable(table);
        WriteToExcel();
    }
    
    public void WriteToExcel() throws IOException{
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Результаты ТОП 10");
        XSSFRow r = sheet.createRow(0);
        r.createCell(0).setCellValue("№");
        r.createCell(1).setCellValue("Имя");
        r.createCell(2).setCellValue("Количество баллов");
        for (int i=0; i<results.size();i++){
            if (i<10){
                XSSFRow r2 = sheet.createRow(i+1);
                r2.createCell(0).setCellValue(i+1);
                r2.createCell(1).setCellValue(results.get(i).getName());
                r2.createCell(2).setCellValue(results.get(i).getPoints());
            }
        }
        File f = new File("C:\\Users\\Arseniy\\Desktop\\Results.xlsx");
        book.write(new FileOutputStream(f));
        book.close();
    }
    
    public ArrayList<Result> getResults(){
        return this.results;
    }

    public void ReadFromExcel() throws IOException{
        XSSFWorkbook book = new XSSFWorkbook("C:\\Users\\Arseniy\\Desktop\\Results.xlsx");
        XSSFSheet sh = book.getSheetAt(0);
        for (int i=1; i<=sh.getLastRowNum();i++) {
            results.add(new Result(sh.getRow(i).getCell(1).getStringCellValue(),(int)sh.getRow(i).getCell(2).getNumericCellValue()));
        }
    }
    
    public void WriteToTable(JTable table){
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for (int i=0; i<results.size();i++){
            if (i<10){
                model.setValueAt(results.get(i).getName(), i, 0);
                model.setValueAt(results.get(i).getPoints(), i, 1);
            }
        }
    }
}
