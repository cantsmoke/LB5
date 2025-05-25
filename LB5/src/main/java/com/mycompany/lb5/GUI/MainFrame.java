/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5.GUI;

import com.mycompany.lb5.Game;
import com.mycompany.lb5.Human;
import com.mycompany.lb5.Player;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/**
 * Главное окно приложения — стартовое меню игры.
 * Здесь игрок может начать новую игру или посмотреть таблицу результатов.
 * Окно содержит две кнопки: "Начать новую игру" и "Посмотреть таблицу результатов".
 * 
 * @author Арсений
 */
public class MainFrame extends JFrame {

    private JButton btnNewGame;
    private JButton btnShowResults;
    private int selectedLocations = 1;
    public static Game game = new Game();
    Human human = null;

    public MainFrame() {
        super("Игра: Битва героев");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1, 10, 10));

        initializeComponents();
        addComponentsToFrame();
        setupActions();
    }
    
    static Object getGame() {
        return game;
    }

    private void initializeComponents() {
        btnNewGame = new JButton("Начать новую игру");
        btnShowResults = new JButton("Посмотреть таблицу результатов");
    }

    private void addComponentsToFrame() {
        add(btnNewGame);
        add(btnShowResults);
    }

    private void setupActions() {
        btnNewGame.addActionListener(this::onStartNewGameClicked);
        btnShowResults.addActionListener(this::onShowResultsClicked);
    }
    
    /**
     * Обработчик нажатия на кнопку "Начать новую игру".
     * Показывает диалог выбора количества локаций, запускает игру.
     * @param e событие нажатия
     */
    public void onStartNewGameClicked(ActionEvent e) {
        System.out.println("Новая игра начата!");

        LocationDialog locationDialog = new LocationDialog(this);
        locationDialog.setVisible(true);

        if (locationDialog.isConfirmed()) {
            selectedLocations = locationDialog.getLocations();
            System.out.println("Игрок выбрал " + selectedLocations + " локаций.");

            human = game.NewHuman();
            startFirstLocation(human, 1);
            setVisible(false);
        }
    }
    
    /**
     * Метод запускает окно боя для первой локации.
     * @param human игрок
     * @param currentLocation текущий номер локации
     */
    private void startFirstLocation(Human human, int currentLocation) {
        List<Player> enemies = game.generateEnemiesForLocation(human.getLevel());
        BattleFrame battleFrame = new BattleFrame(human, enemies, game, currentLocation, selectedLocations);
        battleFrame.setVisible(true);
    }
    
    /**
     * Обработчик нажатия на кнопку "Посмотреть таблицу результатов".
     * Показывает диалог с таблицей результатов.
     * @param e событие нажатия
     */
    public void onShowResultsClicked(ActionEvent e) {
        System.out.println("Кнопка 'Посмотреть таблицу результатов' нажата.");
        ScoreboardDialog scoreboardDialog = new ScoreboardDialog(this);
        scoreboardDialog.setVisible(true);
    }
}