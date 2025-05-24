/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Arseniy
 */
public class ExcelManager {
    
    public static void writeToExcel(String playerName, int playerScore) {
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


    public static List<Integer> loadTop10ScoresFromExcel() {
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
    
    public static List<String[]> loadTop10TableFromExcel() {
        List<String[]> top10 = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("C:\\Users\\Arseniy\\Desktop\\Results.xlsx"); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                String name = row.getCell(0).getStringCellValue();
                int score = (int) row.getCell(1).getNumericCellValue();
                top10.add(new String[]{name, String.valueOf(score)});
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return top10;
    }
    
}
