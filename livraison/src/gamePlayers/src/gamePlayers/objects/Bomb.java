package gamePlayers.objects;

import gamePlayers.util.*;

/**
 * Represents a bomb object in the game.
 * A bomb has a set damage value and a time before it explodes.
 * The bomb can be detonated after its time reaches zero.
 * 
 * <p>
 * The bomb can also decrease its time before explosion each turn, and it can be
 * cloned to create identical bombs.
 * </p>
 */
public class Bomb extends Weapon {

    private int timeBeforeExplosion;

    /**
     * Constructor for creating a new Bomb instance.
     * 
     * @param position            The position of the bomb on the game field.
     * @param damage              The damage value that the bomb inflicts when it
     *                            detonates.
     * @param player              The player who owns the bomb.
     * @param timeBeforeExplosion The time (in turns) before the bomb explodes.
     * @throws IllegalArgumentException if the timeBeforeExplosion is negative.
     */
    public Bomb(Position position, int damage, Player player, int timeBeforeExplosion) {
        super(EntityType.BOMB, position, player, damage);
        if (timeBeforeExplosion < 0) {
            throw new IllegalArgumentException("You cannot provide a value under 0 as bomb time.");
        }
        this.timeBeforeExplosion = timeBeforeExplosion;
    }

    /**
     * Retrieves the time remaining before the bomb explodes.
     * 
     * @return The time remaining before the bomb explodes.
     */
    public int getTimeBeforeExplosion() {
        return timeBeforeExplosion;
    }

    /**
     * Creates a clone of the current bomb.
     * 
     * @return A new instance of Bomb, identical to the current one.
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Bomb clone() throws CloneNotSupportedException {
        Position clonedPosition = this.getPosition() != null ? new Position(this.getPosition()) : null;
        Player clonedPlayer = this.getOwner() != null ?this.getOwner().clone(): null;

        return new Bomb(clonedPosition, this.getDamage(), clonedPlayer, this.timeBeforeExplosion);
    }

    /**
     * Decreases the time before explosion by 1.
     * This method is called each turn to reduce the time before the bomb detonates.
     */
    public void decreaseTime() {
        this.timeBeforeExplosion--;
    }

    /**
     * Sets the time remaining before the bomb explodes.
     * 
     * @param timeBeforeExplosion The new time before explosion.
     */
    public void setTimeBeforeExplosion(int timeBeforeExplosion) {
        this.timeBeforeExplosion = timeBeforeExplosion;
    }
}
