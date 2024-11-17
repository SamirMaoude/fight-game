package fightGame.view;

import javax.swing.*;

import fightGame.UnchangeableSettings;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.aiAlgorithms.RandomStrategy;
import fightGame.view.widgets.GameButton;
import fightGame.view.widgets.GameView;
import gamePlayers.util.Position;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class HomeView extends JFrame implements ActionListener {
    private GameButton loadButton;
    private GameButton newGameButton;
    private GameButton exitButton;

    public HomeView() {
        UnchangeableSettings.loadSettings();
        setTitle("Home View");
        buildView();
        this.setVisible(true);
    }

    public void buildView(){
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Définir un layout manager pour centrer les composants
        setLayout(new BorderLayout());

        JLabel label = new JLabel("FIGHT GAME", JLabel.CENTER);
        label.setFont(InterfaceSetting.TITLE_FONT);
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        loadButton = new GameButton("Load", 150, 50);
        newGameButton = new GameButton("New", 150, 50);
        exitButton = new GameButton("Exit", 150, 50);

        loadButton.addActionListener(this);
        newGameButton.addActionListener(this);
        exitButton.addActionListener(this);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.newGameButton)) {
            this.newGame();
        }
        if (e.getSource().equals(this.loadButton)) {
            System.out.println("TO DO");
        }
        if (e.getSource().equals(this.exitButton)) {
            System.exit(0);
        }
    }

    private void newGame() {
        GameBoard gameBoard = new GameBoard(UnchangeableSettings.NB_ROWS, UnchangeableSettings.NB_COLS);
        Random random = new Random();
        for (int i = 0; i < UnchangeableSettings.NB_PLAYERS; i++) {
            int x = random.nextInt(0, UnchangeableSettings.NB_ROWS);
            int y = random.nextInt(0, UnchangeableSettings.NB_COLS);
            FightGamePlayer player = new RandomStrategy(gameBoard, "RP_" + (i + 1), i, new Position(x, y));
            gameBoard.addPlayer(player);
            new GameView("View for Player " +player.getName(), gameBoard, player.getGameBoardProxy());
        }

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                gameBoard.run(); // Tâche longue ici
                return null;
            }
    
            @Override
            protected void done() {
                System.out.println("Game loop finished.");
            }
        };
    
        worker.execute();
    }
}
