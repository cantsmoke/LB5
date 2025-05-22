/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.lb5;

import com.mycompany.lb5.GUI.MainFrame;
//import com.mycompany.lb5.JFrames;
import javax.swing.SwingUtilities;

/**
 *
 * @author Мария
 */
public class LB5{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Запуск главного меню
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
    //public static void main(String[] args) {
        
      //  JFrames app = new JFrames();
      //  app.setVisible(true);
       
        /*Human human = new Human (0,80,16,1);
        Soldier mage = new Soldier(0,60,20,0);
        Fight f = new Fight();
        //System.out.println("human "+human.getHealth()+"    enemy "+mage.getHealth());
        f.Round(human, mage);*/
}

