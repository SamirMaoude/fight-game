package gamePlayers.fighters;

import java.util.LinkedList;
import gamePlayers.objects.Bomb;
import gamePlayers.objects.Mine;
import gamePlayers.objects.Projectile;
import gamePlayers.util.*;

/**
 * Builder class for constructing instances of {@link Unit}.
 * This class provides a fluent API to set various properties for a unit before
 * creating it.
 */
public class UnitBuilder {
    private Position position;
    private Player owner;
    private String name;
    private int energy;
    private LinkedList<Bomb> bombs;
    private LinkedList<Mine> mines;
    private LinkedList<Projectile> projectiles;
    private int shieldRetention;

    /**
     * Initializes the UnitBuilder with default values.
     */
    public UnitBuilder() {
        this.position = null;
        this.name = null;
        this.energy = 0;
        this.bombs = null;
        this.mines = null;
        this.projectiles = null;
        this.owner = null;
        this.shieldRetention = 0;
    }

    /**
     * Sets the owner of the unit.
     * 
     * @param player The player who owns the unit.
     * @return The current instance of {@link UnitBuilder}.
     */
    public UnitBuilder withOwner(Player player) {
        this.owner = player;
        return this;
    }

    /**
     * Sets the position of the unit.
     * 
     * @param position The position where the unit will be placed.
     * @return The current instance of {@link UnitBuilder}.
     */
    public UnitBuilder withPosition(Position position) {
        this.position = position;
        return this;
    }

    /**
     * Sets the name of the unit.
     * 
     * @param name The name of the unit.
     * @return The current instance of {@link UnitBuilder}.
     */
    public UnitBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the shield retention value of the unit.
     * 
     * @param shieldRetention The shield retention value.
     * @return The current instance of {@link UnitBuilder}.
     */
    public UnitBuilder withShieldRetention(int shieldRetention) {
        this.shieldRetention = shieldRetention;
        return this;
    }

    /**
     * Sets the energy of the unit.
     * 
     * @param energy The energy value of the unit.
     * @return The current instance of {@link UnitBuilder}.
     */
    public UnitBuilder withEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    /**
     * Sets the number of bombs the unit will have, along with their damage and time
     * before explosion.
     * 
     * @param nbBombs             The number of bombs.
     * @param damage              The damage value of each bomb.
     * @param timeBeforeExplosion The time before the bomb explodes after being
     *                            deployed.
     * @return The current instance of {@link UnitBuilder}.
     */
    public UnitBuilder withBombs(int nbBombs, int damage, int timeBeforeExplosion) {
        LinkedList<Bomb> bombs = new LinkedList<>();
        for (int i = 0; i < nbBombs; i++) {
            bombs.add(new Bomb(null, damage, this.owner, timeBeforeExplosion));
        }
        this.bombs = bombs;
        return this;
    }

    /**
     * Sets the number of mines the unit will have, along with their damage.
     * 
     * @param nbMines The number of mines.
     * @param damage  The damage value of each mine.
     * @return The current instance of {@link UnitBuilder}.
     */
    public UnitBuilder withMines(int nbMines, int damage) {
        LinkedList<Mine> mines = new LinkedList<>();
        for (int i = 0; i < nbMines; i++) {
            mines.add(new Mine(null, damage, this.owner));
        }
        this.mines = mines;
        return this;
    }

    /**
     * Sets the number of projectiles the unit will have, along with their scope and
     * damage.
     * 
     * @param nbProjectiles The number of projectiles.
     * @param scope         The scope (range) of each projectile.
     * @param damage        The damage value of each projectile.
     * @return The current instance of {@link UnitBuilder}.
     */
    public UnitBuilder withProjectiles(int nbProjectiles, int scope, int damage) {
        LinkedList<Projectile> projectiles = new LinkedList<>();
        for (int i = 0; i < nbProjectiles; i++) {
            projectiles.add(new Projectile(null, scope, damage, this.owner));
        }
        this.projectiles = projectiles;
        return this;
    }

    /**
     * Builds and returns the {@link Unit} instance with the specified properties.
     * 
     * @return The constructed {@link Unit} instance.
     */
    public Unit build() {
        return new Unit(this.position, this.name, this.owner, this.energy, this.bombs, this.mines, this.projectiles,
                this.shieldRetention);
    }
}
