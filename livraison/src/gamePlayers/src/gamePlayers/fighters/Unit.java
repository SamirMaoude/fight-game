package gamePlayers.fighters;

import java.util.LinkedList;

import gamePlayers.AbstractGameEntity;
import gamePlayers.objects.*;
import gamePlayers.util.*;

/**
 * Unit class for defining the base of any unit type.
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

    public Unit(Position position, String name, Player owner, int energy, LinkedList<Bomb> bombs,
            LinkedList<Mine> mines,
            LinkedList<Projectile> projectiles, int shieldRetention) {
        super(EntityType.UNIT, position, owner);
        if (energy <= 0) {
            throw new IllegalArgumentException("Unit energy must be higher than 0 during creation");
        }
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
     * @return unit name.
     */
    public String getName() {
        return this.name;
    }

    public void receiveDamage(int damage) {
        if (this.shieldActivated) {
            if(damage>= this.shieldRetention){
                this.energy -= damage;
                this.energy += this.shieldRetention;
            }
            
        } else {
            this.energy -= damage;
        }
    }

    public void setShieldActivated(boolean shieldActivated) {
        this.shieldActivated = shieldActivated;
    }

    public boolean getShieldActivated() {
        return this.shieldActivated;
    }

    public int getEnergy() {
        return energy;
    }

    /**
     * Updates the name of the unit.
     * 
     * It verifies that the name is not empty. Additionally, it capitalizes the
     * string.
     * 
     * @param name new name of the unit.
     */
    public void setName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("You cannot provide an empty string as unit name!");
        }

        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        this.name = name;
    }

    public LinkedList<Bomb> getBombs() {
        return bombs;
    }

    public LinkedList<Mine> getMines() {
        return mines;
    }

    public LinkedList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setShieldTimer(int time) {
        this.shieldTimer = time;
    }

    public int getShieldTimer() {
        return this.shieldTimer;
    }

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

    public boolean hasBombs() {
        return !(this.bombs == null || this.bombs.isEmpty());
    }

    public boolean hasMines() {
        return !(this.mines == null || this.mines.isEmpty());
    }

    public boolean hasProjectiles() {
        return !(this.projectiles == null || this.projectiles.isEmpty());
    }

    public void takePellet(Pellet pellet) {
        this.energy += pellet.getEnergy();
    }

    public Bomb useBomb() {
        if (this.hasBombs()) {
            return this.bombs.pop();
        }

        return null;
    }

    public Mine useMine() {
        if (this.hasMines()){
            return this.mines.pop();
        }

        return null;
    }

    public Projectile useProjectile() {
        if (this.hasProjectiles()){
            return this.projectiles.pop();
        }
        return null;
    }

    @Override
    public void destroy() {
        this.position = null;
        this.bombs = null;
        this.mines = null;
        this.projectiles = null;
    }

    public boolean isAlive() {
        return this.energy > 0;
    }

    @Override
    public Unit clone()throws CloneNotSupportedException{
        Unit clone = null;
        try {
            clone = (Unit) super.clone();
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        clone.name = this.name;
        clone.energy = this.energy;
        clone.bombs = (LinkedList<Bomb>) this.bombs.clone();
        clone.mines = (LinkedList<Mine>) this.mines.clone();
        clone.projectiles = (LinkedList<Projectile>) this.projectiles.clone();
        clone.position = (Position) this.position;
        clone.owner = (Player) this.owner.clone();
        clone.type = EntityType.UNIT;
        return clone;
    }

}
