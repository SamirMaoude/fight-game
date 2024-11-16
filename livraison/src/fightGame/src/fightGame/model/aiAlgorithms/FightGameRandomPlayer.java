package fightGame.model.aiAlgorithms;

import java.util.List;
import java.util.Random;

import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import gamePlayers.util.Action;
import gamePlayers.util.Position;

public class FightGameRandomPlayer extends FightGamePlayer{
    private GameBoardProxy gameBoardProxy;
    
    public FightGameRandomPlayer(GameBoard gameBoard, String name, int playerIndex, Position startingposition){
        super(gameBoard, name, playerIndex, startingposition);
        this.gameBoardProxy = new GameBoardProxy(gameBoard, this);
    }

    @Override
    public Action play() {
        List<Action> actions = this.gameBoardProxy.getActions();

        Random random = new Random();
        Action randomAction = actions.get(random.nextInt(actions.size()));
        

        return randomAction;
    }

    public GameBoardProxy getGameBoardProxy() {
        return gameBoardProxy;
    }

    

}
