package fightGame.view;

import javax.swing.*;

import fightGame.UnchangeableSettings;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.aiAlgorithms.*;
import fightGame.model.io.GameBoardIO;
import fightGame.view.widgets.GameButton;
import fightGame.view.widgets.GameView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class HomeView extends JFrame implements ActionListener {
    private GameButton loadButton;
    private GameButton newGameButton;
    private GameButton exitButton;

    public HomeView() {
        UnchangeableSettings.loadSettings();
        setTitle("Home View");
        buildView();
    }

    public void buildView() {
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
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.newGameButton)) {
            this.newGame();
        }
        if (e.getSource().equals(this.loadButton)) {
            this.loadGame();
        }
        if (e.getSource().equals(this.exitButton)) {
            System.exit(0);
        }
    }

    private void newGame() {
        GameBoard gameBoard = new GameBoard(UnchangeableSettings.NB_ROWS, UnchangeableSettings.NB_COLS, new ConnerFillStrategy());

        for (int i = 0; i < UnchangeableSettings.NB_RANDOM_PLAYERS; i++) {
            FightGamePlayer player = new FightGamePlayer(gameBoard,"RP_" + (i + 1), i);
            player.setStrategy(new RandomStrategy());
            gameBoard.addPlayer(player);
        }
        for (int i = 0; i < UnchangeableSettings.NB_MINIMAX_PLAYERS; i++) {
            FightGamePlayer player = new FightGamePlayer(gameBoard,"MINIMAX_" + (i+1), (i+UnchangeableSettings.NB_RANDOM_PLAYERS));//new RandomStrategy(gameBoard, "RP_" + (i + 1), i, new Position(x, y));
            player.setStrategy(new MinimaxStrategy());
            gameBoard.addPlayer(player);
        }
        gameBoard.fillGameBoard();
        int nbPlayers = UnchangeableSettings.NB_MINIMAX_PLAYERS + UnchangeableSettings.NB_RANDOM_PLAYERS;
        new GameView("View", gameBoard, null);
        for (int i = 0; i < nbPlayers; i++) {
            FightGamePlayer player = gameBoard.getPlayers().get(i);
            new GameView("View for Player " + player.getName(), gameBoard, player.getGameBoardProxy());
        }
        

       //play(gameBoard);
    }

    private void play(GameBoard gameBoard){
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                gameBoard.run();
                return null;
            }

            @Override
            protected void done() {
                System.out.println("Game loop finished.");
            }
        };

        worker.execute();
    }
    private void loadGame() {
       GameBoardIO.chooseFile(this);
    }
}
