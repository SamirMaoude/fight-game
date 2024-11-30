package gamePlayers.objects;

import gamePlayers.util.*;

/**
 * Represents a mine object in the game.
 * A mine is a type of weapon that has a set damage value and can be used by a
 * player.
 * 
 * <p>
 * Currently, the mine does not implement any active behavior but provides the
 * functionality
 * for cloning and handling the damage it inflicts when used.
 * </p>
 */
public class Mine extends Weapon {

    /**
     * Constructor for creating a new Mine instance.
     * 
     * @param position The position of the mine on the game field.
     * @param damage   The damage value that the mine inflicts when triggered.
     * @param player   The player who owns the mine.
     */
    public Mine(Position position, int damage, Player player) {
        super(EntityType.MINE, position, player, damage);
    }

    /**
     * Placeholder method for using the mine.
     * The behavior of using a mine should be defined here.
     * Currently, it does not implement any specific functionality.
     */
    public void use() {
        // Mine usage logic goes here
    }

    /**
     * Creates a clone of the current mine.
     * 
     * @return A new instance of Mine, identical to the current one.
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Mine clone() throws CloneNotSupportedException {
        Position clonedPosition = this.getPosition() != null ? new Position(this.getPosition()) : null;
        Player clonedPlayer = this.getOwner() != null ?this.getOwner().clone(): null;

        return new Mine(clonedPosition, this.getDamage(), clonedPlayer);
    }
}

