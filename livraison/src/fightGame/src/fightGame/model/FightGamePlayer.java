package fightGame.model;

import java.io.Serializable;
import java.util.Objects;

import fightGame.UnchangeableSettings;
import fightGame.model.aiAlgorithms.RandomStrategy;
import fightGame.model.strategy.FightGamePlayerStrategy;
import gamePlayers.fighters.Unit;
import gamePlayers.fighters.UnitBuilder;
import gamePlayers.util.Action;
import gamePlayers.util.Player;

public class FightGamePlayer implements Player, Serializable {
    protected GameBoardProxy gameBoardProxy;
    protected int playerIndex;
    protected Unit unit;
    protected String name;
    private FightGamePlayerStrategy strategy;
    
    public FightGamePlayer(GameBoard gameBoard, String name, int playerIndex){
        this.playerIndex = playerIndex;
        this.name = name;
        this.unit = new UnitBuilder()
                                .withName(name)
                                .withOwner(this)
                                .withBombs(UnchangeableSettings.NB_BOMBS, UnchangeableSettings.BOMB_DAMAGE, UnchangeableSettings.BOMB_TIMER)
                                .withMines(UnchangeableSettings.NB_MINES, UnchangeableSettings.MINE_DAMAGE)
                                .withProjectiles(UnchangeableSettings.NB_PROJECTILES, UnchangeableSettings.PROJECTILE_SCOPE, UnchangeableSettings.PROJECTILE_DAMAGE)
                                .withShieldRetention(UnchangeableSettings.SHIELD_ABSORPTION)
                                .withEnergy(UnchangeableSettings.STARTING_ENERGY)
                                .build();
       // gameBoard.addEntity(unit, startingposition);
        this.gameBoardProxy = new GameBoardProxy(gameBoard, this);

    }

    public FightGamePlayer(FightGamePlayer player){
        try {
            this.playerIndex = player.getPlayerIndex();
            this.unit = player.getUnit().clone();
        } catch (Exception e) {
           System.out.print(e.getMessage());
        }
        
    }

    public FightGamePlayer(GameBoard gameBoard, int playerIndex){
        this.playerIndex = playerIndex;
        this.name = Integer.toString(playerIndex);
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

    @Override
    public Action play() {
        
        if (strategy != null) {
            return strategy.play(this, gameBoardProxy);
        } 

        return new RandomStrategy().play(this, gameBoardProxy);

    }

    public void setStrategy(FightGamePlayerStrategy strategy) {
        this.strategy = strategy;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public FightGamePlayerStrategy getStrategy() {
        return strategy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerIndex);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FightGamePlayer other = (FightGamePlayer) obj;
        if (playerIndex != other.getPlayerIndex())
            return false;
        return true;
    }

    public void setUnit(Unit unit){
        this.unit = unit;
    }

    

    
    

}
