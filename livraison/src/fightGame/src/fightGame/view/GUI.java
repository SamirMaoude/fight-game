package fightGame.view;

import javax.swing.*;

import fightGame.UnchangeableSettings;
import fightGame.controller.GameManager;
import fightGame.model.*;
import fightGame.model.io.GameBoardIO;
import fightGame.model.io.Logger;
import fightGame.model.strategy.*;
import fightGame.model.strategy.aiAlgorithms.MinimaxStrategy;
import fightGame.model.strategy.aiAlgorithms.MultiStrategy;
import fightGame.model.strategy.aiAlgorithms.RandomStrategy;
import fightGame.view.widgets.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main graphical user interface (GUI) class for the Fight Game.
 * This class is responsible for managing the home screen where users can start
 * a new game,
 * load an existing game, or exit the application.
 */
public class GUI extends JFrame implements ActionListener {

    private GameButton loadButton;
    private GameButton newGameButton;
    private GameButton robotButton;
    private GameButton exitButton;
    private GameButton humainButton;

    public static List<GameView> gameViews;
    private GameBoard gameBoard;

    /**
     * Constructs the GUI, initializes game settings, and creates the home screen.
     * The constructor loads the settings and initializes the game views.
     */
    public GUI() {
        UnchangeableSettings.loadSettings(null);
        this.gameViews = new ArrayList<>();

        setTitle("Home View");
        buildView();
    }

    /**
     * Builds the layout of the home screen, including buttons for loading, starting
     * a new game, and exiting.
     * This method sets up the main window size, layout, and adds buttons to the
     * screen.
     */
    public void buildView() {
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout manager to center components
        setLayout(new BorderLayout());

        JLabel label = new JLabel("FIGHT GAME", JLabel.CENTER);
        label.setFont(InterfaceSetting.TITLE_FONT);
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        loadButton = new GameButton("Load", 150, 50);
        newGameButton = new GameButton("New", 150, 50);
        exitButton = new GameButton("Exit", 150, 50);
        robotButton = new GameButton("Watch", 150, 50);
        humainButton = new GameButton("H vs R ", 150, 50);

        loadButton.addActionListener(this);
        newGameButton.addActionListener(this);
        exitButton.addActionListener(this);
        robotButton.addActionListener(this);
        humainButton.addActionListener(this);

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
        buttonPanel.add(humainButton, gbc);

        gbc.gridy = 4;
        buttonPanel.add(exitButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Handles button click events to start a new game, load a game, or exit the
     * application.
     * This method is invoked when any of the buttons are clicked.
     *
     * @param e the ActionEvent triggered by a button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.newGameButton)) {
            if (UnchangeableSettings.NB_HUMAIN_PLAYERS <= 0) {
                this.newGame(0);
            } else {
                new InfosView(this, "Invalid configuration", "Put the number of human players at 0", false);
            }
        }
        if (e.getSource().equals(this.loadButton)) {
            GameBoardIO.chooseFile(this);
            this.dispose();
        }
        if (e.getSource().equals(this.exitButton)) {
            System.exit(0);
        }
        if (e.getSource().equals(this.robotButton)) {
            if (UnchangeableSettings.NB_HUMAIN_PLAYERS <= 0) {
                this.newGame(1);
            } else {
                new InfosView(this, "Invalid configuration", "Put the number of human players at 0", false);
            }
        }
        if (e.getSource().equals(this.humainButton)) {
            if (UnchangeableSettings.NB_HUMAIN_PLAYERS > 0) {
                this.newGame(2);
            } else {
                new InfosView(this, "Invalid configuration", "The number of human players is invalid", false);
            }
        }
    }

    /**
     * Starts a new game with the option to include robot players.
     * This method initializes the game board and adds the specified number of
     * players.
     *
     * @param withRobot  indicates whether the game should include robot players
     * @param withHumain indicates whether the game should include human players
     */
    public void newGame(int type) {
        int nbEntity = UnchangeableSettings.NB_MINIMAX_PLAYERS + UnchangeableSettings.NB_RANDOM_PLAYERS +
                UnchangeableSettings.NB_MULTY_STRAT_PLAYERS + UnchangeableSettings.NB_HUMAIN_PLAYERS
                + UnchangeableSettings.NB_WALL + UnchangeableSettings.NB_INIT_PELLET;
        int nbCases = (UnchangeableSettings.NB_ROWS * UnchangeableSettings.NB_COLS);

        if (nbEntity < nbCases) {
            Logger logger = new Logger();
            GameBordInitFillStrategy fillStrategy;

            // Choose fill strategy based on settings
            switch (UnchangeableSettings.FILL_STRATEGIE) {
                case 1:
                    fillStrategy = new ConnerFillStrategy();
                    break;
                default:
                    fillStrategy = new RandaomFillStrategy();
                    break;
            }

            // Initialize game board
            this.gameBoard = new GameBoard(UnchangeableSettings.NB_ROWS, UnchangeableSettings.NB_COLS, fillStrategy);

            this.addPlayer(UnchangeableSettings.NB_RANDOM_PLAYERS, this.gameBoard, "RP_", 0, new RandomStrategy());
            this.addPlayer(UnchangeableSettings.NB_MINIMAX_PLAYERS, this.gameBoard, "MINIMAX_",
                    UnchangeableSettings.NB_RANDOM_PLAYERS, new MinimaxStrategy());
            this.addPlayer(UnchangeableSettings.NB_MULTY_STRAT_PLAYERS, this.gameBoard, "MULTY_STRAT_",
                    UnchangeableSettings.NB_RANDOM_PLAYERS + UnchangeableSettings.NB_MINIMAX_PLAYERS,
                    new MultiStrategy());
            this.addPlayer(UnchangeableSettings.NB_HUMAIN_PLAYERS, this.gameBoard, "HUMAIN_",
                    UnchangeableSettings.NB_RANDOM_PLAYERS + UnchangeableSettings.NB_MINIMAX_PLAYERS
                            + UnchangeableSettings.NB_MULTY_STRAT_PLAYERS,
                    new HumainStrategy());

            // Fill the game board with entities
            gameBoard.fillGameBoard();

            int nbPlayers = UnchangeableSettings.NB_MINIMAX_PLAYERS + UnchangeableSettings.NB_RANDOM_PLAYERS +
                    UnchangeableSettings.NB_MULTY_STRAT_PLAYERS + UnchangeableSettings.NB_HUMAIN_PLAYERS;

            GameManager manager;
            switch (type) {
                case 0:
                    manager = null;
                    break;
                default:
                    manager = new GameManager(gameBoard, logger);
                    break;
            }

            // Create views for the game
            for (int i = 0; i < nbPlayers; i++) {
                FightGamePlayer player = gameBoard.getPlayers().get(i);
                this.gameViews.add(new GameView("View for Player " + player.getName(), gameBoard,
                        player.getGameBoardProxy(), manager, logger));
            }

            this.gameViews.add(new GameView("Main frame", gameBoard, null, manager, logger));

            if (manager != null) {
                manager.playGame();
            }

            this.dispose();
        } else {
            new InfosView(this, "Invalid configuration", "There are too many entities than boxes", false);
        }
    }

    /**
     * Adds players to the game board with the specified strategy.
     *
     * @param nbPlayers the number of players to add
     * @param gameBoard the game board to add players to
     * @param prefixe   the prefix to assign to the players' names
     * @param start     the index at which to start numbering the players
     * @param strategy  the strategy to assign to the players
     */
    private void addPlayer(int nbPlayers, GameBoard gameBoard, String prefixe, int start,
            FightGamePlayerStrategy strategy) {
        for (int i = 0; i < nbPlayers; i++) {
            FightGamePlayer player = new FightGamePlayer(gameBoard, prefixe + (i + 1), i + start, strategy);
            gameBoard.addPlayer(player);
        }
    }

   
}
