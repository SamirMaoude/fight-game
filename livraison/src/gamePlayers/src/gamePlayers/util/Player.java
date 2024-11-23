package gamePlayers.util;

import gamePlayers.fighters.Unit;

/**
 * Represents a player in the game, providing methods for actions, cloning, and
 * equality.
 */
public interface Player extends Cloneable {

    /**
     * Retrieves the unit associated with the player.
     *
     * @return the player's unit
     */
    public Unit getUnit();

    /**
     * Defines the player's action during their turn.
     *
     * @return the action performed by the player
     */
    public Action play();

    /**
     * Compares this player to another object for equality.
     *
     * @param obj the object to compare with
     * @return true if the object is equal to this player, false otherwise
     */
    @Override
    public boolean equals(Object obj);

    /**
     * Creates and returns a copy of this player.
     *
     * @return a cloned player
     * @throws CloneNotSupportedException if cloning is not supported
     */
    public Player clone() throws CloneNotSupportedException;

    /**
     * Gets the index of the player in the game.
     *
     * @return the player's index
     */
    public int getPlayerIndex();
}
