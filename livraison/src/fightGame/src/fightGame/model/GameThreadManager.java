package fightGame.model;

import fightGame.view.widgets.InfosView;
import gamePlayers.util.Action;
import gamePlayers.util.Player;

public class GameThreadManager {
    private GameBoard gameBoard;
    private boolean paused = false;
    private final Object pauseLock = new Object();

    public GameThreadManager(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void playGame(){
        new Thread(()->run()).start();
    }

    public void run() {
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
                FightGamePlayer player = this.gameBoard.getNextPlayer();
                Action action = player.play();
                this.gameBoard.performAction((FightGameAction) action, player);
                Thread.sleep(1000);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
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
            new InfosView(null, "Game Over","All players are dead", true);

        }else{
            new InfosView(null, "Game Over", winnerName + " winne the game.", true);
        }

    }

    public void pause() {
        paused = true; 
        System.out.println("pause");
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false; 
            pauseLock.notifyAll(); 
            System.out.println("resume");
        }
    }
}
