package fightGame.model;

import java.io.Serializable;
import java.util.Objects;

import fightGame.UnchangeableSettings;
import fightGame.model.strategy.FightGamePlayerStrategy;
import fightGame.model.strategy.aiAlgorithms.RandomStrategy;
import gamePlayers.fighters.Unit;
import gamePlayers.fighters.UnitBuilder;
import gamePlayers.util.Action;
import gamePlayers.util.Player;

/**
 * Represents a player in the Fight Game, extending the {@link Player}
 * interface.
 * The player is associated with a {@link Unit} and a strategy that defines the
 * player's behavior during the game.
 * The player can interact with the game world by performing actions such as
 * moving, using mines, bombs, etc.
 * Each player has a unique index and a name.
 */
public class FightGamePlayer implements Player, Serializable {

    protected GameBoardProxy gameBoardProxy; // Proxy for the GameBoard
    protected int playerIndex; // The player's index in the game
    protected Unit unit; // The unit representing the player in the game
    protected String name; // The player's name
    private FightGamePlayerStrategy strategy; // The strategy used by the player to make moves

    /**
     * Constructs a new FightGamePlayer with the specified game board, name, and
     * player index.
     * The unit for the player is built using the provided settings.
     *
     * @param gameBoard   the game board the player interacts with
     * @param name        the name of the player
     * @param playerIndex the index of the player
     */
    public FightGamePlayer(GameBoard gameBoard, String name, int playerIndex, FightGamePlayerStrategy strategy) {
        this.playerIndex = playerIndex;
        this.name = name;
        this.unit = new UnitBuilder()
                .withName(name)
                .withOwner(this)
                .withBombs(UnchangeableSettings.NB_BOMBS, UnchangeableSettings.BOMB_DAMAGE,
                        UnchangeableSettings.BOMB_TIMER)
                .withMines(UnchangeableSettings.NB_MINES, UnchangeableSettings.MINE_DAMAGE)
                .withProjectiles(UnchangeableSettings.NB_PROJECTILES, UnchangeableSettings.PROJECTILE_SCOPE,
                        UnchangeableSettings.PROJECTILE_DAMAGE)
                .withShieldRetention(UnchangeableSettings.SHIELD_ABSORPTION)
                .withEnergy(UnchangeableSettings.STARTING_ENERGY)
                .build();
        this.strategy = strategy;
        this.gameBoardProxy = new GameBoardProxy(gameBoard, this);
    }

    public FightGamePlayer(GameBoard gameBoard, String name, int playerIndex) {
        this(gameBoard, name, playerIndex, null);
    }

    /**
     * Constructs a new FightGamePlayer with the specified game board and player
     * index.
     * The player's name is set based on the player index.
     *
     * @param gameBoard   the game board the player interacts with
     * @param playerIndex the index of the player
     */
    public FightGamePlayer(GameBoard gameBoard, int playerIndex) {
        this.playerIndex = playerIndex;
        this.name = Integer.toString(playerIndex);
        this.gameBoardProxy = new GameBoardProxy(gameBoard, this);
    }

    /**
     * Creates a copy of the specified FightGamePlayer.
     *
     * @param player the player to clone
     */
    public FightGamePlayer(FightGamePlayer player) {
        try {
            this.playerIndex = player.getPlayerIndex();
            this.unit = player.getUnit().clone();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * Returns the proxy for the game board associated with this player.
     *
     * @return the game board proxy
     */
    public GameBoardProxy getGameBoardProxy() {
        return gameBoardProxy;
    }

    /**
     * Sets the proxy for the game board associated with this player.
     *
     * @param gameBoardProxy the game board proxy to set
     */
    public void setGameBoardProxy(GameBoardProxy gameBoardProxy) {
        this.gameBoardProxy = gameBoardProxy;
    }

    /**
     * Creates and returns a copy of the player.
     * 
     * @return a cloned player
     * @throws CloneNotSupportedException if the player cannot be cloned
     */
    @Override
    public Player clone() throws CloneNotSupportedException {
        return (Player) super.clone();
    }

    /**
     * Returns the unit representing this player in the game.
     *
     * @return the player's unit
     */
    @Override
    public Unit getUnit() {
        return this.unit;
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Performs the action based on the player's strategy.
     * If no strategy is set, a random strategy is used.
     *
     * @return the action performed by the player
     */
    @Override
    public Action play() {
        if (strategy != null) {
            return strategy.play(this, gameBoardProxy);
        }
        return new RandomStrategy().play(this, gameBoardProxy);
    }

    /**
     * Sets the strategy used by the player to make decisions.
     *
     * @param strategy the strategy to set
     */
    public void setStrategy(FightGamePlayerStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Returns the player's index.
     *
     * @return the player's index
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    /**
     * Returns the strategy used by the player.
     *
     * @return the player's strategy
     */
    public FightGamePlayerStrategy getStrategy() {
        return strategy;
    }

    /**
     * Returns a hash code for this player based on their player index.
     *
     * @return the hash code for this player
     */
    @Override
    public int hashCode() {
        return Objects.hash(playerIndex);
    }

    /**
     * Compares this player to another object for equality.
     * Players are considered equal if they have the same player index.
     *
     * @param obj the object to compare to
     * @return true if the players are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FightGamePlayer other = (FightGamePlayer) obj;
        return playerIndex == other.getPlayerIndex();
    }

    /**
     * Sets the unit representing this player.
     *
     * @param unit the unit to set
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
