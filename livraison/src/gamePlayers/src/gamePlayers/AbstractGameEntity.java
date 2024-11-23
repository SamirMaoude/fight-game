package gamePlayers;

import java.io.Serializable;

import gamePlayers.util.*;

/**
 * Abstract base class representing a game entity.
 * Implements common behaviors for all entities in the game.
 * Extends {@link AbtractListenableModel} and implements {@link GameEntity}.
 */
public abstract class AbstractGameEntity extends AbtractListenableModel implements GameEntity, Cloneable, Serializable {

    /**
     * The type of the entity (e.g., UNIT, BOMB, MINE, etc.).
     */
    protected EntityType type;

    /**
     * The position of the entity on the game board.
     */
    protected Position position;

    /**
     * The owner of the entity (player).
     */
    protected Player owner;

    /**
     * Constructs an AbstractGameEntity with the specified type, position, and
     * owner.
     *
     * @param type     the type of the entity
     * @param position the position of the entity
     * @param owner    the player who owns this entity
     */
    public AbstractGameEntity(EntityType type, Position position, Player owner) {
        this.type = type;
        this.position = position;
        this.owner = owner;
    }

    /**
     * Gets the position of the entity.
     *
     * @return the position of the entity
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the entity.
     *
     * @param position the new position of the entity
     */
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets the type of the entity.
     *
     * @return the entity type
     */
    @Override
    public EntityType getType() {
        return this.type;
    }

    /**
     * Sets the owner of the entity.
     *
     * @param player the new owner of the entity
     */
    @Override
    public void setOwner(Player player) {
        this.owner = player;
    }

    /**
     * Gets the owner of the entity.
     *
     * @return the player who owns this entity
     */
    @Override
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Creates a clone of this entity.
     *
     * @return a cloned instance of this entity
     * @throws CloneNotSupportedException if cloning is not supported
     */
    @Override
    public AbstractGameEntity clone() throws CloneNotSupportedException {
        return (AbstractGameEntity) super.clone();
    }

    /**
     * Destroys the entity, releasing any associated resources or references.
     */
    public void destroy() {
        // Default implementation (override if needed)
    }

    /**
     * Returns a string representation of the entity, typically its type.
     *
     * @return a string representing the entity
     */
    @Override
    public String toString() {
        return this.type.toString();
    }
}
