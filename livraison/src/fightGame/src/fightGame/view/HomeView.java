package fightGame.view;

import javax.swing.*;

import fightGame.view.widgets.GameButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JFrame {
    private GameButton loadButton;
    private GameButton newGameButton;
    private GameButton exitButton;

    public HomeView() {
        setTitle("Home View");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        
        // Définir un layout manager pour centrer les composants
        setLayout(new BorderLayout());
        
       
        JLabel label = new JLabel("FIGHT GAME", JLabel.CENTER);
        label.setFont(InterfaceSetting.TITLE_FONT);
        add(label, BorderLayout.NORTH);  
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout()); 
        
        
        loadButton = new GameButton("Load",150,50);
        newGameButton = new GameButton("New",150,50);
        exitButton = new GameButton("Exit",150,50);
        
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.insets = new Insets(20, 10, 10, 20); 
        buttonPanel.add(loadButton, gbc);  
        
        gbc.gridy = 1; 
        buttonPanel.add(newGameButton, gbc);  
        
        gbc.gridy = 2;  
        buttonPanel.add(exitButton, gbc);  
        
        
        add(buttonPanel, BorderLayout.CENTER);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomeView().setVisible(true);
            }
        });
    }
}
