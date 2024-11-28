package fightGame.controller;

import fightGame.model.FightGameAction;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import fightGame.model.io.Logger;
import fightGame.view.GUI;
import fightGame.view.widgets.ActionView;
import fightGame.view.widgets.GameView;
import fightGame.view.widgets.InfosView;
import gamePlayers.util.Action;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;
import gamePlayers.util.Player;

public class GameWithHumainManager implements ModelListener {
    private GameBoard gameBoard;
    private Logger logger;
    private FightGamePlayer currentPlayer;
    private boolean paused = false;
    private final Object pauseLock = new Object();
    private ActionView actionView;
    Thread thread;


    public GameWithHumainManager(GameBoard gameBoard, Logger logger){
        this.gameBoard = gameBoard;
        this.logger = logger;
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                play();
            }
        });

    }

    public void playGame(){
        this.thread.start();
    }

    @SuppressWarnings("static-access")
    public void play() {
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
                this.thread.sleep(2000);
                this.currentPlayer = this.gameBoard.getNextPlayer();
                for(GameView view : GUI.gameViews){
                    GameBoardProxy proxy = view.getProxy();
                    if(proxy != null){
                        if(proxy.getPlayer().equals(this.currentPlayer)){
                            this.actionView = new ActionView(view);
                            actionView.addModelListener(this);
                            break;
                        }
                    }
                    
                }
                
                pause();

            } catch (Exception e) {
                new InfosView(null, "Exception", e.getMessage(), false);
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

    @Override
    public void update(ListenableModel source) {
        Action action = this.actionView.getAction();
        logger.log(this.currentPlayer, action.toString());
        this.gameBoard.performAction((FightGameAction) action, this.currentPlayer);
        
        resume();
    }

    public void pause() {
        synchronized (pauseLock) {
            paused = true; // Définir le flag sur "pause"
        }
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false; 
            pauseLock.notifyAll(); 
            System.out.println("resume");
        }
    }


}
