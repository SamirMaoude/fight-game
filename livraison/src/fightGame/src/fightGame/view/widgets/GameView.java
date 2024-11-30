package fightGame.view.widgets;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import fightGame.controller.GameBoardAdapterToTable;
import fightGame.controller.GameManager;
import fightGame.model.*;
import fightGame.model.io.*;
import fightGame.view.GUI;
import fightGame.view.InterfaceSetting;
import gamePlayers.util.*;
import gamePlayers.util.Action;

/**
 * Main view for the game, providing the graphical user interface (GUI) for
 * interacting with the game.
 * Displays the game board, player dashboard, and control buttons.
 */
public class GameView extends JFrame implements ModelListener, ActionListener {
    private GameButton nextButton;
    private GameButton saveButton;
    private GameButton exitButton;
    private GameButton homeButton;
    private GameButton pauseButton;
    private GameButton resumeButton;

    private Logger logger;
    private GameBoard gameBoard;
    private GameBoardTable gameBoardTable;
    private DashBordView dashBordView;
    private GameBoardProxy proxy;
    private GameManager manager;

    /**
     * Constructor for GameView.
     *
     * @param name          the title of the game window
     * @param gameBoard     the current game board
     * @param proxy         the proxy for game board actions
     * @param robotPlay     whether the game is in robot-play mode
     * @param threadManager the manager for game threads
     * @param logger        the logger to record actions
     */
    public GameView(String name, GameBoard gameBoard, GameBoardProxy proxy, GameManager manager,
            Logger logger) {
        super(name);
        this.gameBoard = gameBoard;
        this.proxy = proxy;
        this.gameBoard.addModelListener(this);
        this.logger = logger;
        this.manager = manager;

        GameBoardAdapterToTable gameBoardAdapterToTable = new GameBoardAdapterToTable(gameBoard, this.proxy);
        this.gameBoardTable = new GameBoardTable(gameBoardAdapterToTable);
        this.dashBordView = new DashBordView(gameBoard);

        buildContainer();
    }

    /**
     * Initializes and arranges the components of the game view.
     */
    private void buildContainer() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        this.exitButton = new GameButton("Exit", 150, 60);
        this.homeButton = new GameButton("Home", 150, 60);

        this.exitButton.addActionListener(this);
        this.homeButton.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        southPanel.setSize(InterfaceSetting.WIDTH, 300);
        southPanel.add(this.exitButton);
        southPanel.add(this.homeButton);

        this.saveButton = new GameButton("Save", 150, 60);
        this.saveButton = new GameButton("Save", 150, 60);
        this.pauseButton = new GameButton("Pause", 150, 60);
        this.resumeButton = new GameButton("Resume", 150, 60);

        this.saveButton.addActionListener(this);
        this.pauseButton.addActionListener(this);
        this.resumeButton.addActionListener(this);
        this.saveButton.addActionListener(this);

        southPanel.add(this.pauseButton);
        southPanel.add(this.resumeButton);
        southPanel.add(this.saveButton);
        southPanel.add(this.saveButton);

        if (this.manager == null) {
            this.nextButton = new GameButton("Next", 150, 60);
            southPanel.add(this.nextButton);
            this.nextButton.addActionListener(this);
        }

        this.dashBordView.setSize(500, 500);

        JScrollPane scrollPane = new JScrollPane(this.dashBordView);

        container.add(this.gameBoardTable, BorderLayout.WEST);
        container.add(scrollPane, BorderLayout.EAST);
        container.add(southPanel, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    public GameBoardProxy getProxy() {
        return proxy;
    }

    /**
     * Updates the GUI when the model changes.
     *
     * @param source the source model that triggered the update
     */
    @Override
    public void update(ListenableModel source) {
        this.revalidate();
        this.repaint();
    }

    /**
     * Handles button click events.
     *
     * @param e the event triggered by user interaction
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.saveButton)) {
            GameBoardIO.saveGame(this, gameBoard);
        }

        if (e.getSource().equals(this.nextButton)) {
            if (!this.gameBoard.isGameOver()) {
                FightGamePlayer player = this.gameBoard.getNextPlayer();
                Action action = player.play();
                logger.log(player, action.toString());
                this.gameBoard.performAction((FightGameAction) action, player);
            } else {
                new InfosView(this, "Information", "Game is over!!", true);
            }
        }

        if (e.getSource().equals(this.exitButton)) {
            System.exit(0);
        }

        if (e.getSource().equals(this.homeButton)) {
            List<GameView> views = GUI.gameViews;
            for (GameView gameView : views) {
                gameView.dispose();
            }
            new GUI();
        }

        if (e.getSource().equals(this.pauseButton)) {
            if (this.manager != null) {
                this.manager.pause();
            }
        }

        if (e.getSource().equals(this.resumeButton)) {
            if (this.manager != null) {
                this.manager.resume();
            }
        }
    }
}
