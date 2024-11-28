package fightGame.model;

import fightGame.model.io.Logger;
import fightGame.view.GUI;
import fightGame.view.widgets.ActionView;
import fightGame.view.widgets.GameView;
import gamePlayers.util.Action;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;

public class GameWithHumainManager implements ModelListener {
    private GameBoard gameBoard;
    private Logger logger;
    private FightGamePlayer currentPlayer;
    private boolean paused = false;
    private final Object pauseLock = new Object();
    private ActionView actionView;


    public GameWithHumainManager(GameBoard gameBoard, Logger logger){
        this.gameBoard = gameBoard;
        this.logger = logger;
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
                System.out.println(e.getMessage());
            }

        }
       /*  boolean allDead = true;
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
        }*/

    }

    @Override
    public void update(ListenableModel source) {
        Action action = this.actionView.getAction();
        logger.log(this.currentPlayer, action.toString());
        this.gameBoard.performAction((FightGameAction) action, this.currentPlayer);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        resume();
        //Thread.sleep(1000);
    }

    public void pause() {
        paused = true; 
        //System.out.println("pause");
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false; 
            pauseLock.notifyAll(); 
            System.out.println("resume");
        }
    }


}
