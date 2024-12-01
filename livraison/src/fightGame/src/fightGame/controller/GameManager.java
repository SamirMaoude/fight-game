package fightGame.controller;

import fightGame.model.*;
import fightGame.model.io.Logger;
import fightGame.model.strategy.HumainStrategy;
import fightGame.view.GUI;
import fightGame.view.widgets.*;
import gamePlayers.util.*;
/**
 * GameManager is responsible for managing a game session between human and AI players.
 * It coordinates the game flow, handles player turns, and updates the game state.
 * Specifically, it manages the interaction when the current player is human, showing a GUI for input.
 * It also pauses and resumes the game as necessary during the player's turn.
 */
public class GameManager implements ModelListener {
    private GameBoard gameBoard;
    private Logger logger;
    private FightGamePlayer currentPlayer;
    private boolean paused = false;
    private final Object pauseLock = new Object();
    private ActionView actionView;
    Thread thread;

    /**
     * Constructs a new GameWithHumainManager instance.
     *
     * @param gameBoard the game board managing the game state
     * @param logger the logger to log actions and events
     */
    public GameManager(GameBoard gameBoard, Logger logger){
        this.gameBoard = gameBoard;
        this.logger = logger;
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                play();
            }
        });
    }

    /**
     * Starts the game by launching the game thread.
     */
    public void playGame(){
        this.thread.start();
    }

    /**
     * The main game loop that continues to run as long as the game is not over.
     * It alternates between human and AI player turns, checking for game over conditions.
     * It pauses the game when a human player is taking their turn and resumes afterward.
     */
    @SuppressWarnings("static-access")
    private void play() {
        while (!this.gameBoard.isGameOver()) {
            synchronized (pauseLock) {
                while (paused) {
                    try {
                        pauseLock.wait(); 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return; 
                    }
                }
            }

            try {
                this.thread.sleep(3000);
                this.currentPlayer = this.gameBoard.getNextPlayer();
                if(this.currentPlayer.getStrategy().getClass().equals(HumainStrategy.class)){
                    for(GameView view : GUI.gameViews){
                        GameBoardProxy proxy = view.getProxy();
                        if(proxy != null){
                            if(proxy.getPlayer().equals(this.currentPlayer)){
                                this.actionView = new ActionView(view, proxy);
                                actionView.addModelListener(this);
                                pause(); // Pauses the game during human player's turn
                                break;
                            }
                        }
                    }
                }else{
                    Action action = this.currentPlayer.play();
                    logger.log(this.currentPlayer, action.toString());
                    this.gameBoard.performAction((FightGameAction)action, this.currentPlayer);
                }

            } catch (Exception e) {
                new InfosView(null, "Exception", e.getMessage(), false);
            }
        }
        // Determine the winner after the game ends
        boolean allDead = true;
        String winnerName = "";
        for (Player player : this.gameBoard.getPlayers()) {
            if(player.getUnit().isAlive()){
                winnerName = player.getUnit().getName();
                allDead = false;
                break;
            }
        }
        if(allDead){
            new InfosView(null, "Game Over", "All players are dead", true);
        } else {
            new InfosView(null, "Game Over", winnerName + " wins the game.", true);
        }
    }

    /**
     * Callback method invoked when the human player submits their action.
     * The action is performed and the game state is updated.
     *
     * @param source the source model that triggered the update
     */
    @Override
    public void update(ListenableModel source) {
        Action action = this.actionView.getAction();
        logger.log(this.currentPlayer, action.toString());
        this.gameBoard.performAction((FightGameAction) action, this.currentPlayer);
        resume();
    }

    /**
     * Pauses the game, preventing the game loop from proceeding until resumed.
     */
    public void pause() {
        synchronized (pauseLock) {
            paused = true;
        }
    }

    /**
     * Resumes the game by notifying the pause lock, allowing the game loop to proceed.
     */
    public void resume() {
        synchronized (pauseLock) {
            paused = false; 
            pauseLock.notifyAll(); 
        }
    }
}
