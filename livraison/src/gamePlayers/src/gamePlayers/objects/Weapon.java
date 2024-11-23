package gamePlayers.objects;

import gamePlayers.AbstractGameEntity;
import gamePlayers.fighters.Unit;
import gamePlayers.util.*;

/**
 * Abstract class representing a weapon in the game.
 * A weapon is an entity that causes damage to other units when used.
 */
public abstract class Weapon extends AbstractGameEntity {

    protected int damage;
    protected Unit unit;

    /**
     * Constructor for creating a new Weapon instance.
     * 
     * @param type     The type of the weapon (e.g., bomb, projectile).
     * @param position The position of the weapon on the game field.
     * @param player   The player who owns the weapon.
     * @param damage   The damage value of the weapon.
     * @throws IllegalArgumentException if the damage is less than 0.
     */
    public Weapon(EntityType type, Position position, Player player, int damage) {
        super(type, position, player);
        if (damage < 0) {
            throw new IllegalArgumentException("Weapon damage value must be positive");
        }
        this.damage = damage;
    }

    /**
     * Sets the damage value of the weapon.
     * 
     * @param damage The new damage value to be set for the weapon.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Retrieves the unit associated with the weapon.
     * 
     * @return The unit that owns or controls this weapon.
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * Sets the unit that owns or controls the weapon.
     * 
     * @param unit The unit to be set as the owner of this weapon.
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * Retrieves the damage value of the weapon.
     * 
     * @return The damage value of the weapon.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Destroys the weapon by clearing its position and unit references.
     * This effectively removes the weapon from the game world.
     */
    @Override
    public void destroy() {
        this.position = null;
        this.unit = null;
    }
}
