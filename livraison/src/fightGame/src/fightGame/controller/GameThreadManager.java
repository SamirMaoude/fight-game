package fightGame.controller;


import fightGame.model.FightGameAction;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.io.Logger;
import fightGame.view.widgets.InfosView;
import gamePlayers.util.Action;
import gamePlayers.util.Player;
/**
 * Manages the execution of a game thread, handling player actions and game progression.
 */
public class GameThreadManager {
    private GameBoard gameBoard;
    private Logger logger;
    private boolean paused = false;
    private final Object pauseLock = new Object();

    /**
     * Constructor for GameThreadManager.
     *
     * @param gameBoard the game board to manage.
     * @param logger    the logger to record actions and events.
     */
    public GameThreadManager(GameBoard gameBoard, Logger logger) {
        this.gameBoard = gameBoard;
        this.logger = logger;
    }

    /**
     * Starts the game loop in a separate thread.
     */
    public void playGame() {
        new Thread(() -> run()).start();
    }

    /**
     * Executes the game loop, where each player takes turns until the game is over.
     */
    public void run() {
        while (!this.gameBoard.isGameOver()) {
            synchronized (pauseLock) {
                while (paused) {
                    try {
                        pauseLock.wait(); // Wait until the game is resumed
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }

            try {
                FightGamePlayer player = this.gameBoard.getNextPlayer();
                Action action = player.play();
                logger.log(player, action.toString());
                this.gameBoard.performAction((FightGameAction) action, player);
                Thread.sleep(1000); // Delay for better visibility of actions
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        boolean allDead = true;
        String winnerName = "";
        for (Player player : this.gameBoard.getPlayers()) {
            if (player.getUnit().isAlive()) {
                winnerName = player.getUnit().getName();
                allDead = false;
                break;
            }
        }

        if (allDead) {
            new InfosView(null, "Game Over", "All players are dead", true);
        } else {
            new InfosView(null, "Game Over", winnerName + " wins the game.", true);
        }
    }

    /**
     * Pauses the game execution.
     */
    public void pause() {
        paused = true;
        System.out.println("pause");
    }

    /**
     * Resumes the game execution.
     */
    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
            System.out.println("resume");
        }
    }
}
