package fightGame.view;

import javax.swing.*;

import fightGame.UnchangeableSettings;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.aiAlgorithms.*;
import fightGame.model.io.GameBoardIO;
import fightGame.model.strategy.ConnerFillStrategy;
import fightGame.model.strategy.GameBordInitFillStrategy;
import fightGame.model.strategy.RandaomFillStrategy;
import fightGame.view.widgets.GameButton;
import fightGame.view.widgets.GameView;
import fightGame.view.widgets.InfosView;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class HomeView extends JFrame implements ActionListener {
    private GameButton loadButton;
    private GameButton newGameButton;
    private GameButton robotButton;
    private GameButton exitButton;
    public static List<GameView> gameViews;
    private GameBoard gameBoard;

    public HomeView() {
        UnchangeableSettings.loadSettings();
        setTitle("Home View");
        buildView();
    }

    public void buildView() {
        setSize(600, 600);
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
        robotButton = new GameButton("Robot", 150, 50);

        loadButton.addActionListener(this);
        newGameButton.addActionListener(this);
        exitButton.addActionListener(this);
        robotButton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 10, 20);
        buttonPanel.add(loadButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(newGameButton, gbc);

        gbc.gridy = 2;
        buttonPanel.add(robotButton, gbc);

        gbc.gridy = 3;
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
        if(e.getSource().equals(this.robotButton)){
            showRobotPlay();
        }
    }

    public void showRobotPlay(){
        newGame();
        play(this.gameBoard);
    }

    public void newGame() {
        this.gameViews = new ArrayList<>();
        GameBordInitFillStrategy fillStrategy;
        
        switch (UnchangeableSettings.FILL_STRATEGIE) {
            case 1:
                fillStrategy = new ConnerFillStrategy();
                break;
          
            default:
            fillStrategy = new RandaomFillStrategy();
                break;
        }
        this.gameBoard = new GameBoard(UnchangeableSettings.NB_ROWS, UnchangeableSettings.NB_COLS,fillStrategy);

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
        this.gameViews.add(new GameView("View", gameBoard, null));
        for (int i = 0; i < nbPlayers; i++) {
            FightGamePlayer player = gameBoard.getPlayers().get(i);
            this.gameViews.add(new GameView("View for Player " + player.getName(), gameBoard, player.getGameBoardProxy()));
        }
        this.dispose();
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
                new InfosView(null, "Game Over", "We have a winner", true);
            }
        };

        worker.execute();
    }
    private void loadGame() {
       GameBoardIO.chooseFile(this);
    }
}
