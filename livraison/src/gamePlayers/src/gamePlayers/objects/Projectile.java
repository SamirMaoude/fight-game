package gamePlayers.objects;

import gamePlayers.util.*;

/**
 * Represents a projectile object in the game.
 * A projectile is a weapon with a defined range (scope) and damage,
 * which can be used to attack enemies in a specific direction.
 */
public class Projectile extends Weapon {

    private int scope;

    /**
     * Constructor for creating a new Projectile instance.
     * 
     * @param position The position of the projectile on the game field.
     * @param scope    The range or distance the projectile can travel.
     * @param damage   The damage the projectile will cause upon impact.
     * @param player   The player who owns and controls the projectile.
     * @throws IllegalArgumentException if the scope or damage is less than or equal
     *                                  to zero.
     */
    public Projectile(Position position, int scope, int damage, Player player) {
        super(EntityType.PROJECTILE, position, player, damage);
        if (scope <= 0) {
            throw new IllegalArgumentException("Projectile distance or damage value must be positive");
        }
        this.scope = scope;
    }

    /**
     * Retrieves the scope (distance) of the projectile.
     * 
     * @return The range or distance that the projectile can travel.
     */
    public int getScope() {
        return scope;
    }

    /**
     * Creates a clone of the current projectile.
     * 
     * @return A new instance of Projectile, identical to the current one.
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Projectile clone() throws CloneNotSupportedException {
        Position clonedPosition = this.getPosition() != null ? new Position(this.getPosition()) : null;
        
        Player clonedPlayer = this.getOwner() != null ?this.getOwner().clone(): null;

        return new Projectile(clonedPosition, this.getScope(), this.getDamage(), clonedPlayer);
    }
}
