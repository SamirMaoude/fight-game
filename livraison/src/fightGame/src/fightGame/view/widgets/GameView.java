package fightGame.view.widgets;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import java.util.List;
import fightGame.controller.GameBoardAdapterToTable;
import fightGame.model.FightGameAction;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import fightGame.model.GameThreadManager;
import fightGame.model.io.*;
import fightGame.view.GUI;
import fightGame.view.InterfaceSetting;
import gamePlayers.util.Action;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;
import gamePlayers.util.Player;

public class GameView extends JFrame implements ModelListener, ActionListener {
    private GameButton nextButton;
    private GameButton saveButton;
    private GameButton exitButton;
    private GameButton homeButton;
    private GameButton pauseButton;
    private GameButton resumeButton;

    private boolean robotPlay;

    private GameBoard gameBoard;
    private GameBoardTable gameBoardTable;
    private DashBordView dashBordView;
    private GameBoardProxy proxy;
    private GameThreadManager threadManager;

    public GameView(String name, GameBoard gameBoard, GameBoardProxy proxy, boolean robotPlay, GameThreadManager threadManager) {
        super(name);
        this.gameBoard = gameBoard;
        this.proxy = proxy;
        this.gameBoard.addModelListener(this);
        this.robotPlay = robotPlay;
        this.threadManager = threadManager;
        GameBoardAdapterToTable gameBoardAdapterToTable = new GameBoardAdapterToTable(gameBoard, this.proxy);
        this.gameBoardTable = new GameBoardTable(gameBoardAdapterToTable);
        this.dashBordView = new DashBordView(gameBoard);

        buildContainer();
    }

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

        if(this.robotPlay){
            this.pauseButton = new GameButton("Pause", 150, 60);
            this.pauseButton.addActionListener(this);
            this.resumeButton = new GameButton("Resume", 150, 60);
            this.resumeButton.addActionListener(this);
            southPanel.add(this.pauseButton);
            southPanel.add(this.resumeButton);
        }else{
            this.saveButton = new GameButton("Save", 150, 60);
            this.saveButton.addActionListener(this);


            this.nextButton = new GameButton("Next", 150, 60);
            this.nextButton.addActionListener(this);
            southPanel.add(this.saveButton);
            southPanel.add(this.nextButton);
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

    @Override
    public void update(ListenableModel source) {
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.saveButton)) {
            GameBoardIO.saveGame(this,gameBoard);
        }
        
        if (e.getSource().equals(this.nextButton)) {
            if (!this.gameBoard.isGameOver()) {
                FightGamePlayer player = this.gameBoard.getNextPlayer();
                Action action = player.play();
                Logger.log(player, action.toString());
                this.gameBoard.performAction((FightGameAction) action, player);
            } else {
                new InfosView(this, "Information", "Game is over!!", true);
            }
        }

        if(e.getSource().equals(this.exitButton)){
            System.exit(0);
        }
        if(e.getSource().equals(this.homeButton)){
            List<GameView> views = GUI.gameViews;
            for (GameView gameView : views) {
                gameView.dispose();
            }
            GUI homeView = new GUI();
            
        }
        if(e.getSource().equals(this.pauseButton)){
            this.threadManager.pause();
        }
        if(e.getSource().equals(this.resumeButton)){
            this.threadManager.resume();
        }
    }
}
