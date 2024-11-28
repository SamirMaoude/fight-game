package fightGame.view;

import javax.swing.*;

import fightGame.UnchangeableSettings;
import fightGame.controller.GameThreadManager;
import fightGame.controller.GameWithHumainManager;
import fightGame.model.*;
import fightGame.model.aiAlgorithms.*;
import fightGame.model.io.GameBoardIO;
import fightGame.model.io.Logger;
import fightGame.model.strategy.*;
import fightGame.view.widgets.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The main GUI class for the Fight Game. This class manages the home screen where the user can start a new game,
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
     */
    public GUI() {
        UnchangeableSettings.loadSettings();
        this.gameViews = new ArrayList<>();

        setTitle("Home View");
        buildView();
    }

    /**
     * Builds the layout of the home screen, including buttons for loading, starting a new game, and exiting.
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
        robotButton = new GameButton("Robot", 150, 50);
        humainButton = new GameButton("Humain", 150, 50);

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
     * Handles button click events to start a new game, load a game, or exit the application.
     *
     * @param e the ActionEvent triggered by a button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.newGameButton)) {
            this.newGame(false,false);
        }
        if (e.getSource().equals(this.loadButton)) {
            this.loadGame();
        }
        if (e.getSource().equals(this.exitButton)) {
            System.exit(0);
        }
        if (e.getSource().equals(this.robotButton)) {
            showRobotPlay();
        }

        if (e.getSource().equals(this.robotButton)) {
            showRobotPlay();
        }
        if(e.getSource().equals(this.humainButton)){
            this.newGame(false, true);
        }
    }

    /**
     * Starts a new game with robot players.
     */
    public void showRobotPlay() {
        this.newGame(true,false);
    }

    /**
     * Starts a new game with the option to include robot players.
     *
     * @param withRobot indicates whether the game should include robot players
     */
    public void newGame(boolean withRobot, boolean withHumain) {
        int nbEntity = UnchangeableSettings.NB_MINIMAX_PLAYERS + UnchangeableSettings.NB_RANDOM_PLAYERS +
                UnchangeableSettings.NB_MULTY_STRAT_PLAYERS + UnchangeableSettings.NB_WALL + UnchangeableSettings.NB_INIT_PELLET;
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

            // Add players based on configuration
            for (int i = 0; i < UnchangeableSettings.NB_RANDOM_PLAYERS; i++) {
                FightGamePlayer player = new FightGamePlayer(gameBoard, "RP_" + (i + 1), i);
                player.setStrategy(new RandomStrategy());
                gameBoard.addPlayer(player);
            }
            for (int i = 0; i < UnchangeableSettings.NB_MINIMAX_PLAYERS; i++) {
                FightGamePlayer player = new FightGamePlayer(gameBoard, "MINIMAX_" + (i + 1),
                        (i + UnchangeableSettings.NB_RANDOM_PLAYERS));
                player.setStrategy(new MinimaxStrategy());
                gameBoard.addPlayer(player);
            }
            for (int i = 0; i < UnchangeableSettings.NB_MULTY_STRAT_PLAYERS; i++) {
                FightGamePlayer player = new FightGamePlayer(gameBoard, "MULTY_STRAT_" + (i + 1),
                        (i + UnchangeableSettings.NB_RANDOM_PLAYERS));
                player.setStrategy(new MultiStrategy());
                gameBoard.addPlayer(player);
            }

            // Fill the game board with entities
            gameBoard.fillGameBoard();

            int nbPlayers = UnchangeableSettings.NB_MINIMAX_PLAYERS + UnchangeableSettings.NB_RANDOM_PLAYERS +
                    UnchangeableSettings.NB_MULTY_STRAT_PLAYERS;

            GameThreadManager threadManager = null;
            if (withRobot) {
                threadManager = new GameThreadManager(gameBoard, logger);
            }

            this.gameViews.add(new GameView("View", gameBoard, null, withRobot,withHumain, threadManager, logger));

            for (int i = 0; i < nbPlayers; i++) {
                FightGamePlayer player = gameBoard.getPlayers().get(i);
                this.gameViews.add(new GameView("View for Player " + player.getName(), gameBoard,
                        player.getGameBoardProxy(), withRobot,withHumain, threadManager, logger));
            }

            if(withHumain){
                GameWithHumainManager gameWithHumainManager = new GameWithHumainManager(gameBoard, logger);
                gameWithHumainManager.playGame();
            }else{
                if (withRobot) {
                    threadManager.playGame();
                }
            }
            this.dispose();
        } else {
            new InfosView(this, "Invalid configuration", "There are too many entities than boxes", false);
        }
    }

    /**
     * Opens the file chooser dialog to load a previously saved game.
     */
    private void loadGame() {
        GameBoardIO.chooseFile(this);
    }
}
