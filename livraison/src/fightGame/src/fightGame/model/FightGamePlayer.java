package fightGame.model;

import gamePlayers.fighters.Unit;
import gamePlayers.util.Player;

public abstract class FightGamePlayer implements Player {
    protected GameBoardProxy gameBoardProxy;
    protected int playerIndex;

    public FightGamePlayer(GameBoardProxy gameBoardProxy, int playerIndex){
        this.gameBoardProxy = gameBoardProxy;
        this.playerIndex = playerIndex;
    }

    public GameBoardProxy getGameBoardProxy() {
        return gameBoardProxy;
    }

    public void setGameBoardProxy(GameBoardProxy gameBoardProxy) {
        this.gameBoardProxy = gameBoardProxy;
    }

}
