/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.lb5;

import com.mycompany.lb5.GUI.MainFrame;
import javax.swing.SwingUtilities;
/**
 * Главный класс запуска приложения.
 * 
 * Здесь используется SwingUtilities.invokeLater для того,
 * чтобы создание и отображение главного окна происходило
 * в потоке обработки событий Swing (Event Dispatch Thread),
 * что является хорошей практикой при работе с GUI на Swing.
 * 
 * @author Арсений
 */
public class LB5{
     /**
     * Точка входа в программу.
     * Создаёт и отображает главное окно приложения.
     * @param args параметры командной строки (не используются)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}