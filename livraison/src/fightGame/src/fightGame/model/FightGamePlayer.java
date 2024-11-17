package fightGame.model;

import java.io.Serializable;

import fightGame.UnchangeableSettings;
import gamePlayers.fighters.Unit;
import gamePlayers.fighters.UnitBuilder;
import gamePlayers.util.Player;
import gamePlayers.util.Position;

public abstract class FightGamePlayer implements Player, Serializable {
    protected GameBoardProxy gameBoardProxy;
    protected int playerIndex;
    protected Unit unit;
    protected String name;

    public FightGamePlayer(GameBoard gameBoard, String name, int playerIndex, Position startingposition){
        this.playerIndex = playerIndex;
        this.name = name;
        this.unit = new UnitBuilder()
                                .withName(name)
                                .withOwner(this)
                                .withPosition(startingposition)
                                .withBombs(UnchangeableSettings.NB_BOMBS, UnchangeableSettings.BOMB_DAMAGE, UnchangeableSettings.BOMB_TIMER)
                                .withMines(UnchangeableSettings.NB_MINES, UnchangeableSettings.MINE_DAMAGE)
                                .withProjectiles(UnchangeableSettings.NB_PROJECTILES, UnchangeableSettings.MINE_DAMAGE)
                                .withShieldRetention(UnchangeableSettings.SHIELD_ABSORPTION)
                                .withEnergy(UnchangeableSettings.STARTING_ENERGY)
                                .build();
        gameBoard.addEntity(unit, startingposition);
        this.gameBoardProxy = new GameBoardProxy(gameBoard, this);

    }

    public GameBoardProxy getGameBoardProxy() {
        return gameBoardProxy;
    }

    public void setGameBoardProxy(GameBoardProxy gameBoardProxy) {
        this.gameBoardProxy = gameBoardProxy;
    }

    @Override
    public Player clone() throws CloneNotSupportedException{
        return (Player) super.clone();
    }

    @Override
    public Unit getUnit() {
       return this.unit;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    
    

}
