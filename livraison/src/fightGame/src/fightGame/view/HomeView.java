package fightGame.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JFrame {

    public HomeView() {
        setTitle("Home View");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        
        // Définir un layout manager pour centrer les composants
        setLayout(new BorderLayout());
        
       
        JLabel label = new JLabel("FIGHT GAME", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);  
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        
        
        JButton btnLoad = new JButton("Load");
        JButton btnNewGame = new JButton("New Game");
        JButton btnExit = new JButton("Exit");
        
        
        btnLoad.setPreferredSize(new Dimension(150, 40));
        btnNewGame.setPreferredSize(new Dimension(150, 40));
        btnExit.setPreferredSize(new Dimension(150, 40));
        
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.insets = new Insets(20, 10, 10, 20); 
        buttonPanel.add(btnLoad, gbc);  
        
        gbc.gridy = 1; 
        buttonPanel.add(btnNewGame, gbc);  
        
        gbc.gridy = 2;  
        buttonPanel.add(btnExit, gbc);  
        
        
        add(buttonPanel, BorderLayout.CENTER);
        
        
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeView.this, "Load game selected");
            }
        });

        btnNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeView.this, "New game started");
            }
        });

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomeView().setVisible(true);
            }
        });
    }
}
