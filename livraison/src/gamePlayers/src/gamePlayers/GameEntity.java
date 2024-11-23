package gamePlayers;

import gamePlayers.util.EntityType;
import gamePlayers.util.Player;
import gamePlayers.util.Position;

/**
 * Interface representing a generic game entity.
 * Defines common behaviors and properties of entities in the game.
 */
public interface GameEntity {

    /**
     * Retrieves the position of the entity.
     *
     * @return the current position of the entity
     */
    public Position getPosition();

    /**
     * Updates the position of the entity.
     *
     * @param position the new position of the entity
     */
    public void setPosition(Position position);

    /**
     * Gets the type of the entity.
     *
     * @return the entity's type
     */
    public EntityType getType();

    /**
     * Destroys the entity, releasing any resources or references.
     */
    public void destroy();

    /**
     * Retrieves the owner of the entity.
     *
     * @return the player who owns this entity
     */
    public Player getOwner();

    /**
     * Sets the owner of the entity.
     *
     * @param player the player who will own this entity
     */
    public void setOwner(Player player);
}
