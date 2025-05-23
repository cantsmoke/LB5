/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
    
    public void writeToExcel(String playerName, int playerScore) {
        String filePath = "C:\\Users\\Arseniy\\Desktop\\Results.xlsx";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0); // Первый лист

            // Считываем текущие записи
            List<Row> rows = new ArrayList<>();
            for (Row row : sheet) {
                rows.add(row);
            }

            // Добавляем новую запись во временный список
            List<PlayerScoreEntry> entries = new ArrayList<>();
            for (Row row : rows) {
                Cell nameCell = row.getCell(0);
                Cell scoreCell = row.getCell(1);
                if (nameCell != null && scoreCell != null) {
                    String name = nameCell.getStringCellValue();
                    int score = (int) scoreCell.getNumericCellValue();
                    entries.add(new PlayerScoreEntry(name, score));
                }
            }

            // Добавляем нового игрока
            entries.add(new PlayerScoreEntry(playerName, playerScore));

            // Сортировка по убыванию
            entries.sort((a, b) -> Integer.compare(b.score, a.score));

            // Сохраняем только топ-10
            if (entries.size() > 10) {
                entries = entries.subList(0, 10);
            }

            // Очистка старых строк
            for (int i = sheet.getLastRowNum(); i >= 0; i--) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }
            }

            // Перезапись данных
            for (int i = 0; i < entries.size(); i++) {
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue(entries.get(i).name);
                row.createCell(1).setCellValue(entries.get(i).score);
            }

            fis.close();

            // Сохраняем файл
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            workbook.close();
            fos.close();

            System.out.println("Результат записан в Excel.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Integer> loadTop10ScoresFromExcel() {
        List<Integer> scores = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File("C:\\Users\\Arseniy\\Desktop\\Results.xlsx"));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Первая вкладка
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Пропустить заголовок, если есть

                Cell scoreCell = row.getCell(1); // Предположим: 1-я колонка — имя, 2-я — очки
                if (scoreCell != null && scoreCell.getCellType() == CellType.NUMERIC) {
                    scores.add((int) scoreCell.getNumericCellValue());
                }
            }

        } catch (Exception e) {
            System.err.println("Ошибка при чтении из Excel: " + e.getMessage());
        }

        // Сортировка по убыванию
        scores.sort(Collections.reverseOrder());

        // Вернуть максимум 10 результатов
        return scores.size() > 10 ? scores.subList(0, 10) : scores;
    }

    public List<String[]> loadTop10TableFromExcel() {
        List<String[]> top10 = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("C:\\Users\\Arseniy\\Desktop\\Results.xlsx")) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                String name = row.getCell(0).getStringCellValue();
                int score = (int) row.getCell(1).getNumericCellValue();
                top10.add(new String[]{name, String.valueOf(score)});
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    return top10;
}
    
}
