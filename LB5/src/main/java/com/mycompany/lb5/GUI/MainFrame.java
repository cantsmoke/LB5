/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb5.GUI;

/**
 *
 * @author Arseniy
 */
import com.mycompany.lb5.Game;
import com.mycompany.lb5.Human;
import com.mycompany.lb5.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    private JButton btnNewGame;
    private JButton btnShowResults;
    private int selectedLocations = 1; // по умолчанию
    
    public static Game game = new Game();
    Human human = null;
    Player enemy = null;

    public MainFrame() {
        super("Игра: Битва героев");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // окно по центру экрана
        setLayout(new GridLayout(2, 1, 10, 10));

        initializeComponents();
        addComponentsToFrame();
        setupActions();
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

    /*public void onStartNewGameClicked(ActionEvent e) {
        System.out.println("New game started!");
        // Пока просто выводим сообщение
        human = game.NewHuman();
        enemy = game.NewEnemy();
        BattleFrame battleFrame = new BattleFrame(human, enemy, game);
        battleFrame.setVisible(true);
        setVisible(false);
        System.out.println("-------------------");
        //Тут создание нового human и enemy и передача их в battleframe при создании
    }*/
    
    public void onStartNewGameClicked(ActionEvent e) {
        System.out.println("Новая игра начата!");

        LocationDialog locationDialog = new LocationDialog(this);
        locationDialog.setVisible(true);

        if (locationDialog.isConfirmed()) {
            selectedLocations = locationDialog.getLocations();
            System.out.println("Игрок выбрал " + selectedLocations + " локаций.");

            human = game.NewHuman(); // создаём игрока
            startFirstLocation(human, 1); // начинаем первую локацию
            setVisible(false); // скрываем главное меню
        }
    }
    
    private void startFirstLocation(Player human, int currentLocation) {
        Player enemy = game.NewEnemy(); // создаём первого врага
        BattleFrame battleFrame = new BattleFrame(human, enemy, game, currentLocation, selectedLocations);
        battleFrame.setVisible(true);
    }

    public void onShowResultsClicked(ActionEvent e) {
        System.out.println("Кнопка 'Посмотреть таблицу результатов' нажата.");
        ScoreboardDialog scoreboardDialog = new ScoreboardDialog(this);
        scoreboardDialog.setVisible(true);
    }

}