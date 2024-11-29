package gamePlayers.fighters;

import java.util.LinkedList;
import gamePlayers.AbstractGameEntity;
import gamePlayers.objects.*;
import gamePlayers.util.*;

/**
 * Represents a unit in the game, which can possess various attributes
 * such as name, energy, weapons, and shields.
 */
public class Unit extends AbstractGameEntity {

    private String name;
    private int energy;
    private LinkedList<Bomb> bombs;
    private LinkedList<Mine> mines;
    private LinkedList<Projectile> projectiles;
    private int shieldRetention;
    private int shieldTimer = 0;
    private boolean shieldActivated = false;

    /**
     * Constructor for creating a new Unit.
     * 
     * @param position        The position of the unit on the game field.
     * @param name            The name of the unit.
     * @param owner           The player that owns the unit.
     * @param energy          The energy of the unit.
     * @param bombs           The bombs available to the unit.
     * @param mines           The mines available to the unit.
     * @param projectiles     The projectiles available to the unit.
     * @param shieldRetention The shield retention value.The shield retention value.
     * @throws IllegalArgumentException if the unit name is empty.
     */
    public Unit(Position position, String name, Player owner, int energy, LinkedList<Bomb> bombs,
            LinkedList<Mine> mines, LinkedList<Projectile> projectiles, int shieldRetention) {
        super(EntityType.UNIT, position, owner);

        if (name.isEmpty()) {
            throw new IllegalArgumentException("You cannot provide an empty string as unit name!");
        }
        this.name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        this.energy = energy;
        this.bombs = bombs;
        this.mines = mines;
        this.projectiles = projectiles;
        this.shieldRetention = shieldRetention;
    }

    /**
     * Retrieves the current name of the unit.
     * 
     * @return The name of the unit.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Reduces the unit's energy when it receives damage.
     * If the shield is active, the shield will absorb part of the damage.
     * 
     * @param damage The damage to be received by the unit.
     */
    public void receiveDamage(int damage) {
        if(damage>0){
            if (this.shieldActivated) {
                if (damage >= this.shieldRetention) {
                    this.energy -= damage;
                    this.energy += this.shieldRetention;
                }
            } else {
                this.energy -= damage;
            }
        }
    }

    /**
     * Activates or deactivates the unit's shield.
     * 
     * @param shieldActivated The state of the shield (active or not).
     */
    public void setShieldActivated(boolean shieldActivated) {
        this.shieldActivated = shieldActivated;
    }

    /**
     * Retrieves the status of the shield.
     * 
     * @return True if the shield is activated, false otherwise.
     */
    public boolean getShieldActivated() {
        return this.shieldActivated;
    }

    /**
     * Retrieves the current energy of the unit.
     * 
     * @return The unit's energy.
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Updates the name of the unit.
     * 
     * @param name The new name of the unit.
     * @throws IllegalArgumentException if the name is empty.
     */
    public void setName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("You cannot provide an empty string as unit name!");
        }

        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        this.name = name;
    }

    /**
     * 
     * @return The shield retention value.
     */

    public int getShieldRetention() {
        return shieldRetention;
    }


    /**
     * Retrieves the list of bombs available to the unit.
     * 
     * @return A list of bombs.
     */
    public LinkedList<Bomb> getBombs() {
        return bombs;
    }

    /**
     * Retrieves the list of mines available to the unit.
     * 
     * @return A list of mines.
     */
    public LinkedList<Mine> getMines() {
        return mines;
    }

    /**
     * Retrieves the list of projectiles available to the unit.
     * 
     * @return A list of projectiles.
     */
    public LinkedList<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * Sets the shield timer for the unit.
     * 
     * @param time The shield timer value.
     */
    public void setShieldTimer(int time) {
        this.shieldTimer = time;
    }

    
    /**
     * Checks if the shield is currently activated.
     * 
     * @return True if the shield is activated, false otherwise.
     */
    public boolean isShieldActivated() {
        return this.shieldActivated;
    }

    /**
     * Retrieves the current shield timer value.
     * 
     * @return The shield timer.
     */
    public int getShieldTimer() {
        return this.shieldTimer;
    }

    /**
     * Moves the unit in the specified direction.
     * 
     * @param move The direction to move the unit.
     */
    public void move(Direction move) {
        switch (move) {
            case LEFT:
                this.position.setCol(this.position.getCol() - 1);
                break;
            case RIGHT:
                this.position.setCol(this.position.getCol() + 1);
                break;
            case TOP:
                this.position.setRow(this.position.getRow() - 1);
                break;
            default:
                this.position.setRow(this.position.getRow() + 1);
                break;
        }
    }

    /**
     * Checks if the unit has any bombs available.
     * 
     * @return True if the unit has bombs, false otherwise.
     */
    public boolean hasBombs() {
        return !(this.bombs == null || this.bombs.isEmpty());
    }

    /**
     * Checks if the unit has any mines available.
     * 
     * @return True if the unit has mines, false otherwise.
     */
    public boolean hasMines() {
        return !(this.mines == null || this.mines.isEmpty());
    }

    /**
     * Checks if the unit has any projectiles available.
     * 
     * @return True if the unit has projectiles, false otherwise.
     */
    public boolean hasProjectiles() {
        return !(this.projectiles == null || this.projectiles.isEmpty());
    }

    /**
     * The unit picks up a pellet, increasing its energy.
     * 
     * @param pellet The pellet to be picked up by the unit.
     */
    public void takePellet(Pellet pellet) {
        this.energy += pellet.getEnergy();
    }

    /**
     * The unit uses a bomb, if available.
     * 
     * @return The bomb used, or null if no bombs are available.
     */
    public Bomb useBomb() {
        if (this.hasBombs()) {
            return this.bombs.pop();
        }
        return null;
    }

    /**
     * The unit uses a mine, if available.
     * 
     * @return The mine used, or null if no mines are available.
     */
    public Mine useMine() {
        if (this.hasMines()) {
            return this.mines.pop();
        }
        return null;
    }

    /**
     * The unit uses a projectile, if available.
     * 
     * @return The projectile used, or null if no projectiles are available.
     */
    public Projectile useProjectile() {
        if (this.hasProjectiles()) {
            return this.projectiles.pop();
        }
        return null;
    }

    /**
     * Destroys the unit, nullifying its position and weapons.
     */
    @Override
    public void destroy() {
        this.position = null;
        this.bombs = null;
        this.mines = null;
        this.projectiles = null;
    }

    /**
     * Checks if the unit is still alive, i.e., has energy greater than 0.
     * 
     * @return True if the unit is alive, false otherwise.
     */
    public boolean isAlive() {
        return this.energy > 0;
    }

    /**
     * Creates and returns a deep copy (clone) of the current unit.
     * This method clones all the properties of the unit, including its bombs,
     * mines,
     * projectiles, and position, as well as other attributes such as energy and
     * shield retention.
     *
     * @return A cloned instance of the unit.
     * @throws CloneNotSupportedException If any part of the unit cannot be cloned.
     */
    @Override
    public Unit clone() throws CloneNotSupportedException {
        try {
            LinkedList<Bomb> clonedBombs = new LinkedList<>();
            for (Bomb bomb : bombs) {
                clonedBombs.add(bomb.clone());
            }

            LinkedList<Mine> clonedMines = new LinkedList<>();
            for (Mine mine : mines) {
                clonedMines.add(mine.clone());
            }

            LinkedList<Projectile> clonedProjectiles = new LinkedList<>();
            for (Projectile projectile : projectiles) {
                clonedProjectiles.add(projectile.clone());
            }

            Position clonedPosition = this.getPosition() != null ? new Position(this.getPosition()) : null;

            Unit clonedUnit = new Unit(clonedPosition, this.name, this.owner.clone(), this.energy,
                    clonedBombs, clonedMines, clonedProjectiles, this.shieldRetention);
            clonedUnit.setShieldTimer(this.shieldTimer);
            clonedUnit.setShieldActivated(this.shieldActivated);

            return clonedUnit;
        } catch (CloneNotSupportedException e) {
            throw new CloneNotSupportedException("Clone not supported");
        }
    }

    /**
     * Sets the bombs for this unit.
     * 
     * @param bombs A linked list of bombs to be assigned to the unit.
     */
    public void setBombs(LinkedList<Bomb> bombs) {
        this.bombs = bombs;
    }

    /**
     * Sets the mines for this unit.
     * 
     * @param mines A linked list of mines to be assigned to the unit.
     */
    public void setMines(LinkedList<Mine> mines) {
        this.mines = mines;
    }

    /**
     * Sets the projectiles for this unit.
     * 
     * @param projectiles A linked list of projectiles to be assigned to the unit.
     */
    public void setProjectiles(LinkedList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    /**
     * Sets the energy value of the unit.
     * 
     * @param energy The new energy value for the unit.
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Sets the shield retention value for the unit.
     * 
     * @param shieldRetention The new shield retention value.
     */
    public void setShieldRetention(int shieldRetention) {
        this.shieldRetention = shieldRetention;
    }

  
}
